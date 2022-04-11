package party.pazdikan.skywars;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import party.pazdikan.skywars.arena.ArenaManager;
import party.pazdikan.skywars.commands.Test;
import party.pazdikan.skywars.commands.arena.ArenaCommands;
import party.pazdikan.skywars.config.LocaleConfig;
import party.pazdikan.skywars.data.Database;
import party.pazdikan.skywars.data.MySQL;
import party.pazdikan.skywars.data.Yaml;
import party.pazdikan.skywars.listeners.PlayerJoin;
import party.pazdikan.skywars.util.ANSI;
import party.pazdikan.skywars.util.FileUtil;

import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;

public final class Skywars extends JavaPlugin {
    private static Skywars instance;
    private static LocaleConfig localeConfig;

    private static Database database;
    private static ArenaManager arenaManager = null;

    @Override
    public void onEnable() {
        instance = this;

        String databaseType = getConfig().getString("database.type");
        switch (databaseType + "") {
            case "mysql":
                database = new MySQL();
                database.connect();
                database.createTable();
                break;
            case "null":
            default:
                database = new Yaml();
                getServer().getConsoleSender().sendMessage(ANSI.RED_BOLD + "VERY IMPORTANT MESSAGE FROM SKYWARS PLUGIN:");
                getServer().getConsoleSender().sendMessage(ANSI.RED_BRIGHT + "Database that you provided in configuration field \"database.type\" could not be identified!");
                getServer().getConsoleSender().sendMessage(ANSI.RED_BRIGHT + "That means that you either mistyped it or it's not supported by Pazdikan's Skywars.");
                getServer().getConsoleSender().sendMessage(ANSI.RED_BRIGHT + "The YAML storing method has been selected fot you, although it's not recommended to use on bigger servers 10+ players.");
                getServer().getConsoleSender().sendMessage(" ");
                getServer().getConsoleSender().sendMessage(ANSI.YELLOW_BRIGHT + "Read more about supported databases at https://github.com/Pazdikan/skywars/wiki/Getting-started#-databases" + ANSI.RESET);
                break;
        }

        arenaManager = new ArenaManager();
        localeConfig = new LocaleConfig(this);

        // Register commands
        CommandService drink = Drink.get(this);
        drink.register(new Test(), "test");
        drink.register(new ArenaCommands(), "arena");
        drink.registerCommands();

        // Config stuff
        localeConfig.createConfiguration();

        // Events
        this.getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
    }

    @Override
    public void onDisable() {
        arenaManager.getArenas().forEach(a -> {
            a.getLocalGameMap().unload();
            FileUtil.delete(a.getLocalGameMap().getActiveWorldFolder());
        });
    }

    public static Skywars getInstance() {
        return instance;
    }

    public static LocaleConfig getLocaleConfig() {
        return localeConfig;
    }

    public static ArenaManager getArenaManager() {
        return arenaManager;
    }
}
