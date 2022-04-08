package party.pazdikan.skywars;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import org.bukkit.plugin.java.JavaPlugin;
import party.pazdikan.skywars.arena.ArenaManager;
import party.pazdikan.skywars.commands.Test;
import party.pazdikan.skywars.config.LocaleConfig;
import party.pazdikan.skywars.listeners.PlayerJoin;
import party.pazdikan.skywars.util.FileUtil;

public final class Skywars extends JavaPlugin {
    private static Skywars instance;
    private static LocaleConfig localeConfig;

    private static ArenaManager arenaManager = null;

    @Override
    public void onEnable() {
        instance = this;

        arenaManager = new ArenaManager();
        localeConfig = new LocaleConfig(this);

        // Register commands
        CommandService drink = Drink.get(this);
        drink.register(new Test(), "test");
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
