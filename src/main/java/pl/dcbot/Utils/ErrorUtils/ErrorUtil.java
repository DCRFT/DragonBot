package pl.dcbot.Utils.ErrorUtils;

import pl.dcbot.DragonBot;
import pl.dcbot.Managers.LanguageManager;

public class ErrorUtil {
    public static final DragonBot plugin = DragonBot.getInstance();

    public static void logError(ErrorReason type) {
        switch (type) {
            case DATABASE:
                for (String s : LanguageManager.getMessageList("errors.database")) {
                    plugin.getLogger().severe((s));
                }
            case DATA:
                for (String s : LanguageManager.getMessageList("errors.data")) {
                    plugin.getLogger().severe((s));
                }
            case CONFIG:
                for (String s : LanguageManager.getMessageList("errors.config")) {
                    plugin.getLogger().severe((s));
                }
            case MESSAGES:
                for (String s : LanguageManager.getMessageList("errors.messages")) {
                    plugin.getLogger().severe((s));
                }
            case DISCORD:
                for (String s : LanguageManager.getMessageList("errors.discord")) {
                    plugin.getLogger().severe((s));
                }
            case OTHER:
                for (String s : LanguageManager.getMessageList("errors.other")) {
                    plugin.getLogger().severe((s));
                }
        }
    }
    public static void logError(String reason) {
            plugin.getLogger().severe(LanguageManager.getMessage("errors.configuration.header"));
            plugin.getLogger().severe(LanguageManager.getMessage("errors.configuration.content") + reason);
            plugin.getLogger().severe(LanguageManager.getMessage("errors.configuration.footer"));
        }
}
