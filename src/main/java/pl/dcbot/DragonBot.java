package pl.dcbot;

import com.alessiodp.libby.BukkitLibraryManager;
import com.alessiodp.libby.Library;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommandYamlParser;
import org.bukkit.plugin.java.JavaPlugin;
import pl.dcbot.Listeners.*;
import pl.dcbot.Managers.*;
import pl.dcbot.Managers.CommandManager.CommandManager;
import pl.dcbot.Managers.CommandManager.DiscordCommandManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DragonBot extends JavaPlugin {


    public static DragonBot getInstance() {
        return instance;
    }

    private static DragonBot instance;

    public List<Library> getLibraryListFromJson(InputStream is){
        List<Library> result = new ArrayList<>();
        try(InputStreamReader reader = new InputStreamReader(is)){
            Gson gson = new Gson();
            JsonArray array = gson.fromJson(reader, JsonArray.class);
            if(array==null||array.isJsonNull())return result;
            for(JsonElement element:array) {
                JsonObject library = element.getAsJsonObject();
                Library.Builder builder =
                        Library.builder()
                                .groupId(library.get("groupId").getAsString())
                                .artifactId(library.get("artifactId").getAsString())
                                .version(library.get("version").getAsString());
                result.add(builder.build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onLoad() {
    }


    @Override
    public void onEnable() {
        BukkitLibraryManager manager = new BukkitLibraryManager(this);
        manager.addMavenCentral();
        manager.addJitPack();
        manager.addJCenter();
        manager.addRepository("https://m2.dv8tion.net/releases");
        manager.addRepository("https://maven.lavalink.dev/releases");
        getLibraryListFromJson(this.getResource("depends.json")).forEach(library -> {
            try {
                manager.loadLibrary(library);
            } catch(RuntimeException e) {
                getLogger().info("Skipping download of\""+library+"\", it either doesnt exist or has no .jar file");
            }
        });

        instance = this;
        ConfigManager.initialize();

        List<Command> commands = PluginCommandYamlParser.parse(this);
        for (Command command : commands) {
            getCommand(command.getName()).setExecutor(new CommandManager());
        }
        Bukkit.getScheduler().runTaskAsynchronously(this, DatabaseManager::openConnection);

        BootstrapManager.connect();

        getLogger().info(LanguageManager.getMessage("plugin.header"));
        getLogger().info("DragonBot");
        getLogger().info(LanguageManager.getMessage("plugin.enabled") + getDescription().getVersion());
        getLogger().info(LanguageManager.getMessage("plugin.footer"));
    }

    public void onDisable() {
        BootstrapManager.disconnect();

        DatabaseManager.closeConnection();

        getLogger().info(LanguageManager.getMessage("plugin.header"));
        getLogger().info("DragonBot");
        getLogger().info(LanguageManager.getMessage("plugin.disabled") + getDescription().getVersion());
        getLogger().info(LanguageManager.getMessage("plugin.footer"));
    }


}
