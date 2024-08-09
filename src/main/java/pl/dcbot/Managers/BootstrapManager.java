package pl.dcbot.Managers;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.UserStatus;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.CommandManager.DiscordCommandManager;
import pl.dcbot.Utils.ErrorUtils.ErrorReason;
import pl.dcbot.Utils.ErrorUtils.ErrorUtil;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BootstrapManager {
    private static final DragonBot plugin = DragonBot.getInstance();

    public static final long serwer = plugin.getConfig().getLong("serverconfig.server");

    public static final long channel_glowny = plugin.getConfig().getLong("serverconfig.channels.glowny");
    public static final long channel_rejestracja = plugin.getConfig().getLong("serverconfig.channels.rejestracja");
    public static final long channel_rejestracja_pomoc = plugin.getConfig().getLong("serverconfig.channels.rejestracja_pomoc");
    public static final long channel_prangi = plugin.getConfig().getLong("serverconfig.channels.przenoszenie_rang");
    public static final long channel_zmiananicku = plugin.getConfig().getLong("serverconfig.channels.zmiana_nicku");
    public static final long channel_zgloszenia_s12 = plugin.getConfig().getLong("serverconfig.channels.zgloszenia_lobby");
    public static final long channel_zgloszenia_lobby = plugin.getConfig().getLong("serverconfig.channels.zgloszenia_s12");
    public static final long channel_zgloszenia_s16 = plugin.getConfig().getLong("serverconfig.channels.zgloszenia_s16");
    public static final long channel_bot = plugin.getConfig().getLong("serverconfig.channels.bot");
    public static final long channel_banykicki = plugin.getConfig().getLong("serverconfig.channels.banykicki");
    public static final long channel_wiadpotw = plugin.getConfig().getLong("serverconfig.channels.wiadpotw");
    public static final long channel_zgloszenia = plugin.getConfig().getLong("serverconfig.channels.zgloszenia");
    public static final long channel_propozycje = plugin.getConfig().getLong("serverconfig.channels.propozycje");

    public static final long channel_pingi = plugin.getConfig().getLong("serverconfig.channels.role");
    public static final long channel_colors = plugin.getConfig().getLong("serverconfig.channels.colors");
    public static final long channel_ogloszenia = plugin.getConfig().getLong("serverconfig.channels.ogloszenia");
    public static final long channel_zmiany = plugin.getConfig().getLong("serverconfig.channels.zmiany");
    public static final long channel_eventy = plugin.getConfig().getLong("serverconfig.channels.eventy");
    public static final long channel_testowy = plugin.getConfig().getLong("serverconfig.channels.testowy");
    public static final long channel_logi = plugin.getConfig().getLong("serverconfig.channels.logi");

    public static final long voicechannel_prywatne = plugin.getConfig().getLong("serverconfig.voicechannels.prywatne");

    public static final long category_prywatne = plugin.getConfig().getLong("serverconfig.categories.prywatne");
    public static final long category_zgl_otwarte = plugin.getConfig().getLong("serverconfig.categories.zgl_otwarte");
    public static final long category_zgl_zamkniete = plugin.getConfig().getLong("serverconfig.categories.zgl_zamkniete");
    public static final long category_propo_otwarte = plugin.getConfig().getLong("serverconfig.categories.propo_otwarte");
    public static final long category_propo_zamkniete = plugin.getConfig().getLong("serverconfig.categories.propo_zamkniete");

    public static final long emoji_plus_jeden = plugin.getConfig().getLong("serverconfig.emojis.plus_jeden");
    public static final long emoji_minus_jeden = plugin.getConfig().getLong("serverconfig.emojis.minus_jeden");

    public static final long role_niezarejestrowany = plugin.getConfig().getLong("serverconfig.roles.niezarejestrowany");
    public static final long role_gracz = plugin.getConfig().getLong("serverconfig.roles.gracz");
    public static final long role_vip = plugin.getConfig().getLong("serverconfig.roles.vip");
    public static final long role_svip = plugin.getConfig().getLong("serverconfig.roles.svip");
    public static final long role_mvip = plugin.getConfig().getLong("serverconfig.roles.mvip");
    public static final long role_evip = plugin.getConfig().getLong("serverconfig.roles.evip");
    public static final long role_pomocnik = plugin.getConfig().getLong("serverconfig.roles.pomocnik");
    public static final long role_moderator = plugin.getConfig().getLong("serverconfig.roles.moderator");
    public static final long role_viceadministrator = plugin.getConfig().getLong("serverconfig.roles.viceadministrator");
    public static final long role_administrator = plugin.getConfig().getLong("serverconfig.roles.administrator");
    public static final long role_opiekun = plugin.getConfig().getLong("serverconfig.roles.opiekun");

    public static final long role_wlasciciel = plugin.getConfig().getLong("serverconfig.roles.wlasciciel");

    public static final long role_ogloszenia = plugin.getConfig().getLong("serverconfig.roles.rola_ogloszenia");
    public static final long role_eventy = plugin.getConfig().getLong("serverconfig.roles.rola_eventy");
    public static final long role_zmiany = plugin.getConfig().getLong("serverconfig.roles.rola_zmiany");

    public static final long role_color_default_pink = plugin.getConfig().getLong("serverconfig.roles.colors.default_pink");
    public static final long role_color_blue = plugin.getConfig().getLong("serverconfig.roles.colors.blue");
    public static final long role_color_green = plugin.getConfig().getLong("serverconfig.roles.colors.green");
    public static final long role_color_yellow = plugin.getConfig().getLong("serverconfig.roles.colors.yellow");
    public static final long role_color_orange = plugin.getConfig().getLong("serverconfig.roles.colors.orange");
    public static final long role_color_purple = plugin.getConfig().getLong("serverconfig.roles.colors.purple");
    public static final long role_color_grey = plugin.getConfig().getLong("serverconfig.roles.colors.grey");
    public static final long role_color_black = plugin.getConfig().getLong("serverconfig.roles.colors.black");
    public static final long role_color_white = plugin.getConfig().getLong("serverconfig.roles.colors.white");


    public static Server server;

    public static DiscordApi api = DragonBot.getApi();

    public static boolean bootstrap() {

        api = DragonBot.getApi();

        List<Long> channels = Arrays.asList(
                channel_rejestracja,
                channel_rejestracja_pomoc,
                channel_glowny,
                channel_prangi,
                channel_zmiananicku,
                channel_zgloszenia_lobby,
                channel_zgloszenia_s12,
                channel_zgloszenia_s16,
                channel_bot,
                channel_banykicki,
                channel_wiadpotw,
                channel_zgloszenia,
                channel_propozycje,
                channel_pingi,
                channel_colors,
                channel_ogloszenia,
                channel_eventy,
                channel_zmiany,
                channel_testowy,
                channel_logi);
        List<Long> vcs = Collections.singletonList(voicechannel_prywatne);
        List<Long> categories = Arrays.asList(
                category_prywatne,
                category_zgl_otwarte,
                category_zgl_zamkniete,
                category_propo_otwarte,
                category_propo_zamkniete);
        List<Long> emojis = Arrays.asList(
                emoji_plus_jeden,
                emoji_minus_jeden);
        List<Long> roles = Arrays.asList(
                role_gracz,
                role_vip,
                role_svip,
                role_mvip,
                role_evip,
                role_pomocnik,
                role_moderator,
                role_viceadministrator,
                role_administrator,
                role_wlasciciel,
                role_ogloszenia,
                role_eventy,
                role_zmiany,

                role_color_default_pink,
                role_color_blue,
                role_color_green,
                role_color_green,
                role_color_yellow,
                role_color_orange,
                role_color_purple,
                role_color_grey,
                role_color_black,
                role_color_white);

        if(!api.getServerById(serwer).isPresent()){
            ErrorUtil.logError(LanguageManager.getMessage("errors.configuration.server") + serwer);
            return false;
        }
        server = api.getServerById(serwer).get();
        for (long i : channels) {
            if(!server.getTextChannelById(i).isPresent()) {
                ErrorUtil.logError(LanguageManager.getMessage("errors.configuration.channel") + i);
                return false;
            }
        }
        for (long i : vcs) {
            if(!server.getVoiceChannelById(i).isPresent()) {
                ErrorUtil.logError(LanguageManager.getMessage("errors.configuration.voicechannel") + i);
                return false;
            }
        }
        for (long i : categories) {
            if(!server.getChannelCategoryById(i).isPresent()) {
                ErrorUtil.logError(LanguageManager.getMessage("errors.configuration.category") + i);
                return false;
            }
        }
        for (long i : emojis) {
            if(!server.getCustomEmojiById(i).isPresent()) {
                ErrorUtil.logError(LanguageManager.getMessage("errors.configuration.emoji") + i);
                return false;
            }
        }
        for (long i : roles) {
            if(!server.getRoleById(i).isPresent()) {
                ErrorUtil.logError(LanguageManager.getMessage("errors.configuration.role") + i);
                return false;
            }
        }
        return true;
    }

    public static void initialize(DiscordApi discordApi) {
        plugin.getLogger().info(LanguageManager.getMessage("connection.bootstrap.connecting"));
        discordApi.updateActivity(ActivityType.STREAMING, LanguageManager.getMessage("activity.connecting"));

        discordApi.updateStatus(UserStatus.DO_NOT_DISTURB);
        if(!BootstrapManager.bootstrap()) {
            plugin.getLogger().info(LanguageManager.getMessage("connection.bootstrap.error"));
            plugin.getPluginLoader().disablePlugin(plugin);
            discordApi.disconnect();
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = dateFormat.format(date);

        if (!ConfigManager.getDataFile().getBoolean("created.channel_zgloszenia")) {
            try {
                long id = new MessageBuilder().setEmbed(
                                new EmbedBuilder()
                                        .setAuthor(plugin.getConfig().getString("embeds.reports.author"))
                                        .addField(plugin.getConfig().getString("embeds.reports.field.name"), plugin.getConfig().getString("embeds.reports.field.value"))
                                        .setColor(Color.RED)
                                        .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                                        .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                                                plugin.getConfig().getString("embeds.footer.icon")))
                        .addComponents(ActionRow.of(
                                Button.success(
                                        plugin.getConfig().getString("embeds.reports.button.id"),
                                        plugin.getConfig().getString("embeds.reports.button.label"))))
                        .send(server.getChannelById(channel_zgloszenia).get().asServerTextChannel().get()).get().getId();
                ConfigManager.getDataFile().set("ids.reports", id);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                ErrorUtil.logError(ErrorReason.DISCORD);
            }
            ConfigManager.getDataFile().set("created.channel_zgloszenia", true);

        }
        if (!ConfigManager.getDataFile().getBoolean("created.channel_propozycje")) {
            try {
                long id = new MessageBuilder().setEmbed(
                        new EmbedBuilder()
                                .setAuthor(plugin.getConfig().getString("embeds.suggestions.author"))
                                .addField(plugin.getConfig().getString("embeds.suggestions.field.name"), plugin.getConfig().getString("embeds.suggestions.field.value"))
                                .setColor(Color.CYAN)
                                .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                                .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                                        plugin.getConfig().getString("embeds.footer.icon")))
                        .addComponents(ActionRow.of(
                                Button.success(
                                        plugin.getConfig().getString("embeds.suggestions.button.id"),
                                        plugin.getConfig().getString("embeds.suggestions.button.label"))))
                        .send(server.getChannelById(channel_propozycje).get().asServerTextChannel().get()).get().getId();
                ConfigManager.getDataFile().set("ids.suggestions", id);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                ErrorUtil.logError(ErrorReason.DISCORD);
            }
            ConfigManager.getDataFile().set("created.channel_propozycje", true);
        }

        if (!ConfigManager.getDataFile().getBoolean("created.channel_pingi")) {

            SelectMenu selectMenu = SelectMenu.create("roles", LanguageManager.getMessage("menus.roles.title"), 0, 3,  Arrays.asList(
                    SelectMenuOption.create(LanguageManager.getMessage("menus.roles.announcements.label"), "announcements_id", LanguageManager.getMessage("menus.roles.announcements.description")),
                    SelectMenuOption.create(LanguageManager.getMessage("menus.roles.events.label"), "events_id", LanguageManager.getMessage("menus.roles.events.description")),
                    SelectMenuOption.create(LanguageManager.getMessage("menus.roles.changes.label"), "changes_id", LanguageManager.getMessage("menus.roles.changes.description"))
                    ));

            try {
                long roles_id = new MessageBuilder().setEmbed(new EmbedBuilder()
                        .setAuthor(plugin.getConfig().getString("embeds.roles.author"))
                        .addField(plugin.getConfig().getString("embeds.roles.field.name"), plugin.getConfig().getString("embeds.roles.field.value"))
                        .setColor(Color.GREEN)
                        .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                        plugin.getConfig().getString("embeds.footer.icon"))).addComponents(ActionRow.of(
                             selectMenu
                        )
                ).send(server.getTextChannelById(channel_pingi).get()).get().getId();

                ConfigManager.getDataFile().set("ids.roles", roles_id);
                plugin.saveConfig();

            } catch (InterruptedException | ExecutionException e) {
                ErrorUtil.logError(ErrorReason.DISCORD);
                e.printStackTrace();
            }
            ConfigManager.getDataFile().set("created.channel_pingi", true);
        }

        if (!ConfigManager.getDataFile().getBoolean("created.channel_colors")) {

            SelectMenu selectMenu = SelectMenu.create("colors", LanguageManager.getMessage("menus.color.title"), 0, 1,  Arrays.asList(
                    SelectMenuOption.create("❌️" + LanguageManager.getMessage("menus.color.default_pink"), "default_pink"),
                    SelectMenuOption.create("\uD83D\uDFE6️" + LanguageManager.getMessage("menus.color.blue"), "blue"),
                    SelectMenuOption.create("\uD83D\uDFE9️" + LanguageManager.getMessage("menus.color.green"), "green"),
                    SelectMenuOption.create("\uD83D\uDFE8️" + LanguageManager.getMessage("menus.color.yellow"), "yellow"),
                    SelectMenuOption.create("\uD83D\uDFE7️" + LanguageManager.getMessage("menus.color.orange"), "orange"),
                    SelectMenuOption.create("\uD83D\uDFEA️" + LanguageManager.getMessage("menus.color.purple"), "purple"),
                    SelectMenuOption.create("⬛️" + LanguageManager.getMessage("menus.color.grey"), "grey"),
                    SelectMenuOption.create("\uD83D\uDD33️" + LanguageManager.getMessage("menus.color.black"), "black"),
                    SelectMenuOption.create("⬜️" + LanguageManager.getMessage("menus.color.white"), "white")
            ));

            try {
                long roles_id = new MessageBuilder().setEmbed(new EmbedBuilder()
                        .setAuthor(plugin.getConfig().getString("embeds.colors.author"))
                        .addField(plugin.getConfig().getString("embeds.colors.field.name"), plugin.getConfig().getString("embeds.colors.field.value"))
                        .setColor(Color.GREEN)
                        .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                        .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                                plugin.getConfig().getString("embeds.footer.icon"))).addComponents(ActionRow.of(
                        selectMenu
                        )
                ).send(server.getTextChannelById(channel_colors).get()).get().getId();

                ConfigManager.getDataFile().set("ids.colors", roles_id);
                plugin.saveConfig();

            } catch (InterruptedException | ExecutionException e) {
                ErrorUtil.logError(ErrorReason.DISCORD);
                e.printStackTrace();
            }
            ConfigManager.getDataFile().set("created.channel_colors", true);
        }
        DiscordCommandManager.registerCommands();

        discordApi.updateStatus(UserStatus.ONLINE);
        discordApi.updateActivity(ActivityType.STREAMING, plugin.getConfig().getString("bot.description").replaceAll("\\{version}", plugin.getDescription().getVersion()));
        plugin.getLogger().info(LanguageManager.getMessage("connection.connected") + " " + discordApi.getYourself().getDiscriminatedName());
        ConfigManager.saveData();
    }
    public static void reconnect() {
        disconnect();
        connect();
    }
    public static void connect(){
            new DiscordApiBuilder()
                    .setToken(plugin.getConfig().getString("bot.token"))
                    .setAllIntents()
                    .login()
                    .thenAccept(plugin::onConnectToDiscord)
                    .exceptionally(error -> {
                        plugin.getLogger().warning(LanguageManager.getMessage("connection.disconnected"));
                        error.printStackTrace();
                        disconnect();
                        plugin.getPluginLoader().disablePlugin(plugin);
                        return null;
                    });
        }
    public static void disconnect(){
        if (api != null) {
            api.updateStatus(UserStatus.DO_NOT_DISTURB);
            api.updateActivity(LanguageManager.getMessage("activity.restart"));
            api.disconnect();
            DragonBot.clearApi();
        }
    }
}
