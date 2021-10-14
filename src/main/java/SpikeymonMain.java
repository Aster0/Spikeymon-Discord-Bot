
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import eventlisteners.CommandListener;
import eventlisteners.OnReadyEvent;
import eventlisteners.VerificationListener;
import json.JsonManager;
import management.SpikeyManagement;
import management.UserManagement;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.utils.Compression;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.*;
import java.math.BigInteger;
import java.util.Calendar;

public class SpikeymonMain {

    private static SpikeyManagement spikeyManagement;


    public static void main(String[] args) throws LoginException {


        try {
            setup();
        } catch (IOException | IllegalStateException e) {

        }

        JDABuilder builder = JDABuilder.createDefault(spikeyManagement.getToken());

        // Disable parts of the cache
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        // Enable the bulk delete event
        builder.setBulkDeleteSplittingEnabled(false);
        // Disable compression (not recommended)
        builder.setCompression(Compression.NONE);
        // Set activity (like "playing Something")
        builder.setActivity(Activity.watching(spikeyManagement.getStatusMessage()));

        builder.addEventListeners(new OnReadyEvent());
        builder.addEventListeners(new VerificationListener());
        builder.addEventListeners(new CommandListener());

        builder.build();










    }

    private static void setup() throws IOException {

        File file = new File("./details.json");


        InputStream is = new FileInputStream(file);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;

        StringBuilder sb = new StringBuilder();

        while((line = reader.readLine()) != null)
        {
            sb.append(line);
        }

        reader.close();




        JsonObject jsonObject = JsonManager.get(sb.toString());




        spikeyManagement = SpikeyManagement.getInstance();

        spikeyManagement.setToken(jsonObject.get("token").getAsString());
        spikeyManagement.setStatusMessage(jsonObject.get("status_message").getAsString());


        JsonObject channelsObject = JsonManager.get(jsonObject.get("channels").toString());


        spikeyManagement.getGuildChannels().setVerificationChannel(
                channelsObject.get("verification_normal_id").getAsLong());

        spikeyManagement.getGuildChannels().setVerificationManagementChannel(
                channelsObject.get("verification_management_id").getAsLong());


        JsonObject colorDetails = JsonManager.get(jsonObject.get("colors").toString());

        spikeyManagement.setPrimaryColor(new Color(


                colorDetails.get("primary_color").getAsInt())
        );

        JsonObject roleDetails = JsonManager.get(jsonObject.get("roles").toString());

        spikeyManagement.getGuildRoles().setVerifiedRole(roleDetails.get("verified_role").getAsLong());
        spikeyManagement.getGuildRoles().setYear1Role(roleDetails.get("year_1_role").getAsLong());
        spikeyManagement.getGuildRoles().setYear2Role(roleDetails.get("year_2_role").getAsLong());
        spikeyManagement.getGuildRoles().setYear3Role(roleDetails.get("year_3_role").getAsLong());


        file = new File("./users.json");


        is = new FileInputStream(file);

        reader = new BufferedReader(new InputStreamReader(is));

        line = "";

        sb = new StringBuilder();

        while((line = reader.readLine()) != null)
        {
            sb.append(line);
        }


        JsonArray userArray = JsonManager.get(sb.toString()).getAsJsonArray("users");


        UserManagement.getInstance().setUsers(userArray);
        SpikeyManagement.sortUsers(userArray);

        System.out.println(UserManagement.getInstance().getUsers() + " USERS");

        reader.close();


        System.out.println("20043".substring(0, 2));

    }



}
