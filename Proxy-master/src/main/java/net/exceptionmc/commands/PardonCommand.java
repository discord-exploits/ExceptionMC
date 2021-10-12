package net.exceptionmc.commands;

import net.exceptionmc.Proxy;
import net.exceptionmc.punish.PunishmentManager;
import net.exceptionmc.util.LanguageUtil;
import net.exceptionmc.util.UniqueIdFetcher;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class PardonCommand extends Command {

    public PardonCommand(String name) {

        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

            if (proxiedPlayer.hasPermission("exception.pardon")) {
                if (strings.length == 1) {

                    String target = strings[0];
                    String uuid = new UniqueIdFetcher().getUniqueId(target);

                    if (new PunishmentManager().alreadyPunished(uuid)) {

                        new PunishmentManager().pardon(uuid);

                        for (ProxiedPlayer proxiedPlayers : BungeeCord.getInstance().getPlayers()) {
                            if (proxiedPlayers.hasPermission("exception.notify")) {

                                String uuids = proxiedPlayers.getUniqueId().toString();

                                proxiedPlayers.sendMessage(new TextComponent(Proxy.prefix +
                                        new LanguageUtil().getString(uuids, "0KQV5ZC3")
                                                .replace("$target", target)
                                                .replace("$executor", proxiedPlayer.getName())).toLegacyText()
                                );
                            }
                        }
                    } else {

                        proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                                new LanguageUtil().getString(uuid, "q3oTfN0o")
                                        .replace("$target", target)).toLegacyText()
                        );
                    }
                } else {

                    proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                            new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "J8BxpV7y")
                            + "§9/" + getName() + " §7<§9target§7>").toLegacyText());
                }
            } else {

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                        new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "HwIw1MJu"))
                        .toLegacyText());
            }
        }
    }
}
