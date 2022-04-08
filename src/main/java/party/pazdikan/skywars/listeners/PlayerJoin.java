package party.pazdikan.skywars.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import party.pazdikan.skywars.Skywars;
import party.pazdikan.skywars.util.PlayerUtil;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Player p = e.getPlayer();

//        Skywars.db.createPlayer(p);

        PlayerUtil.sendToLobby(p);
    }
}
