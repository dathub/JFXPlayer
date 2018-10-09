package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Controller {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Menu m_media;

    @FXML
    private MenuItem mi_openFile;

    @FXML
    private MenuItem mi_openFolder;

    @FXML
    private MenuItem mi_openPlaylist;

    @FXML
    private MenuItem mi_savePlaylistToFile;

    @FXML
    private MenuItem mi_close;

    @FXML
    private Menu m_playback;

    @FXML
    private MenuItem mi_play;

    @FXML
    private MenuItem mi_stop;

    @FXML
    private MenuItem mi_previous;

    @FXML
    private MenuItem mi_next;

    @FXML
    private RadioMenuItem rmi_shuffleMode;

    @FXML
    private RadioMenuItem rmi_repeatMode;

    @FXML
    private RadioMenuItem rmi_crossfadeMode;

    @FXML
    private Menu m_audio;

    @FXML
    private MenuItem mi_increaseVolume;

    @FXML
    private MenuItem mi_decreaseVolume;

    @FXML
    private MenuItem mi_mute;

    @FXML
    private Menu m_help;

    @FXML
    private MenuItem mi_about;

    private MP3PlayerHandler mp3PlayerHandler = new MP3PlayerHandler();

    @FXML
    void onAbout(ActionEvent event) {

    }

    @FXML
    void onCrossfadeMode(ActionEvent event) {
        mp3PlayerHandler.enableCrossfadeMode(rmi_crossfadeMode.isSelected());
    }

    @FXML
    void onExitProgram(ActionEvent event) {

    }

    @FXML
    void onIncreaseVolume(ActionEvent event) {

    }

    @FXML
    void onMute(ActionEvent event) {

    }

    @FXML
    void onNext(ActionEvent event) {

    }

    @FXML
    void onOpenFiles(ActionEvent event) {
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files (*.mp3)", "*.mp3"));
        Stage stage = (Stage)anchorPane.getScene().getWindow();
        List<File> mediaFiles = fileChooser.showOpenMultipleDialog(stage);

        mp3PlayerHandler.createNewPlaylist(mediaFiles);
    }

    @FXML
    void onAddFilesToPlaylist(ActionEvent event) {

    }

    @FXML
    void onOpenPlaylist(ActionEvent event) {

    }

    @FXML
    void onPlay(ActionEvent event) {
        mp3PlayerHandler.playCurrentPlaylist();
    }

    @FXML
    void onPrevious(ActionEvent event) {

    }

    @FXML
    void onRepeatMode(ActionEvent event) {
        mp3PlayerHandler.enableRepeatMode(rmi_repeatMode.isSelected());
    }

    @FXML
    void onSavePlaylistToFIle(ActionEvent event) {

    }

    @FXML
    void onShuffleMode(ActionEvent event) {
        mp3PlayerHandler.enableShuffleMode(rmi_shuffleMode.isSelected());
    }

    @FXML
    void onStop(ActionEvent event) {
        mp3PlayerHandler.stop();
    }

}
