package party.pazdikan.skywars;

import com.tchristofferson.configupdater.ConfigUpdater;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public final class Skywars extends JavaPlugin {
    private static Skywars instance;

    public static Skywars getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Updates config.yml with new values while keeping ones that already exists.
        saveDefaultConfig();
        File configFile = new File(getDataFolder(), "config.yml");

        try {
            ConfigUpdater.update(this, "config.yml", configFile, Collections.emptyList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        reloadConfig();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
