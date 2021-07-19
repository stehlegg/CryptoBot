package Core;

import net.dv8tion.jda.api.*;
import org.slf4j.Logger;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;

public class Main   {
    static Logger logger = Log.Discord.getLogger();

    ////////////////////////////////////////////////////////////////////////////
    //* Starting point of the Program                                        *//
    //* Calling initialize and startBot                                      *//
    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws IllegalArgumentException, IOException, LoginException, InterruptedException {
        initialize();
        startBot();
        try {
            APICall.Scrape();
        } catch (MalformedURLException e)   {
            logger.error("URL fehlt in CFG. add url = ... in cfg.properties");
            System.exit(69);
        }
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

        logger.info("Logged in as: " + jda.getSelfUser().getName());

        API.setAPI(jda);
    }
}