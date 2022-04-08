package party.pazdikan.skywars;

import com.jonahseguin.drink.CommandService;
import com.jonahseguin.drink.Drink;
import org.bukkit.plugin.java.JavaPlugin;
import party.pazdikan.skywars.commands.Test;

public final class Skywars extends JavaPlugin {
    private static Skywars instance;

    public static Skywars getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        // Register commands
        CommandService drink = Drink.get(this);
        drink.register(new Test(), "test");
        drink.registerCommands();

        // Config stuff
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
