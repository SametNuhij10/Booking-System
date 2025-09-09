package Resources;

import Entity.Customer;
import Services.CustomerService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    private CustomerService service;

    @GET
    public List<Customer> list() {
        return service.getAllCustomers();
    }

    @GET
    @Path("/{id}")
    public Customer get(@PathParam("id") Long id) {
        Customer c = service.findCustomerById(id);
        if (c == null) throw new NotFoundException("Customer not found: " + id);
        return c;
    }

    @POST
    public Response create(Customer c) {
        Customer created = service.createCustomer(c);
        return Response.created(URI.create("/customers/" + created.getCustomerId()))
                .entity(created)
                .build();
    }

    @PATCH
    @Path("/{id}")
    public Customer patch(@PathParam("id") Long id, Customer patch) {
        Customer existing = service.findCustomerById(id);
        if (existing == null) throw new NotFoundException("Customer not found: " + id);

        if (patch.getName()  != null) existing.setName(patch.getName());
        if (patch.getEmail() != null) existing.setEmail(patch.getEmail());
        if (patch.getPhone() != null) existing.setPhone(patch.getPhone());

        return service.updateCustomer(existing);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        Customer existing = service.findCustomerById(id);
        if (existing == null) throw new NotFoundException("Customer not found: " + id);
        service.deleteCustomer(existing);
        return Response.noContent().build();
    }
}
