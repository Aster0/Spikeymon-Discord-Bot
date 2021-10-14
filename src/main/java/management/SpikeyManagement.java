package management;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import data.GDDUser;
import data.session.VerificationSession;
import json.JsonManager;
import management.data.GuildChannels;
import management.data.GuildRoles;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SpikeyManagement {

    private static SpikeyManagement instance = null;


    public static SpikeyManagement getInstance()
    {
        if(instance == null)
        {
            instance = new SpikeyManagement();

            instance.guildChannels = new GuildChannels();
            instance.guildRoles = new GuildRoles();
        }

        return instance;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    private String statusMessage;


    public GuildChannels getGuildChannels() {
        return guildChannels;
    }

    private GuildChannels guildChannels;

    public GuildRoles getGuildRoles() {
        return guildRoles;
    }

    public void setGuildRoles(GuildRoles guildRoles) {
        this.guildRoles = guildRoles;
    }

    private GuildRoles guildRoles;

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }


    public Color getPrimaryColor() {
       return primaryColor;
    }
    private Color primaryColor;

    public Map<User, VerificationSession> getVerificationSessions() {
        return verificationSessions;
    }

    private Map<User, VerificationSession> verificationSessions = new HashMap<>();




    public String[] verificationQuestions = {"Second question: What's your class?",
            "Last question: What's your school email?"};



    public static void sortUsers(JsonArray array)
    {

        for(int i = 0; i < array.size(); i++)
        {
            JsonObject object = JsonManager.get(array.get(i).toString());
            System.out.println(object);


            saveUserData(object.get("full_name").getAsString(),
                    object.get("class").getAsString(), object.get("school_email").getAsString(),
                    object.get("user_id").getAsString(),
                    object.get("year").getAsString());


        }

    }

    public static void saveUserData(String ...details)
    {


        if(details.length != 5)
        {
            return;
        }

        UserManagement userManagement = UserManagement.getInstance();

        GDDUser user = new GDDUser();
        user.getData().add(details[0]);
        user.getData().add(details[1]);
        user.getData().add(details[2]);
        user.setYear(Integer.valueOf(details[4]));

        userManagement.getUserList().put(Long.valueOf(details[3]),
                user
        );


        System.out.println(userManagement.getUserList() + " DAATA");
    }
}
