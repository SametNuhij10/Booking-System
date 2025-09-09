package Resources;

import Entity.PaymentTransaction;
import Services.PaymentTransactionService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentTransactionResource {

    @Inject
    private PaymentTransactionService service;


    @GET
    public List<PaymentTransaction> list() {
        return service.findAll();
    }

    @GET
    @Path("/{id}")
    public PaymentTransaction get(@PathParam("id") Long id) {
        PaymentTransaction txn = service.findById(id);
        if (txn == null) throw new NotFoundException("Transaction not found: " + id);
        return txn;
    }


    @POST
    public Response create(@QueryParam("bookingId") Long bookingId,
                           @QueryParam("amount") double amount) {
        PaymentTransaction created = service.createTransaction(bookingId, amount);
        return Response.created(URI.create("/payments/" + created.getTxnId()))
                .entity(created)
                .build();
    }


    @PATCH
    @Path("/{id}/status")
    public PaymentTransaction updateStatus(@PathParam("id") Long id,
                                           @QueryParam("status") String newStatus) {
        PaymentTransaction updated = service.updateStatus(id, newStatus);
        if (updated == null) throw new NotFoundException("Transaction not found: " + id);
        return updated;
    }


    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
