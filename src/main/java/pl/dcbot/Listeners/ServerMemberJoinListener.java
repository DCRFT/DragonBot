package pl.dcbot.Listeners;

import org.javacord.api.event.server.member.ServerMemberJoinEvent;

import static pl.dcbot.Managers.BootstrapManager.*;

public class ServerMemberJoinListener implements org.javacord.api.listener.server.member.ServerMemberJoinListener {
    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent e) {
        e.getApi().getTextChannelById(channel_glowny).get().sendMessage("Witaj **" + e.getUser().getMentionTag() + "** na serwerze \uD83D\uDC32 \uD835\uDC03\uD835\uDC2B\uD835\uDC1A\uD835\uDC20\uD835\uDC28\uD835\uDC27\uD835\uDC02\uD835\uDC2B\uD835\uDC1A\uD835\uDC1F\uD835\uDC2D!" +
                " Jeśli chcesz się zarejestrować, postępuj zgodnie z instrukcjami na kanale " + e.getServer().getTextChannelById(channel_rejestracja_pomoc).get().getMentionTag() + ". Po rejestracji otrzymasz dostęp do kanałów tekstowych i głosowych. Aby dostosować swoje powiadomienia, sprawdź kanał " + e.getServer().getTextChannelById(channel_pingi).get().getMentionTag() + ".");
        e.getUser().addRole(api.getRoleById(role_niezarejestrowany).get()).join();
    }
}
