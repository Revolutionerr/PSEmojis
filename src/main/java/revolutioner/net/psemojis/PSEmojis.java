package revolutioner.net.psemojis;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

import java.util.Map;


public final class PSEmojis extends JavaPlugin {
    public static PSEmojis plugin;
    public Map<String, String> codesAndEmsMap = new HashMap<String, String>();
    public Map<String, String> codesAndEmsPremMap = new HashMap<String, String>();

    @Override
    public void onEnable() {
        plugin = this;
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "ПСЭмодзи подключено");
        getServer().getPluginManager().registerEvents(new Listen(), this);
        getServer().getPluginManager().registerEvents(new PSMenu(), this);;
        getCommand("emoji").setExecutor(new PSMenu());
        addDefaults();
        reloadConfig();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
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

    public void addDefaults () {
        getConfig().addDefault("sign", true);
        getConfig().addDefault("surrounding", ":");
        getConfig().addDefault("page1", "Playstrix Эмодзи [стр. 1]");
        getConfig().addDefault("page2", "Playstrix Эмодзи [стр. 2]");
        getConfig().addDefault("page3", "Playstrix Премиум Эмодзи ⭐");
        getConfig().addDefault("invsize", 54);
        getConfig().addDefault("gridmaterial", "WHITE_STAINED_GLASS_PANE");
        getConfig().addDefault("hint1", "Для того чтобы использовать этот эмодзи в чате");
        getConfig().addDefault("hint2", "введите его код или же нажмите на предмет");
        getConfig().addDefault("notpremtext", "У вас нету привилегии для доступа к примиум эмодзи!");
        getConfig().addDefault("notadmintext", "У вас нету доступа к данной команде!");
    }
}



/**
 * Made by some cool dude that likes drugs and spending time with girls, aka Revolutioner aka JRabble
 */