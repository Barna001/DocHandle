package rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import pojo.Document;
import service.DocumentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by BB on 2016.05.22..
 */
@Path("/documents")
@Produces(MediaType.APPLICATION_JSON)
public class DocumentRestService {

    private static DocumentService service = new DocumentService();
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GET
    @Path("/docById")
    public String getDocById(@QueryParam("id") String id) throws IOException {
        Object doc = service.getDocumentById(id);
        return ow.writeValueAsString(doc);
    }

    @GET
    @Path("/docByName")
    public String getDocByName(@QueryParam("name") String name) throws IOException {
        Object docs = service.getDocumentByName(name);
        return ow.writeValueAsString(docs);
    }

    @GET
    @Path("/all")
    public String getAllDocs() throws IOException {
        Object docs = service.getAllDocuments();
        return ow.writeValueAsString(docs);
    }

    @POST
    @Path("/new")
    public void addNewDoc(@QueryParam("name") String name, @QueryParam("content") String content) {
        Document doc = new Document(name, content, null);
        service.addDocument(doc);
    }

    @POST
    @Path("/deleteAll")
    public void deleteAll() {
        service.deleteAll();
    }

    //If you call this before shutting down the server, you get less warning info because threads started but not stopped
    @POST
    @Path("/close")
    public void closeConnections() {
        service.closeAll();
    }

}
