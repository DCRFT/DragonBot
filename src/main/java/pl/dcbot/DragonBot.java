package pl.dcbot;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.plugin.java.JavaPlugin;
import org.javacord.api.DiscordApi;
import pl.dcbot.Listeners.*;
import pl.dcbot.Managers.BootstrapManager;
import pl.dcbot.Managers.CommandManager.CommandManager;
import pl.dcbot.Managers.CommandManager.DiscordCommandManager;
import pl.dcbot.Managers.ConfigManager;
import pl.dcbot.Managers.DatabaseManager;
import pl.dcbot.Managers.LanguageManager;

import java.util.List;

public class DragonBot extends JavaPlugin {

    private static DiscordApi api;

    public static DiscordApi getApi() {
        return api;
    }

    public static void clearApi() {
        DragonBot.api = null;
    }
    public static DragonBot getInstance() {
        return instance;
    }

    private static DragonBot instance;
    @Override
    public void onEnable() {
        instance = this;
        ConfigManager.initialize();

        List<Command> commands = PluginCommandYamlParser.parse(this);
        for (Command command : commands) {
            getCommand(command.getName()).setExecutor(new CommandManager());
        }
        Bukkit.getScheduler().runTaskAsynchronously(this, DatabaseManager::openConnection);

        BootstrapManager.connect();

        getLogger().info(LanguageManager.getMessage("plugin.header"));
        getLogger().info("§e§lDragon§a§lBot");
        getLogger().info(LanguageManager.getMessage("plugin.enabled") + getDescription().getVersion());
        getLogger().info(LanguageManager.getMessage("plugin.footer"));
    }

    public void onDisable() {
        BootstrapManager.disconnect();

        DatabaseManager.closeConnection();

        getLogger().info(LanguageManager.getMessage("plugin.header"));
        getLogger().info("§e§lDragon§a§lBot");
        getLogger().info(LanguageManager.getMessage("plugin.disabled") + getDescription().getVersion());
        getLogger().info(LanguageManager.getMessage("plugin.footer"));
    }

    public void onConnectToDiscord(DiscordApi api) {
        DragonBot.api = api;

        api.addListener(new DiscordCommandManager());
        api.addListener(new ServerMemberJoinListener());
        api.addListener(new DirectMessageListener());
        api.addListener(new ServerBanListener());
        api.addListener(new ReactionListener());
        api.addListener(new ServerVoiceChannelListener());
        api.addListener(new MessageCreateListener());
        api.addSlashCommandCreateListener(new SlashCommandsListener());
        api.addMessageComponentCreateListener(new MessageComponentCreateListener());

        BootstrapManager.initialize(api);
    }
}
