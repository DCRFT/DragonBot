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
import java.sql.Statement;
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
            try {
                DatabaseManager.openConnection();
                Statement statement = DatabaseManager.connection.createStatement();

                String usun = "DELETE FROM `" + DatabaseManager.table + "` WHERE nick='" + username + "'";
                String dodaj = "INSERT INTO `" + DatabaseManager.table + "` (nick, kod) VALUES ('" + username + "', " + code + ")";

                statement.executeUpdate(usun);
                statement.executeUpdate(dodaj);
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public static void dcLink(Plugin plugin, String username, int code) {
        insertCode(plugin, username, code);
    }

    public static void registerDiscord(Plugin plugin, SlashCommandInteraction message, User user, int code) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                DatabaseManager.openConnection();
                Statement discord = DatabaseManager.connection.createStatement();
                String szukajkodu = "SELECT * FROM `discord` WHERE kod='" + code + "'";
                discord.executeQuery(szukajkodu);
                ResultSet result = discord.getResultSet();
                if (!result.next()) {
                    discord.close();
                    result.close();
                    message.createImmediateResponder()
                            .setContent(LanguageManager.getMessage("dclink.wrong_code"))
                            .respond();
                } else {
                    String nick = result.getString("nick");
                    DatabaseManager.openConnection();
                    Statement rankstatement = DatabaseManager.connection.createStatement();
                    String rank = "SELECT ranga FROM `staty_s16` WHERE nick='" + nick + "'";
                    rankstatement.executeQuery(rank);
                    ResultSet result2 = rankstatement.getResultSet();
                    if (!result2.next()) {
                        rankstatement.close();
                        discord.close();
                        result.close();
                        message.createImmediateResponder()
                                .setContent(LanguageManager.getMessage("dclink.error") + " err_no_rank")
                                .respond();
                    } else {
                        String finalRank = result2.getString("ranga");
                        discord.executeUpdate("DELETE FROM `discord` WHERE kod='" + code + "'");
                        rankstatement.close();
                        discord.close();
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
                            rankstatement.close();
                            discord.close();
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
                    }


                }
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
