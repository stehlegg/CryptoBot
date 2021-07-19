package Core;

import net.dv8tion.jda.api.JDA;

public class API {

    ////////////////////////////////////////////////////////////////////////////
    //* Discord API Object                                                   *//
    //* Getter and Setter for Discord API                                    *//
    ////////////////////////////////////////////////////////////////////////////
    private static JDA api;
    public static void setAPI(JDA jda)  {
        api = jda;
    }
    public static JDA getApi()  {
        return api;
    }
}