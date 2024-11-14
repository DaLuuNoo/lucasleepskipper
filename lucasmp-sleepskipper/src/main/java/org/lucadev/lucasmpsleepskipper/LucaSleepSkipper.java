package org.lucadev.lucasmpsleepskipper;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class LucaSleepSkipper extends JavaPlugin implements Listener {

    private static final double REQUIRED_SLEEP_PERCENT = 0.33; // change the percentage as you wish

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("Luca's SleepSkipper plugin activated.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Luca's SleepSkipper plugin deactivated.");
    }

    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        World world = event.getPlayer().getWorld();
        long totalPlayers = Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getWorld().equals(world))
                .count();

        int sleepersNeeded = (int) Math.ceil(totalPlayers * REQUIRED_SLEEP_PERCENT);

        long currentSleepers = Bukkit.getOnlinePlayers().stream()
                .filter(Player::isSleeping)
                .filter(player -> player.getWorld().equals(world))
                .count();

        if (currentSleepers + 1 >= sleepersNeeded) {
            world.setTime(0);
            world.setStorm(false);
            world.setThundering(false);
            Bukkit.broadcastMessage("Good morning!"); // changeable message after a new day starts
        }
    }
}
