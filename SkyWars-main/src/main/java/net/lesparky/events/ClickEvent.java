package net.lesparky.events;

import net.lesparky.api.language.LanguageManager;
import net.lesparky.api.spigot.game.voting.MapVoting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ClickEvent implements Listener {

    @EventHandler
    public void on(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() != null) {

            if (event.getInventory().getTitle().equals(new LanguageManager().getString(player,
                    "language.inventory.voting.title"))) {

                event.setCancelled(true);

                for (int i = 0; i < MapVoting.getVotingInventoryOrder().length; i++) {
                    if (MapVoting.getVotingInventoryOrder()[i] == event.getSlot()) {

                        MapVoting.vote(player, i);

                        return;
                    }
                }
            }
        }
    }
}
