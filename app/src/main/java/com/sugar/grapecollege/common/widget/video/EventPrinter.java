package com.sugar.grapecollege.common.widget.video;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.MediaMetadata;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.qsmaxmin.qsbase.common.log.L;

import java.util.List;

/**
 * @CreateBy qsmaxmin
 * @Date 2021/6/23 15:44
 * @Description
 */
public class EventPrinter {
    private StringBuilder builder;

    public void printEvent(Player player, Player.Events events) {
        if (!L.isEnable()) return;
        if (builder == null) {
            builder = new StringBuilder();
        } else {
            builder.delete(0, builder.length());
        }

        if (events.contains(Player.EVENT_TIMELINE_CHANGED)) {
            Timeline timeline = player.getCurrentTimeline();
            builder.append("\nEVENT_TIMELINE_CHANGED：windowCount -> windowCount:").append(timeline.getWindowCount());
        }

        //changed or the player started repeating the current item
        if (events.contains(Player.EVENT_MEDIA_ITEM_TRANSITION)) {
            MediaItem item = player.getCurrentMediaItem();
            if (item != null) {
                MediaMetadata m = item.mediaMetadata;
                builder.append("\nEVENT_MEDIA_ITEM_TRANSITION -> ").append(getMediaMetadataText(m));
            }
        }

        if (events.contains(Player.EVENT_TRACKS_CHANGED)) {
            TrackGroupArray trackGroups = player.getCurrentTrackGroups();
            TrackSelectionArray selections = player.getCurrentTrackSelections();
            builder.append("\nEVENT_TRACKS_CHANGED -> currentTrackGroups:").append(trackGroups.length)
                    .append(", currentTrackSelections:").append(selections.length);
        }

        if (events.contains(Player.EVENT_STATIC_METADATA_CHANGED)) {
            List<Metadata> staticMetadata = player.getCurrentStaticMetadata();
            builder.append("\nEVENT_STATIC_METADATA_CHANGED -> staticMetadata:").append(staticMetadata.size());
        }

        if (events.contains(Player.EVENT_IS_LOADING_CHANGED)) {
            boolean isLoading = player.isLoading();
            builder.append("\nEVENT_IS_LOADING_CHANGED -> loading:").append(isLoading);
        }

        if (events.contains(Player.EVENT_PLAYBACK_STATE_CHANGED)) {
            int playbackState = player.getPlaybackState();
            builder.append("\nEVENT_PLAYBACK_STATE_CHANGED -> playbackState:").append(getPlaybackStateText(playbackState));
        }

        //播放状态改变，根据该状态可设置播放按钮的播放和暂停UI
        if (events.contains(Player.EVENT_PLAY_WHEN_READY_CHANGED)) {
            boolean playWhenReady = player.getPlayWhenReady();
            builder.append("\nEVENT_PLAY_WHEN_READY_CHANGED -> playWhenReady:").append(playWhenReady);
        }

        if (events.contains(Player.EVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED)) {
            int reason = player.getPlaybackSuppressionReason();
            builder.append("\nEVENT_PLAYBACK_SUPPRESSION_REASON_CHANGED -> reason:").append(reason);
        }

        if (events.contains(Player.EVENT_IS_PLAYING_CHANGED)) {
            boolean isPlaying = player.isPlaying();
            builder.append("\nEVENT_IS_PLAYING_CHANGED -> isPlaying:").append(isPlaying);
        }

        if (events.contains(Player.EVENT_REPEAT_MODE_CHANGED)) {
            int repeatMode = player.getRepeatMode();
            builder.append("\nEVENT_REPEAT_MODE_CHANGED -> repeatMode:").append(repeatMode);
        }

        if (events.contains(Player.EVENT_SHUFFLE_MODE_ENABLED_CHANGED)) {
            boolean shuffleModeEnabled = player.getShuffleModeEnabled();
            builder.append("\nEVENT_SHUFFLE_MODE_ENABLED_CHANGED -> shuffleModeEnabled:").append(shuffleModeEnabled);
        }

        if (events.contains(Player.EVENT_PLAYER_ERROR)) {
            ExoPlaybackException e = player.getPlayerError();
            builder.append("\nEVENT_PLAYER_ERROR -> ").append(e == null ? "null" : e.getMessage());
            if (e != null) {
                L.e("EventPrinter", e);
            }
        }

        if (events.contains(Player.EVENT_POSITION_DISCONTINUITY)) {
            builder.append("\nEVENT_POSITION_DISCONTINUITY -> looking on onPositionDiscontinuity()");
        }

        if (events.contains(Player.EVENT_PLAYBACK_PARAMETERS_CHANGED)) {
            PlaybackParameters parameters = player.getPlaybackParameters();
            builder.append("\nEVENT_PLAYBACK_PARAMETERS_CHANGED -> ").append(parameters.toString());
        }

        if (events.contains(Player.EVENT_AVAILABLE_COMMANDS_CHANGED)) {
            Player.Commands commands = player.getAvailableCommands();
            builder.append("\nEVENT_AVAILABLE_COMMANDS_CHANGED -> available commands:");
            appendCommandsText(builder, commands);
        }

        if (events.contains(Player.EVENT_MEDIA_METADATA_CHANGED)) {
            MediaMetadata metadata = player.getMediaMetadata();
            builder.append("\nEVENT_MEDIA_METADATA_CHANGED -> ").append(getMediaMetadataText(metadata));
        }
        builder.append("\n--------------------------end-----------------------------\n-\n-");
        L.i("EventPrinter", "onEvents------------>" + builder.toString());
    }

    private void appendCommandsText(StringBuilder sb, Player.Commands commands) {
        sb.append('[');
        int size = commands.size();
        for (int i = 0; i < size; i++) {
            int command = commands.get(i);
            sb.append(command);
            if (i != size - 1) sb.append(',');
        }
        sb.append(']');
    }

    private CharSequence getPlaybackStateText(int playbackState) {
        if (playbackState == Player.STATE_IDLE) {
            return "STATE_IDLE";
        } else if (playbackState == Player.STATE_BUFFERING) {
            return "STATE_BUFFERING";
        } else if (playbackState == Player.STATE_READY) {
            return "STATE_READY";
        } else if (playbackState == Player.STATE_ENDED) {
            return "STATE_ENDED";
        }
        return "unKnow state(" + playbackState + ")";
    }

    private String getMediaMetadataText(MediaMetadata metadata) {
        return "title:" + metadata.title
                + ", subtitle:" + metadata.subtitle
                + ", albumTitle:" + metadata.albumTitle
                + ", albumArtist:" + metadata.albumArtist
                + ", mediaUri:" + metadata.mediaUri;
    }
}
