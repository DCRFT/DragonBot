package pl.dcbot.Listeners;

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
import pl.dcbot.Managers.MusicManager.MusicManager;
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

        } else if (command.getCommandName().equalsIgnoreCase("mute")) {

            User user = command.getOptionByIndex(0).get().getUserValue().orElse(null);
            long days = command.getOptionByIndex(1).get().getLongValue().orElse(0L);
            long hours = command.getOptionByIndex(2).get().getLongValue().orElse(0L);
            long minutes = command.getOptionByIndex(3).get().getLongValue().orElse(0L);

            String reason = command.getOptionByIndex(4).get().getStringValue().orElse(plugin.getConfig().getString("punishments,default_reason"));

            if (user == null) {
                MessageManager.sendMessage(command, "punishments.wrong_user");
                return;
            }
            MessageManager.sendMessage(command, "punishments.mute");

            PunishmentManager.muteUser(user, server, days, hours, minutes, reason);
        } else if (command.getCommandName().equalsIgnoreCase("unmute")) {
            User user = command.getOptionByIndex(0).get().getUserValue().orElse(null);
            if (user == null) {
                MessageManager.sendMessage(command, "punishments.wrong_user");
                return;
            }
            MessageManager.sendMessage(command, "punishments.unmute");
            PunishmentManager.unmuteUser(user, server);
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
        } else if (command.getCommandName().equalsIgnoreCase("p")
                || command.getCommandName().equalsIgnoreCase("play")
                || command.getCommandName().equalsIgnoreCase("graj")) {
            String track = command.getOptionByIndex(0).get().getStringValue().orElse(null);
            if (track == null) {
                MessageManager.sendMessage(command, "music.play.no_track");
            } else if (!command.getUser().getConnectedVoiceChannel(server).isPresent()) {
                MessageManager.sendMessage(command, "music.play.no_vc");
            } else if (api.getYourself().getConnectedVoiceChannel(server).isPresent() && !command.getUser().getConnectedVoiceChannel(server).get().isConnected(api.getYourself())) {
                MessageManager.sendMessage(command, "music.play.already_playing_other_channel");
            } else {
                ServerVoiceChannel voiceChannel = command.getUser().getConnectedVoiceChannel(server).get();
                if (voiceChannel.canYouConnect() && voiceChannel.canYouSee()) {
                    MusicManager.playTrack(track, command, voiceChannel);
                }
            }
        } else if (command.getCommandName().equalsIgnoreCase("stop")) {
            if (command.getUser().getConnectedVoiceChannel(server).isPresent()) {
                ServerVoiceChannel voiceChannel = command.getUser().getConnectedVoiceChannel(server).get();
                if (voiceChannel.isConnected(api.getYourself())) {
                    AudioSource as = server.getAudioConnection().get().getAudioSource().get();
                    MusicManager.stopTrack(as);
                    MessageManager.sendMessage(command, "music.stop.success");
                } else {
                    MessageManager.sendMessage(command, "music.stop.no_vc_bot");
                }
            } else {
                MessageManager.sendMessage(command, "music.stop.no_vc");
            }
        } else if (command.getCommandName().equalsIgnoreCase("quit")
                || command.getCommandName().equalsIgnoreCase("wyjdz")
                || command.getCommandName().equalsIgnoreCase("wyjdź")) {
            if (command.getUser().getConnectedVoiceChannel(server).isPresent()) {
                ServerVoiceChannel voiceChannel = command.getUser().getConnectedVoiceChannel(server).get();
                if (voiceChannel.isConnected(api.getYourself())) {
                    server.getAudioConnection().get().close();
                    MessageManager.sendMessage(command, "music.quit.success");
                } else {
                    MessageManager.sendMessage(command, "music.quit.no_vc_bot");
                }
            } else {
                MessageManager.sendMessage(command, "music.quit.no_vc");
            }
        } else if (command.getCommandName().equalsIgnoreCase("pause")
                || command.getCommandName().equalsIgnoreCase("pauza")
                || command.getCommandName().equalsIgnoreCase("wstrzymaj")) {
            if (command.getUser().getConnectedVoiceChannel(server).isPresent()) {
                ServerVoiceChannel voiceChannel = command.getUser().getConnectedVoiceChannel(server).get();
                if (voiceChannel.isConnected(api.getYourself())) {
                    AudioSource as = server.getAudioConnection().get().getAudioSource().get();
                    MusicManager.pauseTrack(as);
                    MessageManager.sendMessage(command, "music.pause.success");
                } else {
                    MessageManager.sendMessage(command, "music.pause.no_vc_bot");
                }
            } else {
                MessageManager.sendMessage(command, "music.pause.no_vc");
            }
        } else if (command.getCommandName().equalsIgnoreCase("resume")
                || command.getCommandName().equalsIgnoreCase("wznow")
                || command.getCommandName().equalsIgnoreCase("wznów")) {
            if (command.getUser().getConnectedVoiceChannel(server).isPresent()) {
                ServerVoiceChannel voiceChannel = command.getUser().getConnectedVoiceChannel(server).get();
                if (voiceChannel.isConnected(api.getYourself())) {
                    AudioConnection audioConnection = server.getAudioConnection().get();
                    if (audioConnection.getAudioSource().get().isMuted()) {
                        AudioSource as = server.getAudioConnection().get().getAudioSource().get();
                        MusicManager.resumeTrack(as);
                        MessageManager.sendMessage(command, "music.resume.success");
                    } else {
                        MessageManager.sendMessage(command, "music.resume.not_paused");
                    }
                } else {
                    MessageManager.sendMessage(command, "music.resume.no_vc_bot");
                }
            } else {
                MessageManager.sendMessage(command, "music.resume.no_vc");
            }
        }
    }
}

