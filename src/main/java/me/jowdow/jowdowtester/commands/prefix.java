package me.jowdow.jowdowtester.commands;

import me.jowdow.jowdowtester.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;


public class prefix extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return; //stopping the bot from reading its own text
        String[] message = e.getMessage().getContentRaw().split(" "); //splitting the message up into array
        if(message[0].equalsIgnoreCase(Bot.getPrefix()+"prefix")) {
            e.getMessage().delete().queue();// Deleting message

            // Description of Command
            if (message.length == 1) {
                prefixDescription(e);
            }
            // Issuing command
            else {
                String[] prefixChar=message[1].split("");
                if(prefixChar.length<=3){
                    EmbedBuilder eb = new EmbedBuilder();// embedding message
                    eb.appendDescription("Prefix changed from (OLD) "+Bot.getPrefix()+" to (NEW) "+message[1]);
                    eb.setColor(Color.BLUE);
                    e.getChannel().sendMessage(eb.build()).queue();
                    Bot.setPrefix(message[1]);
                }
            }
        }
    }
    public void prefixDescription(GuildMessageReceivedEvent e){
        EmbedBuilder eb = new EmbedBuilder();// embedding message
        eb.setTitle("Command: "+Bot.getPrefix()+"prefix");
        eb.addField("Description:", "Change the prefix to string that is less than or equal to 3 characters",false );
        eb.addField("Usage:",Bot.getPrefix()+"prefix [new prefix]",false);


        eb.setColor(Color.BLUE);
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
