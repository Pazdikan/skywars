package party.pazdikan.skywars.map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import party.pazdikan.skywars.Skywars;
import party.pazdikan.skywars.util.FileUtil;
import party.pazdikan.skywars.util.PlayerUtil;

import java.io.File;
import java.io.IOException;

public class LocalMap implements Map {
    private final File sourceWorldFolder;
    private File activeWorldFolder;
    private World bukkitWorld;

    public LocalMap(File worldFolder, String worldName, boolean loadOnInit) {
        this.sourceWorldFolder = new File(
                worldFolder,
                worldName
        );
        if (loadOnInit) load();
    }

    public File getActiveWorldFolder() {
        return activeWorldFolder;
    }

    public boolean load() {
        if (isLoaded()) return true;

        this.activeWorldFolder = new File(
                Bukkit.getWorldContainer().getParentFile(),
                sourceWorldFolder.getName() + "_active_" + System.currentTimeMillis()
        );

        try {
            FileUtil.copy(sourceWorldFolder, activeWorldFolder);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Failed to load GameMap from source folder " + sourceWorldFolder);
            e.printStackTrace();
            return false;
        }
        this.bukkitWorld = Bukkit.createWorld(new WorldCreator(activeWorldFolder.getName()));
        if (bukkitWorld != null) this.bukkitWorld.setAutoSave(false);
        return isLoaded();
    }

    public void unload() {
        if (bukkitWorld != null) {
            Bukkit.unloadWorld(bukkitWorld, false);
        }
        if (activeWorldFolder != null) {
            FileUtil.delete(activeWorldFolder);
        }
        bukkitWorld = null;
        activeWorldFolder = null;
    }

    public boolean restoreFromSource() {
        for (Player player : this.getWorld().getPlayers()) {
//            PlayerUtil.sendMessage(player, Skywars.getLo().getLocale(player, "ARENA_RESETTING_PLAYER_LEFT"), false);
        }

        unload();
        return load();
    }

    @Override
    public boolean isLoaded() {
        return getWorld() != null;
    }

    @Override
    public World getWorld() {
        return bukkitWorld;
    }
}
