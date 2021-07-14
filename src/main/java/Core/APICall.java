package Core;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class APICall {
    static JDA jda = API.getApi();
    public static void APICall() throws IOException, InterruptedException {

        ////////////////////////////////////////////////////////////////////////////
        //* Setting the bots status                                              *//
        //* And writing a status message in #talk                                *//
        ////////////////////////////////////////////////////////////////////////////
        //jda.getTextChannelById("864137167613198356").sendMessage("I started to look for changes for 5 minutes or until i find a change!").queue();
        jda.getPresence().setPresence(OnlineStatus.ONLINE, Activity.playing("LOOKING FOR CHANGES!"));


        ////////////////////////////////////////////////////////////////////////////
        //* Loop running 4500 times, every 200ms (15mins, 5 times a sec          *//
        ////////////////////////////////////////////////////////////////////////////
        for(int i = 0; i < 4500; i++)   {
            Config.loadConfig();

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
                //jda.getTextChannelById("864186142013390848").sendMessage(currentTime + ":   timeStamp changed. CONTRACT: " + APIObject.getString("contractAddress") + " TokenName: " + APIObject.getString("tokenName")).queue();
                //jda.getTextChannelById("864137167613198356").sendMessage("I have stopped looking for changes. Do !start to start me again!").queue();
                jda.getPresence().setPresence(OnlineStatus.DO_NOT_DISTURB, Activity.playing("!START TO START ME!"));
                break;
            }
            Thread.sleep(200);
        }
    }
}
