package be.vinci.api;

import be.vinci.domain.Page;
import be.vinci.services.PageDataService;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Singleton
@Path("/pages")
public class PageResource {

    private PageDataService myPageDataService = new PageDataService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Page> getPages(){
        return myPageDataService
    }
}
