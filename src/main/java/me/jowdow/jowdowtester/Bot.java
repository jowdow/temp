package me.jowdow.jowdowtester;

import me.jowdow.jowdowtester.commands.name;
import me.jowdow.jowdowtester.commands.prefix;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

public class Bot {
    private static String prefixStr = ">";
    private static String rename = "[REDACTED SOCK]";
    private static String nameDirectMessContent = "Yo bro your name ? remebber that thing it's no longer the same . wanna know why?? because you didn't follow the rules";
    public static void main(String args[]) throws Exception{
        JDA jda = JDABuilder.createDefault("").build();

        jda.addEventListener(new name());
        jda.addEventListener(new prefix());

    }

    public static String getPrefix() {
        return prefixStr;
    }
    public static String getRename() {
        return rename;
    }
    public static String getNameDirectMessContent(){ return nameDirectMessContent; }

    public static void setPrefix(String string){
        prefixStr=string;
    }
}

