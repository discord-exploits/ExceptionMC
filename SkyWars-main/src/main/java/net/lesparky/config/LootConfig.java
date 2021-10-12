package net.lesparky.config;

import net.lesparky.SkyWars;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LootConfig {

    public File file = new File(SkyWars.getPlugin().getDataFolder(), "loot.yml");
    public FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration(file);

    public void create() {

        if (!file.exists()) {

            fileConfiguration.set("innerChest.example1.material", Material.DIAMOND_SWORD.name());
            fileConfiguration.set("innerChest.example1.percentage", "50");
            fileConfiguration.set("innerChest.example1.minAmount", "1");
            fileConfiguration.set("innerChest.example1.maxAmount", "1");

            fileConfiguration.set("innerChest.example2.material", Material.WOOD.name());
            fileConfiguration.set("innerChest.example2.percentage", "75");
            fileConfiguration.set("innerChest.example2.minAmount", "1");
            fileConfiguration.set("innerChest.example2.maxAmount", "20");

            fileConfiguration.set("outerChest.example1.material", Material.DIAMOND_SWORD.name());
            fileConfiguration.set("outerChest.example1.percentage", "50");
            fileConfiguration.set("outerChest.example1.minAmount", "1");
            fileConfiguration.set("outerChest.example1.maxAmount", "1");

            fileConfiguration.set("outerChest.example2.material", Material.WOOD.name());
            fileConfiguration.set("outerChest.example2.percentage", "75");
            fileConfiguration.set("outerChest.example2.minAmount", "1");
            fileConfiguration.set("outerChest.example2.maxAmount", "20");

            try {

                fileConfiguration.save(file);
            } catch (IOException ioException) {

                ioException.printStackTrace();
            }
        }
    }

    public String get(String path) {

        return fileConfiguration.getString(path);
    }

}
