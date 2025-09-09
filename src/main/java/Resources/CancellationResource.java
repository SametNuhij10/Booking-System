package Resources;

import Entity.Cancellation;
import Services.CancellationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/cancellations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CancellationResource {

    @Inject
    private CancellationService service;


    @GET
    public List<Cancellation> list() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public Cancellation get(@PathParam("id") Long id) {
        Cancellation c = service.findById(id);
        if (c == null) throw new NotFoundException("Cancellation not found: " + id);
        return c;
    }


    @POST
    public Response create(@QueryParam("bookingId") Long bookingId,
                           @QueryParam("reason") String reason,
                           @QueryParam("refunded") @DefaultValue("false") boolean refunded) {
        Cancellation created = service.createCancellation(bookingId, reason, refunded);
        return Response.created(URI.create("/cancellations/" + created.getCancelId()))
                .entity(created)
                .build();
    }


    @PATCH
    @Path("/{id}")
    public Cancellation update(@PathParam("id") Long id, Cancellation patch) {
        Cancellation existing = service.findById(id);
        if (existing == null) throw new NotFoundException("Cancellation not found: " + id);

        if (patch.getReason() != null) existing.setReason(patch.getReason());
        if (patch.getRefunded() != null) existing.setRefunded(patch.getRefunded());

        return service.updateCancellation(existing);
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.deleteCancellation(id);
        return Response.noContent().build();
    }
}
