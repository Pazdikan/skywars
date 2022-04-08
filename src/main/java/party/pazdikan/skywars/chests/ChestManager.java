package party.pazdikan.skywars.chests;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import party.pazdikan.skywars.Skywars;
import party.pazdikan.skywars.arena.Arena;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class ChestManager {
    private final Set<Location> openedChests = new HashSet<>();
    private final List<LootItem> lootItems = new ArrayList<>();
    private Arena arena;

    /**
     * Chest Manager object.
     */
    public ChestManager(Arena arena) {
        this.arena = arena;
        ConfigurationSection itemsSection = Skywars.getInstance().getConfig().getConfigurationSection ("lootItems") ;
        if (itemsSection == null)
            Bukkit.getLogger().severe("Please setup your 'lootItems' in the config.yml!") ;

        for (String key : itemsSection.getKeys(false)) {
            ConfigurationSection section = itemsSection.getConfigurationSection(key);
            lootItems.add(new LootItem(section));
        }
    }

    /**
     * Method used to fill chest inventory.
     *
     * @param inventory Chest Inventory to fill.
     */
    public void fill(Inventory inventory) {
        inventory.clear();

        ThreadLocalRandom random = ThreadLocalRandom.current();
        Set<LootItem> used = new HashSet<>();

        for (int slotIndex = 0; slotIndex < inventory.getSize(); slotIndex++) {
            LootItem randomItem = lootItems.get(
                    random.nextInt(lootItems.size())
            );

            if (used.contains(randomItem)) continue;
            used.add(randomItem);

            if (randomItem.shouldFill(random, inventory.getTitle())) {
                ItemStack itemStack = randomItem.make();
                inventory.setItem(slotIndex, itemStack);
            }
        }
    }

    /**
     * Method used to mark chest as opened to prevent
     *
     * @param location Chest location.
     */
    public void markAsOpened(Location location) {
        openedChests.add(location);
    }

    public boolean hasBeenOpened(Location location) {
        return openedChests.contains(location);
    }

    public void resetChests() {
        openedChests.clear();
    }
}
