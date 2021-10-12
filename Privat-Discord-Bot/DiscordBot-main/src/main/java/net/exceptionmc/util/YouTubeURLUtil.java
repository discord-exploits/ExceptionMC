package net.exceptionmc.util;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

public class YouTubeURLUtil {

    public String getYouTubeUniformResourceLocatorByTitle(String query) {

        YouTube youtube = null;

        try {

            youtube = new YouTube.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                    new JacksonFactory(), request -> {
            }).setApplicationName("youtube-cmdline-search-sample").build();
        } catch (GeneralSecurityException | IOException exception) {

            exception.printStackTrace();
        }

        YouTube.Search.List search = null;

        try {

            assert youtube != null;
            search = youtube.search().list("id,snippet");
        } catch (IOException ioException) {

            ioException.printStackTrace();
        }

        assert search != null;
        search.setKey("AIzaSyCQ7DcuN4HKKqb5n3nS_9ieRX4RBtUPVrk");

        search.setQ(query);
        search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
        search.setMaxResults((long) 10);

        SearchListResponse searchResponse = null;

        try {

            searchResponse = search.execute();
        } catch (IOException ioException) {

            ioException.printStackTrace();
        }

        assert searchResponse != null;
        List<SearchResult> searchResultList = searchResponse.getItems();

        Iterator<SearchResult> iteratorSearchResults = searchResultList.iterator();

        if (!searchResultList.isEmpty()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId resourceIdId = singleVideo.getId();

            return "youtube.com/watch?v=" + resourceIdId.getVideoId();
        }

        return null;
    }
}
