package Resources;

import Entity.ServiceEntity;
import Entity.ServiceCategory;
import Services.ServiceCatalogService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/catalog")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServiceCatalogResource {

    @Inject
    private ServiceCatalogService service;


    @GET
    @Path("/services")
    public List<ServiceEntity> listServices() {
        return service.getAllServices();
    }

    @GET
    @Path("/services/{id}")
    public ServiceEntity getService(@PathParam("id") Long id) {
        ServiceEntity s = service.findServiceById(id);
        if (s == null) throw new NotFoundException("Service not found: " + id);
        return s;
    }

    @POST
    @Path("/services")
    public Response createService(ServiceEntity s) {
        ServiceEntity created = service.createService(s);
        return Response.created(URI.create("/catalog/services/" + created.getServiceId()))
                .entity(created)
                .build();
    }

    @PATCH
    @Path("/services/{id}")
    public ServiceEntity patchService(@PathParam("id") Long id, ServiceEntity patch) {
        ServiceEntity existing = service.findServiceById(id);
        if (existing == null) throw new NotFoundException("Service not found: " + id);

        if (patch.getName() != null) existing.setName(patch.getName());
        if (patch.getDurationMinutes() != 0) existing.setDurationMinutes(patch.getDurationMinutes());
        if (patch.getPrice() != null) {
            existing.setPrice(patch.getPrice());
        }        if (patch.getProviderId() != null) existing.setProviderId(patch.getProviderId());

        return service.updateService(existing);
    }

    @DELETE
    @Path("/services/{id}")
    public Response deleteService(@PathParam("id") Long id) {
        ServiceEntity existing = service.findServiceById(id);
        if (existing == null) throw new NotFoundException("Service not found: " + id);
        service.deleteService(existing);
        return Response.noContent().build();
    }


    @GET
    @Path("/categories")
    public List<ServiceCategory> listCategories() {
        return service.getAllCategories();
    }

    @GET
    @Path("/categories/{id}")
    public ServiceCategory getCategory(@PathParam("id") Long id) {
        ServiceCategory c = service.findCategoryById(id);
        if (c == null) throw new NotFoundException("Category not found: " + id);
        return c;
    }

    @POST
    @Path("/categories")
    public Response createCategory(ServiceCategory c) {
        ServiceCategory created = service.createCategory(c);
        return Response.created(URI.create("/catalog/categories/" + created.getCategoryId()))
                .entity(created)
                .build();
    }

    @PATCH
    @Path("/categories/{id}")
    public ServiceCategory patchCategory(@PathParam("id") Long id, ServiceCategory patch) {
        ServiceCategory existing = service.findCategoryById(id);
        if (existing == null) throw new NotFoundException("Category not found: " + id);

        if (patch.getName() != null) existing.setName(patch.getName());
        return service.updateCategory(existing);
    }

    @DELETE
    @Path("/categories/{id}")
    public Response deleteCategory(@PathParam("id") Long id) {
        ServiceCategory existing = service.findCategoryById(id);
        if (existing == null) throw new NotFoundException("Category not found: " + id);
        service.deleteCategory(existing);
        return Response.noContent().build();
    }
}
