package Core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import net.dv8tion.jda.api.entities.TextChannel;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class APICall {
    static JDA jda = API.getApi();
    static Logger logger = Log.Discord.getLogger();

    public static void scrape() throws IOException, InterruptedException {

        ////////////////////////////////////////////////////////////////////////////
        //* Added Implementation to get                                          *//
        //* respective TextChannels to write Messages in from CFG                *//
        ////////////////////////////////////////////////////////////////////////////
        TextChannel talkChannel = jda.getTextChannelById(Config.getValue("talkChannel"));
        TextChannel notifChannel = jda.getTextChannelById(Config.getValue("notifChannel"));

        ////////////////////////////////////////////////////////////////////////////
        //* Setting the bots status                                              *//
        //* And writing a status message in #talk                                *//
        ////////////////////////////////////////////////////////////////////////////
        assert talkChannel != null;
        talkChannel.sendMessage("I started to look for changes for 15 minutes or until i find a change!").queue();
        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("LOOKING FOR CHANGES!"));

        ////////////////////////////////////////////////////////////////////////////
        //* Loop running 4500 times, every 200ms (15mins, 5 times a sec)         *//
        ////////////////////////////////////////////////////////////////////////////
        for(int i = 0; i < 4500; i++)   {
            Config.loadConfig();
            if(!jda.getPresence().getStatus().toString().equalsIgnoreCase("online"))
                jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("LOOKING FOR CHANGES!"));

            ////////////////////////////////////////////////////////////////////////////
            //* Dissecting the JSON                                                  *//
            //* splitting all infos we need                                          *//
            ////////////////////////////////////////////////////////////////////////////
            JSONObject APICall = JSON.readJson(Config.getValue("url"));
            JSONArray APIArray = APICall.getJSONArray("result");
            JSONObject APIObject = APIArray.getJSONObject(0);
            String APITimeStamp = APIObject.getString("timeStamp");

            ////////////////////////////////////////////////////////////////////////////
            //* check if there is a timeStamp value in config, if not set one        *//
            ////////////////////////////////////////////////////////////////////////////
            if(Config.getValue("timeStamp") == null)    {
                Config.putValue("timeStamp", APITimeStamp);
            }

            ////////////////////////////////////////////////////////////////////////////
            //* else If the timeStamp is different from tS in config                 *//
            //* update tS in config, notify, update status and stop loop             *//
            ////////////////////////////////////////////////////////////////////////////
            else if(!Config.getValue("timeStamp").equalsIgnoreCase(APITimeStamp))   {
                String currentTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
                Config.putValue("timeStamp", APITimeStamp);
                assert notifChannel != null;
                notifChannel.sendMessage(currentTime + ":   timeStamp changed. CONTRACT: " + APIObject.getString("contractAddress") + " TokenName: " + APIObject.getString("tokenName")).queue();
                logger.info(":   timeStamp changed. CONTRACT: " + APIObject.getString("contractAddress") + " TokenName: " + APIObject.getString("tokenName"));
                for(int j = 0; j < 10; j++) {
                    System.out.println(currentTime + ":   timeStamp changed. CONTRACT: " + APIObject.getString("contractAddress") + " TokenName: " + APIObject.getString("tokenName"));
                    logger.warn(APIObject.getString("contractAddress"));
                }
                talkChannel.sendMessage("I have stopped looking for changes. Do !start to start me again!").queue();
                if(!jda.getPresence().getStatus().toString().equalsIgnoreCase("do_not_disturb"))
                    jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.playing("!START TO START ME!"));
                break;
            }

            ////////////////////////////////////////////////////////////////////////////
            //* catching interruption when discord disconnects for whatever reason   *//
            ////////////////////////////////////////////////////////////////////////////
            try {
                Thread.sleep(200);
            } catch (InterruptedException e)    {
                logger.error("Sleep Interrupted by Disconnect?");
                if(!jda.getPresence().getStatus().toString().equalsIgnoreCase("do_not_disturb"))
                    jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.playing("!START TO START ME!"));
                break;
            }
        }
    }
}
