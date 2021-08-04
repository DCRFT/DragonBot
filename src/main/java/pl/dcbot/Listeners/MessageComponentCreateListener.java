package pl.dcbot.Listeners;

import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import pl.dcbot.DragonBot;
import pl.dcbot.Managers.ConfigManager;
import pl.dcbot.Managers.ReportManager.ReportManager;
import pl.dcbot.Managers.ReportManager.SuggestionCloseReason;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static pl.dcbot.Managers.BootstrapManager.*;

public class MessageComponentCreateListener implements org.javacord.api.listener.interaction.MessageComponentCreateListener {
    private static final DragonBot plugin = DragonBot.getInstance();

    final long reports_id = ConfigManager.getDataFile().getLong("ids.reports");
    final long suggestions_id = ConfigManager.getDataFile().getLong("ids.suggestions");

    @Override
    public void onComponentCreate(MessageComponentCreateEvent e) {
        MessageComponentInteraction message = e.getMessageComponentInteraction();
        String id = message.getCustomId();

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        String time = dateFormat.format(date);

        if((message.getChannel().isPresent()
                && message.getChannel().get().asServerTextChannel().isPresent()
                && message.getChannel().get().asServerTextChannel().get().getCategory().isPresent()
                && message.getChannel().get().asServerTextChannel().get().getCategory().get().getId() == category_zgl_otwarte)){
                ReportManager.closeReport(message, message.getChannel().get().asServerTextChannel().get(), message.getUser(), time);
        } else if((message.getChannel().isPresent()
                && message.getChannel().get().asServerTextChannel().isPresent()
                && message.getChannel().get().asServerTextChannel().get().getCategory().isPresent()
                && message.getChannel().get().asServerTextChannel().get().getCategory().get().getId() == category_propo_otwarte)){
            if(!message.getUser().getRoles(server).contains(server.getRoleById(role_wlasciciel).get())){
                message.createImmediateResponder().respond();
                return;
            }
            SuggestionCloseReason reason = SuggestionCloseReason.CLOSED;
            if(id.equalsIgnoreCase(plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_accept.id"))){
                reason = SuggestionCloseReason.ACCEPTED;
            }
            else if(id.equalsIgnoreCase(plugin.getConfig().getString("embeds.suggestions.new_suggestion.button_reject.id"))){
                reason = SuggestionCloseReason.REJECTED;
            }
            ReportManager.closeSuggestion(message, message.getChannel().get().asServerTextChannel().get(), message.getUser(), reason, time);
        }

        else if(e.getMessageComponentInteraction().getMessageId() == reports_id){
            if(id.equalsIgnoreCase(plugin.getConfig().getString("embeds.reports.button.id"))){
                message.createImmediateResponder().respond();
                ReportManager.newReport(message, time);
            }
        }
        else if(e.getMessageComponentInteraction().getMessageId() == suggestions_id){
            if(id.equalsIgnoreCase(plugin.getConfig().getString("embeds.suggestions.button.id"))){
                message.createImmediateResponder().respond();
                ReportManager.newSuggestion(message, time);
            }
        }
    }
}
