package net.lesparky.commands;

import net.lesparky.SkyWars;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SetupCommand implements CommandExecutor {

    public static boolean running = false;

    public static boolean lobbyBoolean = false;
    public static Location lobbyLocation;

    public static boolean nameBoolean = false;
    public static String nameString;

    public static boolean builderBoolean = false;
    public static String builderString;

    public static boolean spectatorBoolean = false;
    public static Location spectatorLocation;

    public static boolean spawnsBoolean = false;
    public static ArrayList<Location> spawnsLocation = new ArrayList<>();

    public static boolean innerChestBoolean = false;
    public static ArrayList<Location> innerChestsLocation = new ArrayList<>();

    public static boolean outerChestBoolean = false;
    public static ArrayList<Location> outerChestsLocation = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length == 1) {
                if (!running) {
                    if (args[0].equalsIgnoreCase("lobby")) {

                        running = true;
                        lobbyBoolean = true;

                        player.sendMessage(SkyWars.getPrefix()
                                + "Bitte begebe dich zum Spawn der Warte-Lobby und schreibe §csetzen §7in den chat");
                    } else if (args[0].equalsIgnoreCase("create")) {

                        nameBoolean = true;

                        player.sendMessage(SkyWars.getPrefix()
                                + "Bitte schreibe nun den Namen der Map in den Chat!");
                    } else {

                        player.sendMessage(SkyWars.getPrefix()
                                + "Bitte benutze /setup lobby, create");
                    }
                } else {

                    player.sendMessage(SkyWars.getPrefix()
                            + "Aktuell läuft bereits ein Setup!");
                }
            } else {

                player.sendMessage(SkyWars.getPrefix()
                        + "Bitte benutze /setup lobby, create");
            }
        }

        return false;
    }
}
