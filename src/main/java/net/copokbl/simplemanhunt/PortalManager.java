package net.copokbl.simplemanhunt;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class PortalManager implements Listener {

    public void register() {
        Main.plugin().registerEvents(this);
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        onPortal(event.getTo(), event.getFrom());
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEvent event) {
        onPortal(event.getTo(), event.getFrom());
    }

    private void onPortal(Location to, Location from) {
        if (to == null || from.getWorld() == null || to.getWorld() == null) {
            return;
        }
        if (!from.getWorld().getName().startsWith(Main.world.getName())) {
            return;  // Not a world that should be linked
        }

        if (to.getWorld().getName().endsWith("_nether")) {  // Go to nether
            to.setWorld(Main.nether);
        } else if (to.getWorld().getName().endsWith("_end")) {  // Go to the end
            to.setWorld(Main.end);
        } else {
            to.setWorld(Main.world);
        }
    }
}
