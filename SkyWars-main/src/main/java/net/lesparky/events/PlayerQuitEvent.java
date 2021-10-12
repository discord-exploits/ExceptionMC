package net.lesparky.events;

import net.lesparky.SkyWars;
import net.lesparky.api.language.LanguageManager;
import net.lesparky.api.spigot.game.gamecountdown.LobbyCountdown;
import net.lesparky.api.spigot.game.gamestate.GameStateManager;
import net.lesparky.api.spigot.game.voting.MapVoting;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener {

    @EventHandler
    public void on(org.bukkit.event.player.PlayerQuitEvent event) {

        Player player = event.getPlayer();

        event.setQuitMessage(null);

        SkyWars.getBuildModePlayers().remove(player);

        if (GameStateManager.getCurrentGameState() == GameStateManager.lobbyState) {

            if (Bukkit.getOnlinePlayers().size() < SkyWars.MIN_PLAYERS && Bukkit.getOnlinePlayers().size() > 1) {
                if (LobbyCountdown.isRunning()) {

                    LobbyCountdown.stop();
                    LobbyCountdown.startIdle();
                }
            }

            if (MapVoting.getPlayerVotes().containsKey(player.getUniqueId())) {

                MapVoting.getVotingMaps()[MapVoting.getPlayerVotes().get(player.getUniqueId())].removeVote();
                MapVoting.getPlayerVotes().remove(player.getUniqueId());
            }

            for (Player players : Bukkit.getOnlinePlayers()) {

                players.sendMessage(SkyWars.getPrefix()
                        + new LanguageManager().getString(players, "language.game.message.leave")
                        .replace("%player%", player.getName()) + " §8[§c" + Bukkit.getOnlinePlayers().size()
                        + "§8/§c" + Bukkit.getMaxPlayers() + "§8]");
            }
        }
    }
}
