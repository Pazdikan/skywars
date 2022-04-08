package party.pazdikan.skywars.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import party.pazdikan.skywars.Skywars;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;

public class LocaleConfig {
    private final Skywars plugin;

    private File file;
    private FileConfiguration fileConfiguration;

    private String[] locales = new String[] {
            "en-US",
            "pl-PL"
    };

    public LocaleConfig(Skywars plugin) {
        this.plugin = plugin;
    }

    public void createConfiguration() {
        file = new File(plugin.getDataFolder(), "locales.yml");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource("locales.yml", true);
        }

        fileConfiguration = new YamlConfiguration();
        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public String getLocale(@Nullable Player player, String key) {
        String locale = "en-US";

        if (player == null) {
            locale = plugin.getConfig().getString("DEFAULT_LOCALE");
        } else {
            // TODO: Getting player's locale from database
            locale = plugin.getConfig().getString("DEFAULT_LOCALE");
        }

        return plugin.getConfig().getString(locale + "." + key);
    }

    public String[] getLocales() {
        return locales;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }
}
