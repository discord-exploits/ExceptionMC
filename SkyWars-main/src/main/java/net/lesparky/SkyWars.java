package net.lesparky;

import net.lesparky.api.spigot.API;
import net.lesparky.api.spigot.game.gameconfig.GameConfig;
import net.lesparky.api.spigot.game.gamestate.GameStateManager;
import net.lesparky.api.spigot.game.team.TeamManager;
import net.lesparky.commands.BuildCommand;
import net.lesparky.commands.SetupCommand;
import net.lesparky.commands.StartCommand;
import net.lesparky.config.LootConfig;
import net.lesparky.events.*;
import net.lesparky.util.UpdateSlotsUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class SkyWars extends JavaPlugin {

    public static int MIN_PLAYERS;
    private static final String prefix = "§8» §c§lLeSparky §8× §7";
    public static SkyWars plugin;
    private static ArrayList<Player> buildModePlayers;
    private static ArrayList<Player> spectatorPlayers;

    @Override
    public void onEnable() {

        plugin = this;

        API.setGameAPI(true);
        API.setLocationConfig(true);
        API.setPrefix(prefix);

        buildModePlayers = new ArrayList<>();
        spectatorPlayers = new ArrayList<>();

        GameStateManager.setGameState(GameStateManager.lobbyState);

        new LootConfig().create();

        new UpdateSlotsUtil().updateSlots(new GameConfig().getGameSize().get(0)
                * new GameConfig().getGameSize().get(1));

        MIN_PLAYERS = Bukkit.getMaxPlayers() / 2;

        registerEvents(Bukkit.getPluginManager());
        registerCommands();
        TeamManager.initTeams();

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "§cDas SkyWars Plugin wurde geladen!");
    }

    @Override
    public void onDisable() {

        Bukkit.getConsoleSender().sendMessage(getPrefix() + "§cDas SkyWars Plugin wurde entladen!");
    }

    public void registerEvents(PluginManager pluginManager) {

        pluginManager.registerEvents(new BlockBreakEvent(), this);
        pluginManager.registerEvents(new ClickEvent(), this);
        pluginManager.registerEvents(new DropEvent(), this);
        pluginManager.registerEvents(new AsyncPlayerChatEvent(), this);
        pluginManager.registerEvents(new PlayerJoinEvent(), this);
        pluginManager.registerEvents(new BlockPlaceEvent(), this);
        pluginManager.registerEvents(new PlayerQuitEvent(), this);
        pluginManager.registerEvents(new PlayerInteractEvent(), this);
        pluginManager.registerEvents(new PlayerDeathEvent(), this);
    }

    public void registerCommands() {

        getCommand("build").setExecutor(new BuildCommand());
        getCommand("setup").setExecutor(new SetupCommand());
        getCommand("start").setExecutor(new StartCommand());
    }

    public static SkyWars getPlugin() {

        return plugin;
    }

    public static String getPrefix() {

        return prefix;
    }

    public static ArrayList<Player> getBuildModePlayers() {

        return buildModePlayers;
    }

    public static ArrayList<Player> getSpectatorPlayers() {

        return spectatorPlayers;
    }
}
