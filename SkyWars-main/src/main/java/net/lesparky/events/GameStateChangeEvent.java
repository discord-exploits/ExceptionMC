package net.lesparky.events;

import net.lesparky.api.spigot.game.gamestate.GameStateManager;
import net.lesparky.chests.InnerChest;
import net.lesparky.chests.OuterChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class GameStateChangeEvent implements Listener {

    @EventHandler
    public void on(net.lesparky.api.spigot.game.gamestate.GameStateChangeEvent gameStateChangeEvent) {

        int currentGameState = gameStateChangeEvent.getCurrentGameState();

        if (currentGameState == GameStateManager.inGameState) {

            OuterChest.fillOuterChests();
            InnerChest.fillInnerChest();
        }
    }
}
