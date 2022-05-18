package revolutioner.net.psemojis;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

import java.util.Map;


public final class PSEmojis extends JavaPlugin {
    public static PSEmojis plugin;
    public Map<String, String> codesAndEmsMap = new HashMap<String, String>(); // dont really know how to take values from map and compare them with keys
    public Map<String, String> codesAndEmsPremMap = new HashMap<String, String>();

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "ПСЭмодзи подключено");
        getServer().getPluginManager().registerEvents(new Listen(), this);
        getServer().getPluginManager().registerEvents(new PSMenu(), this);;
        getCommand("emoji").setExecutor(new PSMenu());
        reloadConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        setCodesAndEms();
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "ПСЭмодзи отключено");
    }

    public void setCodesAndEms () {
        for(String key : getConfig().getConfigurationSection("codesAndEms").getKeys(false)) {
            codesAndEmsMap.put(key, getConfig().getString("codesAndEms." + key));
        }
        for(String key : getConfig().getConfigurationSection("codesAndEmsPrem").getKeys(false)) {
            codesAndEmsPremMap.put(key, getConfig().getString("codesAndEmsPrem." + key));
        }
    }
}



/**
 * Made by some cool dude that likes drugs and spending time with girls, aka Revolutioner aka JRabble
 */