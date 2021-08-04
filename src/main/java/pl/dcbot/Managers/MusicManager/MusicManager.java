package pl.dcbot.Managers.MusicManager;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import org.javacord.api.DiscordApi;
import org.javacord.api.audio.AudioConnection;
import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.interaction.SlashCommandInteraction;
import pl.dcbot.Managers.BootstrapManager;
import pl.dcbot.Managers.MusicManager.ResultManager.ResultManager;
import pl.dcbot.Managers.MusicManager.ResultManager.ResultReason;
import pl.dcbot.Utils.ErrorUtils.ErrorReason;
import pl.dcbot.Utils.ErrorUtils.ErrorUtil;
import pl.dcbot.Utils.URLUtil;

import static pl.dcbot.Managers.BootstrapManager.serwer;

public class MusicManager {
    private static final DiscordApi api = BootstrapManager.api;
    private static final Server server = api.getServerById(serwer).get();

    public static void playTrack(String track, SlashCommandInteraction command, ServerVoiceChannel voiceChannel) {

        command.respondLater();
        String graj;
        AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        playerManager.registerSourceManager(new YoutubeAudioSourceManager());
        AudioPlayer player = playerManager.createPlayer();
        AudioSource source = new LavaplayerManager(api, player);

        if (URLUtil.isUrl(track)) {
            graj = track;
            String finalGraj = graj;
            if (voiceChannel.isConnected(api.getYourself())) {
                AudioConnection audioConnection = server.getAudioConnection().get();
                audioConnection.setAudioSource(source);

                playerManager.loadItem(finalGraj, new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack track) {
                        ResultManager.sendResult(command, ResultReason.SUCCESS, track.getInfo().title, track.getInfo().uri);
                        player.playTrack(track);
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist playlist) {
                        for (AudioTrack track : playlist.getTracks()) {
                            ResultManager.sendResult(command, ResultReason.SUCCESS, track.getInfo().title, track.getInfo().uri);
                            player.playTrack(track);
                        }
                    }

                    @Override
                    public void noMatches() {
                        ResultManager.sendResult(command, ResultReason.NO_RESULTS, null, null);
                    }

                    @Override
                    public void loadFailed(FriendlyException throwable) {
                        ResultManager.sendResult(command, ResultReason.ERROR, null, null);
                    }
                });
            } else {
                voiceChannel.connect().thenAccept(audioConnection -> {
                    audioConnection.setAudioSource(source);

                    playerManager.loadItem(finalGraj, new AudioLoadResultHandler() {
                        @Override
                        public void trackLoaded(AudioTrack track) {
                            ResultManager.sendResult(command, ResultReason.SUCCESS, track.getInfo().title, track.getInfo().uri);
                            player.playTrack(track);
                        }

                        @Override
                        public void playlistLoaded(AudioPlaylist playlist) {
                            for (AudioTrack track : playlist.getTracks()) {
                                ResultManager.sendResult(command, ResultReason.SUCCESS, track.getInfo().title, track.getInfo().uri);
                                player.playTrack(track);
                            }
                        }

                        @Override
                        public void noMatches() {
                            ResultManager.sendResult(command, ResultReason.NO_RESULTS, null, null);
                        }

                        @Override
                        public void loadFailed(FriendlyException throwable) {
                            ResultManager.sendResult(command, ResultReason.ERROR, null, null);
                        }
                    });
                }).exceptionally(err -> {
                    err.printStackTrace();
                    ResultManager.sendResult(command, ResultReason.ERROR, null, null);
                    return null;
                });
            }
        } else {
            String[] trackFinal = track.split(" ");
            AudioTrack[] results = YouTubeManager.search(trackFinal);

            if (voiceChannel.isConnected(api.getYourself())) {
                AudioConnection audioConnection = server.getAudioConnection().get();
                AudioTrack at = null;
                if (results != null && results.length > 0) {
                    at = results[0];
                }
                audioConnection.setAudioSource(source);

                if (at != null) {
                    playerManager.loadItem(at.getIdentifier(), new AudioLoadResultHandler() {
                        @Override
                        public void trackLoaded(AudioTrack track) {
                            ResultManager.sendResult(command, ResultReason.SUCCESS, track.getInfo().title, track.getInfo().uri);
                            player.playTrack(track);
                        }

                        @Override
                        public void playlistLoaded(AudioPlaylist playlist) {
                            for (AudioTrack track : playlist.getTracks()) {
                                ResultManager.sendResult(command, ResultReason.SUCCESS, track.getInfo().title, track.getInfo().uri);
                                player.playTrack(track);
                            }
                        }

                        @Override
                        public void noMatches() {
                            ResultManager.sendResult(command, ResultReason.NO_RESULTS, null, null);
                        }

                        @Override
                        public void loadFailed(FriendlyException throwable) {
                            ResultManager.sendResult(command, ResultReason.ERROR, null, null);
                        }
                    });
                } else {
                    ResultManager.sendResult(command, ResultReason.ERROR, null, null);
                }
            } else {
                voiceChannel.connect().thenAccept(audioConnection -> {
                    AudioTrack at = null;
                    if (results != null && results.length > 0) {
                        at = results[0];
                    }
                    audioConnection.setAudioSource(source);

                    if (at != null) {
                        playerManager.loadItem(at.getIdentifier(), new AudioLoadResultHandler() {
                            @Override
                            public void trackLoaded(AudioTrack track) {
                                ResultManager.sendResult(command, ResultReason.SUCCESS, track.getInfo().title, track.getInfo().uri);
                                player.playTrack(track);
                            }

                            @Override
                            public void playlistLoaded(AudioPlaylist playlist) {
                                for (AudioTrack track : playlist.getTracks()) {
                                    ResultManager.sendResult(command, ResultReason.SUCCESS, track.getInfo().title, track.getInfo().uri);
                                    player.playTrack(track);
                                }
                            }

                            @Override
                            public void noMatches() {
                                ResultManager.sendResult(command, ResultReason.NO_RESULTS, null, null);
                            }

                            @Override
                            public void loadFailed(FriendlyException throwable) {
                                ResultManager.sendResult(command, ResultReason.ERROR, null, null);

                            }
                        });
                    } else {
                        ResultManager.sendResult(command, ResultReason.ERROR, null, null);
                    }
                }).exceptionally(err -> {
                    err.printStackTrace();
                    ErrorUtil.logError(ErrorReason.DISCORD);
                    ResultManager.sendResult(command, ResultReason.ERROR, null, null);
                    return null;
                });
            }
        }
    }
    public static void stopTrack(AudioSource audioSource) {
        audioSource.mute();
    }
    public static void pauseTrack(AudioSource audioSource) {
        audioSource.mute();
    }
    public static void resumeTrack(AudioSource audioSource) {
        audioSource.unmute();
    }
}
