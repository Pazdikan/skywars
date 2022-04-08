package party.pazdikan.skywars.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class PlayerLeaveArenaEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    Player player;

    public PlayerLeaveArenaEvent(
            Player player
    ) {
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }
}
