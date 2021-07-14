package Core;

import net.dv8tion.jda.api.*;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;

public class Main   {
    ////////////////////////////////////////////////////////////////////////////
    //* Starting point of the Program                                        *//
    //* Calling initialize and startBot                                      *//
    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws IllegalArgumentException, IOException, LoginException, InterruptedException {
        initialize();
        startBot();
        APICall.APICall();
    }

    ////////////////////////////////////////////////////////////////////////////
    //* Initializing needed variables to manage Cookies for HTTP Connection  *//
    //* And Load the Config initially                                        *//
    ////////////////////////////////////////////////////////////////////////////
    private static void initialize() throws IOException {
        CookieManager cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
        Config.loadConfig();
    }

    ////////////////////////////////////////////////////////////////////////////
    //* starting up the DiscordBot instance with Token loaded from Config    *//
    //* write API and Presence Object to API class                           *//
    ////////////////////////////////////////////////////////////////////////////
    private static void startBot() throws LoginException, InterruptedException, IOException {
        JDA jda = JDABuilder
                .createDefault(Config.getValue("token"))
                .addEventListeners(
                        new Events.GuildMessage())
                .build().awaitReady();

        API.setAPI(jda);
        API.setPres(jda.getPresence());
    }
}