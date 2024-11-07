package net.copokbl.simplemanhunt;

import net.copokbl.simplemanhunt.commands.ManHuntCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Main extends JavaPlugin {
    private static Main plugin;

    // State for current match
    public static Set<Player> runners = new HashSet<>();
    public static World world = null;
    public static World nether = null;
    public static World end = null;
    public static final Set<World> createdWorlds = new HashSet<>();

    @Override
    public void onEnable() {
        plugin = this;

        new CompassManager().register();
        new PortalManager().register();

        Objects.requireNonNull(getCommand("manhunt")).setExecutor(new ManHuntCommand());

        Bukkit.getLogger().info("Enabled SimpleManHunt by CoPokBl");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Disabling SimpleManHunt");

        // Delete all worlds
        for (World world : createdWorlds) {
            getServer().unloadWorld(world, false);
            Utils.deleteWorld(world);
            getLogger().info("Deleted " + world.getName());
        }
    }

    public void registerEvents(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    public static Main plugin() {
        return plugin;
    }
}