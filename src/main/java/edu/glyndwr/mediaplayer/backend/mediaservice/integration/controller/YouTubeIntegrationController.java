
package edu.glyndwr.mediaplayer.backend.mediaservice.integration.controller;

import edu.glyndwr.mediaplayer.backend.mediaservice.integration.configuration.MediaPlayerApiConfiguration;
import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeSearchQuery;
import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeVideo;
import edu.glyndwr.mediaplayer.backend.mediaservice.integration.service.YouTubeService;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Component
@Getter
@Setter
public class YouTubeIntegrationController {

    @Autowired
    private YouTubeService youtubeService;
    private YouTubeSearchQuery youtubeSearchCriteria;
    
    public YouTubeIntegrationController(){
        initializeController();
    }
    
    
    private void initializeController(){   
        youtubeSearchCriteria = new YouTubeSearchQuery();
    }
    
    public List<YouTubeVideo> loadVideosbySearchQuery(){
        List<YouTubeVideo> videos = youtubeService.fetchVideosByQuery(youtubeSearchCriteria.getQueryTerm());
        return videos;
    }
    
}
