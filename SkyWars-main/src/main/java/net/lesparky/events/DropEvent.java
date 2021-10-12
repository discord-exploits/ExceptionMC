package net.lesparky.events;

import net.lesparky.SkyWars;
import net.lesparky.api.spigot.game.gamestate.GameStateManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent implements Listener {

    @EventHandler
    public void on(PlayerDropItemEvent event) {

        Player player = event.getPlayer();

        if (!SkyWars.getBuildModePlayers().contains(player)
                && (GameStateManager.getCurrentGameState() != GameStateManager.inGameState))  {

            event.setCancelled(true);
        }
    }
}
