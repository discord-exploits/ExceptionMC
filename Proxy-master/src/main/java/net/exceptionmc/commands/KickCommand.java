package net.exceptionmc.commands;

import net.exceptionmc.Proxy;
import net.exceptionmc.util.LanguageUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class KickCommand extends Command {

    //ToDo: Implement LanguageUtil

    public KickCommand(String name) {

        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

            if (proxiedPlayer.hasPermission("exception.kick")) {
                if (strings.length == 0) {

                    proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                            new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "J8BxpV7y") +
                            "§9/" + getName() + " §7<§9target§7> §7[§9reasons§7]§8.").toLegacyText());

                } else if (strings.length == 1) {
                    if (BungeeCord.getInstance().getPlayer(strings[0]) != null) {

                        ProxiedPlayer proxiedPlayer1 = BungeeCord.getInstance().getPlayer(strings[0]);

                        if (!proxiedPlayer1.getName().equals(proxiedPlayer.getName())) {

                            for (ProxiedPlayer allProxiedPlayers : BungeeCord.getInstance().getPlayers()) {
                                if (allProxiedPlayers.hasPermission("exception.notify")) {

                                    allProxiedPlayers.sendMessage(
                                            new TextComponent(Proxy.prefix + new LanguageUtil().getString(allProxiedPlayers.getUniqueId().toString(), "cf8aMG8b".replace("target", proxiedPlayer1.getName()).replace("$executor", proxiedPlayer.getName())
                                            )).toLegacyText()
                                    );
                                }
                            }

                            proxiedPlayer1.disconnect(new TextComponent(
                                    "§9ExceptionMC" + new LanguageUtil().getString(proxiedPlayer1.getUniqueId().toString(), "NryTLMII")));
                        } else {

                            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                                    .getString(proxiedPlayer.getUniqueId().toString(), "Bkv3AdFr"))
                                    .toLegacyText());
                        }

                    } else {

                        proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                                .getString(proxiedPlayer.getUniqueId().toString(), "5JnIVxvj"))
                                .toLegacyText());
                    }
                } else {
                    if (BungeeCord.getInstance().getPlayer(strings[0]) != null) {

                        ProxiedPlayer proxiedPlayer1 = BungeeCord.getInstance().getPlayer(strings[0]);

                        if (!proxiedPlayer1.getName().equals(proxiedPlayer.getName())) {

                            StringBuilder reason = new StringBuilder();
                            for (int integer = 1; integer < strings.length; integer++) {

                                reason.append(strings[integer]);
                                if (integer < strings.length - 1) {

                                    reason.append(" ");
                                }
                            }

                            for (ProxiedPlayer allProxiedPlayers : BungeeCord.getInstance().getPlayers()) {
                                if (allProxiedPlayers.hasPermission("exception.notify")) {

                                    allProxiedPlayers.sendMessage(new TextComponent(
                                            Proxy.prefix + new LanguageUtil().getString(allProxiedPlayers.getUniqueId().toString(), "id0XXTSu").replace("$target", proxiedPlayer1.getName()).replace("$executor", proxiedPlayer.getName()).replace("$reason", reason)).toLegacyText()
                                    );
                                }
                            }

                            proxiedPlayer1.disconnect(new TextComponent(
                                    "§9ExceptionMC" + new LanguageUtil().getString(proxiedPlayer1.getUniqueId().toString(), "xcuB7SPB".replace("$reason", reason))));
                        } else {

                            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                                    .getString(proxiedPlayer.getUniqueId().toString(), "Bkv3AdFr")).toLegacyText());
                        }
                    } else {

                        proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                                new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "5JnIVxvj"))
                                .toLegacyText());
                    }
                }
            } else {

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                        new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "HwIw1MJu"))
                        .toLegacyText());
            }
        }
    }
}
