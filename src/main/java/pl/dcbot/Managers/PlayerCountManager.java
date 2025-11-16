package pl.dcbot.Managers;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import pl.dcbot.DragonBot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static pl.dcbot.Managers.BootstrapManager.serwer;

public class PlayerCountManager {
    private static DragonBot plugin = DragonBot.getInstance();

    public static void startRefreshing() {
        DiscordApi api = DiscordAPIManager.getApi();
        final Server server = api.getServerById(serwer).get();

        ServerVoiceChannel playerCountChannel = server.getVoiceChannelById(BootstrapManager.voicechannel_playercount).get();

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            int playerCount = 0;

            String apiUrl = "https://api.mcsrvstat.us/2/dcrft.pl";
            try {
                BufferedReader in = getBufferedReader(apiUrl);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
                playerCount = jsonObject.getAsJsonObject("players").get("online").getAsInt();
            } catch (Exception e) {
                e.printStackTrace();
            }

            playerCountChannel.updateName("Graczy: " + playerCount);
        }, 0, 600);

    }

    private static BufferedReader getBufferedReader(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent",
                "Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.102011-10-16 20:23:10");

        return new BufferedReader(new InputStreamReader(connection.getInputStream()));
    }
}
