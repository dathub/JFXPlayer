package sample;

import java.io.File;
import java.util.List;

public class MP3PlayerHandler {

    MP3Player mp3Player = new MP3Player();

    public void createNewPlaylist(List<File> newFilesList) {
        if(newFilesList != null) {
            mp3Player.addNewMediaList(newFilesList);
        }
    }

    public void addToPlaylist(List<File> newFilesList) {
        if(newFilesList != null) {
            mp3Player.addToCurrentMediaList(newFilesList);
        }
    }

    public void playCurrentPlaylist() {
        mp3Player.play();
    }

    public void pause(){
        mp3Player.pause();
    }

    public void resume(){
        mp3Player.resume();
    }

    public void stop(){
        mp3Player.stop();
    }

    public void enableShuffleMode(boolean val){
        mp3Player.setShuffleModeEnabled(val);
    }

    public void enableCrossfadeMode(boolean val){
        mp3Player.setCrossfadeModeEnabled(val);
    }

    public void enableRepeatMode(boolean val){
        mp3Player.setLoopModeEnabled(val);
    }
}
