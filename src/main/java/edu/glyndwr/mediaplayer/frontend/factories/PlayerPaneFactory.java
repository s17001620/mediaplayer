
package edu.glyndwr.mediaplayer.frontend.factories;

import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeVideo;
import edu.glyndwr.mediaplayer.frontend.controller.MediaplayerFrontendController;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import static org.apache.commons.lang3.text.WordUtils.wrap;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Component
public class PlayerPaneFactory {
    
    private GridPane buildSearchBarPane(MediaplayerFrontendController controller){
        controller.getSearchButton().setText("Search");
        controller.getSearchButton().setOnAction((ActionEvent e) -> {
          if(!controller.getSearchField().getText().isEmpty() && !controller.getSearchField().getText().isBlank()){
             controller.loadVideos(controller.getSearchField().getText().trim());  
          }
        });     
        
        GridPane mediaPane = new GridPane();
        mediaPane.setPadding(new Insets(5, 5, 5, 5));  
        mediaPane.setHgap(5.0);
        mediaPane.setVgap(5.0);
        mediaPane.addRow(0, controller.getSearchField(), controller.getSearchButton());
        return mediaPane;
    }
    
    
    public GridPane buildPlayerPane(MediaplayerFrontendController controller, ScrollPane resultpane){
        GridPane mediaPane = new GridPane();
        mediaPane.setPadding(new Insets(5, 5, 5, 5));  
        mediaPane.setHgap(5.0);
        mediaPane.setVgap(5.0);
        mediaPane.setId("mediapane");
        GridPane titlepane = new GridPane();
        titlepane.setPadding(new Insets(5, 5, 5, 5));
        titlepane.setId("titlepane");
        Label title = new Label("no video selected");
        title.setId("title");
        titlepane.addRow(0, title);
        mediaPane.setPadding(new Insets(5, 5, 5, 5));  
        mediaPane.addRow(0, buildSearchBarPane(controller));
        mediaPane.addRow(1, controller.getPlayer(), resultpane);
        mediaPane.addRow(2, titlepane);
        return mediaPane;
    }
    
        public void buildTitlePane(MediaplayerFrontendController controller,YouTubeVideo video){
        GridPane titlepane = (GridPane) controller.getPrimaryStage().getScene().lookup("#titlepane");
        titlepane.getChildren().clear();
        Label title = new Label(wrap(video.getTitle(),85));
        Label description = new Label(wrap(video.getDescription(),85));
        titlepane.addRow(0, title, new Label(""));
        titlepane.addRow(1, description, new Label(""));
    }
    
}
