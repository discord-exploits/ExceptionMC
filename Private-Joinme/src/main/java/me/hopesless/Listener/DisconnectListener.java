package me.hopesless.Listener;

import me.hopesless.main.Main;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectListener implements Listener
{
    @EventHandler
    public void onDisconnectListener(final PlayerDisconnectEvent e) {
        final ProxiedPlayer p = e.getPlayer();
        if (Main.login.contains(p)) {
            Main.login.remove(p);
            for (final ProxiedPlayer all : BungeeCord.getInstance().getPlayers()) {
                if (Main.login.contains(all)) {
                    all.sendMessage(String.valueOf(Main.ConfigPrefix) + " Der Spieler §a" + p.getDisplayName() + " §7hat den Server &averlassen §7und wurde somit §aausgeloggt§7!");

                }
            }
        }
        if (Main.listed.contains(p)) {
            Main.listed.remove(p);
        }
    }

}
