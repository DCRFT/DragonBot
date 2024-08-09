package pl.dcbot.Managers.PunishmentManager;

import org.bukkit.Bukkit;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.ConfigManager;
import pl.dcbot.Managers.LanguageManager;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

public class PunishmentManager {

    private static final DragonBot plugin = DragonBot.getInstance();

    public static void banUser(User user, Server server, String reason){

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = dateFormat.format(date);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(LanguageManager.getMessage("punishments.embeds.ban.author") + " " + server.getName())
                .addField(LanguageManager.getMessage("punishments.embeds.ban.field1"), reason)
                .addField(LanguageManager.getMessage("punishments.embeds.ban.field2.name"), LanguageManager.getMessage("punishments.embeds.ban.field2.value"))
                .setColor(Color.RED)
                .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                        plugin.getConfig().getString("embeds.footer.icon"));
        user.sendMessage(embed);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> server.banUser(user, Duration.ZERO, reason).join(), 10L);
    }
    public static void kickUser(User user, Server server, String reason){

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = dateFormat.format(date);

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(LanguageManager.getMessage("punishments.embeds.kick.author") + " " + server.getName())
                .addField(LanguageManager.getMessage("punishments.embeds.kick.field1"), reason)
                .addField(LanguageManager.getMessage("punishments.embeds.kick.field2.name"), LanguageManager.getMessage("punishments.embeds.kick.field2.value"))
                .setColor(Color.RED)
                .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                        plugin.getConfig().getString("embeds.footer.icon"));
        user.sendMessage(embed);

        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> server.kickUser(user, reason).join(), 10L);
    }
}
