package me.karamujo.regionprotection.commands;

import com.google.common.collect.Lists;
import me.karamujo.regionprotection.RegionProtectionPlugin;
import me.karamujo.regionprotection.model.FlagType;
import me.karamujo.regionprotection.model.Region;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

/**
 *
 * @author Enzo
 */
public class RegionCommand extends Command {

    public RegionCommand() {
        super("regiao", "Comando para usar em regiões", "Use /regiao", Lists.newArrayList());
    }

    @Override
    public boolean execute(CommandSender sender, String string, String[] strings) {
        if (!(sender instanceof Player)) {
            Bukkit.getServer().getConsoleSender().sendMessage("§cUse esse comando apenas in-game.");
            return false;
        }

        Player player = (Player) sender;

        if (!RegionProtectionPlugin.isWorking()) {
            player.sendMessage("§cO plugin está temporariamente desabilitado.");
            return false;
        }

        if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("pos1")) {
                player.setMetadata("region-pos1", new FixedMetadataValue(RegionProtectionPlugin.getInstance(), player.getLocation()));
                player.sendMessage("§eMarcada posição um.");
            } else if (strings[0].equalsIgnoreCase("pos2")) {
                player.setMetadata("region-pos2", new FixedMetadataValue(RegionProtectionPlugin.getInstance(), player.getLocation()));
                player.sendMessage("§eMarcada posição dois.");
            } else if (strings[0].equalsIgnoreCase("info")) {
                Region region = RegionProtectionPlugin.getRegionsData().getRegionByLocation(player.getLocation());
                if (region == null) {
                    player.sendMessage("§cNão existe nenhuma região aqui.");
                    return false;
                }
                player.sendMessage("§aRegião: §7" + region.getName());
                player.sendMessage("§aDono: §7" + region.getOwner());
                player.sendMessage("§a[" + region.getxMin() + "," + region.getyMin() + "," + region.getzMin() + "] §7-> §a["
                        + region.getxMax() + "," + region.getyMax() + "," + region.getzMax() + "]");
                player.sendMessage("§aFlags: Mobs: " + getAllowMessage(region.getFlag(FlagType.MOBS).isAllow()) + ", PvP: " + getAllowMessage(region.getFlag(FlagType.PVP).isAllow()) + ", "
                        + "Build: " + getAllowMessage(region.getFlag(FlagType.BUILD).isAllow()) + "");
            } else {
                sendHelpMessage(player);
            }
        } else if (strings.length > 1) {
            if (strings[0].equalsIgnoreCase("criar")) {
                if (strings.length != 2) {
                    player.sendMessage("§cUse: /regiao criar <nome>");
                    return false;
                }
                if (!player.hasMetadata("region-pos1") || !player.hasMetadata("region-pos2")) {
                    player.sendMessage("§cPrimeiro marque as posições utilizando /regiao <pos1/pos2>");
                    return false;
                }
                String name = strings[1];
                if (RegionProtectionPlugin.getRegionsData().existsRegion(name)) {
                    player.sendMessage("§cJá existe uma região com esse nome.");
                    return false;
                }

                Location min = (Location) player.getMetadata("region-pos1").get(0).value();
                Location max = (Location) player.getMetadata("region-pos2").get(0).value();

                if (RegionProtectionPlugin.getRegionsData().getRegionByLocation(min) != null || RegionProtectionPlugin.getRegionsData().getRegionByLocation(max) != null) {
                    player.sendMessage("§cJá existe uma região em cima dessa área, marque outra.");
                    return false;
                }

                Region region = new Region(name, player.getName(), max, min);
                RegionProtectionPlugin.getRegionsData().register(region);
                player.removeMetadata("region-pos1", RegionProtectionPlugin.getInstance());
                player.removeMetadata("region-pos2", RegionProtectionPlugin.getInstance());
                player.sendMessage("§aRegião criada com sucesso!");
                return false;
            } else if (strings[0].equalsIgnoreCase("apagar")) {
                if (strings.length != 2) {
                    player.sendMessage("§cUse: §7/regiao apagar <nome>");
                    return false;
                }

                String name = strings[1];

                if (!RegionProtectionPlugin.getRegionsData().existsRegion(name)) {
                    player.sendMessage("§cNão existe nenhuma área com esse nome.");
                    return false;
                }

                RegionProtectionPlugin.getRegionsData().unregister(name);
                player.sendMessage("§aRegião apagada com sucesso!");
            } else if (strings[0].equalsIgnoreCase("info")) {
                if (strings.length != 2) {
                    player.sendMessage("§cUse: §7/regiao info <nome>");
                    return false;
                }
                String name = strings[1];
                if (!RegionProtectionPlugin.getRegionsData().existsRegion(name)) {
                    player.sendMessage("§cNão existe nenhuma região com esse nome.");
                    return false;
                }
                Region region = RegionProtectionPlugin.getRegionsData().getRegion(name);
                player.sendMessage("§aRegião: §7" + region.getName());
                player.sendMessage("§aDono: §7" + region.getOwner());
                player.sendMessage("§a[" + region.getxMin() + "," + region.getyMin() + "," + region.getzMin() + "] §7-> §a["
                        + region.getxMax() + "," + region.getyMax() + "," + region.getzMax() + "]");
                player.sendMessage("§aFlags: Mobs: " + getAllowMessage(region.getFlag(FlagType.MOBS).isAllow()) + ", PvP: " + getAllowMessage(region.getFlag(FlagType.PVP).isAllow()) + ", "
                        + "Build: " + getAllowMessage(region.getFlag(FlagType.BUILD).isAllow()) + "");
            } else if (strings[0].equalsIgnoreCase("flag")) {
                if (strings.length != 4) {
                    player.sendMessage("§cUse: §7/regiao flag <nome> <flag> <on/off>");
                    return false;
                }
                String name = strings[1];
                if (!RegionProtectionPlugin.getRegionsData().existsRegion(name)) {
                    player.sendMessage("§cNão existe nenhuma região com esse nome.");
                    return false;
                }
                Region region = RegionProtectionPlugin.getRegionsData().getRegion(name);
                if (!region.getOwner().equalsIgnoreCase(player.getName())) {
                    player.sendMessage("§cVocê não é dono desse terreno.");
                    return false;
                }
                FlagType flag = FlagType.valueOf(strings[2].toUpperCase());
                if (flag == null) {
                    player.sendMessage("§cFlag inválda.");
                    return false;
                }
                String allow = strings[3];
                if (allow.equalsIgnoreCase("on")) {
                    RegionProtectionPlugin.getRegionsData().updateFlag(region.getName(), flag, true);
                    player.sendMessage("§aFlag atualizada");
                } else if (allow.equalsIgnoreCase("off")) {
                    RegionProtectionPlugin.getRegionsData().updateFlag(region.getName(), flag, false);
                    player.sendMessage("§aFlag atualizada");
                } else {
                    player.sendMessage("§cUse apenas on/off.");
                }
            } else {
                sendHelpMessage(player);
            }
        } else {
            sendHelpMessage(player);
        }
        return false;
    }

    public String getAllowMessage(boolean allow) {
        return allow == true ? "on" : "off";
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage("§a/regiao pos1 §7- comando para marcar a posição um.");
        player.sendMessage("§a/regiao pos2 §7- comando para marcar a posição dois.");
        player.sendMessage("§a/regiao criar <nome> §7- comando para criar uma nova região.");
        player.sendMessage("§a/regiao apagar <nome> §7- comando para apagar uma região.");
        player.sendMessage("§a/regiao info [nome] §7- comando para apagar uma região.");
        player.sendMessage("§a/regiao flag <nome> <flag> <on/off> §7- comando para definir uma flag para região.");
    }

}
