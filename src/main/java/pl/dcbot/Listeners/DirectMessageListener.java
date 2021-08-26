package pl.dcbot.Listeners;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import pl.dcbot.Managers.ConfigManager;
import pl.dcbot.Managers.LanguageManager;

import java.util.Random;

import static pl.dcbot.Managers.BootstrapManager.channel_testowy;
import static pl.dcbot.Managers.BootstrapManager.server;

public class DirectMessageListener implements MessageCreateListener {


    public static int generateCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return Integer.parseInt(String.format("%06d", number));
    }

    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        if (e.getMessage().isPrivateMessage()) {
            if (!e.getMessageAuthor().isYourself()) {
                if (e.getMessage().getContent().startsWith("/kod")) {
                    if(!ConfigManager.getDataFile().getBoolean("users." + e.getMessageAuthor().getIdAsString() + ".code")) {
                        String code = String.valueOf(generateCode());
                        e.getMessageAuthor().asUser().get().sendMessage(LanguageManager.getMessage("code.success") + " " + e.getMessageAuthor().getIdAsString() + code);

                        server.getChannelById(channel_testowy).get().asServerTextChannel().get().sendMessage(e.getMessageAuthor().getDisplayName() + " " + e.getMessageAuthor().getIdAsString() + code);
                        ConfigManager.getDataFile().set("users." + e.getMessageAuthor().getIdAsString() + ".code", true);
                        ConfigManager.saveData();
                    } else {
                        e.getMessageAuthor().asUser().get().sendMessage(LanguageManager.getMessage("code.error"));
                    }
                    } else {
                    e.getChannel().sendMessage(LanguageManager.getMessage("dm_error"));
                }
            }
        }
        else if (e.getServer().isPresent() &&
        e.getServer().get() == server &&
        e.getMessageAuthor().canBanUsersFromServer() &&
        e.getMessageContent().startsWith("/usun")) {
            String[] args = e.getMessageContent().split(" ");
            if(args.length > 1){
                ConfigManager.getDataFile().set("users." + args[1] + ".code", null);
                ConfigManager.saveData();
                e.getMessage().getChannel().sendMessage(LanguageManager.getMessage("delete") + " " + args[1]);
            }
        }
    }
}