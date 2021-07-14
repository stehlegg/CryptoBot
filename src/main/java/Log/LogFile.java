package Log;

import net.dv8tion.jda.api.entities.TextChannel;

import java.io.File;

public class LogFile {
    public static void getLogFile(TextChannel ch) {
        File file = new File("bot.log");
        ch.sendFile(file).queue();
    }
}