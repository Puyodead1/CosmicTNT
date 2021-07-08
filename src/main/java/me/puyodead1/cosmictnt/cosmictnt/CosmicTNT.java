package me.puyodead1.cosmictnt.cosmictnt;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public final class CosmicTNT extends JavaPlugin {

    private CosmicTNT PLUGIN;
    private BukkitCommandManager commandManager;

    @Override
    public void onEnable() {
        PLUGIN = this;
        saveDefaultConfig();

        commandManager = new BukkitCommandManager(this);

        commandManager.registerCommand(new CosmicTNTCommand());

        // Register events
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        // Load TNT
        for (final String key : getConfig().getKeys(false)) {
            final Material material = Material.matchMaterial(getConfig().getString(key + ".material"));
            if (material == null) {
                System.out.println("WARN: Material is null");
                continue;
            }
            new CustomTNT(key, material, getConfig().getString(key + ".name"), getConfig().getStringList(key + ".lore"), getConfig().getStringList(key + ".attributes"));
        }
    }

    @Override
    public void onDisable() {
        PLUGIN = null;
    }

    public BukkitCommandManager getCommandManager() {
        return commandManager;
    }
}
