package pl.dcbot.Managers.ReportManager;

import org.bukkit.Bukkit;
import org.javacord.api.entity.channel.RegularServerChannel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.ButtonBuilder;
import org.javacord.api.entity.message.component.ButtonStyle;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.PermissionState;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.MessageComponentInteraction;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.ConfigManager;
import pl.dcbot.Managers.LanguageManager;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static pl.dcbot.Managers.BootstrapManager.*;

public class ReportManager {
    private static final DragonBot plugin = DragonBot.getInstance();

    public static void newReport(MessageComponentInteraction message, String time) {
        int id = ConfigManager.getDataFile().getInt("numbers.reports_discord") + 1;
        ServerTextChannel report = new ServerTextChannelBuilder(server)
                .setName(plugin.getConfig().getString("embeds.reports.new_report.channel_name") + id)
                .setCategory(api.getChannelCategoryById(category_zgl_otwarte).get())
                .setTopic(String.valueOf(id))
                .addPermissionOverwrite(message.getUser(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .setState(PermissionType.READ_MESSAGE_HISTORY, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getEveryoneRole(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.DENIED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_pomocnik).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_moderator).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_viceadministrator).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_administrator).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_wlasciciel).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .create().join();

        report.sendMessage(message.getUser().getNicknameMentionTag());
        try {
            new MessageBuilder().setEmbed(
                    new EmbedBuilder()
                            .setAuthor(plugin.getConfig().getString("embeds.reports.new_report.author") + id)
                            .addField(plugin.getConfig().getString("embeds.reports.new_report.field1.name"), plugin.getConfig().getString("embeds.reports.new_report.field1.value"))
                            .addField(plugin.getConfig().getString("embeds.reports.new_report.field2.name"), plugin.getConfig().getString("embeds.reports.new_report.field2.value"))
                            .setColor(Color.GREEN)
                            .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                            .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                                    plugin.getConfig().getString("embeds.footer.icon")))
                    .addComponents(ActionRow.of(
                            Button.danger(
                                    plugin.getConfig().getString("embeds.reports.new_report.button.id"),
                                    plugin.getConfig().getString("embeds.reports.new_report.button.label")),
                            Button.primary(
                                    plugin.getConfig().getString("embeds.reports.new_report.claim.id"),
                                    plugin.getConfig().getString("embeds.reports.new_report.claim.label"))))
                    .send(report).get().getId();
        } catch (InterruptedException | ExecutionException err) {
            err.printStackTrace();
        }
        ConfigManager.getDataFile().set("numbers.reports_discord", id);
        ConfigManager.saveData();
    }

    public static void newSuggestion(MessageComponentInteraction message, String time) {
        int id = ConfigManager.getDataFile().getInt("numbers.suggestions_discord") + 1;
        ServerTextChannel report = new ServerTextChannelBuilder(server)
                .setName(plugin.getConfig().getString("embeds.suggestions.new_suggestion.channel_name") + id)
                .setCategory(api.getChannelCategoryById(category_propo_otwarte).get())
                .setTopic(String.valueOf(id))
                .addPermissionOverwrite(message.getUser(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getEveryoneRole(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.DENIED)
                        .setState(PermissionType.ADD_REACTIONS, PermissionState.DENIED)
                        .setState(PermissionType.READ_MESSAGE_HISTORY, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_pomocnik).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_moderator).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_viceadministrator).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_administrator).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .addPermissionOverwrite(server.getRoleById(role_wlasciciel).get(), new PermissionsBuilder()
                        .setState(PermissionType.VIEW_CHANNEL, PermissionState.ALLOWED)
                        .setState(PermissionType.SEND_MESSAGES, PermissionState.ALLOWED)
                        .build())
                .create().join();

        report.sendMessage(message.getUser().getNicknameMentionTag());
        try {
            Message mess = new MessageBuilder().setEmbed(
                    new EmbedBuilder()
                            .setAuthor(plugin.getConfig().getString("embeds.suggestions.new_suggestion.author") + id)
                            .addField(plugin.getConfig().getString("embeds.suggestions.new_suggestion.field1.name"), plugin.getConfig().getString("embeds.suggestions.new_suggestion.field1.value"))
                            .addField(plugin.getConfig().getString("embeds.suggestions.new_suggestion.field2.name"), plugin.getConfig().getString("embeds.suggestions.new_suggestion.field2.value"))
                            .setColor(Color.GREEN)
                            .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                            .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                                    plugin.getConfig().getString("embeds.footer.icon")))
                    .addComponents(ActionRow.of(
                            Button.success(
                                    plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_accept.id"),
                                    plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_accept.label")),
                            Button.secondary(
                                    plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_close.id"),
                                    plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_close.label")),
                            Button.danger(
                                    plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_reject.id"),
                                    plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_reject.label"))
                            ))
                    .send(report).get();
            mess.addReaction(plugin.getConfig().getString("serverconfig.emojis.plus_jeden_name") + ":" + emoji_plus_jeden); //+1
            mess.addReaction(plugin.getConfig().getString("serverconfig.emojis.minus_jeden_name") + ":" + emoji_minus_jeden); //-1
        } catch (InterruptedException | ExecutionException err) {
            err.printStackTrace();
        }
        ConfigManager.getDataFile().set("numbers.suggestions_discord", id);
        ConfigManager.saveData();
    }


    public static void closeReport(MessageComponentInteraction message, ServerTextChannel channel, User user, String time) {
        message.createOriginalMessageUpdater().addComponents(ActionRow.of(
                new ButtonBuilder()
                        .setLabel(plugin.getConfig().getString("embeds.reports.new_report.button.closed.label"))
                        .setCustomId(plugin.getConfig().getString("embeds.reports.new_report.button.closed.id"))
                        .setStyle(ButtonStyle.DANGER)
                        .setDisabled(true)
                        .build()
        )).update().join();

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor("Zgłoszenie #" + channel.getTopic())
                .addField("Zgłoszenie zostało zamknięte przez", user.getDisplayName(server))
                .setColor(Color.RED)
                .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                        plugin.getConfig().getString("embeds.footer.icon"));
        channel.sendMessage(embed);


        List<RegularServerChannel> channels = server.getChannelCategoryById(category_zgl_zamkniete).get().getChannels();
        if (channels.size() >= 15) {
            long descMin = System.currentTimeMillis();
            int iMin = channels.size() - 1;
            for(int i = 0; i < channels.size(); i ++) {
                long currentDescTimestamp = Long.parseLong(channels.get(i).asServerTextChannel().get().getTopic());
                if(currentDescTimestamp < descMin){
                    descMin = currentDescTimestamp;
                    iMin = i;
                }
            }
            server.getChannelCategoryById(category_zgl_zamkniete).get().getChannels().get(iMin).delete("DragonBot reports cleanup.");
        }
        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
            message.getChannel().get().asServerTextChannel().get().updateCategory(server.getChannelCategoryById(category_zgl_zamkniete).get());
            message.getChannel().get().asServerTextChannel().get().updateTopic(String.valueOf(System.currentTimeMillis()));
            channel.createUpdater().setRawPosition(100);
        }, 5L);
        }


    public static void sendClaim(MessageComponentInteraction message, ServerTextChannel channel, User user, String time) {
        message.createOriginalMessageUpdater().addComponents(ActionRow.of(
                new ButtonBuilder()
                        .setLabel(plugin.getConfig().getString("embeds.reports.new_report.button.label"))
                        .setCustomId(plugin.getConfig().getString("embeds.reports.new_report.button.id"))
                        .setStyle(ButtonStyle.DANGER)
                        .setDisabled(false)
                        .build(),
                new ButtonBuilder()
                        .setLabel(plugin.getConfig().getString("embeds.reports.new_report.claim.label"))
                        .setCustomId(plugin.getConfig().getString("embeds.reports.new_report.claim.id"))
                        .setStyle(ButtonStyle.PRIMARY)
                        .setDisabled(false)
                        .build()
        )).update().join();

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(plugin.getConfig().getString("embeds.reports.new_report.claim.embed.author") + channel.getTopic())
                .addField(plugin.getConfig().getString("embeds.reports.new_report.claim.embed.field1.name"), user.getDisplayName(server))
                .setColor(Color.CYAN)
                .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                        plugin.getConfig().getString("embeds.footer.icon"));
        channel.sendMessage(embed);
    }



    public static void closeSuggestion(MessageComponentInteraction message, ServerTextChannel channel, User user, SuggestionCloseReason reason, String time) {

        int upvotes = message.getMessage().getReactionByEmoji(server.getCustomEmojiById(emoji_plus_jeden).get()).get().getCount() - 1;
        int downvotes = message.getMessage().getReactionByEmoji(server.getCustomEmojiById(emoji_minus_jeden).get()).get().getCount() - 1;

        message.getMessage().removeAllReactions().join();

        ButtonStyle style = ButtonStyle.SECONDARY;
        String label = plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_close.closed.label");
        String id = plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_close.closed.id");

        EmbedBuilder embed = new EmbedBuilder()
                .setAuthor(plugin.getConfig().getString("embeds.suggestions.new_suggestion.author") + channel.getTopic())
                .setColor(Color.LIGHT_GRAY)
                .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                        plugin.getConfig().getString("embeds.footer.icon"));

        switch (reason){
            case ACCEPTED:
                style = ButtonStyle.SUCCESS;
                label = plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_accept.closed.label");
                id = plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_accept.id");
                embed.addField(plugin.getConfig().getString("embeds.suggestions.new_suggestion.accepted"), user.getDisplayName(server));
                embed.setColor(Color.GREEN);
                break;
            case REJECTED:
                style = ButtonStyle.DANGER;
                label = plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_reject.closed.label");
                id = plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_reject.id");
                embed.addField(plugin.getConfig().getString("embeds.suggestions.new_suggestion.rejected"), user.getDisplayName(server));
                embed.setColor(Color.RED);
                break;
            case CLOSED:
                embed.addField(plugin.getConfig().getString("embeds.suggestions.new_suggestion.closed") , user.getDisplayName(server));
                break;
        }
        embed.addField(LanguageManager.getMessage("suggestions.upvotes"), String.valueOf(upvotes), true);
        embed.addField(LanguageManager.getMessage("suggestions.downvotes"), String.valueOf(downvotes), true);
        message.createOriginalMessageUpdater().addComponents(ActionRow.of(
                new ButtonBuilder()
                        .setLabel(label)
                        .setCustomId(id)
                        .setStyle(style)
                        .setDisabled(true)
                        .build()
        )).update().join();

        channel.sendMessage(embed).join();


        List<RegularServerChannel> channels = server.getChannelCategoryById(category_propo_zamkniete).get().getChannels();
        if (channels.size() >= 15) {
            long descMin = System.currentTimeMillis();
            int iMin = channels.size() - 1;
            for(int i = 0; i < channels.size(); i ++) {
                long currentDescTimestamp = Long.parseLong(channels.get(i).asServerTextChannel().get().getTopic());
                if(currentDescTimestamp < descMin){
                    descMin = currentDescTimestamp;
                    iMin = i;
                }
            }
            server.getChannelCategoryById(category_propo_zamkniete).get().getChannels().get(iMin).delete("DragonBot reports cleanup.");
        }

        Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, () -> {
            message.getChannel().get().asServerTextChannel().get().updateCategory(server.getChannelCategoryById(category_propo_zamkniete).get());
            message.getChannel().get().asServerTextChannel().get().updateTopic(String.valueOf(System.currentTimeMillis()));
            channel.createUpdater().setRawPosition(100);
        }, 5L);
    }
}
