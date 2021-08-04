package pl.dcbot.Listeners;

import org.bukkit.Bukkit;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.DatabaseManager;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pl.dcbot.Managers.BootstrapManager.*;

public class MessageCreateListener implements org.javacord.api.listener.message.MessageCreateListener {
    private static final DragonBot plugin = DragonBot.getInstance();

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        if (e.getChannel().getId() == channel_glowny) {
            String autor = e.getMessageAuthor().getDisplayName();
            String tresc = e.getMessageContent();

            Date date = new Date();

            DateFormat timeFormat = new SimpleDateFormat("HH:mm");

            String time = timeFormat.format(date);

            EmbedBuilder embed = new EmbedBuilder()
                    .setAuthor(autor)
                    .addField(tresc, "\u200b")
                    .setColor(Color.BLUE)
                    .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                    .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                            plugin.getConfig().getString("embeds.footer.icon"));
            e.getServer().get().getChannelById(channel_logi).get().asServerTextChannel().get().sendMessage(embed);
        }

        if (e.getChannel().getId() == channel_ogloszenia || e.getChannel().getId() == channel_eventy) {

            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date date = new Date();

            String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
            String data = dateFormat.format(date);
            String nick = e.getMessageAuthor().getDisplayName();
            String tresc = e.getMessageContent().replaceAll(characterFilter, "");

            StringBuilder sb = new StringBuilder();
            for (String word : tresc.split("(^|$|\\s)")) {

                if (word.startsWith("#")) {
                    if (e.getServer().get().getTextChannelById(word.replaceAll("#", "")).isPresent()) {
                        word = "#" + e.getServer().get().getTextChannelById(word.replaceAll("#", "")).get().getName();
                    }
                } else if (word.startsWith("@&")) {
                    if (e.getServer().get().getRoleById(word.replaceAll("@&", "")).isPresent()) {
                        word = "@" + e.getServer().get().getRoleById(word.replaceAll("@&", "")).get().getName();
                    }
                } else if (word.startsWith(":")) {
                    if (e.getServer().get().getCustomEmojiById(word.replaceAll("\\D+", "")).isPresent()) {
                        word = "";
                    }
                }
                sb.append(word);
                sb.append(" ");
            }
            tresc = sb.toString();

            String finalTresc = tresc;
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    DatabaseManager.openConnection();
                    PreparedStatement pstmt;
                    if (e.getChannel().getId() == channel_eventy) {
                        pstmt = DatabaseManager.connection.prepareStatement("INSERT INTO `ogloszenia` (nick, data, tresc, typ) VALUES (?, ?, ?, ?)");
                        pstmt.setInt(4, 1);
                    } else {
                        pstmt = DatabaseManager.connection.prepareStatement("INSERT INTO `ogloszenia` (nick, data, tresc, typ) VALUES (?, ?, ?, ?)");
                        pstmt.setInt(4, 0);
                    }

                    pstmt.setString(1, nick);
                    pstmt.setString(2, data);
                    pstmt.setString(3, finalTresc)
                    ;
                    pstmt.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
        }

        if (e.getChannel().getId() == channel_testowy) {


            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
            Date date = new Date();

            String characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]";
            String data = dateFormat.format(date);
            String nick = e.getMessageAuthor().getDisplayName();
            String tresc = e.getMessageContent().replaceAll(characterFilter, "");

            StringBuilder sb = new StringBuilder();
            for (String word : tresc.split("(^|$|\\s)")) {

                if (word.startsWith("#")) {
                    if (e.getServer().get().getTextChannelById(word.replaceAll("#", "").replaceAll(" ", "")).isPresent()) {
                        word = "#" + e.getServer().get().getTextChannelById(word.replaceAll("#", "").replaceAll(" ", "")).get().getName();
                    }
                } else if (word.startsWith("@&")) {
                    if (e.getServer().get().getRoleById(word.replaceAll("@&", "").replaceAll(" ", "")).isPresent()) {
                        word = "@" + e.getServer().get().getRoleById(word.replaceAll("@&", "").replaceAll(" ", "")).get().getName();
                    }
                } else if (word.startsWith(":")) {
                    if (e.getServer().get().getCustomEmojiById(word.replaceAll("\\D+", "").replaceAll(" ", "")).isPresent()) {
                        word = "";
                    }
                }
                sb.append(word);
                sb.append(" ");
            }
            tresc = sb.toString();

            String finalTresc = tresc;
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    DatabaseManager.openConnection();
                    PreparedStatement pstmt;
                    if (e.getChannel().getId() == channel_eventy) {
                        pstmt = DatabaseManager.connection.prepareStatement("INSERT INTO `ogloszenia_testy` (nick, data, tresc, typ) VALUES (?, ?, ?, ?)");
                        pstmt.setInt(4, 1);
                    } else {
                        pstmt = DatabaseManager.connection.prepareStatement("INSERT INTO `ogloszenia_testy` (nick, data, tresc, typ) VALUES (?, ?, ?, ?)");
                        pstmt.setInt(4, 0);
                    }

                    pstmt.setString(1, nick);
                    pstmt.setString(2, data);
                    pstmt.setString(3, finalTresc);
                    pstmt.executeUpdate();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });


        }
    }
}
