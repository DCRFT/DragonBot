package pl.dcbot.Managers.MusicManager;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeSearchProvider;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import java.util.ArrayList;
import java.util.List;

public class YouTubeManager {
    public final static YoutubeSearchProvider youtubeSearchProvider =
            new YoutubeSearchProvider();

    public static YoutubeAudioSourceManager youtubeSourceManager;

    public static AudioTrack[] search(String[] queries) {
        List<AudioTrack> results = new ArrayList<>();
        AudioItem playlist;
        StringBuilder sb = new StringBuilder();
        for (String query : queries) {
            sb.append(query).append(" ");
        }
        String allArgs = sb.toString().trim();

        playlist = youtubeSearchProvider.loadSearchResult(allArgs, info -> new YoutubeAudioTrack(info, youtubeSourceManager));
        if (playlist instanceof AudioPlaylist) {
            results.addAll(((AudioPlaylist) playlist).getTracks());
        }
        return results.isEmpty() ? null :
                results.toArray(new AudioTrack[0]);

    }
}
