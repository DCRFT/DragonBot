package pl.dcbot.Managers.PunishmentManager;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.ConfigManager;

import static pl.dcbot.Managers.BootstrapManager.role_wyciszony;
import static pl.dcbot.Managers.BootstrapManager.serwer;

public class MuteManager {
    private static final DragonBot plugin = DragonBot.getInstance();

    private static final DiscordApi api = DragonBot.getApi();
    private static final Server server = api.getServerById(serwer).get();

    public static void startListening() {
        Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(plugin, new BukkitRunnable() {
            @Override
            public void run() {
                for (User user : server.getMembers()) {
                    if (user.getRoles(server).contains(server.getRoleById(role_wyciszony).get())) {
                        if (System.currentTimeMillis() >= ConfigManager.getDataFile().getLong("users." + user.getId() + ".mute")) {
                            user.removeRole(server.getRoleById(role_wyciszony).get());
                            ConfigManager.getDataFile().set("users." + user.getId() + ".mute", null);
                        }
                    }
                }
            }
        }, 0L, 18000L);
    }
}
