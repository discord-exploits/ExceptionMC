package net.exceptionmc.commands;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import net.exceptionmc.Proxy;
import net.exceptionmc.util.LanguageUtil;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

@SuppressWarnings("deprecation")
public class BuildServerCommand extends Command {

    public BuildServerCommand(String name) {

        super(name);
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {

            ProxiedPlayer proxiedPlayer = (ProxiedPlayer) commandSender;
            String uuid = proxiedPlayer.getUniqueId().toString();

            if (proxiedPlayer.hasPermission("exception.buildServer")) {
                if (BungeeCord.getInstance().getServerInfo("BuildServer-1") != null) {

                    ServerInfo serverInfo = BungeeCord.getInstance().getServerInfo("BuildServer-1");

                    IPlayerManager iPlayerManager
                            = CloudNetDriver.getInstance().getServicesRegistry().getFirstService(IPlayerManager.class);

                    ICloudPlayer iCloudPlayer = iPlayerManager.getOnlinePlayer(proxiedPlayer.getUniqueId());

                    assert iCloudPlayer != null;
                    iCloudPlayer.getPlayerExecutor().connect(serverInfo.getName());
                } else {

                    proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                            new LanguageUtil().getString(uuid, "zEIbSjYV")).toLegacyText());
                }
            } else {

                proxiedPlayer.sendMessage(new TextComponent(Proxy.prefix +
                        new LanguageUtil().getString(proxiedPlayer.getUniqueId().toString(), "HwIw1MJu"))
                        .toLegacyText());
            }
        }
    }
}
