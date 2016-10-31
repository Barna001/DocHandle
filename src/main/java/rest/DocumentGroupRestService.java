package rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import pojo.DocumentGroup;
import service.DocumentGroupService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by Barna on 2016.05.26..
 */
@Path("/documentGroups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocumentGroupRestService {
    private static DocumentGroupService service = new DocumentGroupService();
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GET
    @Path("/documentGroupById")
    public String getDocumentGroupById(@QueryParam("id") String id) throws IOException {
        Object documentGroup = service.getDocumentGroupById(id);
        return ow.writeValueAsString(documentGroup);
    }

    @GET
    @Path("/documentGroupByName")
    public String getDocumentGroupsByName(@QueryParam("name") String name) throws IOException {
        Object groups = service.getDocumentGroupByName(name);
        return ow.writeValueAsString(groups);
    }

    @GET
    @Path("/all")
    public String getAllDocumentGroups() throws IOException {
        Object groups = service.getAllDocumentGroups();
        return ow.writeValueAsString(groups);
    }

    @GET
    @Path("/allDocumentInGroup")
    public String getAllDocumentInGroup(@QueryParam("id") String id) throws IOException {
        DocumentGroup group = service.getDocumentGroupById(id);
        Object documents = group.getDocuments();
        return ow.writeValueAsString(documents);
    }

    @POST
    @Path("/new")
    public void addNewDocumentGroup(DocumentGroup dg) {
        service.addGroup(dg);
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    public void deleteDocGroup(@QueryParam("id") String docGroupId) {
        if (docGroupId.equals("*")) {
            service.deleteAll();
        } else {
        }
    }

    //If you call this before shutting down the server, you get less warning info because threads started but not stopped
    @POST
    @Path("/close")
    public void closeConnections() {
        service.closeAll();
    }
}
