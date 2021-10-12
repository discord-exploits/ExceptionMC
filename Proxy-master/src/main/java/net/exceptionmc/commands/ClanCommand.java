package net.exceptionmc.commands;

import net.exceptionmc.Proxy;
import net.exceptionmc.util.ClanUtil;
import net.exceptionmc.util.CoinsUtil;
import net.exceptionmc.util.LanguageUtil;
import net.exceptionmc.util.UniqueIdFetcher;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.ArrayList;

@SuppressWarnings("all")
public class ClanCommand extends Command {

    private final ArrayList<ProxiedPlayer> creation = new ArrayList<>();

    public ClanCommand(String name) {

        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {

        ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;

        if (strings.length == 1) {

            // /clan info
            // /clan stats
            if (strings[0].equalsIgnoreCase("help")) {

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                        new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "bBJYB4pQ")));

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan help").toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan create <name> <tag>")
                        .toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan invite <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan info [clan]").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan stats [clan]").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan promote <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan chat <message>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan kick <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan ban <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan jump <target>").toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan jump toggle true/false")
                        .toLegacyText());
            } else {

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                        new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "bBJYB4pQ")));

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan help").toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan create <name> <tag>")
                        .toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan invite <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan info [clan]").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan stats [clan]").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan promote <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan chat <message>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan kick <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan ban <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan jump <target>").toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan jump toggle true/false")
                        .toLegacyText());
            }
        } else if (strings.length == 2) {

            //  /clan promote <Player>
            //  /clan chat <Message>
            //  /clan kick <Player>
            //  /clan ban <Player>
            //  /clan jump <Player>

            if (strings[0].equalsIgnoreCase("invite")) {
                if (BungeeCord.getInstance().getPlayer(strings[1]) != null) {

                    ProxiedPlayer targetProxiedPlayer = BungeeCord.getInstance().getPlayer(strings[1]);

                    if (!new ClanUtil().isInClan(targetProxiedPlayer.getUniqueId().toString())) {

                        new ClanUtil().invite(targetProxiedPlayer.getUniqueId().toString(),
                                new ClanUtil().getClan(proxiedPlayer.getUniqueId().toString()));

                        proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                                .getString(proxiedPlayer.getUniqueId().toString(), "RCykFpIe")
                                .replace("$target", targetProxiedPlayer.getName())).toLegacyText());

                        TextComponent textComponent = new TextComponent(Proxy.prefix + new LanguageUtil()
                                .getString(targetProxiedPlayer.getUniqueId().toString(), "zzkMdG8d")
                                .replace("$player", proxiedPlayer.getName())
                                .replace("$clan", new ClanUtil().getClanName(new ClanUtil()
                                        .getClan(proxiedPlayer.getUniqueId().toString()))));

                        textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan accept "
                                + new ClanUtil().getClan(proxiedPlayer.getUniqueId().toString())));

                        targetProxiedPlayer.sendMessage(textComponent);
                    } else {

                        proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                                .getString(proxiedPlayer.getUniqueId().toString(), "nRlIWsN8")
                                .replace("$target", targetProxiedPlayer.getName())).toLegacyText());
                    }
                } else {

                    proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                            .getString(proxiedPlayer.getUniqueId().toString(), "5JnIVxvj")).toLegacyText());
                }
            } else if (strings[0].equalsIgnoreCase("accept")) {

                String clanId = strings[1];
                if (!new ClanUtil().isInClan(proxiedPlayer.getUniqueId().toString())) {
                    if (new ClanUtil().isInvited(proxiedPlayer.getUniqueId().toString(), clanId)) {
                        for (String currentUniqueId : new ClanUtil().getClanMembers(clanId)) {
                            if (BungeeCord.getInstance().getPlayer(new UniqueIdFetcher()
                                    .getLastUsedName(currentUniqueId)) != null) {

                                ProxiedPlayer clanMembers = BungeeCord.getInstance()
                                        .getPlayer(new UniqueIdFetcher().getLastUsedName(currentUniqueId));
                                clanMembers.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                                        .getString(clanMembers.getUniqueId().toString(), "fQ9Z2eoa")
                                        .replace("$player", proxiedPlayer.getName())).toLegacyText());
                            }
                        }

                        proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                                .getString(proxiedPlayer.getUniqueId().toString(), "3oLLBHUU")
                                .replace("$clan", new ClanUtil().getClanName(clanId))).toLegacyText());

                        new ClanUtil().acceptInvitation(proxiedPlayer.getUniqueId().toString(), clanId);
                    } else {

                        proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                                .getString(proxiedPlayer.getUniqueId().toString(), "UJE4gKFy")
                                .replace("$clan", new ClanUtil().getClanName(clanId))).toLegacyText());
                    }
                } else {

                    proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + new LanguageUtil()
                            .getString(proxiedPlayer.getUniqueId().toString(), "cfQyoiQQ")).toLegacyText());
                }
            } else {

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                        new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "J8BxpV7y") + "§" +
                        new LanguageUtil().getColor(proxiedPlayer.getUniqueId().toString()) + "/clan help")
                        .toLegacyText());
            }
        } else if (strings.length == 3) {

            String clan = strings[1];
            String clanTag = strings[2];

            if (strings[0].equalsIgnoreCase("create")) {
                if (new CoinsUtil().getCoins(proxiedPlayer.getUniqueId().toString()) >= 1000) {
                    if (!new ClanUtil().isInClan(proxiedPlayer.getUniqueId().toString())) {
                        if (!new ClanUtil().clanNameExists(clan)) {
                            if (!new ClanUtil().clanTagExists(clanTag)) {
                                if (creation.contains(proxiedPlayer)) {

                                    new ClanUtil().createClan(proxiedPlayer.getUniqueId().toString(), clan, clanTag);
                                    new CoinsUtil().subtractCoins(proxiedPlayer.getUniqueId().toString(), 1000);
                                    creation.remove(proxiedPlayer);

                                    proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                                            new LanguageUtil()
                                                    .getString(proxiedPlayer.getUniqueId().toString(), "HEgb6lWa")
                                                    .replace("$clan", clan)
                                                    .replace("$clanTag", clanTag)).toLegacyText());
                                } else {

                                    creation.add(proxiedPlayer);
                                    TextComponent textComponent = new TextComponent(Proxy.prefix +
                                            new LanguageUtil()
                                                    .getString(proxiedPlayer.getUniqueId().toString(), "zkc4Fp9q")
                                                    .replace("$clan", clan)
                                                    .replace("$clanTag", clanTag));

                                    textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                                            "/clan create " + clan + " " + clanTag));

                                    proxiedPlayer.sendMessage(textComponent);
                                }
                            } else {

                                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                                        new LanguageUtil()
                                                .getString(proxiedPlayer.getUniqueId().toString(), "Aujm7HkF")
                                                .replace("$clanTag", clanTag)).toLegacyText());
                            }
                        } else {

                            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                                    new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "kaWdmZvu")
                                            .replace("$clan", clan)).toLegacyText());
                        }
                    } else {

                        proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                                new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "cfQyoiQQ"))
                                .toLegacyText());
                    }
                } else {

                    proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                            new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "jJK88oi4")));
                }
            } else {

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                        new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "bBJYB4pQ")));

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan help").toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan create <name> <tag>")
                        .toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan invite <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan info [clan]").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan stats [clan]").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan promote <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan chat <message>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan kick <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan ban <target>").toLegacyText());
                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan jump <target>").toLegacyText());

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan jump toggle true/false")
                        .toLegacyText());
            }
        } else {

            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                    new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "bBJYB4pQ")));

            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan help").toLegacyText());

            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan create <name> <tag>")
                    .toLegacyText());

            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan invite <target>").toLegacyText());
            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan info [clan]").toLegacyText());
            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan stats [clan]").toLegacyText());
            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan promote <target>").toLegacyText());
            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan chat <message>").toLegacyText());
            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan kick <target>").toLegacyText());
            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan ban <target>").toLegacyText());
            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan jump <target>").toLegacyText());

            proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix + "/clan jump toggle true/false")
                    .toLegacyText());
        }
    }
}
