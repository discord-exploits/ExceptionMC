package me.hopesless.main;

import me.hopesless.Listener.DisconnectListener;
import me.hopesless.commands.teamchat;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import javax.security.auth.login.Configuration;
import java.io.File;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;

public final class Main extends Plugin
{
    public static String ConfigPrefix;
    public static ArrayList<ProxiedPlayer> login;
    public static ArrayList<ProxiedPlayer> listed;

    static {
        Main.ConfigPrefix = "&cTeamChat ";
        Main.login = new ArrayList<ProxiedPlayer>();
        Main.listed = new ArrayList<ProxiedPlayer>();
    }

    public void onEnable() {
        this.createFolder();
        this.getProxy().getPluginManager().registerCommand((Plugin)this, (Command) new teamchat());
        this.getProxy().getPluginManager().registerListener((Plugin)this, (Listener) new DisconnectListener());
    }

    public void onDisable() {
    }

    public void createFolder() {
        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdir();
            }
            final File file = new File(this.getDataFolder().getPath(), "config.yml");
            if (!file.exists()) {
                Files.copy(this.getResourceAsStream("config.yml"), file.toPath(), new CopyOption[0]);
            }
            final Configuration config = ConfigurationProvider.getProvider((Class) YamlConfiguration.class).load(file);
            Main.ConfigPrefix = config.getString("Prefix");
        }
        catch (Exception ex) {}
    }
}
