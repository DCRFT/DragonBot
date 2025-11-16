package pl.dcbot.Listeners;

import org.bukkit.Bukkit;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.event.interaction.SelectMenuChooseEvent;
import org.javacord.api.listener.interaction.SelectMenuChooseListener;

import static pl.dcbot.Managers.BootstrapManager.*;

public class SelectMenuListener implements SelectMenuChooseListener {

    //TODO customization etc

    @Override
    public void onSelectMenuChoose(SelectMenuChooseEvent e) {
        if (e.getSelectMenuInteraction().getCustomId().equalsIgnoreCase("roles")) {

            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_eventy).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_ogloszenia).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_zmiany).get());

            for (SelectMenuOption o : e.getSelectMenuInteraction().getChosenOptions()) {
                if (o.getValue().equalsIgnoreCase("events_id")) {
                    e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_eventy).get());
                } else if (o.getValue().equalsIgnoreCase("announcements_id")) {
                    e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_ogloszenia).get());
                } else if (o.getValue().equalsIgnoreCase("changes_id")) {
                    e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_zmiany).get());
                }
            }
//            e.getSelectMenuInteraction().respondLater().join();
            e.getInteraction().respondLater(true).thenAccept(updater -> {
               updater.setContent("Role zostały zaktualizowane!").update().join();
            });
//            e.getSelectMenuInteraction().createImmediateResponder().respond().join();
        } else if (e.getSelectMenuInteraction().getCustomId().equalsIgnoreCase("colors")) {

            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_default_pink).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_blue).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_green).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_yellow).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_orange).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_purple).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_grey).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_black).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_color_white).get());

            for (SelectMenuOption o : e.getSelectMenuInteraction().getChosenOptions()) {
                switch (o.getValue()) {
                    case "default_pink":
                        break;
                    case "blue":
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_color_blue).get());
                        break;
                    case "green":
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_color_green).get());
                        break;
                    case "yellow":
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_color_yellow).get());
                        break;
                    case "orange":
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_color_orange).get());
                        break;
                    case "purple":
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_color_purple).get());
                        break;
                    case "grey":
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_color_grey).get());
                        break;
                    case "black":
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_color_black).get());
                        break;
                    case "white":
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_color_white).get());
                        break;

                }
            }
            e.getSelectMenuInteraction().createImmediateResponder().respond();
        }
    }
}
