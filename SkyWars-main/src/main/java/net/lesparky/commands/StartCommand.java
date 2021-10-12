package net.lesparky.commands;

import net.lesparky.SkyWars;
import net.lesparky.api.language.LanguageManager;
import net.lesparky.api.spigot.game.gamecountdown.LobbyCountdown;
import net.lesparky.api.spigot.game.gamestate.GameStateManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {

    private static final int START_SECONDS = 10;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (player.hasPermission("lesparky.start")) {
                if (args.length == 0) {
                    if (GameStateManager.getCurrentGameState() == GameStateManager.lobbyState) {
                        if (Bukkit.getOnlinePlayers().size() >= SkyWars.MIN_PLAYERS
                                && Bukkit.getOnlinePlayers().size() > 1) {

                            if (LobbyCountdown.isRunning()
                                    && (LobbyCountdown.getSeconds() > START_SECONDS)) {

                                LobbyCountdown.setSeconds(START_SECONDS);

                                player.sendMessage(SkyWars.getPrefix()
                                        + new LanguageManager().getString(player,
                                        "language.command.start.success.own"));

                                for (Player players : Bukkit.getOnlinePlayers()) {

                                    players.sendMessage(SkyWars.getPrefix()
                                            + new LanguageManager().getString(players,
                                            "language.command.start.success.brodcast")
                                            .replace("%player%", player.getName()));
                                }
                            } else {

                                player.sendMessage(SkyWars.getPrefix()
                                        + new LanguageManager().getString(player,
                                        "language.command.start.gameisstarting"));
                            }
                        } else {

                            player.sendMessage(SkyWars.getPrefix()
                                    + new LanguageManager().getString(player,
                                    "language.command.start.notenougtplayers"));
                        }
                    } else {

                        player.sendMessage(SkyWars.getPrefix()
                                + new LanguageManager().getString(player,
                                "language.command.start.gamealreadystarted"));
                    }
                } else {

                    player.sendMessage(SkyWars.getPrefix()
                            + new LanguageManager().getString(player, "language.command.usage") + "§c/start");
                }
            } else {

                player.sendMessage(SkyWars.getPrefix()
                        + new LanguageManager().getString(player, "language.command.insufficient"));
            }
        }

        return false;
    }
}
