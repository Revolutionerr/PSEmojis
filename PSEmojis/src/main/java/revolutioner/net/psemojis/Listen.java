package revolutioner.net.psemojis;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Listen implements Listener {
    PSEmojis plugin = PSEmojis.plugin;
    String surrounding = plugin.getConfig().getString("surrounding");
    String notpremtext = plugin.getConfig().getString("notpremtext");

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String msg = event.getMessage();
        event.setMessage(msg);
        for (int i = 0; i < plugin.codes.length; i++) {
            if (msg.contains(this.surrounding + plugin.codes[i] + this.surrounding)) { // how do i take all the values and compare them
                msg = msg.replace(this.surrounding + plugin.emojes[i] + this.surrounding, plugin.emojes[i]);
                event.setMessage(msg);
            }
        }
        for (int i = 0; i < plugin.codesPrem.length; i++) {
            if (msg.contains(this.surrounding + plugin.codesPrem[i] + this.surrounding)) {
                if (event.getPlayer().hasPermission("psemoji.prem")) {
                    msg = msg.replace(this.surrounding + plugin.codesPrem[i] + this.surrounding, plugin.emojesPrem[i]);
                    event.setMessage(msg);
                } else {
                    event.getPlayer().sendMessage(ChatColor.RED + notpremtext);
                }
            }
        }
    }

   @EventHandler
    public void onSignChange(SignChangeEvent e) {
       Player p = e.getPlayer();
       if (plugin.getConfig().getBoolean("sign")) {
           for (int i = 0; i < 4; ++i) {
               for (int j = 0; j < plugin.codes.length; j++) {
                   if (e.getLine(i).contains(this.surrounding + plugin.codes[j] + this.surrounding)) {
                       e.setLine(i, e.getLine(i).replace(this.surrounding + plugin.codes[j] + this.surrounding, ChatColor.WHITE + "" + plugin.emojes[j]
                               + ChatColor.RESET));
                   }
               }
               for (int j = 0; j < plugin.codesPrem.length; j++) {
                   if (e.getLine(i).contains(this.surrounding + plugin.codesPrem[j] + this.surrounding)) {
                       if (p.hasPermission("psemoji.prem")) {
                           e.setLine(i, e.getLine(i).replace(this.surrounding + plugin.codesPrem[j] + this.surrounding, ChatColor.WHITE + "" + plugin.emojesPrem[j]
                                   + ChatColor.RESET));
                       } else {
                           p.sendMessage(ChatColor.RED + notpremtext);
                       }
                   }
               }
           }
       }
   }
}
