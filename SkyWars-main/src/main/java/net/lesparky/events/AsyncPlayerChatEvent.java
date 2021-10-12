package net.lesparky.events;

import net.lesparky.SkyWars;
import net.lesparky.api.spigot.config.LocationConfig;
import net.lesparky.api.spigot.game.voting.MapManager;
import net.lesparky.commands.SetupCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class AsyncPlayerChatEvent implements Listener {

    public static int spawnsCount = 1;

    @EventHandler
    public void on(org.bukkit.event.player.AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        String message = event.getMessage();

        if (SetupCommand.lobbyBoolean) {

            if (message.equalsIgnoreCase("setzen")) {

                event.setCancelled(true);

                SetupCommand.running = false;
                SetupCommand.lobbyBoolean = false;
                SetupCommand.lobbyLocation = player.getLocation();

                new LocationConfig().setLocation("lobby", SetupCommand.lobbyLocation);

                player.sendMessage(SkyWars.getPrefix()
                        + "Die §cLobby §7wurde erfolgreich gesetzt.");
            }

        } else if (SetupCommand.nameBoolean) {

            event.setCancelled(true);

            SetupCommand.nameBoolean = false;
            SetupCommand.builderBoolean = true;
            SetupCommand.nameString = message;

            player.sendMessage(SkyWars.getPrefix()
                    + "Bitte schreibe nun den Namen des Builders in den Chat!");

        } else if (SetupCommand.builderBoolean) {

            event.setCancelled(true);

            SetupCommand.builderBoolean = false;
            SetupCommand.spectatorBoolean = true;
            SetupCommand.builderString = message;

            player.sendMessage(SkyWars.getPrefix()
                    + "Bitte begebe dich nun zum Spectator-Spawn und schreibe §csetzen §7in den Chat.");
        } else if (SetupCommand.spectatorBoolean) {
            if (message.equalsIgnoreCase("setzen")) {

                event.setCancelled(true);

                SetupCommand.spectatorBoolean = false;
                SetupCommand.spawnsBoolean = true;
                SetupCommand.spectatorLocation = player.getLocation();

                player.sendMessage(SkyWars.getPrefix()
                        + "Bitte begebe dich nun zum einem Spawn und schreibe §csetzen §7in den Chat.");
            }
        } else if (SetupCommand.spawnsBoolean) {
            if (message.equalsIgnoreCase("setzen")) {

                event.setCancelled(true);

                if (spawnsCount <= Bukkit.getMaxPlayers()) {

                    spawnsCount++;

                    SetupCommand.spawnsLocation.add(player.getLocation());

                    if (spawnsCount <= Bukkit.getMaxPlayers()) {

                        player.sendMessage(SkyWars.getPrefix()
                                + "Bitte begebe dich nun zum nächsten Spawn und schreibe §csetzen §7in den Chat.");
                    } else {

                        SetupCommand.spawnsBoolean = false;
                        SetupCommand.innerChestBoolean = true;

                        player.sendMessage(SkyWars.getPrefix()
                                + "Bitte begebe dich nun zu den Kisten in der Mitte und schlage diese. Wenn du alle "
                                + "Kisten geschlagen hast, schreibe bitte §cgesetzt §7in den Chat.");
                    }
                }
            }
        } else if (SetupCommand.innerChestBoolean) {

            if (message.equalsIgnoreCase("gesetzt")) {

                event.setCancelled(true);

                SetupCommand.innerChestBoolean = false;
                SetupCommand.outerChestBoolean = true;

                player.sendMessage(SkyWars.getPrefix()
                        + "Bitte begebe dich nun zu den Kisten außerhalb der Mitte und schlage diese. Wenn du alle "
                        + "Kisten geschlagen hast, schreibe bitte §cgesetzt §7in den Chat.");
            }
        } else if (SetupCommand.outerChestBoolean) {

            if (message.equalsIgnoreCase("gesetzt")) {

                event.setCancelled(true);

                SetupCommand.running = false;
                SetupCommand.outerChestBoolean = false;

                new MapManager(SetupCommand.nameString).create(SetupCommand.builderString);

                new LocationConfig().setLocation(SetupCommand.nameString
                        + ".spectator", SetupCommand.spectatorLocation);

                for (int i = 1; i <= SetupCommand.spawnsLocation.size(); i++) {

                    Location location = SetupCommand.spawnsLocation.get(i - 1);

                    new LocationConfig().setLocation(SetupCommand.nameString + ".spawn." + i, location);
                }

                for (int i = 1; i <= SetupCommand.innerChestsLocation.size(); i++) {

                    Location location = SetupCommand.innerChestsLocation.get(i - 1);

                    new LocationConfig().setLocation(SetupCommand.nameString + ".chest.inner." + i, location);
                }

                for (int i = 1; i <= SetupCommand.outerChestsLocation.size(); i++) {

                    Location location = SetupCommand.outerChestsLocation.get(i - 1);

                    new LocationConfig().setLocation(SetupCommand.nameString + ".chest.outer." + i, location);
                }

                SetupCommand.spawnsLocation = new ArrayList<>();
                SetupCommand.innerChestsLocation = new ArrayList<>();
                SetupCommand.outerChestsLocation = new ArrayList<>();

                player.sendMessage(SkyWars.getPrefix()
                        + "Das Setup wurde erfolgreich beendet. Der Server startet in §c10 §7Sekunden neu!");

                Bukkit.getScheduler().scheduleAsyncDelayedTask(SkyWars.getPlugin(), new Runnable() {
                    @Override
                    public void run() {

                        Bukkit.broadcastMessage(SkyWars.getPrefix()
                                + "Der Server startet nun neu!");
                        Bukkit.shutdown();
                    }
                }, 200);
            }
        }
    }
}
