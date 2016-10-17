package rest;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.server.filter.HttpMethodOverrideFilter;
import pojo.File;
import pojo.FileVersion;
import service.FileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by BB on 2016.05.22..
 */
@Path("/files")
public class FileRestService {

    private static FileService service = new FileService();
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GET
    @Path("/fileById")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getFileById(@QueryParam("id") String id) throws IOException {
        Object file = service.getFileById(id);
        return ow.writeValueAsString(file);
    }

    @GET
    @Path("/fileByName")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getFilesByName(@QueryParam("name") String name) throws IOException {
        Object files = service.getFilesByName(name);
        return ow.writeValueAsString(files);
    }

    //// TODO: 2016.05.24. kézzel össze kellene fűzni a json-t, és azt visszaadni, abba bele lehet tenni a rootdoc nevét és id-ját,
    //// egyenlőre csak ki van kapcsolva a jsonösítés a rootDocument változóra
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getAllFiles() throws IOException {
        Object files = service.getAllFiles();
        return ow.writeValueAsString(files);
    }

    @GET
    @Path("/latestVersion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getLatestVersion(@QueryParam("fileId") String fileId) throws IOException {
        Object version = service.getLatestVersion(fileId);
        return ow.writeValueAsString(version);
    }

    @GET
    @Path("/allVersions")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getAllVersions(@QueryParam("fileId") String fileId) throws IOException {
        Object versions = service.getAllVersionsForFileId(fileId);
        return ow.writeValueAsString(versions);
    }

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewFile(File file) {
        service.addFile(file);
    }

    @POST
    @Path("/newWithVersion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewFileWithVersion(@QueryParam("name") String filename, FileVersion version) {
        File file = new File("filename", null);
        service.addFileWithVersion(file, version);
    }

    @POST
    @Path("/addNewVersion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewVersionToFile(FileVersion version) {
        service.addVersionToFile(version);
    }

    @POST
    @Path("/addNewVersionString/{str}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewVersionToFileString(FileVersion version, @PathParam("str") String str) {
        try {
            version.setData(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        service.addVersionToFile(version);
    }

    @POST
    @Path("/addNewVersionFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addNewVersionToFileFile(@FormDataParam("file") InputStream fileData, @QueryParam("fileId") String rootId) {
        FileVersion version = new FileVersion(rootId, null);
        try {
            byte[] byteData = IOUtils.toByteArray(fileData);
            version.setData(byteData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.addVersionToFile(version);
    }

    @DELETE
    @Path("/deleteAll")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public void deleteAll() {
        service.deleteAll();
    }

    //If you call this before shutting down the server, you get less warning info because threads started but not stopped
    @POST
    @Path("/close")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void closeConnections() {
        service.closeAll();
    }
}
