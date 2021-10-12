package net.lesparky.events;

import net.lesparky.SkyWars;
import net.lesparky.api.language.LanguageManager;
import net.lesparky.util.SpectatorUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.entity.PlayerDeathEvent playerDeathEvent) {

        if (playerDeathEvent.getEntityType() == EntityType.PLAYER) {

            Player player = playerDeathEvent.getEntity().getPlayer();
            Player killer = playerDeathEvent.getEntity().getKiller();

            new SpectatorUtil().setSpectator(player);

            for (Player players : Bukkit.getOnlinePlayers()) {

                players.sendMessage(SkyWars.getPrefix()
                        + new LanguageManager().getString(players, "en.game.message.death")
                        .replace("%player%", player.getName()).replace("%killer%", killer.getName()));
            }
        }
    }

}
