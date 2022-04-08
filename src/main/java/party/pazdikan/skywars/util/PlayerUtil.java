package party.pazdikan.skywars.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class PlayerUtil {
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
}
