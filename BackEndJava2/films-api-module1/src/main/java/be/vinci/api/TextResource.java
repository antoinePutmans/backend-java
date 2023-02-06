package be.vinci.api;

import be.vinci.api.filters.Authorize;
import be.vinci.domain.Text;
import be.vinci.domain.User;
import be.vinci.services.Json;
import be.vinci.services.TextDataService;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;
import org.glassfish.jersey.server.ContainerRequest;

import java.util.List;

@Singleton
@Path("texts")
public class    TextResource {

    private TextDataService myTextDataService = new TextDataService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Text> getAll(@DefaultValue("none") @QueryParam("level") String level) {
        return myTextDataService.getAll(level);
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Text getOne(@PathParam("id") int id) {
        Text textFound = myTextDataService.getOne(id);
        if (textFound == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return textFound;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Text createOne(Text text, @Context ContainerRequest request) {
        User authenticatedUser = (User) request.getProperty("user");
        System.out.println("A new film is added by " + authenticatedUser.getLogin() );
        if (text == null || text.getContent() == null || text.getContent().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        return myTextDataService.createOne(text);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Authorize
    public Text deleteOne(@PathParam("id") int id) {
        if (id == 0) // default value of an integer => has not been initialized
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                    .type("text/plain").build());
        Text deletedText = myTextDataService.deleteOne(id);
        if (deletedText == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return deletedText;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorize
    public Text updateOne(Text text, @PathParam("id") int id) {
        if (id == 0 || text == null || text.getContent() == null || text.getContent().isBlank())
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info").type("text/plain").build());
        Text updatedFilm = myTextDataService.updateOne(text, id);
        if (updatedFilm == null)
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        return updatedFilm;
    }
}
