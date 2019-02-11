package edu.glyndwr.mediaplayer.backend.mediaservice.integration.models;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Getter
@Setter
public class YouTubeVideo {

    private String title;
    private String url;
    private String thumbnailUrl;
    private String publishDate;
    private String description;
    private String category;
            
}
