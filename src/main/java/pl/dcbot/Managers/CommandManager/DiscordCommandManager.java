package pl.dcbot.Managers.CommandManager;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.*;
import org.javacord.api.listener.message.MessageCreateListener;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.LanguageManager;
import pl.dcbot.Managers.MessageManager;

import java.util.Arrays;
import java.util.Collections;

import static pl.dcbot.Managers.BootstrapManager.*;

public class DiscordCommandManager implements MessageCreateListener {

    private static final DiscordApi api = DragonBot.getApi();

    static final Server server = api.getServerById(serwer).get();

    public static void registerCommands(){


        SlashCommand ban =
                SlashCommand.with("ban", LanguageManager.getMessage("discord_commands.ban.description"),
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.USER,
                                        LanguageManager.getMessage("discord_commands.user.name"),
                                        LanguageManager.getMessage("discord_commands.user.description"), true),
                                SlashCommandOption.create(SlashCommandOptionType.STRING,
                                        LanguageManager.getMessage("discord_commands.reason.name"),
                                        LanguageManager.getMessage("discord_commands.reason.description"), true)
                        ))
                        .setDefaultPermission(false)
                        .createForServer(server)
                        .join();


        SlashCommand kick =
                SlashCommand.with("kick", LanguageManager.getMessage("discord_commands.kick.description"),
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.USER,
                                        LanguageManager.getMessage("discord_commands.user.name"),
                                        LanguageManager.getMessage("discord_commands.user.description"), true),
                                SlashCommandOption.create(SlashCommandOptionType.STRING,
                                        LanguageManager.getMessage("discord_commands.reason.name"),
                                        LanguageManager.getMessage("discord_commands.reason.description"), true)
                        ))
                        .setDefaultPermission(false)
                        .createForServer(server)
                        .join();

        SlashCommand mute =
                SlashCommand.with("mute", LanguageManager.getMessage("discord_commands.mute.description"),
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.USER,
                                        LanguageManager.getMessage("discord_commands.user.name"),
                                        LanguageManager.getMessage("discord_commands.user.description"), true),
                                SlashCommandOption.create(SlashCommandOptionType.INTEGER,
                                        LanguageManager.getMessage("discord_commands.days.name"),
                                        LanguageManager.getMessage("discord_commands.days.description"), true),
                                SlashCommandOption.create(SlashCommandOptionType.INTEGER,
                                        LanguageManager.getMessage("discord_commands.hours.name"),
                                        LanguageManager.getMessage("discord_commands.hours.description"), true),
                                SlashCommandOption.create(SlashCommandOptionType.INTEGER,
                                        LanguageManager.getMessage("discord_commands.minutes.name"),
                                        LanguageManager.getMessage("discord_commands.minutes.description"), true),
                                SlashCommandOption.create(SlashCommandOptionType.STRING,
                                        LanguageManager.getMessage("discord_commands.reason.name"),
                                        LanguageManager.getMessage("discord_commands.reason.description"), true)
                        ))
                        .setDefaultPermission(false)
                        .createForServer(server)
                        .join();

        SlashCommand unmute =
                SlashCommand.with("unmute", LanguageManager.getMessage("discord_commands.unmute.description"),
                        Collections.singletonList(
                                SlashCommandOption.create(SlashCommandOptionType.USER,
                                        LanguageManager.getMessage("discord_commands.user.name"),
                                        LanguageManager.getMessage("discord_commands.user.description"), true)
                        ))
                        .setDefaultPermission(false)
                        .createForServer(server)
                        .join();

        SlashCommand wiad =
                SlashCommand.with("wiad", LanguageManager.getMessage("discord_commands.wiad.description"),
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.USER,
                                        LanguageManager.getMessage("discord_commands.user.name"),
                                        LanguageManager.getMessage("discord_commands.user.description"), true),
                                SlashCommandOption.create(SlashCommandOptionType.STRING,
                                 LanguageManager.getMessage("discord_commands.string.name"),
                                        LanguageManager.getMessage("discord_commands.string.description"), true)
                        ))
                        .setDefaultPermission(false)
                        .createForServer(server)
                        .join();

        SlashCommand potw =
                SlashCommand.with("potw", LanguageManager.getMessage("discord_commands.potw.description"),
                        Arrays.asList(
                                SlashCommandOption.create(SlashCommandOptionType.USER,
                                        LanguageManager.getMessage("discord_commands.user.name"),
                                        LanguageManager.getMessage("discord_commands.user.description"), true),
                                SlashCommandOption.create(SlashCommandOptionType.STRING,
                                        LanguageManager.getMessage("discord_commands.string.name"),
                                        LanguageManager.getMessage("discord_commands.string.description"), true)
                        ))
                        .setDefaultPermission(false)
                        .createForServer(server)
                        .join();

        SlashCommand.with("dclink", LanguageManager.getMessage("discord_commands.dclink.description"),
                Collections.singletonList(
                        SlashCommandOption.create(SlashCommandOptionType.INTEGER,
                                LanguageManager.getMessage("discord_commands.code.name"),
                                LanguageManager.getMessage("discord_commands.code.description"), true)
                ))
                        .setDefaultPermission(true)
                        .createForServer(server)
                        .join();

       SlashCommand.with("play", LanguageManager.getMessage("discord_commands.play.description"),
               Collections.singletonList(
                       SlashCommandOption.create(SlashCommandOptionType.STRING,
                               LanguageManager.getMessage("discord_commands.music.name"),
                               LanguageManager.getMessage("discord_commands.music.description"), true)
               ))
                        .setDefaultPermission(true)
                        .createForServer(server)
                        .join();
        SlashCommand.with("p", LanguageManager.getMessage("discord_commands.play.description"),
                Collections.singletonList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                LanguageManager.getMessage("discord_commands.music.name"),
                                LanguageManager.getMessage("discord_commands.music.description"), true)
                ))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();
        SlashCommand.with("graj", LanguageManager.getMessage("discord_commands.play.description"),
                Collections.singletonList(
                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                LanguageManager.getMessage("discord_commands.music.name"),
                                LanguageManager.getMessage("discord_commands.music.description"), true)
                ))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();

        SlashCommand.with("stop", LanguageManager.getMessage("discord_commands.stop.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();

        SlashCommand.with("quit", LanguageManager.getMessage("discord_commands.quit.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();
        SlashCommand.with("wyjdz", LanguageManager.getMessage("discord_commands.quit.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();
        SlashCommand.with("wyjdź", LanguageManager.getMessage("discord_commands.quit.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();

        SlashCommand.with("pause", LanguageManager.getMessage("discord_commands.resume.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();
        SlashCommand.with("pauza", LanguageManager.getMessage("discord_commands.resume.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();
        SlashCommand.with("wstrzymaj", LanguageManager.getMessage("discord_commands.resume.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();

        SlashCommand.with("resume", LanguageManager.getMessage("discord_commands.resume.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();
        SlashCommand.with("wznow", LanguageManager.getMessage("discord_commands.resume.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();
        SlashCommand.with("wznów", LanguageManager.getMessage("discord_commands.resume.description"))
                .setDefaultPermission(true)
                .createForServer(server)
                .join();

       SlashCommand.with("cake", LanguageManager.getMessage("discord_commands.cake.description"))
                        .setDefaultPermission(true)
                        .createForServer(server)
                        .join();

        SlashCommandPermissionsUpdater ban_permission = new SlashCommandPermissionsUpdater(server)
                .setPermissions(Arrays.asList(
                        SlashCommandPermissions.create(role_viceadministrator, SlashCommandPermissionType.ROLE, true),
                        SlashCommandPermissions.create(role_administrator, SlashCommandPermissionType.ROLE, true),
                        SlashCommandPermissions.create(role_wlasciciel, SlashCommandPermissionType.ROLE, true)
                ));

        SlashCommandPermissionsUpdater kick_permission = new SlashCommandPermissionsUpdater(server)
                .setPermissions(Arrays.asList(
                        SlashCommandPermissions.create(role_moderator, SlashCommandPermissionType.ROLE, true),
                        SlashCommandPermissions.create(role_viceadministrator, SlashCommandPermissionType.ROLE, true),
                        SlashCommandPermissions.create(role_administrator, SlashCommandPermissionType.ROLE, true),
                        SlashCommandPermissions.create(role_wlasciciel, SlashCommandPermissionType.ROLE, true)
                ));

        ban_permission.update(ban.getId());
        kick_permission.update(wiad.getId());
        kick_permission.update(potw.getId());
        kick_permission.update(kick.getId());
        kick_permission.update(mute.getId());
        kick_permission.update(unmute.getId());
    }
    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        if (!e.getMessageAuthor().isYourself()) {
            if (e.getChannel().getId() == channel_rejestracja && !e.getMessage().getType().equals(MessageType.SLASH_COMMAND)) {
                MessageManager.sendRawMessage(e.getChannel(),
                        LanguageManager.getMessage("dclink.wrong_message")
                                .replace("{user}", e.getMessageAuthor().asUser().get().getMentionTag())
                                        .replace("{rejestracja_pomoc}", server.getTextChannelById(channel_rejestracja_pomoc).get().getMentionTag()));
                e.getMessage().delete();
            } else if (e.getMessage().getMentionedUsers().contains(api.getYourself())) {
                MessageManager.sendRawMessage(e.getChannel(), LanguageManager.getMessage("ping")
                        .replace("{bot}", server.getTextChannelById(channel_bot).get().getMentionTag()));
            }
        }
    }
}
