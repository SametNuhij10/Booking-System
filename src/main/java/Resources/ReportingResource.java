package Resources;

import Entity.Booking;
import Entity.Cancellation;
import Entity.PaymentTransaction;
import Services.ReportingService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.List;

@Path("/reports")
@Produces(MediaType.APPLICATION_JSON)
public class ReportingResource {

    @Inject
    private ReportingService service;


    @GET
    @Path("/bookings/day/{date}")
    public List<Booking> bookingsByDay(@PathParam("date") String date) {
        return service.getBookingsByDay(LocalDate.parse(date));
    }

    @GET
    @Path("/bookings/provider/{providerId}")
    public List<Booking> bookingsByProvider(@PathParam("providerId") Long providerId) {
        return service.getBookingsByProvider(providerId);
    }


    @GET
    @Path("/cancellations")
    public List<Cancellation> allCancellations() {
        return service.getAllCancellations();
    }


    @GET
    @Path("/revenue/total")
    public Response totalRevenue() {
        double total = service.getTotalRevenue();
        return Response.ok("{\"totalRevenue\":" + total + "}").build();
    }

    @GET
    @Path("/payments/failed")
    public List<PaymentTransaction> failedTransactions() {
        return service.getFailedTransactions();
    }
}

