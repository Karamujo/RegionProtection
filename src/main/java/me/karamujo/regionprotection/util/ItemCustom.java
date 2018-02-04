package me.karamujo.regionprotection.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 *
 * @author Enzo
 */
public class ItemCustom implements Listener, Cloneable {

    private ItemStack item;

    public ItemCustom() {
        item = new ItemStack(Material.AIR, 1);
    }

    public ItemCustom(ItemStack item) {
        this.item = item.clone();
    }

    public ItemCustom type(Material type) {
        item.setType(type);
        return this;
    }

    public ItemCustom data(short value) {
        item.setDurability(value);
        return this;
    }

    public ItemCustom amount(int quantia) {
        item.setAmount(quantia);
        return this;
    }

    public ItemCustom amount(String quantia) {
        item.setAmount(Integer.valueOf(quantia));
        return this;
    }

    public ItemCustom name(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemCustom lore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();

        List<String> loreReplace = new ArrayList<>();

        for (String s : lore) {
            loreReplace.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        meta.setLore(loreReplace);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCustom addLore(String line) {
        ItemMeta meta = item.getItemMeta();
        List<String> x;
        if (meta.getLore() != null) {
            x = meta.getLore();
        } else {
            x = new ArrayList<>();
        }
        x.add(ChatColor.translateAlternateColorCodes('&', line));
        lore(x);
        return this;
    }

    public ItemCustom lore(String... lore) {
        ItemMeta meta = item.getItemMeta();
        List<String> loreReplace = new ArrayList<>();

        for (String s : lore) {
            loreReplace.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        meta.setLore(loreReplace);
        item.setItemMeta(meta);
        return this;
    }

    public ItemCustom removeLore() {
        ItemMeta im = item.getItemMeta();
        im.setLore(null);
        item.setItemMeta(im);
        return this;
    }

    public ItemCustom potion(PotionType type, PotionEffectType effect, int duration, int level, boolean splash) {
        item = new Potion(type).toItemStack(1);
        PotionMeta potionMeta = (PotionMeta) item.getItemMeta();
        potionMeta.addCustomEffect(new PotionEffect(effect, duration * 60, level), splash);
        item.setItemMeta(potionMeta);
        return this;
    }

    public ItemCustom ownerSkull(String owner, String name) {
        item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(owner);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        item.setItemMeta(meta);
        return this;
    }

    public ItemCustom addEnchant(Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
        return this;
    }

    public ItemCustom addEnchants(Map<Enchantment, Integer> enchants) {
        for (Enchantment enchant : enchants.keySet()) {
            item.addUnsafeEnchantment(enchant, enchants.get(enchant));
        }
        return this;
    }

    public ItemCustom setColor(Color color) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) item.getItemMeta();
        leatherArmorMeta.setColor(color);
        item.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemCustom addFlags(ItemFlag... flags) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(flags);
        item.setItemMeta(itemMeta);
        return this;
    }

    public ItemCustom cloneItem() throws CloneNotSupportedException {
        ItemCustom ic = new ItemCustom();
        ic.item = this.item.clone();
        return ic;
    }

    public ItemStack build() {
        return item;
    }
}
