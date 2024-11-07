package net.copokbl.simplemanhunt;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.inventory.ItemStack;

public class MorePearls implements Listener {

    public void register() {
        Main.plugin().registerEvents(this);
    }

    @EventHandler
    public void onTrade(PiglinBarterEvent event) {
        event.getOutcome().add(new ItemStack(Material.ENDER_PEARL));
    }
}
