package me.puyodead1.cosmictnt.cosmictnt;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CustomTNT {
    public static HashMap<String, CustomTNT> CUSTOM_TNT = new HashMap<>();
    public static HashMap<Location, CustomTNT> PLACED = new HashMap<>();
    public static HashMap<UUID, CustomTNT> PRIMED = new HashMap<>();

    private Material material;
    private List<String> attributes;
    private ItemStack itemStack;
    private Location location;
    private UUID uuid;

    public CustomTNT(final String identifier, final Material material, final String name, final List<String> lore, final List<String> attributes) {
        this.material = material;
        this.attributes = attributes;

        itemStack = new ItemStack(material);
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(colorizeString(name));
        itemMeta.setLore(colorizeList(lore));
        itemStack.setItemMeta(itemMeta);

        CUSTOM_TNT.put(identifier, this);
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public List<String> getAttributes() {
        return attributes;
    }

    private List<String> colorizeList(final List<String> list) {
        final List<String> translated = new ArrayList<>();
        for (final String s : list) {
            translated.add(ChatColor.translateAlternateColorCodes('&', s));
        }

        return translated;
    }

    private String colorizeString(final String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static HashMap<String, CustomTNT> getCustomTnt() {
        return CUSTOM_TNT;
    }

    public static HashMap<Location, CustomTNT> getPLACED() {
        return PLACED;
    }

    public static HashMap<UUID, CustomTNT> getPRIMED() {
        return PRIMED;
    }


    public static CustomTNT valueOfItemstack(final ItemStack itemStack) {
        for (final CustomTNT tnt : CUSTOM_TNT.values()) {
            if (tnt.getItemStack().equals(itemStack)) return tnt;
        }

        return null;
    }
}
