package net.exceptionmc;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.exceptionmc.commandexecutor.*;
import net.exceptionmc.commandmanager.CommandHandler;
import net.exceptionmc.listeners.*;
import net.exceptionmc.timer.SecondTimer;
import net.exceptionmc.util.*;

import javax.security.auth.login.LoginException;
import java.util.Base64;
import java.util.Timer;

@SuppressWarnings("all")
public class DiscordBot {

    private static final Base64.Decoder decoder = Base64.getDecoder();

    private static final String encodedToken =
            "ODg4NDA0MjUyMDQzNjU3MjI2.YUSM7w.H22qMlnFPI95szoRVt_p5nw_FKY";
    private static final String decodedToken = new String(decoder.decode(encodedToken));

    private static final DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(decodedToken);
    public static ShardManager jda;
    public final Guild guild = jda.getGuildById("842417128559607818");;

    public static void main(String[] args) throws LoginException {

        builder.setToken(decodedToken);
        builder.setAutoReconnect(true);
        builder.setIdle(false);
        builder.setEnableShutdownHook(true);
        builder.enableCache(CacheFlag.VOICE_STATE);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.playing("ScopeRS"));

        builder.enableIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));

        addListeners();
        addCommands();
        startTimer();
        initDatabase();
        initGameModes();

        LevelUtil.initLevel();

        jda = builder.build();
    }

    public static void addCommands() {

        CommandHandler.commandManager.put("clear", new ClearCommand());
        CommandHandler.commandManager.put("play", new PlayCommand());
        CommandHandler.commandManager.put("lock", new LockCommand());
        CommandHandler.commandManager.put("skip", new SkipCommand());
        CommandHandler.commandManager.put("verify", new VerifyCommand());
        CommandHandler.commandManager.put("stats", new StatsCommand());
        CommandHandler.commandManager.put("tictactoe", new TicTacToeCommand());
        CommandHandler.commandManager.put("level", new LevelCommand());
        CommandHandler.commandManager.put("warn", new WarnCommand());

        net.exceptionmc.commandmanager.GuildMessageReceivedEvent.everywhereAllowedCommands.add("clear");
        net.exceptionmc.commandmanager.GuildMessageReceivedEvent.everywhereAllowedCommands.add("lock");
        net.exceptionmc.commandmanager.GuildMessageReceivedEvent.everywhereAllowedCommands.add("tictactoe");
        net.exceptionmc.commandmanager.GuildMessageReceivedEvent.everywhereAllowedCommands.add("warn");
    }

    public static void addListeners() {

        //  Command-System
        builder.addEventListeners(new net.exceptionmc.commandmanager.GuildMessageReceivedEvent());

        //  Listeners
        builder.addEventListeners(new GuildMemberJoinEvent());
        builder.addEventListeners(new GuildMemberRemoveEvent());
        builder.addEventListeners(new GuildMemberUpdateBoostTimeEvent());
        builder.addEventListeners(new GuildMessageReactionAddEvent());
        builder.addEventListeners(new GuildMessageReceivedEvent());
        builder.addEventListeners(new GuildVoiceUpdateEvent());
        builder.addEventListeners(new PrivateMessageReactionAddEvent());
        builder.addEventListeners(new PrivateMessageReceivedEvent());
        builder.addEventListeners(new ReadyEvent());
    }

    public static void startTimer() {

        //  Second Timer
        Timer scondTimerTask = new Timer();
        SecondTimer secondTimer = new SecondTimer();
        scondTimerTask.scheduleAtFixedRate(secondTimer, 10000, 1000);
    }

    public static void initDatabase() {

        new LevelUtil().createTable();
        new TicketUtil().createTables();
        new TicketUtil().initTables();
        new VerificationUtil().createTables();
        new WarnUtil().createTable();
    }

    public static void initGameModes() {

        StatsUtil.addGameMode("buildffa", "YkhYZ2hRMnFnZGchOTYqWQ==",
                new String[]{"Points", "Kills", "Deaths"});
    }
}
