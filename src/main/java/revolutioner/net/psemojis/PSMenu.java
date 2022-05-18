package revolutioner.net.psemojis;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * cringe
 */


public class PSMenu implements Listener, CommandExecutor, TabCompleter {
    PSEmojis plugin = PSEmojis.plugin;
    String hint1 = plugin.getConfig().getString("hint1");
    String hint2 = plugin.getConfig().getString("hint2");
    String surrounding = plugin.getConfig().getString("surrounding");
    String notadmintext = plugin.getConfig().getString("notadmintext");
    int invsize = plugin.getConfig().getInt("invsize");
    String gridmaterial = plugin.getConfig().getString("gridmaterial");
    String page1 = plugin.getConfig().getString("page1");
    String page2 = plugin.getConfig().getString("page2");
    String page3 = plugin.getConfig().getString("page3");
    Inventory MenuInvent = Bukkit.getServer().createInventory(null, invsize, page1);
    Inventory MenuInvent2 = Bukkit.getServer().createInventory(null, invsize, page2);
    Inventory PremInvent = Bukkit.getServer().createInventory(null, invsize, page3);
    String notpremtext = plugin.getConfig().getString("notpremtext");

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        if (cmd.getName().equals("emoji")) {
            if (args.length == 0) {
                player.openInventory(MenuInvent);
                MenuInv(player);
                return true;
            }
            if (args[0].equals("info")) {
                TextComponent.Builder componentBuilder = Component.text()
                        .append(Component.text(plugin.getName()).color(TextColor.fromHexString("#fba7000")).decorate(TextDecoration.BOLD))
                        .append(Component.text(" "))
                        .append(Component.text(plugin.getDescription().getVersion()).color(TextColor.fromHexString("#fba700")))
                        .append(Component.text(" by Revolutioner (JRabble)").color(TextColor.fromHexString("#fba700"))).append(Component.newline())
                        .append(Component.text("Плагин позволяет вам использовать смайлики сервера как в чате так и на табличках").color(TextColor.fromHexString("#f2ae25")).decorate(TextDecoration.ITALIC)).append(Component.newline())
                        .append(Component.text("Также чтобы получить доступ ко всем смайликам произведите покупку привилегии").color(TextColor.fromHexString("#f2ae25")).decorate(TextDecoration.ITALIC));
                sender.sendMessage(componentBuilder.build());
                return true;
            }
            if (args[0].equals("test")) {
                player.sendMessage(plugin.codesAndEmsMap + "");
                return true;
            }
            for (String code : plugin.codesAndEmsMap.keySet()) {
                String ems = plugin.codesAndEmsMap.get(code);
                if (args[0].equals(surrounding + code + surrounding)) {
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Этот эмодзи выглядит так: " + ChatColor.RESET + ems);
                    return true;
                }
                if (args[0].equals(code)) {
                    player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Этот эмодзи выглядит так: " + ChatColor.RESET + ems + ChatColor.GRAY + ChatColor.ITALIC + "  \n (Пожалуйста используйте " + ChatColor.RESET + "\":\"" + ChatColor.GRAY + ChatColor.ITALIC + " перед и после кода эмодзи. Пример: :" + args[0] + ":)");
                    return true;
                }
            }
                if (args[0].equals("reload")) {
                    if (player.hasPermission("psemoji.reload")) {
                        plugin.reloadConfig();
                        plugin.getConfig().options().copyDefaults(true);
                        plugin.saveConfig();
                        player.sendMessage(ChatColor.GOLD + "Конфигурация перезагружена!");
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + notadmintext);
                    }
                } else {
                player.sendMessage(ChatColor.RED + "Такой команды не существует");
            }
            return true;
        }
        return true;
    }

    public void MenuInv(Player p) {
        ItemStack emoje = new ItemStack(Material.getMaterial(gridmaterial)); ItemMeta emojeMeta = emoje.getItemMeta();
        ItemStack next = new ItemStack(Material.ARROW, 1, (short) 5); ItemMeta npmeta = next.getItemMeta(); npmeta.setDisplayName(ChatColor.GREEN + "След. страница"); next.setItemMeta(npmeta);
        ItemStack prev = new ItemStack(Material.ARROW, 1, (short) 5); ItemMeta pvmeta = next.getItemMeta(); pvmeta.setDisplayName(ChatColor.RED + "Пред. страница"); prev.setItemMeta(pvmeta);
        next.setItemMeta(npmeta);
        MenuInvent.setItem(MenuInvent.getSize()-1, next);
        npmeta.setDisplayName(ChatColor.GOLD + "Премиум эмодзи");
        next.setItemMeta(npmeta);
        MenuInvent2.setItem(MenuInvent2.getSize()-1, next);
        MenuInvent2.setItem(MenuInvent.getSize()-9, prev);
        PremInvent.setItem(PremInvent.getSize()-9, prev);
        int d = 0;
        int r = 0;
        for (String code : plugin.codesAndEmsMap.keySet()) {
            String ems = plugin.codesAndEmsMap.get(code);
            addMets(emoje, emojeMeta, ems, code);
            if (d<invsize-1 && p.getOpenInventory().getTitle().equals(page1)) {
                MenuInvent.setItem(d, emoje);
            }
            if (d>invsize-2 && p.getOpenInventory().getTitle().equals(page2)) {
                MenuInvent2.setItem(d - (invsize-1), emoje);
            }
            d++;
        }
        if (p.getOpenInventory().getTitle().equals(page3)) {
            for (String code : plugin.codesAndEmsPremMap.keySet()) {
                String ems = plugin.codesAndEmsPremMap.get(code);
                addMets(emoje, emojeMeta, ems, code);
                if (r < invsize - 1) {
                    PremInvent.setItem(r, emoje);
                }
                r++;
            }
        }
    }

    public void addMets (ItemStack emoje, ItemMeta emojeMeta, String ems, String code) {
        String[] lore1 = {hint1, hint2};
        String lore2 = ChatColor.BLUE + "[ЛКМ]" + ChatColor.WHITE + " - в глобальный чат";
        String lore3 = ChatColor.GOLD + "[ПКМ]" + ChatColor.WHITE + " - в локальный чат";
        emojeMeta.setDisplayName(ChatColor.RESET + ems);
        emojeMeta.setLore(Arrays.asList(ChatColor.GRAY + lore1[0], ChatColor.GRAY + lore1[1], "", ChatColor.RED + surrounding + code + surrounding, "", lore2, lore3));
        emoje.setItemMeta(emojeMeta);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        final Player p = (Player) e.getWhoClicked();
        if (p.getOpenInventory().getTitle().equals(page1) || p.getOpenInventory().getTitle().equals(page2) || p.getOpenInventory().getTitle().equals(page3)) {
            final ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType().isAir()) return;
            for (String code : plugin.codesAndEmsMap.keySet()) {
                String ems = plugin.codesAndEmsMap.get(code);
                if(e.getClick() == ClickType.LEFT){
                    if (clickedItem.getItemMeta().getDisplayName().equals(ems)) {
                        p.playSound(p.getLocation(), "block.note_block.pling", 0.5F, 2F);
                        p.chat("!" + ems);
                    }
                }
                if(e.getClick() == ClickType.RIGHT){
                    if (clickedItem.getItemMeta().getDisplayName().equals(ems)) {
                        p.chat(ems);
                        p.playSound(p.getLocation(), "block.note_block.pling", 0.5F, 1F);
                    }
                }
            }
            for (String code : plugin.codesAndEmsPremMap.keySet()) {
                String ems = plugin.codesAndEmsPremMap.get(code);
                if(e.getClick() == ClickType.LEFT){
                    if (clickedItem.getItemMeta().getDisplayName().equals(ems)) {
                        p.chat("!" + ems);
                        p.playSound(p.getLocation(), "entity.experience_orb.pickup", 0.5F, 1F);
                    }
                }
                if(e.getClick() == ClickType.RIGHT){
                    if (clickedItem.getItemMeta().getDisplayName().equals(ems)) {
                        p.chat(ems);
                        p.playSound(p.getLocation(), "entity.experience_orb.pickup", 0.5F, 2F);
                    }
                }
            }

            if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GREEN + "След. страница") || clickedItem.getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Премиум эмодзи")) {
                if (p.getOpenInventory().getTitle().equals(page2)) {
                    if (p.hasPermission("psemoji.prem")) {
                        p.openInventory(PremInvent);
                        MenuInv(p);
                    } else {
                        p.sendMessage(ChatColor.RED + notpremtext);
                    }
                }
                else {
                    p.openInventory(MenuInvent2);
                    MenuInv(p);
                }
            }
            if (clickedItem.getItemMeta().getDisplayName().equals(ChatColor.RED + "Пред. страница")) {
                if (p.getOpenInventory().getTitle().equals(page3)) {
                    p.openInventory(MenuInvent2);
                    MenuInv(p);
                }
                else {
                    p.openInventory(MenuInvent);
                    MenuInv(p);
                }
            }
            e.setCancelled(true);
        }
    }
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return filter(complete(sender, args), args);
    }

    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return Lists.newArrayList("info", "reload");
        return Lists.newArrayList();
    }

    private List<String> filter(List<String> list, String[] args) {
        if (list == null) return null;
        String last = args[args.length - 1];
        List<String> result = new ArrayList<>();
        for (String arg : list) {
            if (arg.toLowerCase().startsWith(last.toLowerCase())) result.add(arg);
        }
        return result;
    }
}