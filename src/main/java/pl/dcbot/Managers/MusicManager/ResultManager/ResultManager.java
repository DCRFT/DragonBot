package pl.dcbot.Managers.MusicManager.ResultManager;

import org.javacord.api.interaction.SlashCommandInteraction;
import pl.dcbot.Managers.LanguageManager;
import pl.dcbot.Managers.MessageManager;

public class ResultManager {
    public static void sendResult(SlashCommandInteraction command, ResultReason reason, String trackTitle, String url) {
        switch (reason) {
            case ERROR:
                MessageManager.sendFollowupMessage(command, "music.play.error");
                break;
            case NO_RESULTS:
                MessageManager.sendFollowupMessage(command, "music.play.no_results");
                break;
            case SUCCESS:
                command.createFollowupMessageBuilder().setContent(LanguageManager.getMessage("music.play.success") + " "  + trackTitle + "\n" + url)
                        .send();
                break;
        }
    }
}
