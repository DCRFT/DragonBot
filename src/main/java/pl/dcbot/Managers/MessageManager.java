package pl.dcbot.Managers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SelectMenuInteraction;
import org.javacord.api.interaction.SlashCommandInteraction;
import pl.dcbot.Utils.ColorUtil;

public class MessageManager {
    private static final String prefix = LanguageManager.getMessage("prefix");

    public static void sendMessage(Object player, String key) {
        if (player instanceof Player) {
            ((Player) player).sendMessage(ColorUtil.colorize(LanguageManager.getMessage(key)));
        } else if (player instanceof CommandSender) {
            ((CommandSender) player).sendMessage(ColorUtil.colorize(LanguageManager.getMessage(key)));
        }
    }
    public static void sendMessage(SlashCommandInteraction command, String key) {
        command.createImmediateResponder()
                .setContent(LanguageManager.getMessage(key))
                .respond();
    }
    public static void sendRawMessage(SlashCommandInteraction command, String message) {
        command.createImmediateResponder()
                .setContent(message)
                .respond();
    }
    public static void sendFollowupMessage(SlashCommandInteraction command, String key) {
        command.createImmediateResponder()
                .setContent(LanguageManager.getMessage(key))
                .respond();
    }
    public static void sendRawFollowupMessage(SlashCommandInteraction command, String message) {
        command.createImmediateResponder()
                .setContent(message)
                .respond();
    }
    public static void sendMessage(SelectMenuInteraction menu, String key) {
        menu.createImmediateResponder()
                .setContent(LanguageManager.getMessage(key))
                .respond();
    }
    public static void sendRawMessage(SelectMenuInteraction menu, String message) {
        menu.createImmediateResponder()
                .setContent(message)
                .respond();
    }
    public static void sendMessage(User user, String message) {
        user.sendMessage(message);
    }
    public static void sendMessage(User user, EmbedBuilder embed) {
        user.sendMessage(embed);
    }
    public static void sendMessage(TextChannel textChannel, EmbedBuilder embed) {
        textChannel.sendMessage(embed);
    }
    public static void sendRawMessage(TextChannel textChannel, String message) {
        textChannel.sendMessage(message);
    }
    public static void sendMessage(TextChannel textChannel, String key) {
        textChannel.sendMessage(LanguageManager.getMessage("key"));
    }
    public static void sendMessageList(Object player, String key) {
        for (final String msg : LanguageManager.getMessageList(key)) {
            if (player instanceof Player) {
                ((Player) player).sendMessage(ColorUtil.colorize(msg));
            } else if (player instanceof CommandSender) {
                ((CommandSender) player).sendMessage(ColorUtil.colorize(msg));
            }
        }
    }

    public static void sendPrefixedMessage(Object player, String key) {
        if (player instanceof Player) {
            ((Player) player).sendMessage(ColorUtil.colorize(prefix + LanguageManager.getMessage(key)));
        } else if (player instanceof CommandSender) {
            ((CommandSender) player).sendMessage(ColorUtil.colorize(prefix + LanguageManager.getMessage(key)));
        }
    }
}