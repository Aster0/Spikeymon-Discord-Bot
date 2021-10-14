package eventlisteners;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import data.GDDUser;
import data.session.VerificationSession;
import json.JsonManager;
import management.EmbedManager;
import management.SpikeyManagement;
import management.UserManagement;
import management.Validator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.exceptions.ContextException;
import net.dv8tion.jda.api.exceptions.ErrorHandler;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.Button;
import net.dv8tion.jda.api.requests.ErrorResponse;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class VerificationListener extends ListenerAdapter {




    private UserManagement userManagement;

    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
    {

        if(!event.getAuthor().isBot())
        {
            System.out.println(event.getMessage());

            VerificationSession session = SpikeyManagement
                    .getInstance().getVerificationSessions().get(event.getAuthor());

            if(session != null)
            {


                if(session.getGddUser() == null)
                {
                    session.setGddUser(new GDDUser(event.getAuthor()));
                }

                GDDUser gddUser = session.getGddUser();




                if(gddUser.getData().size() <
                        SpikeyManagement.getInstance().verificationQuestions.length + 1)
                {

                    String currentQuestion = "";

                    try
                    {
                        currentQuestion = SpikeyManagement.getInstance().verificationQuestions[gddUser.getData().size() - 1];

                    }
                    catch (ArrayIndexOutOfBoundsException e) {}

                    if(currentQuestion.contains("school email"))
                    {
                        boolean valid = Validator.isValidEmail(event.getMessage().getContentRaw());

                        if(!valid)
                        {
                            event.getChannel().sendMessage("Invalid School Email, make sure to have `student.tp.edu.sg` at the back.").queue();
                            return;
                        }
                        else
                        {
                            Calendar cal = Calendar.getInstance();

                            System.out.println(cal.getTime());

                            int dateNow = Integer.valueOf(cal.getTime().toString().split(" ")[5].substring(2));

                            try
                            {
                                int emailDate = Integer.valueOf(event.getMessage().getContentRaw().substring(0, 2));


                                int year = (dateNow - emailDate) + 1;
                                gddUser.setYear(year);
                            }
                            catch(NumberFormatException e)
                            {
                                event.getChannel().sendMessage("Invalid School Email!").queue();

                                return;
                            }




                        }

                    }

                    gddUser.getData().add(event.getMessage().getContentRaw());

                    String response;

                    try
                    {
                        response =
                                SpikeyManagement.getInstance().verificationQuestions[gddUser.getData().size() - 1];


                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        response = "Your verification request" +
                                " has been sent to the GDD SIG members. Please wait to be verified by them.";


                        MessageEmbed messageEmbed = new EmbedManager().build("<:gddmon:898229885481418782>" +
                                " New Verification Request",

                                new String[]{"User Details: ",
                                        "Discord Name: " + event.getAuthor().getAsTag() +
                                                "\nFull Name: " + gddUser.getData().get(0) +
                                                "\nClass: " + gddUser.getData().get(1) +
                                                "\nSchool Email: " + gddUser.getData().get(2) +
                                                "\nYear: " + gddUser.getYear()});



                        event.getJDA()
                                .getTextChannelById(SpikeyManagement.getInstance()
                                        .getGuildChannels()
                                        .getVerificationManagementChannel()).sendMessage(messageEmbed)
                                .setActionRow(
                                        Button.success("verification_authenticate", "\uD83D\uDCDD AUTHENTICATE USER"),
                                        Button.danger("verification_reject", "REJECT")).queue(

                                                message -> {

                                                    session.setRequestId(message.getIdLong());


                                                }
                        );







                    }





                    event.getChannel().sendMessage(response).queue();


                    System.out.println(response);




                }

                System.out.println(session.getGddUser() + " USER");
            }
        }

    }


    public void onButtonClick(ButtonClickEvent event) {

        System.out.println(event.getComponentId());
        if (event.getComponentId().equals("authenticate_button")) {

            if(SpikeyManagement.getInstance().getVerificationSessions().get(event.getUser()) != null)
            {
                event.reply("Hi, " + event.getUser().getAsMention() + "!\nYou have already requested a verification request. Please check your private messages.").queue(

                        (message) -> {

                            message.deleteOriginal().queueAfter(2, TimeUnit.SECONDS);


                        }
                );
                return;
            }



            event.getUser().openPrivateChannel().queue(privateChannel -> {

                ErrorHandler handler = new ErrorHandler().handle(ErrorResponse.CANNOT_SEND_TO_USER, (error) -> {
                    event.reply("Hi, " + event.getUser().getAsMention() + "!\nI can't seem to private message you! Please enable private messages.").queue(

                            (message) -> {

                                message.deleteOriginal().queueAfter(2, TimeUnit.SECONDS);


                            }
                    );
                });



                privateChannel.sendMessage("Let's get you started." +
                        " \n\nFirst question: What's your name?").queue(message -> {

                    SpikeyManagement.getInstance().getVerificationSessions()
                            .put(event.getUser(), new VerificationSession());

                    event.reply("Hi, " + event.getUser().getAsMention() + "!\nI have sent you a private message, please check.").queue(

                            (message1) -> {

                                message1.deleteOriginal().queueAfter(2, TimeUnit.SECONDS);


                            }
                    );
                }, handler);





            });
        }
        else if (event.getComponentId().equals("verification_authenticate")) {

            VerificationSession obtainedSession =
                    getVerificationSession(event, true);


        }
        else if (event.getComponentId().equals("verification_reject")) {


            VerificationSession obtainedSession =
                    getVerificationSession(event, false);

            obtainedSession.getGddUser().getUser().openPrivateChannel().queue(

                    privateChannel -> {

                        privateChannel.sendMessage("You have been rejected!").queue();
                    }
            );

            event.getMessage().delete().queue();
        }

    }



    private VerificationSession getVerificationSession(ButtonClickEvent event, boolean authenticate)
    {
        VerificationSession obtainedSession = null;

        for(VerificationSession session : SpikeyManagement.getInstance().getVerificationSessions().values())
        {


            if(session.getRequestId() == event.getMessageIdLong())
            {
                obtainedSession = session;
                break;
            }
        }

        if(obtainedSession != null && authenticate)
        {
            event.getGuild().addRoleToMember(obtainedSession.getGddUser().getUser().getIdLong(),

                    event.getJDA().getRoleById(SpikeyManagement.getInstance().getGuildRoles().getVerifiedRole())).queue();


            long role = SpikeyManagement.getInstance().getGuildRoles().getYear1Role();


            if(obtainedSession.getGddUser().getYear() == 2)
                role = SpikeyManagement.getInstance().getGuildRoles().getYear2Role();
            else if(obtainedSession.getGddUser().getYear() == 3)
                role = SpikeyManagement.getInstance().getGuildRoles().getYear3Role();



            event.getGuild().addRoleToMember(obtainedSession.getGddUser().getUser().getIdLong(),

                    event.getJDA().getRoleById(role)).queue();

            SpikeyManagement.getInstance().getVerificationSessions().remove(obtainedSession);

            obtainedSession.getGddUser().getUser().openPrivateChannel().queue(

                    privateChannel -> {

                        privateChannel.sendMessage("You have been verified!").queue();
                    }
            );


            event.reply("Action is a success.").queue(

                    message -> {
                        message.deleteOriginal().queueAfter(2, TimeUnit.SECONDS);
                    }
            );


            String newJson =  "{" +
                    "\"user_id\" : " + obtainedSession.getGddUser().getUser().getIdLong() +
                    ", \"full_name\" : \"" + obtainedSession.getGddUser().getData().get(0).toUpperCase() +
                    "\", \"class\" : \"" + obtainedSession.getGddUser().getData().get(1).toUpperCase() +
                    "\", \"school_email\" : \"" + obtainedSession.getGddUser().getData().get(2) +
                    "\", \"year\" : " + obtainedSession.getGddUser().getYear() +
                    "}";


            String separator = "";

            try
            {
                if(UserManagement.getInstance().getUsers().size() > 0)
                {
                    separator = ",";
                }
            }
            catch(NullPointerException e)
            {

            }



            String oldUserBody = "";

            if(UserManagement.getInstance().getUsers() != null)
            {
                oldUserBody = UserManagement.getInstance().getUsers().toString().replace("[", "").replace("]", "");
            }

            String newBody = "{" +
                    "\"users\" : [" +
                    "" +
                    oldUserBody +
                    separator +
                    newJson +
                    "]" +
                    "}";


            System.out.println(newBody);
            JsonObject jsonObject = JsonManager.get(newBody);

            UserManagement.getInstance().setUsers(jsonObject.getAsJsonArray("users"));


            SpikeyManagement.saveUserData(obtainedSession.getGddUser().getData().get(0),
                    obtainedSession.getGddUser().getData().get(1),
                    obtainedSession.getGddUser().getData().get(2),
                    obtainedSession.getGddUser().getUser().getId(),
                    String.valueOf(obtainedSession.getGddUser().getYear()));




            BufferedWriter writer = null;

            try {
                writer = new BufferedWriter(new FileWriter("./users.json"));

                writer.write(
                        jsonObject.toString());

                System.out.println(jsonObject.toString());

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }




            event.getMessage().delete().queue();

            SpikeyManagement.getInstance().getVerificationSessions().remove(obtainedSession);




        }



        return obtainedSession;
    }
}
