package rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import pojo.User;
import pojo.UserRoleEnum;
import service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserRestService {

    private static UserService service = new UserService();
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GET
    @Path("/defaultUser")
    public String getDefaultUserInJSON() throws IOException {
        Object user = service.getDefaultUser();
        return ow.writeValueAsString(user);
    }

    @GET
    @Path("/userById")
    public String getUserById(@QueryParam("id") String id) throws IOException {
        Object user = service.getUserById(id);
        return ow.writeValueAsString(user);
    }

    @GET
    @Path("/userByName")
    public String getUserByName(@QueryParam("name") String name) throws IOException {
        Object user = service.getUserByName(name);
        return ow.writeValueAsString(user);
    }

    @GET
    @Path("/all")
    public String getAllUsers() throws IOException {
        Object users = service.getAllUsers();
        return ow.writeValueAsString(users);
    }

    @GET
    @Path("/usersGroups")
    public String getUsersGroups(@QueryParam("id") String userId) throws IOException {
        User user = service.getUserById(userId);
        Object groups = user.getGroups();
        return ow.writeValueAsString(groups);
    }

    @GET
    @Path("/usersOwnDocuments")
    public String getUsersDocuments(@QueryParam("id") String userId) throws IOException {
        User user = service.getUserById(userId);
        Object documents = user.getOwnDocuments();
        return ow.writeValueAsString(documents);
    }

    @GET
    @Path("/roles")
    public String getRoles() throws IOException {
        Object[] roles = UserRoleEnum.values();
        return ow.writeValueAsString(roles);
    }

    @POST
    @Path("/new")
    public void addNewUser(User user) {
        service.addUser(user);
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
