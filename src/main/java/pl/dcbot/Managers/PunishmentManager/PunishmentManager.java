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

import static pl.dcbot.Managers.BootstrapManager.role_wyciszony;

public class PunishmentManager {

    private static final DragonBot plugin = DragonBot.getInstance();

    public static void muteUser(User user, Server server, long days, long hours, long minutes, String reason) {
        String muteTime;
        String days_format = LanguageManager.getMessage("punishments.embeds.timeformat.days");
        String hours_format = LanguageManager.getMessage("punishments.embeds.timeformat.hours");
        String minutes_format = LanguageManager.getMessage("punishments.embeds.timeformat.minutes");
        if(days == 0){
            if(hours == 0){
                muteTime = minutes + " " + minutes_format;
            } else {
              muteTime = hours + " " + hours_format;
              if(minutes != 0){
                    muteTime = hours + " " + hours_format + " " + minutes + " " + minutes_format;
                }
            }
        } else {
            muteTime = days + " " + days_format;
            if(hours != 0){
                muteTime = days + " " + days_format + " " + hours + " " + hours_format;
            }
            if(minutes != 0){
                muteTime = days + " " + days_format + " " + hours + " " + hours_format + " " + minutes + " " + minutes_format;
            }
        }
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, (int) days);
        cal.add(Calendar.HOUR, (int) hours);
        cal.add(Calendar.MINUTE, (int) minutes);


        DateFormat df = new SimpleDateFormat("HH:mm");
        String time = df.format(date);


        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(LanguageManager.getMessage("punishments.embeds.mute.author") + " " + server.getName())
                .addField(LanguageManager.getMessage("punishments.embeds.mute.field1"), reason)
                .addField(LanguageManager.getMessage("punishments.embeds.mute.field2"), muteTime)
                .addField(LanguageManager.getMessage("punishments.embeds.mute.field3.name"), LanguageManager.getMessage("punishments.embeds.mute.field3.value"))
                .setColor(Color.RED)
                .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                        plugin.getConfig().getString("embeds.footer.icon"));
        user.sendMessage(embed);
        user.addRole(server.getRoleById(role_wyciszony).get());


        ConfigManager.getDataFile().set("users." + user.getId() + ".mute", cal.getTimeInMillis());
        ConfigManager.saveData();
    }
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
    public static void unmuteUser(User user, Server server) {
        if(user.getRoles(server).contains(server.getRoleById(role_wyciszony).get())){
            user.removeRole(server.getRoleById(role_wyciszony).get());
        }
    }
}
