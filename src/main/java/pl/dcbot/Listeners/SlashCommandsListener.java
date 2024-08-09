package pl.dcbot.Listeners;

import org.bukkit.Bukkit;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.interaction.SlashCommandInteraction;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.DCLink.DCLinkManager;
import pl.dcbot.Managers.LanguageManager;
import pl.dcbot.Managers.MessageManager;
import pl.dcbot.Managers.PunishmentManager.PunishmentManager;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pl.dcbot.Managers.BootstrapManager.*;

public class SlashCommandsListener implements SlashCommandCreateListener {
    private static final DragonBot plugin = DragonBot.getInstance();

    @Override
    public void onSlashCommandCreate(SlashCommandCreateEvent e) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = dateFormat.format(date);
        SlashCommandInteraction command = e.getSlashCommandInteraction();

        if (command.getCommandName().equalsIgnoreCase("ban")) {
            User user = command.getOptionByIndex(0).get().getUserValue().orElse(null);
            String reason = command.getOptionByIndex(1).get().getStringValue().orElse(plugin.getConfig().getString("punishments,default_reason"));
            if (user == null) {
                MessageManager.sendMessage(command, "punishments.wrong_user");
                return;
            } else {
                PunishmentManager.banUser(user, server, reason);
            }
            MessageManager.sendMessage(command, "punishments.ban");
        } else if (command.getCommandName().equalsIgnoreCase("kick")) {
            User user = command.getOptionByIndex(0).get().getUserValue().orElse(null);
            String reason = command.getOptionByIndex(1).get().getStringValue().orElse(plugin.getConfig().getString("punishments,default_reason"));
            if (user == null) {
                MessageManager.sendMessage(command, "punishments.wrong_user");
                return;
            } else {
                PunishmentManager.kickUser(user, server, reason);
            }
            MessageManager.sendMessage(command, "punishments.kick");

        } else if (command.getCommandName().equalsIgnoreCase("wiad")) {
            User user = command.getOptionByIndex(0).get().getUserValue().orElse(null);
            String m = command.getOptionByIndex(1).get().getStringValue().orElse("?");
            if (user == null) {
                MessageManager.sendMessage(command, "directmessage.wrong_user");
                return;
            }
            EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor(LanguageManager.getMessage("directmessage.embed.author") + " " + server.getName())
                    .addField(LanguageManager.getMessage("directmessage.embed.field1"), m)
                    .addField(LanguageManager.getMessage("directmessage.embed.field2.name"), LanguageManager.getMessage("directmessage.embed.field2.value"))
                    .setColor(Color.RED)
                    .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                    .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                            plugin.getConfig().getString("embeds.footer.icon"));
            user.sendMessage(embed);
            MessageManager.sendMessage(command, "directmessage.sent");
        } else if (command.getCommandName().equalsIgnoreCase("potw")) {
            User user = command.getOptionByIndex(0).get().getUserValue().orElse(null);
            if (user == null) {
                MessageManager.sendMessage(command, "potw.wrong_user");
                return;
            }
            DateFormat dataFormat = new SimpleDateFormat("dd.MM.yyyy");
            String date1 = dataFormat.format(date);
            EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor(LanguageManager.getMessage("potw.embed.author"))
                    .addField(LanguageManager.getMessage("potw.embed.field1.name"), LanguageManager.getMessage("potw.embed.field1.value") + " " + date1 + " " + user.getIdAsString())
                    .addField(LanguageManager.getMessage("potw.embed.field2.name"), LanguageManager.getMessage("potw.embed.field2.value"))
                    .setColor(Color.RED)
                    .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                    .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                            plugin.getConfig().getString("embeds.footer.icon"));
            user.sendMessage(embed);
            MessageManager.sendMessage(command, "potw.sent");
        } else if (command.getCommandName().equalsIgnoreCase("dclink")) {
            if (command.getChannel().isPresent()
                    && command.getChannel().get().asServerTextChannel().isPresent()
                    && command.getChannel().get().getId() != channel_rejestracja) {
                e.getInteraction().createImmediateResponder()
                        .setContent(
                                LanguageManager.getMessage("dclink.wrong_channel")
                                        .replace("{rejestracja}", server.getTextChannelById(channel_rejestracja).get().getMentionTag()))
                        .respond();
                return;
            }
            long code = command.getOptionByIndex(0).get().getLongValue().orElse(0L);
            if (code == 0L) {
                MessageManager.sendMessage(command, "dclink.wrong_code");
                return;
            }
            DCLinkManager.registerDiscord(plugin, command, command.getUser(), code);
        } else if (command.getCommandName().equalsIgnoreCase("cake")) {
            MessageManager.sendMessage(command, "cake");
        }
    }
}

