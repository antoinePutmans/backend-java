package be.vinci.api;

import be.vinci.api.filters.Authorize;
import be.vinci.domain.Page;
import be.vinci.services.PageDataService;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.server.ContainerRequest;

import java.util.List;

@Singleton
@Path("/pages")
public class PageResource {

    private PageDataService myPageDataService = new PageDataService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public List<Page> getPages(){
        return myPageDataService.getPages();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Page createPage(Page page, @Context ContainerRequest request){
        if ( page == null || page.getStatus().isBlank()){
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        }
        return myPageDataService.createOne(page);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Page deleteOne(@PathParam("id") int id) {
        if (id == 0) throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                .type("text/plain").build());
        Page deletedPage = myPageDataService.deleteOne(id);
        if (deletedPage == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return deletedPage;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Page updateOne(Page page, @PathParam("id") int id){
        if (page == null || page.getStatus().isBlank())
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                    .type("text/plain").build());
        Page updatedPage = myPageDataService.updateOne(page,id);
        if (updatedPage == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return updatedPage;
    }
}
