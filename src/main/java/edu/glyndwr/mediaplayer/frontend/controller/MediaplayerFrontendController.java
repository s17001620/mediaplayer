package edu.glyndwr.mediaplayer.frontend.controller;

import edu.glyndwr.mediaplayer.backend.mediaservice.integration.controller.YouTubeIntegrationController;
import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeVideo;
import edu.glyndwr.mediaplayer.frontend.factories.MediaPlayerUIFactory;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Getter
@Setter
@Component
public class MediaplayerFrontendController {

    @Autowired
    private YouTubeIntegrationController youTubeIntegrationController;
    @Autowired
    private MediaPlayerUIFactory uiFactory;

    private WebView player;
    private TextField searchField;
    private Button searchButton;
    private Stage primaryStage;
    private GridPane resultPane;
    private ArrayList<YouTubeVideo> videos = new ArrayList<>();

    public void initializeStage(Stage primaryStage) {
        initializeFields(primaryStage);
        uiFactory.buildUI(this).show();
        
    }



    public void loadVideos(String query) {
        videos = new ArrayList<>();
        youTubeIntegrationController.getYoutubeSearchCriteria().setQueryTerm(query);
        videos.addAll(youTubeIntegrationController.loadVideosbySearchQuery());
        uiFactory.rebuildResultPane();
    }

    public void playVideo(YouTubeVideo video) {
        uiFactory.rebuildPlayerPane(video);
        this.player.getEngine().load(video.getUrl());
    }
    private void initializeFields(Stage primaryStage1) {
        this.resultPane = new GridPane();
        this.resultPane.setId("resultpane");
        this.player = new WebView();
        this.player.setPrefSize(640, 360);
        this.searchField =  new TextField();
        this.searchButton = new Button();
        this.primaryStage = primaryStage1;
    }
}
