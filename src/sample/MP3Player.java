package sample;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class MP3Player {

    private MediaPlayer mediaPlayer = null;

    private List<Media> playQueue = new ArrayList<>();
    private Deque<Media> lastPlayedQueue = new ArrayDeque<>();
    private Media nowPlayingMedia;
    private Media lastPlayedMedia;

    private boolean loopModeEnabled = false;
    private boolean crossfadeModeEnabled = false;
    private boolean shuffleModeEnabled = false;
    private Double fadeDuration = 1.0;

    private boolean isMediaPlayerReady = false;

    public void setLoopModeEnabled(boolean loopModeEnabled) {
        this.loopModeEnabled = loopModeEnabled;
    }

    public void setCrossfadeModeEnabled(boolean crossfadeModeEnabled) {
        this.crossfadeModeEnabled = crossfadeModeEnabled;
    }

    public void setShuffleModeEnabled(boolean shuffleModeEnabled) {
        this.shuffleModeEnabled = shuffleModeEnabled;
    }

    public synchronized void setMediaPlayer(MediaPlayer mediaPlayer) {
        if(this.mediaPlayer != null) {
            lastPlayedMedia = this.mediaPlayer.getMedia();
            System.out.println("LastPlayedMedia: " + lastPlayedMedia.getSource());
        }

        nowPlayingMedia = mediaPlayer.getMedia();
        this.mediaPlayer = mediaPlayer;
        if(crossfadeModeEnabled) {
            playCrossFade();
        } else {
            System.out.println("No CrossFade");
            playWithoutCrossFade();
        }

        System.out.println("nowPlayingMedia: " + nowPlayingMedia.getSource());
    }

    public void play(){
        Media media = getMedia();
        if(media != null) {
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            setMediaPlayer(mediaPlayer);
        } else {
            System.out.println("Empty playlist");
        }
    }

    public Media getMedia(){
        Media media = null;
        if(!playQueue.isEmpty()){
            media = playQueue.remove(0);
            if(media != null) {
                lastPlayedQueue.add(media);
            }
        } else {
            if(loopModeEnabled) {
                media = lastPlayedQueue.remove();
                lastPlayedQueue.add(media);
            }
        }
        return media;
    }

//    public void addMediaList(List<String> filePaths){
//        for (String filePath:filePaths) {
//            addMedia(filePath);
//        }
//    }
//
//    public void addMedia(String filePath) {
//        File file = new File(filePath);
//        Media media = new Media(file.toURI().toString());
//        playQueue.add(media);
//    }

    public void addNewMediaList(List<File> files){

        for (File file:files) {
            addMedia(file);
        }
    }

    public void addToCurrentMediaList(List<File> files){
        playQueue.clear();
        lastPlayedQueue.clear();
        for (File file:files) {
            addMedia(file);
        }
    }

    public void addMedia(File file) {
        Media media = new Media(file.toURI().toString());
        playQueue.add(media);
    }

    public synchronized void playCrossFade() {

        mediaPlayer.setOnReady(() ->{
                isMediaPlayerReady = true;
                System.out.println("Ready");
                mediaPlayer.setVolume(0);
                mediaPlayer.play();
                System.out.println("Playing");
            }
        );

        mediaPlayer.setOnEndOfMedia(()->{
            System.out.println("EOM");
        });

        mediaPlayer.setOnPlaying(()->{
            System.out.println("FadeIn volume");
//            MediaPlayer oldPlayer = mediaPlayer;
            Timeline startingTimeLine = new Timeline(
                    new KeyFrame(Duration.seconds(fadeDuration),
                            new KeyValue(mediaPlayer.volumeProperty(), 1)));
            Timeline endingTimeLine = new Timeline(
                    new KeyFrame(Duration.seconds(fadeDuration),
                            new KeyValue(mediaPlayer.volumeProperty(), 0)));
            Timeline waitingTimeLine = new Timeline(
                    new KeyFrame(Duration.seconds(mediaPlayer.getTotalDuration().toSeconds() - 2*fadeDuration),
                            new KeyValue(mediaPlayer.volumeProperty(), 1)));

            startingTimeLine.setOnFinished(event -> {
                System.out.println("stabled volume");
                waitingTimeLine.play();
            });

            waitingTimeLine.setOnFinished(event -> {
                System.out.println("FadeOut volume");
                Media media = getMedia();
                if(media != null) {
                    System.out.println("Next media available");
                    setMediaPlayer(new MediaPlayer(media));
                } else {
                    System.out.println("Next media not available ");
                }
                endingTimeLine.play();
            });

            endingTimeLine.setOnFinished(event -> {
                System.out.println(mediaPlayer.getMedia().getSource());
            });

            startingTimeLine.play();

        });

    }

    public synchronized void playWithoutCrossFade() {
        mediaPlayer.setOnReady(() ->{
            System.out.println("Start playing");
            mediaPlayer.play();
            Thread t = new Thread(runnable);
//            t.start();
        });

        mediaPlayer.setOnEndOfMedia(()->{
            System.out.println("End of play");
            Media media = getMedia();
            if(media != null)
                setMediaPlayer(new MediaPlayer(media));
        });
    }

    Runnable runnable = () -> {
        while(true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(mediaPlayer == null)
                System.out.println("MP null");
            else
                System.out.println("MP is good");
        }
    };

    public void stop() {
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void resume() {
        if(mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void pause() {
        if(mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }
}
