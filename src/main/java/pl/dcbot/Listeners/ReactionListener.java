package pl.dcbot.Listeners;

import org.javacord.api.event.message.reaction.ReactionAddEvent;
import org.javacord.api.event.message.reaction.ReactionRemoveEvent;
import org.javacord.api.listener.message.reaction.ReactionAddListener;
import org.javacord.api.listener.message.reaction.ReactionRemoveListener;
import pl.dcbot.Managers.ConfigManager;

import java.util.concurrent.ExecutionException;

import static pl.dcbot.Managers.BootstrapManager.*;

public class ReactionListener implements ReactionAddListener, ReactionRemoveListener {

    //TODO reaction roles - menu
    //TODO reaction colors - menu

    @Override
    public void onReactionRemove(ReactionRemoveEvent e) {
        if (e.getUser().get().isYourself()) {
            return;
        } try {
            if (e.getChannel().asTextChannel().isPresent()
                    && e.getChannel().asServerTextChannel().get().getCategory().isPresent()
                    && e.getChannel().asServerTextChannel().get().getCategory().get().getId() == category_propo_otwarte) {
                if(e.requestReaction().get().isPresent()){
                    if(e.requestReaction().get().get().getEmoji().equalsEmoji(server.getCustomEmojiById(emoji_plus_jeden).get())){
                        if(ConfigManager.getDataFile().get("ids.suggestions." + e.getChannel().getId() + ".upvotes") != null) {
                            ConfigManager.getDataFile().set("ids.suggestions." + e.getChannel().getId() + ".upvotes", ConfigManager.getDataFile().getInt("ids.suggestions." + e.getChannel().getId() + ".upvotes") - 1);
                        }
                        ConfigManager.saveData();
                    } else if(e.requestReaction().get().get().getEmoji().equalsEmoji(server.getCustomEmojiById(emoji_minus_jeden).get())){
                        if(ConfigManager.getDataFile().get("ids.suggestions." + e.getChannel().getId() + ".downvotes") != null) {
                            ConfigManager.getDataFile().set("ids.suggestions." + e.getChannel().getId() + ".downvotes", ConfigManager.getDataFile().getInt("ids.suggestions." + e.getChannel().getId() + ".downvotes") - 1);
                        }
                        ConfigManager.saveData();
                    }
                }
            }
        } catch (InterruptedException | ExecutionException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    @Override
    public void onReactionAdd(ReactionAddEvent e) {
        if (e.getUser().get().isYourself()) {
            return;
        } try {
            if (e.getChannel().asTextChannel().isPresent()
                    && e.getChannel().asServerTextChannel().get().getCategory().isPresent()
                    && e.getChannel().asServerTextChannel().get().getCategory().get().getId() == category_propo_otwarte) {
                if(e.requestReaction().get().isPresent()){
                    if(e.requestReaction().get().get().getEmoji().equalsEmoji(server.getCustomEmojiById(emoji_plus_jeden).get())){
                        if(ConfigManager.getDataFile().get("ids.suggestion." + e.getChannel().getId() + ".upvotes") == null) {
                            ConfigManager.getDataFile().set("ids.suggestion." + e.getChannel().getId() + ".upvotes", 1);
                        } else {
                            ConfigManager.getDataFile().set("ids.suggestion." + e.getChannel().getId() + ".upvotes", ConfigManager.getDataFile().getInt("ids.suggestion." + e.getChannel().getId() + ".upvotes") + 1);
                        }
                        ConfigManager.saveData();
                    } else if(e.requestReaction().get().get().getEmoji().equalsEmoji(server.getCustomEmojiById(emoji_minus_jeden).get())){
                        if(ConfigManager.getDataFile().get("ids.suggestion." + e.getChannel().getId() + ".downvotes") == null) {
                            ConfigManager.getDataFile().set("ids.suggestion." + e.getChannel().getId() + ".downvotes", 1);
                        } else {
                            ConfigManager.getDataFile().set("ids.suggestion." + e.getChannel().getId() + ".downvotes", ConfigManager.getDataFile().getInt("ids.suggestion." + e.getChannel().getId() + ".downvotes") + 1);
                        }
                        ConfigManager.saveData();
                    }
                }
            }
        } catch (InterruptedException | ExecutionException interruptedException) {
            interruptedException.printStackTrace();
        }
    }
}
