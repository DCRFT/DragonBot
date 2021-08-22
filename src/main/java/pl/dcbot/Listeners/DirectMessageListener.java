package pl.dcbot.Listeners;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Random;

import static pl.dcbot.Managers.BootstrapManager.channel_testowy;
import static pl.dcbot.Managers.BootstrapManager.server;

public class DirectMessageListener implements MessageCreateListener {

    public static int generateCode() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return Integer.parseInt(String.format("%06d", number));
    }
    //TODO messages.yml
    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        if (e.getMessage().isPrivateMessage()) {
            if (!e.getMessageAuthor().isYourself()) {
                if(e.getMessage().getContent().startsWith("/kod")) {
                    String code = String.valueOf(generateCode());
                    e.getMessageAuthor().asUser().get().sendMessage("Twój kod: " + e.getMessageAuthor().getIdAsString() + code);
                    server.getChannelById(channel_testowy).get().asServerTextChannel().get().sendMessage(e.getMessageAuthor().getDisplayName() + " " + e.getMessageAuthor().getIdAsString() + code);
                } else {
                    e.getChannel().sendMessage("⟨❗⟩ Hej! Nie działam w wiadomościach prywatnych.");
                }
            }
        }
    }
}
