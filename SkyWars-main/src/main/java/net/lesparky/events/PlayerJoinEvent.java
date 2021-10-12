package net.lesparky.events;

import com.mojang.authlib.GameProfile;
import net.lesparky.SkyWars;
import net.lesparky.api.language.LanguageManager;
import net.lesparky.api.spigot.config.LocationConfig;
import net.lesparky.api.spigot.game.gamecountdown.LobbyCountdown;
import net.lesparky.api.spigot.game.gamestate.GameStateManager;
import net.lesparky.scoreboard.ScoreboardManager;
import net.lesparky.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerJoinEvent implements Listener {

    private ItemStack voteItem;
    private ItemStack quitItem;

    @EventHandler
    public void on(org.bukkit.event.player.PlayerJoinEvent event) {

        Player player = event.getPlayer();

        event.setJoinMessage(null);

        if (GameStateManager.getCurrentGameState() == GameStateManager.lobbyState) {

            GameProfile gameProfile = new GameProfile(null, "MHF_ArrowRight");

            quitItem = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta quitItemMeta = (SkullMeta) quitItem.getItemMeta();
            quitItemMeta.setOwner(gameProfile.getName());
            quitItemMeta.setDisplayName(new LanguageManager().getString(player, "language.item.leave.name"));
            quitItem.setItemMeta(quitItemMeta);

            voteItem = new ItemBuilder(Material.NETHER_STAR)
                    .setDisplayName(new LanguageManager().getString(player, "language.item.vote.name")).build();

            player.getInventory().clear();
            player.getInventory().setItem(4, voteItem);
            player.getInventory().setItem(8, quitItem);

            new ScoreboardManager().sendScoreboard(player);

            if (new LocationConfig().getLocation("lobby") != null) {

                player.teleport(new LocationConfig().getLocation("lobby"));
            } else {

                player.sendMessage(SkyWars.getPrefix() + "Die §cLobby §7wurde noch nicht gesetzt");
            }

            if (Bukkit.getOnlinePlayers().size() >= SkyWars.MIN_PLAYERS && Bukkit.getOnlinePlayers().size() > 1) {

                if (!(LobbyCountdown.isRunning())) {

                    LobbyCountdown.stopIdle();
                    LobbyCountdown.start();
                }
            }

            for (Player players : Bukkit.getOnlinePlayers()) {

                players.sendMessage(SkyWars.getPrefix()
                        + new LanguageManager().getString(players, "language.game.message.join")
                        .replace("%player%", player.getName()) + " §8[§c" + Bukkit.getOnlinePlayers().size()
                        + "§8/§c" + Bukkit.getMaxPlayers() + "§8]");
            }
        }
    }
}
