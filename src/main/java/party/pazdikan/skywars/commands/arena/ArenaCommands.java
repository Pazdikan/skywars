package party.pazdikan.skywars.commands.arena;

import com.jonahseguin.drink.annotation.Command;
import com.jonahseguin.drink.annotation.Flag;
import com.jonahseguin.drink.annotation.OptArg;
import com.jonahseguin.drink.annotation.Sender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import party.pazdikan.skywars.Skywars;
import party.pazdikan.skywars.map.LocalMap;
import party.pazdikan.skywars.util.PlayerUtil;

public class ArenaCommands {

    @Command(name = "arena", desc = "Arena commands", aliases = {"a"})
    public void root(@Sender CommandSender sender) {
    }

    @Command(name = "join", desc = "Join a random arena.")
    public void join(@Sender CommandSender sender) {
        if (!Skywars.getArenaManager().addPlayer(((Player) sender)))
            PlayerUtil.sendMessage(((Player) sender), Skywars.getLocaleConfig().getLocale((Player) sender, "ARENA_CANNON_FIND_EMPTY_ARENA"), false);
    }

    @Command(name = "create", desc = "Create a new arena.")
    public void create(@Sender CommandSender sender) {
        Skywars.getArenaManager().createArena("TestChests");
    }
}
