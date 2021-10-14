package management;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import data.GDDUser;

import java.util.HashMap;
import java.util.Map;

public class UserManagement {

    private static UserManagement instance = null;


    public static UserManagement getInstance()
    {
        if(instance == null)
        {
            instance = new UserManagement();

        }

        return instance;
    }

    public JsonArray getUsers() {
        return users;
    }

    public void setUsers(JsonArray users) {
        this.users = users;
    }

    private JsonArray users;

    public Map<Long, GDDUser> getUserList() {
        return userList;
    }

    private Map<Long, GDDUser> userList = new HashMap<>();
}
