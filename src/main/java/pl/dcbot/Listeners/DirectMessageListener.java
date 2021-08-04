package pl.dcbot.Listeners;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class DirectMessageListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent e) {
        if (e.getMessage().isPrivateMessage()) {
            if (!e.getMessageAuthor().isYourself()) {
                e.getChannel().sendMessage("⟨❗⟩ Hej! Nie działam w wiadomościach prywatnych.");
            }
        }
    }
}
