package me.hopesless.commands;

import me.hopesless.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class teamchat extends Command
{
    public teamchat() {
        super("tc");
    }

    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer) {
            final ProxiedPlayer p = (ProxiedPlayer)sender;
            if (p.hasPermission("tc.see")) {
                if (args.length > 0) {
                    if (args[0].equalsIgnoreCase("login")) {
                        if (!Main.login.contains(p)) {
                            for (final ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                                if (Main.login.contains(all)) {
                                    all.sendMessage(String.valueOf(Main.ConfigPrefix) + " Der Spieler §a" + p.getDisplayName() + "§7 hat sich eingeloggt!");
                                }
                                else {
                                    Main.login.contains(all);
                                }
                            }
                            Main.login.add(p);
                            p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Du hast dich eingeloggt!");
                        }
                        else if (Main.login.contains(p)) {
                            p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Du bist bereits eingeloggt!");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("logout")) {
                        if (Main.login.contains(p)) {
                            Main.login.remove(p);
                            p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Du hast dich ausgeloggt!");
                            if (!Main.login.contains(p)) {
                                for (final ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                                    if (Main.login.contains(all)) {
                                        all.sendMessage(String.valueOf(Main.ConfigPrefix) + " Der Spieler §a" + p.getDisplayName() + " §7hat sich ausgeloggt!");
                                    }
                                    else {
                                        Main.login.contains(all);
                                    }
                                }
                            }
                        }
                        else if (!Main.login.contains(p)) {
                            p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Du bist bereits ausgeloggt!");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("help")) {
                        p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Nutze: <§alogin §7| §clogout §7| §elist§7 | §bNachricht §7| §3add§7/§3remove list§7>");
                    }
                    else if (args[0].equalsIgnoreCase("list")) {
                        if (Main.login.contains(p)) {
                            p.sendMessage("");
                            p.sendMessage(String.valueOf(Main.ConfigPrefix) + "§8----------=" + Main.ConfigPrefix + "§8=----------");
                            for (final ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                                if (Main.login.contains(all)) {
                                    if (!Main.listed.contains(all)) {
                                        p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Der Spieler §a" + all.getName() + " §7ist §aeingeloggt §7auf: §a§l" + all.getServer().getInfo().getName());
                                    }
                                    else {
                                        p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Der Spieler §a" + all.getName() + " §7ist §aeingeloggt §7auf: §a§lHIDDEN");
                                    }
                                }
                            }
                            p.sendMessage(String.valueOf(Main.ConfigPrefix) + "§8----------=" + Main.ConfigPrefix + "§8=----------");
                        }
                        else {
                            p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Du musst eingeloggt sein!");
                        }
                    }
                    else if (args[0].equalsIgnoreCase("add")) {
                        if (args[1].equalsIgnoreCase("list")) {
                            if (Main.login.contains(p)) {
                                if (!Main.listed.contains(p)) {
                                    Main.listed.add(p);
                                    p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Dein §aaktueller Server §7ist nun im TeamChat §aversteckt§7!");
                                }
                                else {
                                    p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Dein aktueller Server ist bereits nicht zu sehen!");
                                }
                            }
                            else {
                                p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Du musst eingeloggt sein!");
                            }
                        }
                    }
                    else if (args[0].equalsIgnoreCase("remove")) {
                        if (args[1].equalsIgnoreCase("list")) {
                            if (Main.login.contains(p)) {
                                if (Main.listed.contains(p)) {
                                    Main.listed.remove(p);
                                    p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Dein §aaktueller Server §7ist nun wieder im TeamChat §azu sehen§7!");
                                }
                                else {
                                    p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Dein aktueller Server ist bereits zu sehen!");
                                }
                            }
                            else {
                                p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Du musst eingeloggt sein!");
                            }
                        }
                    }
                    else if (Main.login.contains(p)) {
                        for (final ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                            if (all.hasPermission("tc.see")) {
                                if (Main.login.contains(all)) {
                                    String nachricht = "";
                                    for (int i = 0; i < args.length; ++i) {
                                        nachricht = String.valueOf(nachricht) + " " + args[i];
                                    }
                                    all.sendMessage(String.valueOf(Main.ConfigPrefix) + " §a" + p.getDisplayName() + " §7\u27a1§7" + nachricht);
                                }
                                else {
                                    Main.login.contains(all);
                                }
                            }
                        }
                    }
                    else if (!Main.login.contains(p)) {
                        p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Du musst eingeloggt sein!");
                    }
                }
                else if (args.length == 0) {
                    p.sendMessage(String.valueOf(Main.ConfigPrefix) + " Nutze: <§alogin §7| §clogout §7| §elist§7 | §bNachricht §7| §3add§7/§3remove list§7>");
                }
            }
            else {
                p.sendMessage("§8>> §cKeine Rechte!");
            }
        }
        else {
            sender.sendMessage("§cMust be a player!");
        }
    }
}
