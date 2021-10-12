
package net.lesparky.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Random;

@SuppressWarnings("unused")
public class ScoreboardManager {

    /**
     * This method is used to set a players scoreboard.
     * @param player is required to set the scoreboard to the provided player.
     */
    public void sendScoreboard(Player player) {

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("aaa", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§8× §cSkyWars §8- §7LeSparky §8×");

        Team team0 = scoreboard.registerNewTeam("coins");
        Team team1 = scoreboard.registerNewTeam("wins");
        Team team2 = scoreboard.registerNewTeam("games");
        Team team3 = scoreboard.registerNewTeam("rank");

        team0.addEntry("§a");
        team1.addEntry("§b");
        team2.addEntry("§c");
        team3.addEntry("§d");

        team0.setPrefix("§8➥ §c" + new Random().nextInt(100));
        team1.setPrefix("§8➥ §c" + new Random().nextInt(100));
        team2.setPrefix("§8➥ §c" + new Random().nextInt(100));
        team3.setPrefix("§8➥ §c" + new Random().nextInt(100));

        objective.getScore("§0").setScore(12);
        objective.getScore("§7Geld").setScore(11);
        objective.getScore("§a").setScore(10);
        objective.getScore("§0 ").setScore(9);
        objective.getScore("§7Gewinne").setScore(8);
        objective.getScore("§b").setScore(7);
        objective.getScore("§0  ").setScore(6);
        objective.getScore("§7Spiele").setScore(5);
        objective.getScore("§c").setScore(4);
        objective.getScore("§0   ").setScore(3);
        objective.getScore("§7Rang").setScore(2);
        objective.getScore("§d").setScore(1);
        objective.getScore("§0    ").setScore(0);

        player.setScoreboard(scoreboard);
    }

    /**
     * This method is used to update the current players scoreboard.
     * @param player is required to get the current scoreboard from the provided player.
     */
    public void update(Player player) {

        Scoreboard scoreboard = player.getScoreboard();

        Team team0 = scoreboard.getTeam("coins");
        Team team1 = scoreboard.getTeam("wins");
        Team team2 = scoreboard.getTeam("games");
        Team team3 = scoreboard.getTeam("rank");

        team0.setPrefix("§8➥ §c" + new Random().nextInt(100));
        team1.setPrefix("§8➥ §c" + new Random().nextInt(100));
        team2.setPrefix("§8➥ §c" + new Random().nextInt(100));
        team3.setPrefix("§8➥ §c" + new Random().nextInt(100));
    }
}