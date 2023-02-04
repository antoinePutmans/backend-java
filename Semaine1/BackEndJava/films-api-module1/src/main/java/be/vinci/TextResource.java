package be.vinci;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

@Singleton
@Path("texts")
public class TextResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Text> getAll(
            @DefaultValue("none") @QueryParam("level") String level){
        var texts = Json.parse();
        if (level.equals("none")) return texts;
        List<Text> filteredLevelText = texts.stream()
                .filter(text -> text.getLevel().equalsIgnoreCase(level)).toList();
        return filteredLevelText;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Text getOne(@PathParam("id") int id){
        var texts = Json.parse();
        Text textFound = texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
        if (textFound==null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        }
        return textFound;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Text createOne(Text text){
        System.out.println(text);
        if (text == null || text.getContent() == null || text.getContent().isBlank()){
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info")
                            .type("text/plain").build());
            }
        var texts = Json.parse();
        text.setId(texts.size()+1);
        text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
        text.setLevel(StringEscapeUtils.escapeHtml4(text.getLevel()));
        texts.add(text);
        System.out.println(texts);
        Json.serialize(texts);
        return text;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Text deleteOne(@PathParam("id") int id) {
        if (id == 0) // default value of an integer => has not been initialized
        {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory id info")
                            .type("text/plain").build());
        }
        var texts = Json.parse();
        Text textToDelete = texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
        if (textToDelete == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        }
        texts.remove(textToDelete);
        Json.serialize(texts);
        return textToDelete;
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Text updateOne(Text text, @PathParam("id") int id) {
        if (id == 0 || text == null || text.getContent() == null || text.getContent().isBlank()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info")
                            .type("text/plain").build());
        }
        var texts = Json.parse();
        Text textToUpdate = texts.stream().filter(f -> f.getId() == id).findAny().orElse(null);
        if (textToUpdate == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("Ressource not found").type("text/plain").build());
        }
        text.setId(id);
        text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
        text.setLevel(StringEscapeUtils.escapeHtml4(text.getLevel()));
        texts.remove(text); // thanks to equals(), films is found via its id
        texts.add(text);
        Json.serialize(texts);
        return text;
    }
}
