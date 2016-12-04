package rest;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import pojo.File;
import pojo.FileVersion;
import service.FileService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.FileOutputStream;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getAllFiles() throws IOException {
        Object files = service.getAllFiles();
        return ow.writeValueAsString(files);
    }

    @GET
    @Path("/latestVersion")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes(MediaType.APPLICATION_JSON)
    public byte[] getLatestVersion(@QueryParam("fileId") String fileId) throws IOException {
        FileVersion version = service.getLatestVersion(fileId);
        byte[] data = version.getData();
//        java.io.File file = new java.io.File("d:/Munka/proba2.docx");
//        if (!file.exists()) {
//            file.createNewFile();
//        }
//        FileOutputStream fos = new FileOutputStream(file);
//        fos.write(data);
//        fos.flush();
//        fos.close();
//        return new String(data,"ISO-8859-1");
        return data;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void addNewFile(File file) {
        file.setRootDocumentName(file.getRootDocument().getName());
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
    @Path("/addNewVersionFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addNewVersionToFileFile(@FormDataParam("file") InputStream fileData, @FormDataParam("file") FormDataContentDisposition fileDetails, @QueryParam("fileId") String rootId) throws IOException {
        FileVersion version = new FileVersion(rootId, null);
        try {
            byte[] byteData = IOUtils.toByteArray(fileData);
            version.setData(byteData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        service.addVersionToFile(version);
        fileData.close();
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    public void delete(@QueryParam("id") String id) {
        if (id.equals("*")) {
            service.deleteAll();
        } else {
            service.delete(id);
        }
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
