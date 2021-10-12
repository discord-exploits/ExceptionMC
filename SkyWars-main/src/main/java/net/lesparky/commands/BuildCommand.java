package net.lesparky.commands;

import net.lesparky.SkyWars;
import net.lesparky.api.language.LanguageManager;
import net.lesparky.api.spigot.game.team.TeamManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            TeamManager.setRandomTeam(player);
            player.sendMessage("Du bist im Team " + TeamManager.playerTeam.get(player));

            if (player.hasPermission("lesparky.build")) {
                if (args.length == 0) {
                    if (!(SkyWars.getBuildModePlayers().contains(player))) {

                        SkyWars.getBuildModePlayers().add(player);

                        player.sendMessage(SkyWars.getPrefix()
                                + new LanguageManager().getString(player, "language.messages.build.enabled.self"));

                    } else {

                        SkyWars.getBuildModePlayers().remove(player);
                        player.sendMessage(SkyWars.getPrefix()
                                + new LanguageManager().getString(player, "language.messages.build.disabled.self"));
                    }
                } else if (args.length == 1) {

                    Player target = Bukkit.getPlayer(args[0]);

                    if (target.isOnline()) {
                        if (!(SkyWars.getBuildModePlayers().contains(target))) {

                            SkyWars.getBuildModePlayers().add(target);

                            player.sendMessage(SkyWars.getPrefix()
                                    + new LanguageManager().getString(player, "language.messages.build.enabled.other")
                                    .replace("%player%", target.getName()));

                            target.sendMessage(SkyWars.getPrefix()
                                    + new LanguageManager().getString(target,
                                    "language.messages.build.enabled.self"));

                        } else {

                            SkyWars.getBuildModePlayers().remove(target);

                            player.sendMessage(SkyWars.getPrefix()
                                    + new LanguageManager().getString(player, "language.messages.build.disabled.other")
                                    .replace("%player%", target.getName()));

                            target.sendMessage(SkyWars.getPrefix()
                                    + new LanguageManager().getString(target,
                                    "language.messages.build.disabled.self"));
                        }
                    } else {

                        player.sendMessage(SkyWars.getPrefix()
                                + new LanguageManager().getString(player, "language.command.online"));
                    }
                } else {

                    player.sendMessage(SkyWars.getPrefix()
                            + new LanguageManager().getString(player, "language.command.usage")
                            + "/build §8[§c%player%§8]".replace("%player%", new LanguageManager().getString(player,
                            "language.message.variable.player")));
                }
            } else {

                player.sendMessage(SkyWars.getPrefix()
                        + new LanguageManager().getString(player, "language.command.insufficient"));
            }
        }

        return false;
    }
}
