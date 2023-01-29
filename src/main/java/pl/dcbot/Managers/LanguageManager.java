package pl.dcbot.Managers;

import org.bukkit.ChatColor;
import pl.dcbot.DragonBot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LanguageManager {

    public static final DragonBot plugin = DragonBot.getInstance();

    public static String getMessage(String key){
            String message = ConfigManager.getMessagesFile().getString(key);
            if(message != null){
                return ChatColor.translateAlternateColorCodes('&', message);
            }
            return "§cError! Unknown string §e" + key + "\n§cCheck if it exists in §emessages.yml§c.";
    }
    public static List<String> getMessageList(String key){
        List<String> message = new ArrayList<>();
        for(String s : ConfigManager.getMessagesFile().getStringList(key)) {
            message.add(ChatColor.translateAlternateColorCodes('&', s));
        }
        return message;
    }
    public static void load() {
        new File(plugin.getDataFolder() + File.separator + "messages.yml");
    }
}