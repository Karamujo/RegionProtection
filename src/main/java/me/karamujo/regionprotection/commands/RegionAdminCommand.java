package me.karamujo.regionprotection.commands;

import com.google.common.collect.Lists;
import me.karamujo.regionprotection.RegionProtectionPlugin;
import me.karamujo.regionprotection.listeners.FlagListeners;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Enzo
 */
public class RegionAdminCommand extends Command {

    public RegionAdminCommand() {
        super("regiaoadmin", "Comandos region", "Use /regiaoadmin", Lists.newArrayList());
    }

    @Override
    public boolean execute(CommandSender sender, String string, String[] strings) {
        if (!(sender instanceof Player)) {
            Bukkit.getServer().getConsoleSender().sendMessage("§cUse esse comando apenas in-game.");
            return false;
        }

        Player player = (Player) sender;
        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("habilitar")) {
                RegionProtectionPlugin.setWorking(true);
                Bukkit.getPluginManager().registerEvents(new FlagListeners(), RegionProtectionPlugin.getInstance());
                player.sendMessage("§aPlugin habilitado.");
            } else if (strings[0].equalsIgnoreCase("desabilitar")) {
                RegionProtectionPlugin.setWorking(false);
                HandlerList.unregisterAll(RegionProtectionPlugin.getInstance());
                player.sendMessage("§aPlugin desabilitado.");
            }
        } else {
            player.sendMessage("§c/regionadmin habilitar comando para habilitar o plugin.");
            player.sendMessage("§c/regionadmin desabilitar comando para desabilitar o plugin.");
        }
        return false;
    }

}
