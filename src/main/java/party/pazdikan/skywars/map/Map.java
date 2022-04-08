package party.pazdikan.skywars.map;

import org.bukkit.World;

public interface Map {
    boolean load();
    void unload();
    boolean restoreFromSource();

    boolean isLoaded();
    World getWorld();
}
