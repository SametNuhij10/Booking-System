package Resources;

import Entity.Provider;
import Entity.Availability;
import Services.ProviderService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Path("/providers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProviderResource {

    @Inject
    private ProviderService service;


    @GET
    public List<Provider> list() {
        return service.getAllProviders();
    }

    @GET
    @Path("/{id}")
    public Provider get(@PathParam("id") Long id) {
        Provider p = service.findProviderById(id);
        if (p == null) throw new NotFoundException("Provider not found: " + id);
        return p;
    }

    @POST
    public Response create(Provider p) {
        Provider created = service.createProvider(p);
        return Response.created(URI.create("/providers/" + created.getProviderId()))
                .entity(created)
                .build();
    }

    @PATCH
    @Path("/{id}")
    public Provider patch(@PathParam("id") Long id, Provider patch) {
        Provider existing = service.findProviderById(id);
        if (existing == null) throw new NotFoundException("Provider not found: " + id);

        if (patch.getName()  != null) existing.setName(patch.getName());
        if (patch.getEmail() != null) existing.setEmail(patch.getEmail());

        return service.updateProvider(existing);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Provider existing = service.findProviderById(id);
        if (existing == null) throw new NotFoundException("Provider not found: " + id);
        service.deleteProvider(existing);
        return Response.noContent().build();
    }


    @GET
    @Path("/availability")
    public List<Availability> listAvailabilities() {
        return service.getAvailabilities();
    }

    @POST
    @Path("/{id}/availability")
    public Response addAvailability(@PathParam("id") Long providerId,
                                    @QueryParam("date") String date,
                                    @QueryParam("start") String start,
                                    @QueryParam("end") String end) {

        Availability created = service.addAvailability(
                providerId,
                LocalDate.parse(date),
                LocalTime.parse(start),
                LocalTime.parse(end)
        );

        return Response.created(URI.create("/providers/" + providerId + "/availability/" + created.getAvailabilityId()))
                .entity(created)
                .build();
    }

    @POST
    @Path("/{id}/availability/weekly")
    public Response addWeeklyAvailability(@PathParam("id") Long providerId,
                                          @QueryParam("dayOfWeek") String dayOfWeek,
                                          @QueryParam("from") String from,
                                          @QueryParam("to") String to,
                                          @QueryParam("start") String start,
                                          @QueryParam("end") String end) {

        service.addWeeklyAvailability(
                providerId,
                DayOfWeek.valueOf(dayOfWeek.toUpperCase()),
                LocalDate.parse(from),
                LocalDate.parse(to),
                LocalTime.parse(start),
                LocalTime.parse(end)
        );

        return Response.ok().build();
    }
}
