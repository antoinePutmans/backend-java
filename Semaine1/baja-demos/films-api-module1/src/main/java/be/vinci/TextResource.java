package be.vinci;

import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
                .filter(text -> text.getLevel().getLowerCase().equalsIgnoreCase(level)).toList();
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

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Text createOne(Text text){
//        if (text == null || text.getContent() == null || text.getContent().isBlank()){
//            throw new WebApplicationException(
//                    Response.status(Response.Status.BAD_REQUEST).entity("Lacks of mandatory info")
//                            .type("text/plain").build());
//            }
//        var texts
//    }
}
