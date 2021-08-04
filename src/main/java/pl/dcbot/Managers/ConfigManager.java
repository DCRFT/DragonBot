package pl.dcbot.Managers;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.dcbot.DragonBot;
import pl.dcbot.Utils.ErrorUtils.ErrorReason;
import pl.dcbot.Utils.ErrorUtils.ErrorUtil;

import java.io.File;
import java.io.IOException;


public class ConfigManager {
    public static final DragonBot plugin = DragonBot.getInstance();

    public static File configFile;
    public static FileConfiguration config;

    public static File databaseConfigFile;
    public static FileConfiguration databaseConfig;

    public static File dataFile;
    public static FileConfiguration data;

    public static File messagesConfigFile;
    public static FileConfiguration messagesConfig;

    public static void initialize() {
        createConfig();
        createDatabaseConfig();
        createDataFile();
        createMessagesFile();
    }

    public static void createConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }

        config = new YamlConfiguration();

        try {
            config.load(configFile);
        } catch (InvalidConfigurationException | IOException e) {
            ErrorUtil.logError(ErrorReason.CONFIG);
            e.printStackTrace();
        }
    }
    public static FileConfiguration getDatabaseFile() {
        return databaseConfig;
    }

    public static void createDatabaseConfig() {
        databaseConfigFile = new File(plugin.getDataFolder(), "database.yml");
        if (!databaseConfigFile.exists()) {
            databaseConfigFile.getParentFile().mkdirs();
            plugin.saveResource("database.yml", false);
        }

        databaseConfig = new YamlConfiguration();

        try {
            databaseConfig.load(databaseConfigFile);
        } catch (InvalidConfigurationException | IOException e) {
             ErrorUtil.logError(ErrorReason.DATA);
            e.printStackTrace();
        }

    }
    public static void saveDatabaseConfig() {
        try {
            databaseConfig.save(databaseConfigFile);

        } catch (Exception e) {
             ErrorUtil.logError(ErrorReason.CONFIG);
            e.printStackTrace();
        }
    }

    public static FileConfiguration getDataFile() {
        return data;
    }

    public static void createDataFile() {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            dataFile.getParentFile().mkdirs();
            plugin.saveResource("data.yml", false);
        }

        data = new YamlConfiguration();

        try {
            data.load(dataFile);
        } catch (InvalidConfigurationException | IOException e) {
             ErrorUtil.logError(ErrorReason.DATA);
            e.printStackTrace();
        }

    }
    public static void saveData() {
        try {
            data.save(dataFile);
        } catch (Exception e) {
             ErrorUtil.logError(ErrorReason.DATA);
            e.printStackTrace();
        }
    }

    public static FileConfiguration getMessagesFile() {
        return messagesConfig;
    }

    public static void createMessagesFile() {
        messagesConfigFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesConfigFile.exists()) {
            messagesConfigFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }

        messagesConfig = new YamlConfiguration();

        try {
            messagesConfig.load(messagesConfigFile);
        } catch (InvalidConfigurationException | IOException e) {
             ErrorUtil.logError(ErrorReason.MESSAGES);
            e.printStackTrace();
        }

    }
    public static void saveMessagesFile() {
        try {
            messagesConfig.save(messagesConfigFile);

        } catch (Exception e) {
             ErrorUtil.logError(ErrorReason.MESSAGES);
            e.printStackTrace();
        }
    }
}