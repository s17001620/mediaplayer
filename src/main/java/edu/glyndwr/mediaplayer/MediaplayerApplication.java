package edu.glyndwr.mediaplayer;

import edu.glyndwr.mediaplayer.frontend.controller.MediaplayerFrontendController;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@Log
@SpringBootApplication
@ComponentScan("edu.glyndwr")
public class MediaplayerApplication extends Application{
    private ConfigurableApplicationContext context;
    @Autowired
    private MediaplayerFrontendController mediaplayerFrontendController;
	
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mediaplayerFrontendController = (MediaplayerFrontendController) context.getBean(MediaplayerFrontendController.class);
        mediaplayerFrontendController.initializeStage(primaryStage);
    }
    
    @Override
    public void init() throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(MediaplayerApplication.class).web(WebApplicationType.NONE)
                .headless(false)
                .bannerMode(Banner.Mode.OFF);         
        context = builder.run(getParameters().getRaw().toArray(new String[0]));
    }
    
      @Override
    public void stop() throws Exception {
        context.close();
    }
}

