

package edu.glyndwr.mediaplayer.frontend.model;

import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeVideo;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Component
@Getter
@Setter
public class MediaplayerFrontendModel {
private ArrayList<YouTubeVideo> videos = new ArrayList<>();
}
