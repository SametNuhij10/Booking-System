package Resources;

import Entity.Booking;
import Services.BookingService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Path("/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookingResource {

    @Inject
    private BookingService bookingService;

    @POST
    public Response createBooking(Booking booking) {
        try {
            Booking created = bookingService.createBooking(booking);
            return Response.created(URI.create("/bookings/" + created.getBookingId()))
                    .entity(created)
                    .build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Invalid booking data"))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response cancelBooking(@PathParam("id") Long bookingId) {
        try {
            bookingService.cancelBooking(bookingId);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Booking not found"))
                    .build();
        }
    }

    @PUT
    @Path("/{id}/reschedule")
    public Response rescheduleBooking(@PathParam("id") Long bookingId,
                                      @QueryParam("date") String date,
                                      @QueryParam("startTime") String startTime,
                                      @QueryParam("endTime") String endTime) {
        if (date == null || startTime == null || endTime == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Date, startTime, and endTime parameters are required"))
                    .build();
        }

        try {
            Booking updated = bookingService.rescheduleBooking(
                    bookingId,
                    LocalDate.parse(date),
                    LocalTime.parse(startTime),
                    LocalTime.parse(endTime)
            );
            return Response.ok(updated).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Invalid date/time format. Use YYYY-MM-DD for date and HH:MM:SS for time"))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Booking not found"))
                    .build();
        } catch (IllegalStateException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(Map.of("error", e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/provider/{providerId}/date/{date}")
    public Response getBookingsForProviderOnDate(@PathParam("providerId") Long providerId,
                                                 @PathParam("date") String date) {
        try {
            List<Booking> bookings = bookingService.getBookingsForProviderOnDate(
                    providerId, LocalDate.parse(date));
            return Response.ok(bookings).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Invalid date format. Use YYYY-MM-DD"))
                    .build();
        }
    }

    @GET
    @Path("/availability")
    public Response checkAvailability(@QueryParam("providerId") Long providerId,
                                      @QueryParam("date") String date,
                                      @QueryParam("startTime") String startTime,
                                      @QueryParam("endTime") String endTime) {
        if (providerId == null || date == null || startTime == null || endTime == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "All parameters (providerId, date, startTime, endTime) are required"))
                    .build();
        }

        try {
            boolean available = bookingService.isSlotAvailable(
                    providerId,
                    LocalDate.parse(date),
                    LocalTime.parse(startTime),
                    LocalTime.parse(endTime)
            );
            return Response.ok(Map.of("available", available)).build();
        } catch (DateTimeParseException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "Invalid date/time format. Use YYYY-MM-DD for date and HH:MM:SS for time"))
                    .build();
        }
    }
}