package pl.dcbot.Managers.DCLink;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.javacord.api.entity.user.User;
import org.javacord.api.interaction.SlashCommandInteraction;
import pl.dcbot.Managers.DatabaseManager;
import pl.dcbot.Managers.LanguageManager;
import pl.dcbot.Utils.ErrorUtils.ErrorReason;
import pl.dcbot.Utils.ErrorUtils.ErrorUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import static pl.dcbot.Managers.BootstrapManager.*;

public class DCLinkManager {
    
    public static int generateCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return Integer.parseInt(String.format("%06d", number));
    }

    public static void insertCode(Plugin plugin, String username, int code) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            DatabaseManager.openConnection();

            String usun = "DELETE FROM `" + DatabaseManager.table + "` WHERE nick='" + username + "'";
            String dodaj = "INSERT INTO `" + DatabaseManager.table + "` (nick, kod) VALUES ('" + username + "', " + code + ")";


            DatabaseManager.get().executeStatement(usun);
            DatabaseManager.get().executeStatement(dodaj);
        });
    }

    public static void dcLink(Plugin plugin, String username, int code) {
        insertCode(plugin, username, code);
    }

    public static void registerDiscord(Plugin plugin, SlashCommandInteraction message, User user, long code) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                DatabaseManager.openConnection();
                String szukajkodu = "SELECT * FROM `discord` WHERE kod='" + code + "'";
                ResultSet result = DatabaseManager.get().executeResultStatement(szukajkodu);

                if (!result.next()) {
                    result.close();
                    message.createImmediateResponder()
                            .setContent(LanguageManager.getMessage("dclink.wrong_code"))
                            .respond();
                } else {
                    String nick = result.getString("nick");
                    DatabaseManager.openConnection();
                    String rank = "SELECT ranga FROM `staty_s16` WHERE nick='" + nick + "'";
                    ResultSet result2 = DatabaseManager.get().executeResultStatement(rank);
                    if (!result2.next()) {
                        result2.close();
                        result.close();
                        message.createImmediateResponder()
                                .setContent(LanguageManager.getMessage("dclink.error") + " err_no_rank")
                                .respond();
                    } else {
                        String finalRank = result2.getString("ranga");
                        String usun = "DELETE FROM `discord` WHERE kod='" + code + "'";
                        if (finalRank.equalsIgnoreCase("Gracz")) {
                            user.addRole(server.getRoleById(role_gracz).get());
                            user.updateNickname(server, "[" + "Gracz" + "]" + nick, LanguageManager.getMessage("dclink.nickname_change_reason"));
                        } else if (finalRank.equalsIgnoreCase("VIP") || finalRank.equalsIgnoreCase("VIP+")) {
                            user.removeRole(server.getRoleById(role_gracz).get());
                            user.addRole(server.getRoleById(role_vip).get());
                            user.updateNickname(server, "[" + "VIP" + "]" + nick, LanguageManager.getMessage("dclink.nickname_change_reason"));
                        } else if (finalRank.equalsIgnoreCase("SVIP") || finalRank.equalsIgnoreCase("SVIP+")) {
                            user.removeRole(server.getRoleById(role_gracz).get());
                            user.removeRole(server.getRoleById(role_vip).get());
                            user.addRole(server.getRoleById(role_svip).get());
                            user.updateNickname(server, "[" + "SVIP" + "]" + nick, LanguageManager.getMessage("dclink.nickname_change_reason"));
                        } else if (finalRank.equalsIgnoreCase("MVIP") || finalRank.equalsIgnoreCase("MVIP+")) {
                            user.removeRole(server.getRoleById(role_gracz).get());
                            user.removeRole(server.getRoleById(role_vip).get());
                            user.removeRole(server.getRoleById(role_svip).get());
                            user.addRole(server.getRoleById(role_mvip).get());
                            user.updateNickname(server, "[" + "MVIP" + "]" + nick, LanguageManager.getMessage("dclink.nickname_change_reason"));
                        } else if (finalRank.equalsIgnoreCase("EVIP") || finalRank.equalsIgnoreCase("EVIP+")) {
                            user.removeRole(server.getRoleById(role_gracz).get());
                            user.removeRole(server.getRoleById(role_vip).get());
                            user.removeRole(server.getRoleById(role_svip).get());
                            user.removeRole(server.getRoleById(role_mvip).get());
                            user.addRole(server.getRoleById(role_evip).get());
                            user.updateNickname(server, "[" + "EVIP" + "]" + nick, LanguageManager.getMessage("dclink.nickname_change_reason"));
                        } else {
                            result2.close();
                            result.close();
                            message.createImmediateResponder()
                                    .setContent(LanguageManager.getMessage("dclink.error") + " err_wrong_rank " + finalRank)
                                    .respond();
                        }
                        result2.close();
                        result.close();
                        message.createImmediateResponder()
                                .setContent(
                                        LanguageManager.getMessage("dclink.success")
                                                .replace("{user}", message.getUser().getMentionTag())
                                                .replace("{minecraft}", nick))
                                .respond();
                        result.close();
                    }
                    result.close();
                }
                result.close();
            } catch (SQLException e) {
                e.printStackTrace();
                ErrorUtil.logError(ErrorReason.DATABASE);
                message.createImmediateResponder()
                        .setContent(LanguageManager.getMessage("dclink.error") + " err_mysql")
                        .respond();
            }
        });
    }
}
