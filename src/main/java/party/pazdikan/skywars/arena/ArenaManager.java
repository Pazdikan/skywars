package party.pazdikan.skywars.arena;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import party.pazdikan.skywars.Skywars;
import party.pazdikan.skywars.event.PlayerJoinArenaEvent;
import party.pazdikan.skywars.event.PlayerLeaveArenaEvent;
import party.pazdikan.skywars.map.LocalMap;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class ArenaManager {
    ArrayList<Arena> arenas;

    File gameMapsFolder = new File(Skywars.getInstance().getDataFolder(), "maps");

    public ArenaManager() {
        this.arenas = new ArrayList<Arena>();
    }

    public ArrayList<Arena> getArenas() {
        return arenas;
    }

    public void addArena(Arena a) {
        arenas.add(a);
    }

    /**
     * Finds arena by UUID.
     *
     * @param p Player to lookup.
     * @return Arena object or null when player isn't on any arena.
     */
    public Arena getArena(Player p) {
        for (Arena a : Skywars.getArenaManager().getArenas()) {
            if (a.getPlayers().contains(p)) {
                return a;
            }
        }
        return null;
    }

    public void createArena(String worldName) {
        addArena(
                new Arena(
                        new LocalMap(gameMapsFolder, worldName, true),
                        1, 16
                )
        );
    }

    /**
     * Adds Player to an empty arena by UUID.
     *
     * @param player Player.
     * @return true or false, depends on if the player could be added (if there is an empty arena)
     */
    public boolean addPlayer(Player player) {
        Arena arena = null;
        for (Arena a : Skywars.getArenaManager().getArenas()) {
            if ((a.getState() == ArenaState.WAITING || a.getState() == ArenaState.STARTING)  && a.getPlayers().size() < a.getMaxPlayers()) {
                arena = a;
            }
        }

        if (arena == null) {
            return false;
        }

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        player.teleport(
                new Location(
                        arena.getWorld(), 0, 27, 0
                )
        );

        arena.getPlayers().add(player);
        Bukkit.getPluginManager().callEvent(new PlayerJoinArenaEvent(player));

        return true;
    }

    /**
     * Removes Player from the arena by UUID.
     *
     * @param player Player
     */
    public void removePlayer(Player player) {
        Bukkit.getPluginManager().callEvent(new PlayerLeaveArenaEvent(player));
    }
}
