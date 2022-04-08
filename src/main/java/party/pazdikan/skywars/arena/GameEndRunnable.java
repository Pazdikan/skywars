package party.pazdikan.skywars.arena;

import org.bukkit.scheduler.BukkitRunnable;
import party.pazdikan.skywars.Skywars;

public class GameEndRunnable extends BukkitRunnable {
    private Skywars plugin;
    private Arena arena;
    private int timeLeft = 5;

    private boolean isRunning = false;

    /**
     * Countdown used to reset the map after arena is ended.
     *
     * @param pl Main plugin instance.
     * @param a Arena object.
     */
    public GameEndRunnable(Skywars pl, Arena a) {
        plugin = pl;
        arena = a;
        this.isRunning = false;
    }

    @Override
    public void run() {
        isRunning = true;
        if (timeLeft == 0) {
            this.cancel();
            arena.reset();
        }
        timeLeft--;
    }

    public boolean isRunning() {
        return !isRunning;
    }
}
