package me.jowdow.jowdowtester.commands;

import me.jowdow.jowdowtester.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class name extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived( GuildMessageReceivedEvent e) {
        if (e.getAuthor().isBot()) return; //stopping the bot from reading its own text
        String[] message = e.getMessage().getContentRaw().split(" "); //splitting the message up into array

        if(message[0].equalsIgnoreCase(Bot.getPrefix()+"name")){
            e.getMessage().delete().queue();// Deleting message

            // Description of Command
            if(message.length==1){
                nameDescription(e);
            }
            // Issuing command
            else{
                String id = e.getMessage().getMentionedMembers().isEmpty() ? message[1] : e.getMessage().getMentionedMembers().get(0).getId();//
                e.getGuild().retrieveMemberById(id).queue(member -> {
                    member.modifyNickname(Bot.getRename()).queue();

                    EmbedBuilder eb = new EmbedBuilder();// embedding message
                    eb.appendDescription(":sock: Username Changed");
                    eb.setColor(Color.BLUE);

                    e.getChannel().sendMessage(eb.build()).queue();
                },err -> {
                    e.getChannel().sendMessage("User was not found could not change name").queue();
                });
                Member member = e.getGuild().retrieveMemberById(id).complete();
                member.getUser().openPrivateChannel().queue(privateChannel -> {
                    privateChannel.sendMessage(Bot.getNameDirectMessContent()).queue();
                },err -> {
                    //TODO fix issue with being blocked or not able to send DM
                    e.getChannel().sendMessage("User has their DM's closed").queue();
                });
            }
        }
    }
    //TODO Make embedded look better
    public void nameDescription(GuildMessageReceivedEvent e){
        EmbedBuilder eb = new EmbedBuilder();// embedding message
        eb.setTitle("Command: "+Bot.getPrefix()+"name");
        eb.addField("Description:", "Change user's name to " + Bot.getRename() +" along with sending the user a DM",false );
        eb.addField("Sub Commands",Bot.getPrefix()+"Name dm - Change the contents of the DM sent to users",true);
        eb.addField("Usage:",Bot.getPrefix()+"name [user]",false);

        eb.appendDescription(Bot.getPrefix()+"name dm [Contents of the dm]");

        eb.setColor(Color.BLUE);
        e.getChannel().sendMessage(eb.build()).queue();
    }
}
