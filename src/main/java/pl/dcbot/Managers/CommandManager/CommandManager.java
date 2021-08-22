package pl.dcbot.Managers.CommandManager;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.BootstrapManager;
import pl.dcbot.Managers.ConfigManager;
import pl.dcbot.Managers.DCLink.DCLinkManager;
import pl.dcbot.Managers.LanguageManager;
import pl.dcbot.Managers.MessageManager;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pl.dcbot.Managers.BootstrapManager.channel_zgloszenia_lobby;
import static pl.dcbot.Managers.BootstrapManager.server;

public class CommandManager implements CommandExecutor {

    private static final DragonBot plugin = DragonBot.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("discord")) {
            MessageManager.sendMessageList(sender, "command.discord");
        }
        else if (cmd.getName().equalsIgnoreCase("dclink")) {
            int code = DCLinkManager.generateCode();
            DCLinkManager.dcLink(plugin, sender.getName(), code);
            sender.sendMessage(LanguageManager.getMessage("commands.dclink").replace("{code}", String.valueOf(code)));
        }
        else if (cmd.getName().equalsIgnoreCase("dcb")) {
            if (!sender.hasPermission("dcb.adm")) {
                MessageManager.sendPrefixedMessage(sender, "notfound");
                return true;
            } else {
                if (args.length == 0){
                    sender.sendMessage("§e§lDragon§a§lBot " + plugin.getDescription().getVersion() + "\n");
                    MessageManager.sendMessageList(sender, "pluginhelp.contents");
                }
                else if (args[0].equalsIgnoreCase("przeladuj") ||args[0].equalsIgnoreCase("przeładuj") || args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    BootstrapManager.reconnect();
                    LanguageManager.load();
                    ConfigManager.saveMessagesFile();
                    MessageManager.sendPrefixedMessage(sender, "reload");
                } else {
                    sender.sendMessage("§e§lDragon§a§lBot " + plugin.getDescription().getVersion() + "\n");
                    MessageManager.sendMessageList(sender, "pluginhelp.contents");
                }
            }
        }
        else if (cmd.getName().equalsIgnoreCase("zglos")) {
            if (args.length == 0) {
                MessageManager.sendPrefixedMessage(sender, "commands.report.usage");
            } else {
                MessageManager.sendPrefixedMessage(sender, "commands.report.sent");
                int number = ConfigManager.getDataFile().getInt("number") + 1;

                final StringBuilder sb = new StringBuilder();
                for (String arg : args) {
                    sb.append(arg).append(" ");
                }
                final String allArgs = sb.toString().trim();

                DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                Date date = new Date();
                String time = dateFormat.format(date);

                TextChannel channel = server.getApi().getTextChannelById(channel_zgloszenia_lobby).get();

                String msg = LanguageManager.getMessage("commands.report.staff_notification").replace("{number}", String.valueOf(number)).replace("{player}", sender.getName());
                for (Player o : Bukkit.getOnlinePlayers()) {
                    if (o.hasPermission("panel.adm")) {
                        o.sendMessage(msg);
                        o.playSound(o.getLocation(), Sound.valueOf(plugin.getConfig().getString("report_sound")), 100, 1);
                    }
                }
                plugin.getLogger().info(msg);

                    EmbedBuilder embed = new EmbedBuilder()
                            .setAuthor(LanguageManager.getMessage("commands.report.embed.report") + number)
                            .addField(sender.getName() + ":", allArgs)
                            .setColor(Color.CYAN)
                            .setFooter(plugin.getConfig().getString("embeds.footer.text")
                                            .replace("{version}", plugin.getDescription().getVersion()).replace("{time}", time),
                                    plugin.getConfig().getString("embeds.footer.icon"));
                    MessageManager.sendMessage(channel, embed);
                    ConfigManager.getDataFile().set("number", number);
                    ConfigManager.saveData();
            }
            return true;
        }
        return true;
    }
}
