package pl.dcbot.Managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import pl.dcbot.DragonBot;

import static pl.dcbot.Managers.BootstrapManager.serwer;

public class PlayerCountManager {
    private static DragonBot plugin = DragonBot.getInstance();

    public static void startRefreshing() {
        DiscordApi api = DiscordAPIManager.getApi();
        final Server server = api.getServerById(serwer).get();

        ServerVoiceChannel playerCountChannel = server.getVoiceChannelById(BootstrapManager.voicechannel_playercount).get();

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            int playerCount = Integer.parseInt(PlaceholderAPI.setPlaceholders(null, "%bungee_total%"));
            playerCountChannel.updateName("Graczy: " + playerCount);
        }, 0, 600);

    }
}
