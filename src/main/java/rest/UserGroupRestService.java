package rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import pojo.UserGroup;
import service.UserGroupService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by BB on 2016.05.22..
 */
@Path("/userGroups")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserGroupRestService {

    private static UserGroupService service = new UserGroupService();
    private ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();

    @GET
    @Path("/userGroupById")
    public String getUserGroupById(@QueryParam("id") String id) throws IOException {
        Object userGroup = service.getUserGroupById(id);
        return ow.writeValueAsString(userGroup);
    }

    @GET
    @Path("/userGroupByName")
    public String getUserGroupsByName(@QueryParam("name") String name) throws IOException {
        Object groups = service.getUserGroupByName(name);
        return ow.writeValueAsString(groups);
    }

    @GET
    @Path("/all")
    public String getAllUserGroups() throws IOException {
        Object groups = service.getAllUserGroups();
        return ow.writeValueAsString(groups);
    }

    @GET
    @Path("/allUserInGroup")
    public String getAllUserInGroup(@QueryParam("id") String id) throws IOException {
        UserGroup group = service.getUserGroupById(id);
        Object users = group.getUsers();
        return ow.writeValueAsString(users);
    }

    @POST
    @Path("/new")
    public void addNewUserGroup(UserGroup group) {
        service.addGroup(group);
    }

    @POST
    @Path("/deleteAll")
    public void deleteAll(UserGroup group) {
        service.deleteAll();
    }

    //If you call this before shutting down the server, you get less warning info because threads started but not stopped
    @POST
    @Path("/close")
    public void closeConnections() {
        service.closeAll();
    }
}
