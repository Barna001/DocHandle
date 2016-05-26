package rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import pojo.File;
import pojo.FileVersion;
import service.FileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by BB on 2016.05.22..
 */
@Path("/files")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FileRestService {

    private static FileService service = new FileService();
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GET
    @Path("/fileById")
    public String getFileById(@QueryParam("id") String id) throws IOException {
        Object file = service.getFileById(id);
        return ow.writeValueAsString(file);
    }

    @GET
    @Path("/fileByName")
    public String getFilesByName(@QueryParam("name") String name) throws IOException {
        Object files = service.getFilesByName(name);
        return ow.writeValueAsString(files);
    }

    //// TODO: 2016.05.24. kézzel össze kellene fűzni a json-t, és azt visszaadni, abba bele lehet tenni a rootdoc nevét és id-ját,
    //// egyenlőre csak ki van kapcsolva a jsonösítés a rootDocument változóra
    @GET
    @Path("/all")
    public String getAllFiles() throws IOException {
        Object files = service.getAllFiles();
        return ow.writeValueAsString(files);
    }

    @GET
    @Path("/latestVersion")
    public String getLatestVersion(@QueryParam("fileId") String fileId) throws IOException {
        Object version = service.getLatestVersion(fileId);
        return ow.writeValueAsString(version);
    }

    @GET
    @Path("/allVersions")
    public String getAllVersions(@QueryParam("fileId") String fileId) throws IOException {
        Object versions = service.getAllVersionsForFileId(fileId);
        return ow.writeValueAsString(versions);
    }

    @POST
    @Path("/new")
    public void addNewFile(File file) {
        service.addFile(file);
    }

    @POST
    @Path("/newWithVersion")
    public void addNewFileWithVersion(@QueryParam("name") String filename, FileVersion version) {
        File file = new File("filename", null);
        service.addFileWithVersion(file, version);
    }

    @POST
    @Path("/testAddNewVersion")
    public void addNewFileVersion() {
        try {
            //have to use a present file
            FileVersion fv = new FileVersion("5744356a817aefdee5b38fab", "Sárga bögre, görbe bögre, felakasztották a szögre".getBytes("UTF-8"));
            service.addVersionToFile(fv);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @POST
    @Path("/addNewVersion")
    public void addNewVersionToFile(FileVersion version) {
        service.addVersionToFile(version);
    }

    @POST
    @Path("/addNewVersionString/{str}")
    public void addNewVersionToFileString(FileVersion version, @PathParam("str") String str) {
        try {
            version.setData(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        service.addVersionToFile(version);
    }


    @POST
    @Path("/deleteAll")
    public void deleteAll(File f) {
        service.deleteAll();
    }

    //If you call this before shutting down the server, you get less warning info because threads started but not stopped
    @POST
    @Path("/close")
    public void closeConnections() {
        service.closeAll();
    }
}
