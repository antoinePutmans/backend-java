package be.vinci.pae.api;

import java.util.List;
import java.util.stream.Collectors;

import be.vinci.Json;
import be.vinci.Text;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.apache.commons.text.StringEscapeUtils;

@Singleton
@Path("/texts")
public class TextResource {

  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Text createOne(Text text) {
    if (text == null || text.getContent() == null || text.getLevel() == null
        // if level is unauthorized, level = null
        || text.getContent().isBlank()) {
      throw new WebApplicationException(
          Response.status(Status.BAD_REQUEST)
              .entity("Lacks of mandatory info or unauthorized text level").type("text/plain")
              .build());
    }

    var texts = Json.parse();
    text.setId(texts.size()
        + 1); // we will improve later the id (issue if we delete items, we could get doublons within ids)
    text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));

    texts.add(text);
    Json.serialize(texts);
    return text;
  }

  @GET
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Text getText(@PathParam("id") int id) {
    var texts = Json.parse();
    Text textFound = texts.stream().filter(text -> text.getId() == id).findAny().orElse(null);
    if (textFound == null) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
          .entity("Ressource not found").type("text/plain").build());
    }
    return textFound;

  }

  @PUT
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public Text updateOne(Text text, @PathParam("id") int id) {

    if (text == null || text.getContent() == null || text.getContent().isBlank()
        || text.getLevel() == null) {
      throw new WebApplicationException(
          Response.status(Status.BAD_REQUEST)
              .entity("Lacks of mandatory info or unauthorized text level").type("text/plain")
              .build());
    }

    var texts = Json.parse();
    Text textFound = texts.stream().filter(t -> t.getId() == id).findAny().orElse(null);
    if (textFound == null) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
          .entity("Ressource not found").type("text/plain").build());
    }

    //text.setId(id);

    text.setId(texts.size()
        + 1); // we will improve later the id (issue if we delete items, we could get doublons within ids)
    text.setContent(StringEscapeUtils.escapeHtml4(text.getContent()));
    texts.remove(text);
    texts.add(text);
    Json.serialize(texts);
    return text;
  }

  @DELETE
  @Path("/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Text deleteText(@PathParam("id") int id) {
    if (id == 0) {
      throw new WebApplicationException(
          Response.status(Status.BAD_REQUEST).entity("Lacks of mandatory id info")
              .type("text/plain").build());
    }

    var texts = Json.parse();
    Text textFound = texts.stream().filter(t -> t.getId() == id).findAny().orElse(null);
    if (textFound == null) {
      throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
          .entity("Ressource not found").type("text/plain").build());
    }

    texts.remove(textFound);
    Json.serialize(texts);
    return textFound;
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<Text> getAllTexts(@DefaultValue("") @QueryParam("level") String level) {
    var texts = Json.parse();
    if (level.isBlank()) {
      return texts;
    }
    return texts.stream().filter(item -> item.getLevel().contentEquals(level))
        .collect(Collectors.toList());
  }


}
