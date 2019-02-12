package edu.glyndwr.mediaplayer.frontend.factories;

import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeVideo;
import edu.glyndwr.mediaplayer.frontend.controller.MediaplayerFrontendController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Component
public class MediaPlayerUIFactory {

    @Autowired
    private PlayerPaneFactory playerPaneFactory;
    @Autowired
    private ResultListFactory resultListFactory;
    private MediaplayerFrontendController controller;
    
    public Stage buildUI(MediaplayerFrontendController controller) {
        this.controller = controller;
        VBox root = new VBox(3);
        root.setStyle("-fx-padding: 10;" + "-fx-border-style: solid inside;" + "-fx-border-width: 2;" + "-fx-border-insets: 5;" + "-fx-border-radius: 5;" + "-fx-border-color: blue;");
        FlowPane pane = new FlowPane();
        pane.setId("contentPane");
        pane.setVgap(5);
        pane.setHgap(5);
        GridPane orderpane = new GridPane();
        orderpane.setPadding(new Insets(5, 5, 5, 5));
        orderpane.setHgap(5.0);
        orderpane.setVgap(5.0);
        orderpane.addRow(0, playerPaneFactory.buildPlayerPane(this.controller , resultListFactory.buildResultScrollPane(this.controller )));
        pane.getChildren().addAll(orderpane);
        root.getChildren().addAll(pane);
        root.setSpacing(5);
        Scene scene = new Scene(root);
        this.controller .getPrimaryStage().setScene(scene);
        this.controller .getPrimaryStage().setTitle("Media Player");
        return this.controller .getPrimaryStage();

    }

    public void rebuildResultPane() {
        resultListFactory.rebuildResultPane();
    }
    
        public void rebuildPlayerPane(YouTubeVideo video) {
            playerPaneFactory.buildTitlePane(this.controller, video);
    }
}
