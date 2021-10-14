package management;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import sun.plugin2.message.Message;

public class EmbedManager {


    public MessageEmbed build(String title, String[] ...message)
    {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title, null);

        eb.addField("",

                "", false);



        for(int i = 0; i < message.length; i++)
        {
            String message1 = "";
            String message2 = "";


            try
            {
                message1 = message[i][0];
                message2 = message[i][1];
            }
            catch(ArrayIndexOutOfBoundsException e)
            {

            }


            eb.addField(message1, message2, false);
        }


        eb.addField("", "", false);

        eb.setColor(SpikeyManagement.getInstance().getPrimaryColor());
        eb.setFooter("Made by Aden Koh Yong Kang", "https://astero.me/assets/images/image04.png");

        return eb.build();
    }

    public MessageEmbed build(String title, String imageUrl, String[] ...message)
    {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title, null);

        eb.addField("",

                "", false);



        for(int i = 0; i < message.length; i++)
        {
            String message1 = "";
            String message2 = "";


            try
            {
                message1 = message[i][0];
                message2 = message[i][1];
            }
            catch(ArrayIndexOutOfBoundsException e)
            {

            }


            eb.addField(message1, message2, false);
        }


        eb.addField("", "", false);

        eb.setImage(imageUrl);

        eb.setColor(SpikeyManagement.getInstance().getPrimaryColor());
        eb.setFooter("Made by Aden Koh Yong Kang", "https://astero.me/assets/images/image04.png");

        return eb.build();
    }
}
