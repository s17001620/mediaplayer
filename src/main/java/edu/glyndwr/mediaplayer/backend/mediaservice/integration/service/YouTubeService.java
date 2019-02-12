package edu.glyndwr.mediaplayer.backend.mediaservice.integration.service;

import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.api.services.youtube.model.Video;
import edu.glyndwr.mediaplayer.backend.mediaservice.integration.configuration.MediaPlayerApiConfiguration;
import edu.glyndwr.mediaplayer.backend.mediaservice.integration.models.YouTubeVideo;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Service
public class YouTubeService {

    @Autowired
    private MediaPlayerApiConfiguration apiConfiguration;
    private static final long MAX_SEARCH_RESULTS = 15;
    private static final String BASE_URL = "https://www.youtube.com/embed?v=";
    
    private static final String VIDEO_TYPE = "video";
    private static final String VIDEO_SEARCH_LIST = "id,snippet";
    private static final String VIDEO_CATEGORY_SEARCH_LIST = "snippet,id";
    private static final String VIDEO_SEARCH_FIELDS = "items(id/kind,id/videoId,snippet/title,snippet/description,snippet/publishedAt,snippet/thumbnails/default/url)";
    private static final String VIDEO_SEARCH_DATE_FORMAT = "MMM dd, yyyy";
    private static final String VIDEO_APPLICATION_NAME = "mediaplayer"; 
    List<YouTubeVideo> videos = new ArrayList<>();

    public List<YouTubeVideo> fetchVideosByQuery(String queryTerm) {
        YouTube youtube = getYouTube();
        try {
            YouTube.Search.List search = youtube.search().list(VIDEO_SEARCH_LIST);
            search.setKey(apiConfiguration.getKey());
            search.setQ(queryTerm);
            search.setType(VIDEO_TYPE);
            search.setFields(VIDEO_SEARCH_FIELDS);
            search.setMaxResults(MAX_SEARCH_RESULTS);
            DateFormat df = new SimpleDateFormat(VIDEO_SEARCH_DATE_FORMAT);
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                searchResultList.stream().map((result) -> {
                    YouTubeVideo video = new YouTubeVideo();
                    YouTube.Videos.List videos = null;
                    try {
                        videos = youtube.videos().list(VIDEO_CATEGORY_SEARCH_LIST).setId(result.getId().getVideoId());
                        VideoListResponse listResponse = videos.execute();
                          List<Video> videoList = listResponse.getItems();                  
                    if (videoList != null) {
                        
                        Video v = videoList.get(0);
                     video.setCategoryId(v.getSnippet().getCategoryId());
                    }
                    } catch (IOException ex) {
                        Logger.getLogger(YouTubeService.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    video.setTitle(result.getSnippet().getTitle());
                    video.setUrl(buildVideoUrl(result.getId().getVideoId()));
                    video.setThumbnailUrl(result.getSnippet().getThumbnails().getDefault().getUrl());
                    video.setDescription(result.getSnippet().getDescription());
                    DateTime dateTime = result.getSnippet().getPublishedAt();
                    Date date = new Date(dateTime.getValue());
                    String dateString = df.format(date);
                    video.setPublishDate(dateString);
                    return video;
                }).forEachOrdered((video) -> {
                    videos.add(video);
                });
            }

        } catch (IOException ex) {
            Logger.getLogger(YouTubeService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return videos;
    }

    private YouTube getYouTube() {
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(),
                (reqeust) -> {
                }).setApplicationName(VIDEO_APPLICATION_NAME).build();

        return youtube;
    }

    private String buildVideoUrl(String videoId) {
        StringBuilder builder = new StringBuilder();
        builder.append(BASE_URL);
        builder.append(videoId);

        return builder.toString();
    }
}
