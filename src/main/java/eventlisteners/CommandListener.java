package eventlisteners;

import data.GDDUser;
import management.EmbedManager;
import management.SpikeyManagement;
import management.UserManagement;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class CommandListener extends ListenerAdapter {

    public void onSlashCommand(SlashCommandEvent event)
    {



        try
        {
            try
            {
                User user = event.getOptions().get(0).getAsUser();
                UserManagement userManagement = UserManagement.getInstance();

                GDDUser targetedGDDUser = userManagement.getUserList().get(user.getIdLong());

                System.out.println(user.getIdLong());

                MessageEmbed messageEmbed = new EmbedManager().build(" Viewing Profile of " + user.getAsTag(),

                        user.getAvatarUrl(),

                        new String[]{"User Details: ",

                                "\nFull Name: " + targetedGDDUser.getData().get(0) +
                                        "\nClass: " + targetedGDDUser.getData().get(1) +
                                        "\nSchool Email: " + targetedGDDUser.getData().get(2)     +
                                        "\nYear: " + targetedGDDUser.getYear()});





                event.reply("Fetching Data...").queueAfter(1, TimeUnit.SECONDS, message -> {


                    event.getChannel().sendMessage(messageEmbed).queue();
                    message.deleteOriginal().queue();
                });
            }
            catch(IndexOutOfBoundsException e)
            {
                event.reply("Invalid Usage. Proper Usage: `/profile profile:@tag`").queue();
            }







        }
        catch (NullPointerException e)
        {
            event.reply("User Profile not found.").queue();


        }




    }
}
