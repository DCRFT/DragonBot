package pl.dcbot.Managers;

import org.javacord.api.DiscordApi;
import pl.dcbot.DragonBot;

public class DiscordAPIManager {

    private static DiscordApi api;

    public static DiscordApi getApi() {
        return api;
    }

    public static void setApi(DiscordApi discordApi) {
        api = discordApi;
    }

    public static void clearApi() {
        api = null;
    }
}
