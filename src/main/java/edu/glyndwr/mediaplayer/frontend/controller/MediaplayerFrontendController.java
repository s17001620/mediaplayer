package edu.glyndwr.mediaplayer.frontend.controller;

import edu.glyndwr.mediaplayer.backend.mediaservice.integration.controller.YouTubeIntegrationController;
import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeSearchQuery;
import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeVideo;
import edu.glyndwr.mediaplayer.frontend.factories.MediaPlayerUIFactory;
import edu.glyndwr.mediaplayer.frontend.model.MediaplayerFrontendModel;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Log
@Getter
@Setter
@Component
public class MediaplayerFrontendController {

    @Autowired
    private YouTubeIntegrationController youTubeIntegrationController;
    @Autowired
    private MediaPlayerUIFactory uiFactory;
    @Autowired
    private MediaplayerFrontendModel mediaplayerFrontendModel;
    
    private WebView player;
    private TextField searchField;
    private Button searchButton;
    private Stage primaryStage;
    private GridPane resultPane;
    

    public void initializeStage(Stage primaryStage) {
        initializeFields(primaryStage);
        primaryStage = uiFactory.buildUI(this);
               InputStream icon = null;
        try {
            icon = new DataInputStream(new FileInputStream(new ClassPathResource("icon.png").getFile()));
            
            if(null!=icon){
                Image imageIcon = new Image(icon);
                primaryStage.getIcons().add(imageIcon); 
            }else{
                log.info("icon inputstream null");
            }
           
        } catch (IOException ex) {
                Logger.getLogger(MediaplayerFrontendController.class.getName()).log(Level.SEVERE, null, ex);
            }
        primaryStage.show();

    }

    public void loadVideos(String query) {
        youTubeIntegrationController.setYoutubeSearchCriteria(new YouTubeSearchQuery());
        youTubeIntegrationController.getYoutubeSearchCriteria().setQueryTerm(query);
        ArrayList<YouTubeVideo> tempVideos = new ArrayList<>();
        tempVideos.addAll(youTubeIntegrationController.loadVideosbySearchQuery());
        tempVideos.removeAll(mediaplayerFrontendModel.getVideos());
        mediaplayerFrontendModel.setVideos(tempVideos);
        uiFactory.rebuildResultPane();
    }

    public void playVideo(YouTubeVideo video) {
        uiFactory.rebuildPlayerPane(video);
        this.player.getEngine().loadContent(video.getEmbedCode());
    }

    private void initializeFields(Stage primaryStage) {

        this.resultPane = new GridPane();
        this.resultPane.setId("resultpane");
        this.player = new WebView();
        this.player.setPrefSize(660, 380);
        this.searchField = new TextField();
        this.searchButton = new Button();
        this.primaryStage = primaryStage;
    }
}
