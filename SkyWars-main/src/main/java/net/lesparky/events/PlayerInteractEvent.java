package net.lesparky.events;

import net.lesparky.SkyWars;
import net.lesparky.api.language.LanguageManager;
import net.lesparky.inventory.VoteInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class PlayerInteractEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (event.getItem() != null) {
            if (event.getItem().getItemMeta().hasDisplayName()) {
                if (event.getAction() == Action.LEFT_CLICK_AIR
                        || event.getAction() == Action.LEFT_CLICK_BLOCK
                        || event.getAction() == Action.RIGHT_CLICK_AIR
                        || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase(
                            new LanguageManager().getString(player, "language.item.leave.name"))) {

                        player.kickPlayer(SkyWars.getPrefix()
                                + new LanguageManager().getString(player, "language.item.leave.message"));
                    } else if (player.getItemInHand().getItemMeta().getDisplayName().
                            equalsIgnoreCase(new LanguageManager().getString(player, "language.item.vote.name"))) {

                        new VoteInventory().open(player);
                    }
                }
            }
        }
    }
}
