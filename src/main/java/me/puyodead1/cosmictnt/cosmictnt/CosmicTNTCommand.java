package me.puyodead1.cosmictnt.cosmictnt;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("ctnt")
@Description("CosmicTNT")
public class CosmicTNTCommand extends BaseCommand {
    @Dependency
    private CosmicTNT plugin;

    @Subcommand("give")
    public void onGiveCommand(CommandSender sender) {
        final Player player = sender instanceof Player ? (Player) sender : null;
        if (player == null) return;

        for (final CustomTNT tnt : CustomTNT.getCustomTnt().values()) {
            player.getInventory().addItem(tnt.getItemStack());
        }
    }
}
