package Events;
import Core.APICall;
import Core.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

////////////////////////////////////////////////////////////////////////////
//* Listening to new messages                                            *//
////////////////////////////////////////////////////////////////////////////
public class GuildMessage extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        ////////////////////////////////////////////////////////////////////////////
        //* if u tell it to !start, it starts                                    *//
        ////////////////////////////////////////////////////////////////////////////
        if(event.getChannel().getId().equalsIgnoreCase("864137167613198356") && event.getMessage().getContentRaw().startsWith("!start"))    {
            try {
                APICall.APICall();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ////////////////////////////////////////////////////////////////////////////
        //* command to set variables in the cfg                                  *//
        ////////////////////////////////////////////////////////////////////////////
        if(event.getChannel().getId().equalsIgnoreCase("864137167613198356") && event.getMessage().getContentRaw().startsWith("!cfg"))    {
            String[] cnt = event.getMessage().getContentRaw().split(" ");
            try {
                Config.putValue(cnt[1], cnt[2]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
