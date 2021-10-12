package net.lesparky.chests;

import net.lesparky.api.spigot.API;
import net.lesparky.api.spigot.config.LocationConfig;
import net.lesparky.api.spigot.game.voting.MapManager;
import net.lesparky.api.spigot.game.voting.MapVoting;
import net.lesparky.config.LootConfig;
import net.lesparky.util.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class OuterChest {

    private static final ArrayList<String> loot = new ArrayList();
    private static ArrayList<String> alreadyAdded = new ArrayList<>();

    public static void fillOuterChests() {

        MapVoting voting = API.getPlugin().getVoting();
        MapManager winnerMap;

        loot.addAll(new LootConfig().fileConfiguration
                .getConfigurationSection("outerChest").getKeys(false));

        if (voting != null) {

            winnerMap = voting.getWinnerMap();
        } else {

            winnerMap = null;
        }

        assert winnerMap != null;
        for (String current : LocationConfig.fileConfiguration.getConfigurationSection("arenas."
                + winnerMap.getName() + ".chest.outer").getKeys(false)) {

            alreadyAdded = new ArrayList<>();

            Block block = new LocationConfig().getLocation("arenas."
                    + winnerMap.getName() + ".chest.outer." + current).getBlock();
            Chest chest = (Chest) block.getState();
            Inventory inventory = chest.getInventory();

            for (int i = 0; i < inventory.getSize(); i++) {

                fill(inventory, i);
            }

            chest.update();
        }
    }

    public static void fill(Inventory inventory, int slot) {

        int random = Utils.rndInt(0, loot.size() - 1);
        int random100 = Utils.rndInt(0, 100);

        if (random100 <= 50) {
            if (!alreadyAdded.contains(loot.get(random))) {

                int percentage = Integer.parseInt(new LootConfig().get("outerChest."
                        + loot.get(random) + ".percentage"));

                if (random100 < percentage) {

                    ItemStack itemStack = new ItemStack(Material.matchMaterial(new LootConfig().get("outerChest."
                            + loot.get(random) + ".material")));

                    itemStack.setAmount(Utils.rndInt(Integer.parseInt(new LootConfig().get("outerChest."
                            + loot.get(random) + ".minAmount")), Integer.parseInt(new LootConfig().get("outerChest."
                            + loot.get(random) + ".maxAmount"))));

                    boolean alreadyAddedExact = false;

                    for (int i = 0; i < alreadyAdded.size(); i++) {
                        String[] alreadyAddedArray = new LootConfig().get("outerChest."
                                + alreadyAdded.get(i) + ".material").split("_");

                        if (alreadyAddedArray.length >= 2) {
                            if (new LootConfig().get("outerChest." + loot.get(random) + ".material")
                                    .endsWith(alreadyAddedArray[1])) {

                                alreadyAddedExact = true;
                            }
                        }
                    }

                    if (!alreadyAddedExact) {

                        inventory.setItem(slot, itemStack);

                        alreadyAdded.add(loot.get(random));
                    } else {

                        fill(inventory, slot);
                    }
                } else {

                    fill(inventory, slot);
                }
            } else {

                fill(inventory, slot);
            }
        }
    }
}

