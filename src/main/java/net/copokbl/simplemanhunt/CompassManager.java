package net.copokbl.simplemanhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class CompassManager implements Listener {
    //                player holding, target
    private final Map<UUID, UUID> compassTargets = new HashMap<>();

    public void register() {
        Main.plugin().registerEvents(this);
    }

    public void setTarget(Player player, Player target) {
        compassTargets.put(player.getUniqueId(), target.getUniqueId());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        UUID targetUuid = event.getPlayer().getUniqueId();

        for (Map.Entry<UUID, UUID> entry : compassTargets.entrySet()) {
            if (entry.getValue().equals(targetUuid)) {
                UUID holdingPlayerUuid = entry.getKey();

                if (holdingPlayerUuid == null) {
                    // idk they disconnected?
                    return;
                }

                Player holdingPlayer = Bukkit.getPlayer(holdingPlayerUuid);
                if (holdingPlayer == null) {
                    // idk they disconnected?
                    return;
                }

                if (!holdingPlayer.getWorld().getName().equals(event.getTo().getWorld().getName())) {
                    return;  // They changed worlds
                }

                holdingPlayer.setCompassTarget(event.getTo());
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getItem() == null) {
            return;
        }

        if (!event.getItem().getType().equals(Material.COMPASS)) {
            return;
        }

        // get random runner
        if (Main.runners.isEmpty()) {
            Utils.actionBar(player, "&cNo available targets");
            return;
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(Main.runners.size());
        Player target = Main.runners.stream().toList().get(randomIndex);
        setTarget(player, target);
        Utils.actionBar(player, "&aSet target to &6" + target.getName());
        player.playSound(player, Sound.BLOCK_AMETHYST_CLUSTER_BREAK, 0.3f, 1f);
    }

    @EventHandler
    public void onDie(PlayerDeathEvent event) {
        event.getDrops().removeIf(i -> i.getType().equals(Material.COMPASS));
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if (Main.runners.contains(event.getPlayer())) {
            return;
        }

        event.getPlayer().getInventory().addItem(new ItemStack(Material.COMPASS));
    }
}
