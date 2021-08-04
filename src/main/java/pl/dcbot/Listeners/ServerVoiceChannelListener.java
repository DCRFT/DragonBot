package pl.dcbot.Listeners;

import org.javacord.api.entity.channel.ServerVoiceChannelBuilder;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberJoinEvent;
import org.javacord.api.event.channel.server.voice.ServerVoiceChannelMemberLeaveEvent;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberJoinListener;
import org.javacord.api.listener.channel.server.voice.ServerVoiceChannelMemberLeaveListener;
import pl.dcbot.Utils.ErrorUtils.ErrorReason;
import pl.dcbot.Utils.ErrorUtils.ErrorUtil;

import java.util.concurrent.ExecutionException;

import static pl.dcbot.Managers.BootstrapManager.category_prywatne;
import static pl.dcbot.Managers.BootstrapManager.voicechannel_prywatne;

public class ServerVoiceChannelListener implements ServerVoiceChannelMemberJoinListener, ServerVoiceChannelMemberLeaveListener {
    @Override
    public void onServerVoiceChannelMemberJoin(ServerVoiceChannelMemberJoinEvent e) {
        if (e.getChannel().getId() == voicechannel_prywatne) {
            ServerVoiceChannelBuilder svcb = new ServerVoiceChannelBuilder(
                    e.getServer())
                    .setCategory(e.getServer().getChannelCategoryById(category_prywatne).get())
                    .setName(e.getUser().getDisplayName(e.getServer()));
            svcb.addPermissionOverwrite(e.getUser(), new PermissionsBuilder().setAllAllowed().build());
            try {
                e.getUser().move(svcb.create().get());
            } catch (InterruptedException | ExecutionException err) {
                err.printStackTrace();
                ErrorUtil.logError(ErrorReason.DISCORD);
            }
        }
    }

    @Override
    public void onServerVoiceChannelMemberLeave(ServerVoiceChannelMemberLeaveEvent e) {
        if (e.getChannel().getCategory().isPresent() && e.getChannel().getCategory().get().getId() == category_prywatne && e.getChannel().getConnectedUsers().isEmpty() && e.getChannel().getId() != voicechannel_prywatne) {
            e.getChannel().delete();
        }
    }
}
