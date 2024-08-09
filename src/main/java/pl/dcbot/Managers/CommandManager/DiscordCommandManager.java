package pl.dcbot.Managers.CommandManager;

import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageType;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Server;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;
import org.javacord.api.interaction.SlashCommandOptionType;
import org.javacord.api.listener.message.MessageCreateListener;
import pl.dcbot.Managers.DiscordAPIManager;
import pl.dcbot.Managers.LanguageManager;
import pl.dcbot.Managers.MessageManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static pl.dcbot.Managers.BootstrapManager.*;

public class DiscordCommandManager implements MessageCreateListener {

    private static final DiscordApi api = DiscordAPIManager.getApi();

    static final Server server = api.getServerById(serwer).get();

    public static void registerCommands() {

        Set<SlashCommandBuilder> builders = new HashSet<>();

        builders.add(
                new SlashCommandBuilder()
                        .setName("ban")
                        .setDescription(LanguageManager.getMessage("discord_commands.ban.description"))
                        .setOptions(
                                Arrays.asList(
                                        SlashCommandOption.create(SlashCommandOptionType.USER,
                                                LanguageManager.getMessage("discord_commands.user.name"),
                                                LanguageManager.getMessage("discord_commands.user.description"), true),
                                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                                LanguageManager.getMessage("discord_commands.reason.name"),
                                                LanguageManager.getMessage("discord_commands.reason.description"), true)
                                ))
                        .setDefaultDisabled()
                        .setDefaultEnabledForPermissions(PermissionType.BAN_MEMBERS)
        );
        builders.add(
                new SlashCommandBuilder()
                        .setName("kick")
                        .setDescription(LanguageManager.getMessage("discord_commands.kick.description"))
                        .setOptions(
                                Arrays.asList(
                                        SlashCommandOption.create(SlashCommandOptionType.USER,
                                                LanguageManager.getMessage("discord_commands.user.name"),
                                                LanguageManager.getMessage("discord_commands.user.description"), true),
                                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                                LanguageManager.getMessage("discord_commands.reason.name"),
                                                LanguageManager.getMessage("discord_commands.reason.description"), true)
                                ))
                        .setDefaultDisabled()
                        .setDefaultEnabledForPermissions(PermissionType.KICK_MEMBERS)
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("mute")
                        .setDescription(LanguageManager.getMessage("discord_commands.mute.description"))
                        .setOptions(
                                Arrays.asList(
                                                SlashCommandOption.create(SlashCommandOptionType.USER,
                                                        LanguageManager.getMessage("discord_commands.user.name"),
                                                        LanguageManager.getMessage("discord_commands.user.description"), true),
                                                SlashCommandOption.create(SlashCommandOptionType.LONG,
                                                        LanguageManager.getMessage("discord_commands.days.name"),
                                                        LanguageManager.getMessage("discord_commands.days.description"), true),
                                                SlashCommandOption.create(SlashCommandOptionType.LONG,
                                                        LanguageManager.getMessage("discord_commands.hours.name"),
                                                        LanguageManager.getMessage("discord_commands.hours.description"), true),
                                                SlashCommandOption.create(SlashCommandOptionType.LONG,
                                                        LanguageManager.getMessage("discord_commands.minutes.name"),
                                                        LanguageManager.getMessage("discord_commands.minutes.description"), true),
                                                SlashCommandOption.create(SlashCommandOptionType.STRING,
                                                        LanguageManager.getMessage("discord_commands.reason.name"),
                                                        LanguageManager.getMessage("discord_commands.reason.description"), true)
                                        ))
                        .setDefaultDisabled()
                        .setDefaultEnabledForPermissions(PermissionType.MUTE_MEMBERS)
        );


        builders.add(
                new SlashCommandBuilder()
                        .setName("unmute")
                        .setDescription(LanguageManager.getMessage("discord_commands.unmute.description"))
                        .setOptions(
                                Collections.singletonList(
                                        SlashCommandOption.create(SlashCommandOptionType.USER,
                                                LanguageManager.getMessage("discord_commands.user.name"),
                                                LanguageManager.getMessage("discord_commands.user.description"), true)
                                ))
                        .setDefaultDisabled()
                        .setDefaultEnabledForPermissions(PermissionType.MUTE_MEMBERS)
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("wiad")
                        .setDescription(LanguageManager.getMessage("discord_commands.wiad.description"))
                        .setOptions(
                                Arrays.asList(
                                        SlashCommandOption.create(SlashCommandOptionType.USER,
                                                LanguageManager.getMessage("discord_commands.user.name"),
                                                LanguageManager.getMessage("discord_commands.user.description"), true),
                                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                                LanguageManager.getMessage("discord_commands.string.name"),
                                                LanguageManager.getMessage("discord_commands.string.description"), true)
                                ))
                        .setDefaultDisabled()
                        .setDefaultEnabledForPermissions(PermissionType.KICK_MEMBERS)
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("potw")
                        .setDescription(LanguageManager.getMessage("discord_commands.potw.description"))
                        .setOptions(
                                Arrays.asList(
                                        SlashCommandOption.create(SlashCommandOptionType.USER,
                                                LanguageManager.getMessage("discord_commands.user.name"),
                                                LanguageManager.getMessage("discord_commands.user.description"), true),
                                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                                LanguageManager.getMessage("discord_commands.string.name"),
                                                LanguageManager.getMessage("discord_commands.string.description"), true)
                                ))
                        .setDefaultDisabled()
                        .setDefaultEnabledForPermissions(PermissionType.KICK_MEMBERS)
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("dclink")
                        .setDescription(LanguageManager.getMessage("discord_commands.dclink.description"))
                        .setOptions(
                                Collections.singletonList(
                                        SlashCommandOption.create(SlashCommandOptionType.LONG,
                                                LanguageManager.getMessage("discord_commands.code.name"),
                                                LanguageManager.getMessage("discord_commands.code.description"), true)
                                ))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("play")
                        .setDescription(LanguageManager.getMessage("discord_commands.play.description"))
                        .setOptions(
                                Collections.singletonList(
                                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                                LanguageManager.getMessage("discord_commands.music.name"),
                                                LanguageManager.getMessage("discord_commands.music.description"), true)
                                ))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("p")
                        .setDescription(LanguageManager.getMessage("discord_commands.play.description"))
                        .setOptions(
                                Collections.singletonList(
                                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                                LanguageManager.getMessage("discord_commands.music.name"),
                                                LanguageManager.getMessage("discord_commands.music.description"), true)
                                ))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("graj")
                        .setDescription(LanguageManager.getMessage("discord_commands.play.description"))
                        .setOptions(
                                Collections.singletonList(
                                        SlashCommandOption.create(SlashCommandOptionType.STRING,
                                                LanguageManager.getMessage("discord_commands.music.name"),
                                                LanguageManager.getMessage("discord_commands.music.description"), true)
                                ))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("stop")
                        .setDescription(LanguageManager.getMessage("discord_commands.stop.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("quit")
                        .setDescription(LanguageManager.getMessage("discord_commands.quit.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("wyjdz")
                        .setDescription(LanguageManager.getMessage("discord_commands.quit.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("wyjdź")
                        .setDescription(LanguageManager.getMessage("discord_commands.quit.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("pause")
                        .setDescription(LanguageManager.getMessage("discord_commands.pause.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("pauza")
                        .setDescription(LanguageManager.getMessage("discord_commands.pause.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("wstrzymaj")
                        .setDescription(LanguageManager.getMessage("discord_commands.pause.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("resume")
                        .setDescription(LanguageManager.getMessage("discord_commands.resume.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("wznow")
                        .setDescription(LanguageManager.getMessage("discord_commands.resume.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("wznów")
                        .setDescription(LanguageManager.getMessage("discord_commands.resume.description"))
                        .setDefaultEnabledForEveryone()
        );

        builders.add(
                new SlashCommandBuilder()
                        .setName("cake")
                        .setDescription(LanguageManager.getMessage("discord_commands.cake.description"))
                        .setDefaultEnabledForEveryone()
        );

        api.bulkOverwriteGlobalApplicationCommands(builders).join();
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
                        .replace("{role}", server.getTextChannelById(channel_role).get().getMentionTag()));
            }
        }
    }
}
