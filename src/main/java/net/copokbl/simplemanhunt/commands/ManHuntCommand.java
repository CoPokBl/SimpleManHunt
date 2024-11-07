package net.copokbl.simplemanhunt.commands;

import net.copokbl.simplemanhunt.Main;
import net.copokbl.simplemanhunt.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

// /manhunt [runner1] [runner2] [runner3...]
public class ManHuntCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Parse runner args
        Main.runners.clear();

        for (String arg : args) {
            Player player = Bukkit.getPlayer(arg);
            if (player == null) {
                sender.sendMessage("Player " + arg + " Could not be found!");
                return false;
            }

            Main.runners.add(player);
        }

        String name = "" + ThreadLocalRandom.current().nextInt(1000000);
        World world = Utils.createWorld(name, WorldType.NORMAL, World.Environment.NORMAL);
        Main.nether = Utils.createWorld(name + "_nether", WorldType.NORMAL, World.Environment.NETHER);
        Main.end = Utils.createWorld(name + "_end", WorldType.NORMAL, World.Environment.THE_END);
        Main.world = world;
        for (Player player : Main.plugin().getServer().getOnlinePlayers()) {
            player.teleport(world.getSpawnLocation());
            for (PotionEffect eff : player.getActivePotionEffects()) {
                player.removePotionEffect(eff.getType());
            }
            player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            player.setSaturation(20);
            player.setFoodLevel(20);
            player.getInventory().clear();
            if (!Main.runners.contains(player)) player.getInventory().addItem(new ItemStack(Material.COMPASS));
            player.setRespawnLocation(world.getSpawnLocation(), true);
            player.setInvulnerable(true);
            AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            assert maxHealth != null;
            maxHealth.getModifiers().forEach(maxHealth::removeModifier);
            maxHealth.setBaseValue(20);
            player.sendTitle(Utils.t("&aStart!"), Utils.t(Main.runners.contains(player) ? "&cRun away" : "&cStart hunting"), 10, 80, 10);
        }

        Main.plugin().getServer().getScheduler().runTaskLater(Main.plugin(), () -> {
            for (Player player : Main.plugin().getServer().getOnlinePlayers()) {
                player.setInvulnerable(false);
            }
        }, 20L);
        return true;
    }
}
