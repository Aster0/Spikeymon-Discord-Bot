package eventlisteners;

import management.EmbedManager;
import management.SpikeyManagement;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.Button;
import org.jetbrains.annotations.NotNull;

public class OnReadyEvent implements EventListener {




    @Override
    public void onEvent(@NotNull GenericEvent genericEvent) {



        if(genericEvent instanceof ReadyEvent)
        {

            genericEvent.getJDA().updateCommands().addCommands(new CommandData("profile",

                    "Shows the profile of the mentioned user.")
                    .addOption(OptionType.USER, "profile", "The user you want to get the profile information on.")).queue();


            TextChannel verifyChannel = genericEvent.getJDA().getTextChannelById(SpikeyManagement.getInstance()
            .getGuildChannels().getVerificationChannel());



            System.out.println(verifyChannel);



            verifyChannel.getHistory().retrievePast(1).queue(message -> {




                if(message.size() > 0) {


                    message.get(0).delete().queue();
                }



                MessageEmbed messageEmbed = new EmbedManager().build("<:gddmon:898229885481418782>" +
                        " Welcome to the official GDD Community Discord!",

                        new String[]{"\uD83D\uDCAA Let's get you started!",
                                "In order to gain **full** access to this Discord Server, " +
                                        "there are a few things we need to be sure of first!"}, new String[]{""}
                        , new String[]{"\uD83D\uDDD2 Information Required:",
                                "- Full Name \n - Class \n - School Email\n" +
                                        "\n" +
                                        "You're required to enable private messages f" +
                                        "rom servers in Discord's settings for the authentication process."},
                        new String[]{""}, new String[]{"\uD83D\uDCDD Ready to authenticate?",
                                "\"Press the **\\\"AUTHENTICATE NOW\\\"** button! \\n \\nA member of the GDD Student Interest Group will then verify your credentials\" +\n" +
                                "                                \" and you'll gain your access very quickly!\""
                        });




                verifyChannel.sendMessage(messageEmbed) .setActionRow(
                        Button.success("authenticate_button", "\uD83D\uDCDD AUTHENTICATE ME")).queue();



            });






        }
    }
}
