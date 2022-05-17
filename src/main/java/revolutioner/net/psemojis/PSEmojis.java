package revolutioner.net.psemojis;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public final class PSEmojis extends JavaPlugin {
    public static PSEmojis plugin;
    public Map<String, String> codesAndEmsMap = new HashMap<String, String>(); // dont really know how to take values from map and compare them with keys


    public List<String> codesEm = getConfig().getStringList("codeEm");
    public String[] codes = codesEm.toArray(new String[codesEm.size()]);
    public List<String> emojEm = getConfig().getStringList("emojEm");
    public String[] emojes = emojEm.toArray(new String[emojEm.size()]);

    public java.util.List<String> codePrem = getConfig().getStringList("codePrem");
    public String[] codesPrem = codePrem.toArray(new String[codePrem.size()]);
    public java.util.List<String> emojPrem = getConfig().getStringList("emojPrem");
    public String[] emojesPrem = emojPrem.toArray(new String[emojPrem.size()]);



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
    }
}



/**
 * Made by some cool dude that likes drugs and spending time with girls, aka Revolutioner aka JRabble
 */