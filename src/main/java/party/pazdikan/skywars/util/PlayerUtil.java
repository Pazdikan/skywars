package party.pazdikan.skywars.util;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import party.pazdikan.skywars.Skywars;
import party.pazdikan.skywars.config.LocaleConfig;

public class PlayerUtil {

    /**
     * Sends a message to the player.
     *
     * @param player Player object.
     * @param text Text to send.
     * @param centered If true, message will be centered.
     */
    public static void sendMessage(Player player, String text, boolean centered) {
        int CENTER_PX = 154;
        String message = ChatColor.translateAlternateColorCodes('&', text);
//        message = PlaceholderAPI.setPlaceholders(player, message);

        if (centered) {
            if (message == null || message.equals("")) player.sendMessage("");

            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;

            for (char c : message.toCharArray()) {
                if (c == '§') {
                    previousCode = true;
                    continue;
                } else if (previousCode == true) {
                    previousCode = false;
                    if (c == 'l' || c == 'L') {
                        isBold = true;
                        continue;
                    } else isBold = false;
                } else {
                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    messagePxSize++;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            int toCompensate = CENTER_PX - halvedMessageSize;
            int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
            int compensated = 0;
            StringBuilder sb = new StringBuilder();
            while (compensated < toCompensate) {
                sb.append(" ");
                compensated += spaceLength;
            }
            player.sendMessage(sb.toString() + message);
        } else {
            player.sendMessage(message);
        }
    }

    public static String getLine(ChatColor color) {
        return ChatColor.translateAlternateColorCodes('&', color + "&l&m--------------------------------------------");
    }

    public static void sendToLobby(Player p) {
        for (PotionEffect effect : p.getActivePotionEffects())
            p.removePotionEffect(effect.getType());
        p.getInventory().setHelmet(null);
        p.getInventory().setChestplate(null);
        p.getInventory().setLeggings(null);
        p.getInventory().setBoots(null);
        p.getInventory().clear();
        p.setGameMode(GameMode.ADVENTURE);
        p.setHealth(20);
        p.setSaturation(100f);
        p.setFoodLevel(20);
        p.teleport(
                new Location(Bukkit.getWorld("world"), 0.5, 79.5, -6.5, -180, 0)
        );

        ItemStack mainMenuItem = new ItemStack(Material.EMERALD);
        ItemMeta mainMenuItemMeta = mainMenuItem.getItemMeta();
        mainMenuItemMeta.setDisplayName(Skywars.getLocaleConfig().getLocale(p, "LOBBY_SKYWARS_MENU"));
        mainMenuItem.setItemMeta(mainMenuItemMeta);
        p.getInventory().setItem(4, mainMenuItem);
    }

    public static String getRankColor(Player p) {
        return ChatColor.stripColor(PlaceholderAPI.setPlaceholders(p, "%vault_prefix%"));
    }

    public static String getRank(Player p) {
        return PlaceholderAPI.setPlaceholders(p, "%vault_rank%");
    }

    public static String formatRank(Player p) {
        return PlaceholderAPI.setPlaceholders(p, "%vault_prefix% " + p.getName());
    }

    public static boolean isInGame(Player p) {
        return p.getWorld().getName().contains("_skywars_");
    }
}
