package me.puyodead1.cosmictnt.cosmictnt;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;

public class EventListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockPlaced(final BlockPlaceEvent e) {
        final Block block = e.getBlock();
        final Location location = block.getLocation();
        final ItemStack item = e.getItemInHand();
        final CustomTNT tnt = CustomTNT.valueOfItemstack(item);
        if (tnt == null) return;

        tnt.setLocation(location);
        CustomTNT.getPLACED().put(location, tnt);

        System.out.println("placed");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent e) {
        final Block block = e.getBlock();
        final Location location = block.getLocation();
        if (!CustomTNT.getPLACED().containsKey(location)) return;

        CustomTNT.getPLACED().remove(location);
        System.out.println("break");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockDispense(BlockDispenseEvent e) {
        final ItemStack item = e.getItem();

        final CustomTNT tnt = CustomTNT.valueOfItemstack(item);
        if (tnt == null) return;

        final Location location = new Location(e.getBlock().getWorld(), e.getVelocity().getX(), e.getVelocity().getY(), e.getVelocity().getZ());
        tnt.setLocation(location);
        CustomTNT.getPLACED().put(location, tnt);

        System.out.println("dispense");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntitySpawn(final EntitySpawnEvent e) {
        final Entity entity = e.getEntity();
        if (!entity.getType().equals(EntityType.PRIMED_TNT)) return;

        final Block blockAt = entity.getLocation().getWorld().getBlockAt(entity.getLocation());
        Location location = null;

        if (CustomTNT.getPLACED().containsKey(entity.getLocation())) location = entity.getLocation();
        else if (CustomTNT.getPLACED().containsKey(blockAt.getLocation())) location = blockAt.getLocation();

        if (location == null) return;

        final CustomTNT tnt = CustomTNT.getPLACED().get(location);

        // set the entity uuid for tracking
        tnt.setUuid(entity.getUniqueId());
        // remove tnt from placed list
        CustomTNT.getPLACED().remove(location);
        // add tnt to primed list
        CustomTNT.getPRIMED().put(entity.getUniqueId(), tnt);

        System.out.println("spawn");
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityExplode(final EntityExplodeEvent e) {
        final Entity entity = e.getEntity();
        if (!entity.getType().equals(EntityType.PRIMED_TNT)) return;

        if (!CustomTNT.getPRIMED().containsKey(entity.getUniqueId())) return;
        final CustomTNT tnt = CustomTNT.getPRIMED().get(entity.getUniqueId());

        e.setCancelled(true);
        final TNTPrimed tntPrimed = e.getLocation().getWorld().spawn(e.getLocation(), TNTPrimed.class);
        tntPrimed.setSource(entity);
        tntPrimed.setFuseTicks(0);
        // TODO: parse attributes
//        tntPrimed.setYield(4F * tnt.getYieldMultiplier());
//        tntPrimed.setIsIncendiary(tnt.isIncendiary());

        System.out.println("explode");
    }
}
