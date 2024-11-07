package net.copokbl.simplemanhunt;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;

import java.io.File;

public class Utils {

    public static World createWorld(String name, WorldType type, World.Environment environment) {
        WorldCreator wc = new WorldCreator(name);
        wc.environment(environment);
        wc.type(type);
        wc.createWorld();
        World world = Bukkit.getWorld(name);
        assert world != null;
        world.setAutoSave(false);
        Main.createdWorlds.add(world);
        return world;
    }

    public static void deleteFsObj(File path) {
        if (path.exists()) {
            File[] files = path.listFiles();
            if (files == null) {
                Bukkit.getLogger().severe("Failed to list files in " + path.getAbsolutePath());
                return;  // failed
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFsObj(file);
                } else {
                    if (!file.delete()) {
                        Bukkit.getLogger().severe("Failed to delete file: " + file.getAbsolutePath());
                        return;  // failed
                    }
                }
            }
        } else {
            Bukkit.getLogger().severe("Failed to delete folder: " + path.getAbsolutePath());
            Bukkit.getLogger().severe("Folder does not exist.");
            return;  // failed
        }
        path.delete();
    }

    public static void deleteWorld(World world) {
        File path = world.getWorldFolder();
        deleteFsObj(path);
    }

    public static String t(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void actionBar(Player player, String msg) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(t(msg)));
    }
}
