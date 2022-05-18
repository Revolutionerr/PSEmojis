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
        for (String code : plugin.codesAndEmsMap.keySet()) {
            String ems = plugin.codesAndEmsMap.get(code);
            if (msg.contains(this.surrounding + code + this.surrounding)) {
                msg = msg.replace(this.surrounding + code + this.surrounding, ems);
                event.setMessage(msg);
            }
        }
        for (String code : plugin.codesAndEmsPremMap.keySet()) {
            String ems = plugin.codesAndEmsPremMap.get(code);
            for (int i = 0; i < plugin.codesAndEmsPremMap.size(); i++) {
                if (msg.contains(this.surrounding + code + this.surrounding)) {
                    if (event.getPlayer().hasPermission("psemoji.prem")) {
                        msg = msg.replace(this.surrounding + code + this.surrounding, ems);
                        event.setMessage(msg);
                    } else {
                        event.getPlayer().sendMessage(ChatColor.RED + notpremtext);
                    }
                }
            }
        }
    }

   @EventHandler
    public void onSignChange(SignChangeEvent e) {
       Player p = e.getPlayer();
       if (plugin.getConfig().getBoolean("sign")) {
           for (int i = 0; i < 4; ++i) {
               for (String code : plugin.codesAndEmsMap.keySet()) {
                   String ems = plugin.codesAndEmsMap.get(code);
                   for (int j = 0; j < plugin.codesAndEmsMap.size(); j++) {
                       if (e.getLine(i).contains(this.surrounding + code + this.surrounding)) {
                           e.setLine(i, e.getLine(i).replace(this.surrounding + code + this.surrounding, ChatColor.WHITE + ems
                                   + ChatColor.RESET));
                       }
                   }
               }
               for (String code : plugin.codesAndEmsPremMap.keySet()) {
                   String ems = plugin.codesAndEmsPremMap.get(code);
                   for (int j = 0; j < plugin.codesAndEmsPremMap.size(); j++) {
                       if (e.getLine(i).contains(this.surrounding + code + this.surrounding)) {
                           if (p.hasPermission("psemoji.prem")) {
                               e.setLine(i, e.getLine(i).replace(this.surrounding + code + this.surrounding, ChatColor.WHITE + ems
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
}
