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
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentRestService {

    private static DocumentService service = new DocumentService();
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GET
    @Path("/{id}")
    public String getDocById(@PathParam("id") String id) throws IOException {
        Object doc = service.getDocumentById(id);
        return ow.writeValueAsString(doc);
    }

    @GET
    public String getDocByName(@QueryParam("name") String name) throws IOException {
        if (name != null) {
            Object docs = service.getDocumentByName(name);
            return ow.writeValueAsString(docs);
        } else {
            Object docs = service.getAllDocuments();
            return ow.writeValueAsString(docs);
        }
    }

    @POST
    public void addNewDoc(Document document) {
        service.addDocument(document);
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    public void delete(@QueryParam("id") String id) {
        service.deleteDocumentById(id);
    }

    //If you call this before shutting down the server, you get less warning info because threads started but not stopped
    @POST
    @Path("/close")
    public void closeConnections() {
        service.closeAll();
    }

}
