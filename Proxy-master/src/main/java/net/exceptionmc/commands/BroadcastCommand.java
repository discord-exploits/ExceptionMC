package net.exceptionmc.commands;

import net.exceptionmc.Proxy;
import net.exceptionmc.util.LanguageUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class BroadcastCommand extends Command {

    public BroadcastCommand(String name, String permission, String... aliases) {

        super(name, permission, aliases);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

            if (proxiedPlayer.hasPermission("exception.broadcast")) {
                if (strings.length > 0) {

                    StringBuilder message = new StringBuilder();

                    for (String string : strings)
                        message.append(string).append(" ");

                    message = new StringBuilder(
                            ChatColor.translateAlternateColorCodes('&', message.toString())
                    );

                    for (ProxiedPlayer proxiedPlayers : BungeeCord.getInstance().getPlayers()) {

                        String broadcastPrefix = ChatColor.translateAlternateColorCodes('&',
                                new LanguageUtil().getString(proxiedPlayers.getUniqueId().toString(), "CSY6DUuW"));

                        proxiedPlayers.sendMessage(new TextComponent(" ").toLegacyText());
                        proxiedPlayers.sendMessage(new TextComponent(broadcastPrefix + message).toLegacyText());
                        proxiedPlayers.sendMessage(new TextComponent(" ").toLegacyText());
                    }

                } else {

                    String usage = new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "J8BxpV7y");
                    proxiedPlayer.sendMessage(new TextComponent(
                            new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "CSY6DUuW")
                                    + usage + "§9/" + getName() + " §8<§9message§8>§8.").toLegacyText());
                }
            } else {

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                        new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "HwIw1MJu"))
                        .toLegacyText());
            }
        }
    }
}
