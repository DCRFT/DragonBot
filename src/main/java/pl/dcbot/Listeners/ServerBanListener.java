package pl.dcbot.Listeners;

import org.bukkit.Bukkit;
import org.javacord.api.event.server.member.ServerMemberBanEvent;
import org.javacord.api.event.server.member.ServerMemberUnbanEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;
import org.javacord.api.listener.server.member.ServerMemberUnbanListener;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.DatabaseManager;
import pl.dcbot.Utils.ErrorUtils.ErrorReason;
import pl.dcbot.Utils.ErrorUtils.ErrorUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class ServerBanListener implements ServerMemberBanListener, ServerMemberUnbanListener {
    private static final DragonBot plugin = DragonBot.getInstance();
    @Override
    public void onServerMemberBan(ServerMemberBanEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                DatabaseManager.openConnection();

                byte[] nick_awatar = "".getBytes();
                if (e.getUser().getAvatar().asByteArray().get() != null) {
                    nick_awatar = e.getUser().getAvatar().asByteArray().get();
                }
                String image = Base64.getEncoder().encodeToString(nick_awatar);

                String powod = "brak powodu";
                if (!e.requestBan().get().getReason().isPresent()) {
                    powod = e.requestBan().get().getReason().toString();
                }

                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date date = new Date();
                String time = dateFormat.format(date);


                String update = "INSERT INTO `bany_discord` (nick_awatar, nick_id, nick, powod, data) VALUES (" +
                        "'" + image + "'," +
                        "'" + e.getUser().getIdAsString() + "'," +
                        " '" + e.getUser().getName() + "'," +
                        " '" + powod + "'," +
                        " '" + time + "');";

                DatabaseManager.get().executeStatement(update);
            } catch (ExecutionException | InterruptedException e1) {
                e1.printStackTrace();
                ErrorUtil.logError(ErrorReason.DATABASE);
            }
        });
    }

    @Override
    public void onServerMemberUnban(ServerMemberUnbanEvent e) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            DatabaseManager.openConnection();

            String update = "DELETE FROM `bany_discord` WHERE `nick_id` = " + e.getUser().getIdAsString() + "";

            DatabaseManager.get().executeStatement(update);
        });
    }
}
