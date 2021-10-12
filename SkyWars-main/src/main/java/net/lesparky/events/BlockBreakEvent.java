package net.lesparky.events;

import net.lesparky.SkyWars;
import net.lesparky.api.spigot.game.gamestate.GameStateManager;
import net.lesparky.commands.SetupCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BlockBreakEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.block.BlockBreakEvent event) {

        Player player = event.getPlayer();

        if (!SkyWars.getBuildModePlayers().contains(player)
                && GameStateManager.getCurrentGameState() != GameStateManager.inGameState)  {

            event.setCancelled(true);
        }

        if (SetupCommand.innerChestBoolean) {
            if (event.getBlock().getType() == Material.CHEST) {

                SetupCommand.innerChestsLocation.add(event.getBlock().getLocation());

                player.sendMessage(SkyWars.getPrefix()
                        + "Kiste erfolgreich hinzugefügt! Weitere Kisten können durch den gleichen Prozess" +
                        "hinzugefügt werden. Wenn alle kisten geschlagen wurden, schreibe §cgesetzt §7in den Chat.");
            } else {

                player.sendMessage(SkyWars.getPrefix() + "Es können nur Kisten hinzugefügt werden!");
            }
        } else if (SetupCommand.outerChestBoolean) {
            if (event.getBlock().getType() == Material.CHEST) {

                SetupCommand.outerChestsLocation.add(event.getBlock().getLocation());

                player.sendMessage(SkyWars.getPrefix()
                        + "Kiste erfolgreich hinzugefügt! Weitere Kisten können durch den gleichen Prozess" +
                        "hinzugefügt werden. Wenn alle kisten geschlagen wurden, schreibe §cgesetzt §7in den Chat.");
            } else {

                player.sendMessage(SkyWars.getPrefix() + "Es können nur Kisten hinzugefügt werden!");
            }
        }
    }
}
