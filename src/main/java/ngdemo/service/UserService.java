package ngdemo.service;

import ngdemo.domain.User;

public class UserService {

    public String getDefaultUser() {
        User user = new User();
        user.setFirstName("JonFromREST");
        user.setLastName("DoeFromREST");
        return "{\"firstName\":\"Jon\",\"lastName\":\"Doe\"}";
    }
}
