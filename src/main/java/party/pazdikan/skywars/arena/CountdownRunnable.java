package party.pazdikan.skywars.arena;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;
import party.pazdikan.skywars.Skywars;

import java.util.UUID;

public class CountdownRunnable extends BukkitRunnable {
    private Skywars plugin;
    private Arena arena;

    /**
     * Countdown used to start the game.
     *
     * @param pl Main plugin instance.
     * @param a  Arena object.
     */
    public CountdownRunnable(Skywars pl, Arena a) {
        plugin = pl;
        arena = a;
    }

    private int timeLeft = 10;

    @Override
    public void run() {
        if (arena.getState() != ArenaState.STARTING) {
            resetCountdown();
            arena.setState(ArenaState.WAITING);
            return;
        }

        if (arena.getPlayers().size() < 2) {
            resetCountdown();
            arena.setState(ArenaState.WAITING);
            return;
        }

        if (timeLeft == 0) {
            onStart();
        } else {
            if (timeLeft == 10) {
                arena.broadcastMessage(Skywars.getLocaleConfig().getLocale(null, "ARENA_STARTS_IN_X_SECONDS").replace("{time}", String.valueOf(timeLeft)), false);
            } else if (timeLeft <= 5) {
                arena.broadcastMessage(Skywars.getLocaleConfig().getLocale(null, "ARENA_STARTING_SOON").replace("{time}", String.valueOf(timeLeft)), false);
            }
        }
        timeLeft--;
    }

    /**
     * Function that should be called every start attempt (after countdown or forced by player)
     */
    public void onStart() {
        if (arena.getState() != ArenaState.STARTING) {
            arena.broadcastMessage(Skywars.getLocaleConfig().getLocale(null, "ARENA_FORCE_START_FAILURE_NOT_IN_STARTING_STATE"), false);
            resetCountdown();
            return;
        }

        arena.setState(ArenaState.PLAYING);
        arena.broadcastMessage(Skywars.getLocaleConfig().getLocale(null, "ARENA_GAME_STARTED"), false);
        resetCountdown();

        for (String key : Skywars.getInstance().getConfig().getConfigurationSection(arena.getName() + ".spawn").getKeys(false)) {
            String[] block = Skywars.getInstance().getConfig().getString(arena.getName() + ".spawn." + key).split(" ");
            arena.getWorld().getBlockAt(Integer.parseInt(block[0]), Integer.parseInt(block[1]) - 1, Integer.parseInt(block[2])).setType(Material.AIR);
        }

//        for (UUID player : arena.getPlayers()) {
//            Bukkit.getPlayer(player).getInventory().addItem(Skywars.getKitsManager().getKit(
//                    Skywars.db.getString(player, "CURRENTKIT")
//            ).getItems());
//
//            arena.getKillCount().put(player, 0);
//        }
    }

    /**
     * Function used to reset every variable that should get reset along with the countdown itself.
     */
    public void resetCountdown() {
        try {
            this.cancel();
        } catch (Exception ignored) {
        }

        timeLeft = 10;
        arena.setCountdownRunnable(new CountdownRunnable(Skywars.getInstance(), arena));
    }
}
