package rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import pojo.File;
import pojo.FileVersion;
import service.FileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

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

    //todo valahogy meg kellene oldani, hogy a fájlnak a rootdoksijának az id-ját megkapjuk,
    //todo a többi részét ne(ha a fájlok rész is benne van, végtelen körkörös hivatkozás lesz :/
    //todo első körben lehetne egy ilyen endpoint is, ami a fájl id-jához lekéri a doksit, csak ez minden fájlhoz meghívódik majd
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
    @Path("/addNewVersion")
    public void addNewVersionToFile(@QueryParam("fileId") String fileId, FileVersion version) {
        service.addVersionToFile(fileId, version);
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
