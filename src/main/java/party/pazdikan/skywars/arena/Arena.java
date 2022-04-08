package party.pazdikan.skywars.arena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import party.pazdikan.skywars.Skywars;
import party.pazdikan.skywars.chests.ChestManager;
import party.pazdikan.skywars.map.LocalMap;
import party.pazdikan.skywars.util.MapUtil;
import party.pazdikan.skywars.util.PlayerUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Arena {
    private String name;
    private World world;
    private LocalMap localMap;
    private ArrayList<Player> players;
    private int minPlayers;
    private int maxPlayers;
    private ArenaState state;
    private CountdownRunnable countdownRunnable = new CountdownRunnable(Skywars.getInstance(), this);
    private GameEndRunnable endGameRunnable = new GameEndRunnable(Skywars.getInstance(), this);

    private Map<Player, Integer> killCount = new HashMap<>();

    private ChestManager chestManager = new ChestManager(this);

    /**
     * Stores every single thing about an arena.
     *
     * @param localMap LocalMap object.
     * @param min Minimum players to start the game.
     * @param max Maximum players that can fit on the map.
     */
    public Arena(LocalMap localMap, int min, int max) {
        this.localMap = localMap;
        this.world = localMap.getWorld();
        this.name = localMap.getWorld().getName().split("_")[0];
        this.players = new ArrayList<Player>();
        this.minPlayers = min;
        this.maxPlayers = max;
        this.state = ArenaState.WAITING;
    }

    /**
     * Sends a chat message to every player on the map.
     *
     * @param message Message to send
     * @param centered If true, message will be cenetered.
     */
    public void broadcastMessage(String message, boolean centered) {
        for (Player player : getWorld().getPlayers()) {
            PlayerUtil.sendMessage(player, message, centered);
        }
    }

    /**
     * Resets every value that should be reset every LocalGameMap change.
     */
    public void reset() {
        setEndGameRunnable(new GameEndRunnable(Skywars.getInstance(), this));
        getLocalGameMap().restoreFromSource();
        setState(ArenaState.WAITING);
        setPlayers(new ArrayList<Player>());
        setWorld(getLocalGameMap().getWorld());
        getChestManager().resetChests();
        getKillCount().clear();
    }

    public void checkGameEnd() {
        if (getPlayers().size() == 1) {
            setState(ArenaState.ENDING);
//            Skywars.db.addInt(getPlayers().get(0).getUniqueId(), "WINS", 1);
//            Skywars.db.addInt(getPlayers().get(0).getUniqueId(), "WINSTREAK", 1);

            Map<Player, Integer> killsTop = MapUtil.sortByValue(getKillCount());
            Object[] killsTopKeys = killsTop.keySet().toArray();

            broadcastMessage(" ", false);
            broadcastMessage(PlayerUtil.getLine(ChatColor.YELLOW), true);
            broadcastMessage(" ", false);
            broadcastMessage("&e&lWinner: &r" + PlayerUtil.formatRank(getPlayers().get(0)), true);
            broadcastMessage(" ", false);
            broadcastMessage("&e&l1st killer: &r" + PlayerUtil.formatRank((Player) killsTopKeys[0]) + "&e - " + killsTop.get(killsTopKeys[0]), true);
            broadcastMessage("&e&l2nd killer: &r" + PlayerUtil.formatRank((Player) killsTopKeys[1]) + "&e - " + killsTop.get(killsTopKeys[1]), true);
            broadcastMessage("&e&l3rd killer: &r" + PlayerUtil.formatRank((Player) killsTopKeys[2]) + "&e - " + killsTop.get(killsTopKeys[2]), true);
            broadcastMessage(" ", false);
            broadcastMessage(PlayerUtil.getLine(ChatColor.YELLOW), true);

            Skywars.getArenaManager().removePlayer(getPlayers().get(0));
            getEndGameRunnable().runTaskTimer(Skywars.getInstance(), 0L, 20L);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public LocalMap getLocalGameMap() {
        return localMap;
    }

    public void setLocalMap(LocalMap localGameMap) {
        this.localMap = localGameMap;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(int minPlayers) {
        this.minPlayers = minPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public ArenaState getState() {
        return state;
    }

    public void setState(ArenaState state) {
        this.state = state;
    }

    public CountdownRunnable getCountdownRunnable() {
        return countdownRunnable;
    }

    public void setCountdownRunnable(CountdownRunnable countdownRunnable) {
        this.countdownRunnable = countdownRunnable;
    }

    public GameEndRunnable getEndGameRunnable() {
        return endGameRunnable;
    }

    public void setEndGameRunnable(GameEndRunnable endGameRunnable) {
        this.endGameRunnable = endGameRunnable;
    }

    public ChestManager getChestManager() {
        return chestManager;
    }

    public Map<Player, Integer> getKillCount() {
        return killCount;
    }
}
