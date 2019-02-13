package edu.glyndwr.mediaplayer.backend.mediaservice.integration.models;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Alexander Bruckbauer s17001620
 */
@Getter
@Setter
public class YouTubeVideo {
    private String id;
    private String categoryId;
    private String title;
    private String url;
    private String thumbnailUrl;
    private String publishDate;
    private String description;
    private String category;
    private final static String EMBED_PREFIX = "<iframe width=\"640\" height=\"360\" src=\"https://www.youtube-nocookie.com/embed/";
    private final static String EMBED_SUFFIX = "\" frameborder=\"0\" allow=\"accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";

    private HashMap<String, String> categories = new HashMap<>();

    public YouTubeVideo(){
        initializeCategories(); 
    }
    
    public String getCategoryAsString(){
        String result= categories.get(this.categoryId);
        if(null == result || result.isEmpty() || result.isBlank()){
            result = "Other/Diverse";
        }
        return result;
    }
    
    public String getEmbedCode(){
        return EMBED_PREFIX+this.getId()+EMBED_SUFFIX;
    }
    
    private void initializeCategories() {
        categories.put("1", "Film & Animation");
        categories.put("2", "Autos & Vehicles");
        categories.put("10", "Music");
        categories.put("15", "Pets & Animals");
        categories.put("17", "Sports");
        categories.put("18", "Short Movies");
        categories.put("19", "Travel & Events");
        categories.put("20", "Gaming");
        categories.put("21", "Videoblogging");
        categories.put("22", "People & Blogs");
        categories.put("23", "Comedy");
        categories.put("24", "Entertainment");
        categories.put("25", "News & Politics");
        categories.put("26", "Howto & Style");
        categories.put("27", "Education");
        categories.put("28", "Science & Technology");
        categories.put("29", "Nonprofits & Activism");
        categories.put("30", "Movies");
        categories.put("31", "Anime/Animation");
        categories.put("32", "Action/Adventure");
        categories.put("33", "Classics");
        categories.put("34", "Comedy");
        categories.put("35", "Documentary");
        categories.put("36", "Drama");
        categories.put("37", "Family");
        categories.put("38", "Foreign");
        categories.put("39", "Horror");
        categories.put("40", "Sci-Fi/Fantasy");
        categories.put("41", "Thriller");
        categories.put("42", "Shorts");
        categories.put("43", "Shows");
        categories.put("44", "Trailers");
    }
}
