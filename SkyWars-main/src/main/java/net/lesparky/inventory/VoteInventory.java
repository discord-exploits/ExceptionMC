package net.lesparky.inventory;

import net.lesparky.SkyWars;
import net.lesparky.api.language.LanguageManager;
import net.lesparky.api.spigot.game.voting.MapManager;
import net.lesparky.api.spigot.game.voting.MapVoting;
import net.lesparky.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class VoteInventory {

    public void open(Player player) {

        MapVoting.votingInventory = Bukkit.createInventory(null, 9 * 3,
                new LanguageManager().getString(player, "language.inventory.voting.title"));

        ItemStack glass1 = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 7).setDisplayName(" ").build();
        ItemStack glass2 = new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 0).setDisplayName(" ").build();

        Bukkit.getScheduler().runTaskLater(SkyWars.getPlugin(), () -> {
            MapVoting.getVotingInventory().setItem(0, glass2);
            MapVoting.getVotingInventory().setItem(1, glass1);
            MapVoting.getVotingInventory().setItem(2, glass1);
            MapVoting.getVotingInventory().setItem(3, glass1);
            MapVoting.getVotingInventory().setItem(4, glass2);
            MapVoting.getVotingInventory().setItem(5, glass1);
            MapVoting.getVotingInventory().setItem(6, glass1);
            MapVoting.getVotingInventory().setItem(7, glass1);
            MapVoting.getVotingInventory().setItem(8, glass2);
            MapVoting.getVotingInventory().setItem(9, glass1);

            MapVoting.getVotingInventory().setItem(17, glass1);
            MapVoting.getVotingInventory().setItem(18, glass2);
            MapVoting.getVotingInventory().setItem(19, glass1);
            MapVoting.getVotingInventory().setItem(20, glass1);
            MapVoting.getVotingInventory().setItem(21, glass1);
            MapVoting.getVotingInventory().setItem(22, glass2);
            MapVoting.getVotingInventory().setItem(23, glass1);
            MapVoting.getVotingInventory().setItem(24, glass1);
            MapVoting.getVotingInventory().setItem(25, glass1);
            MapVoting.getVotingInventory().setItem(26, glass2);
        }, 5);

        Bukkit.getScheduler().runTaskLater(SkyWars.getPlugin(), () -> {
            MapVoting.getVotingInventory().setItem(10, glass2);
            MapVoting.getVotingInventory().setItem(12, glass2);
            MapVoting.getVotingInventory().setItem(13, glass1);
            MapVoting.getVotingInventory().setItem(14, glass2);
            MapVoting.getVotingInventory().setItem(16, glass2);

            for (int i = 0; i < MapVoting.getVotingMaps().length; i++) {

                MapManager currentMap = MapVoting.getVotingMaps()[i];

                MapVoting.getVotingInventory().setItem(MapVoting.getVotingInventoryOrder()[i],
                        new ItemBuilder(Material.PAPER).setDisplayName("§8» §c" +
                                currentMap.getName()).setLore(" ", new LanguageManager().getString(player,
                                "language.message.countdown.start.builder") + currentMap.getBuilder(),
                                new LanguageManager().getString(player,
                                        "language.message.countdown.start.votes")
                                        + currentMap.getVotes(), " ").build());
            }
        }, 10);

        player.openInventory(MapVoting.getVotingInventory());
    }
}
