package pl.dcbot.Listeners;

import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.event.interaction.SelectMenuChooseEvent;
import org.javacord.api.listener.interaction.SelectMenuChooseListener;

import static pl.dcbot.Managers.BootstrapManager.*;

public class SelectMenuListener implements SelectMenuChooseListener {

    //TODO customization etc

    @Override
    public void onSelectMenuChoose(SelectMenuChooseEvent e) {
        if (e.getSelectMenuInteraction().getCustomId().equalsIgnoreCase("roles")){

            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_eventy).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_ogloszenia).get());
            e.getSelectMenuInteraction().getUser().removeRole(server.getRoleById(role_zmiany).get());

            for (SelectMenuOption o : e.getSelectMenuInteraction().getChosenOptions()){
                if(o.getValue().equalsIgnoreCase("EventyA")){
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_eventy).get());
                } else if(o.getValue().equalsIgnoreCase("OgłoszeniaA")){
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_ogloszenia).get());
                } else if(o.getValue().equalsIgnoreCase("ZmianyA")){
                        e.getSelectMenuInteraction().getUser().addRole(server.getRoleById(role_zmiany).get());
                }
            }
            e.getSelectMenuInteraction().createImmediateResponder().respond();
        }
    }
}
