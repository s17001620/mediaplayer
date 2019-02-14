package edu.glyndwr.mediaplayer.frontend.factories;

import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeVideo;
import edu.glyndwr.mediaplayer.frontend.controller.MediaplayerFrontendController;
import javafx.scene.control.ScrollPane;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import static org.apache.commons.lang3.text.WordUtils.wrap;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Component
public class ResultListFactory {

    private MediaplayerFrontendController controller;

    public ScrollPane buildResultScrollPane(MediaplayerFrontendController controller) {
        this.controller = controller;
        final ScrollPane scroll = new ScrollPane();
        scroll.setId("resultScroll");
        scroll.setPrefHeight(365);
        scroll.setPrefWidth(645);
        scroll.setHbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(javafx.scene.control.ScrollPane.ScrollBarPolicy.AS_NEEDED);
        GridPane pane = controller.getResultPane();
        pane.setPadding(new Insets(5, 5, 5, 5));  
        pane.setHgap(5.0);
        pane.setVgap(5.0);
        pane.setPrefHeight(360);
        pane.setPrefWidth(640);
        int count = 0;
        for (YouTubeVideo video : controller.getMediaplayerFrontendModel().getVideos()) {
            pane.addRow(count++, buildSingleVideoResultPane(video));
        }
        scroll.setContent(pane);
        return scroll;
    }

    public GridPane buildSingleVideoResultPane(YouTubeVideo video) {
        GridPane singleResultPane = new GridPane();
        GridPane imagePane = new GridPane();
        GridPane descriptionPane = new GridPane();

        descriptionPane.setPadding(new Insets(5, 5, 5, 5));     
        ImageView youTubeThumbmail = new ImageView(new Image(video.getThumbnailUrl()));
        imagePane.addColumn(0, youTubeThumbmail);
        Button playbutton = new Button();
        playbutton.setText("Play");
        playbutton.setOnAction((ActionEvent e) -> {
            controller.playVideo(video);
        });
        descriptionPane.addRow(0, new Label(breakStringForVideoLabel(video.getTitle() + " " + video.getPublishDate())));
        descriptionPane.addRow(1, new Label(breakStringForVideoLabel(video.getDescription())));
        GridPane playpane = new GridPane();
        playpane.addRow(0,playbutton, new Label(" Category: "), new Label(breakStringForVideoLabel(video.getCategoryAsString())));
        descriptionPane.addRow(2, playpane);
        descriptionPane.setPrefWidth(500);
        singleResultPane.setHgap(5);  
        singleResultPane.setVgap(5); 
        singleResultPane.setPadding(new Insets(5, 5, 5, 5));  
        singleResultPane.addRow(0, imagePane, descriptionPane);
        singleResultPane.setPrefWidth(600);
        return singleResultPane;
    }

    public void rebuildResultPane() {

        ScrollPane resultscroll = (ScrollPane) controller.getPrimaryStage().getScene().lookup("#resultScroll");
        GridPane pane = new GridPane();
        pane.setHgap(5);  
        pane.setVgap(5); 
        pane.setPadding(new Insets(5, 5, 5, 5));  
        int count = 0;
        for (YouTubeVideo video : controller.getMediaplayerFrontendModel().getVideos()) {
            pane.addRow(count++, buildSingleVideoResultPane(video));
        }
        controller.setResultPane(pane);
        resultscroll.setContent(controller.getResultPane());
    }

    public String breakStringForVideoLabel(String text) {
        return wrap(text, 90);
    }
}
