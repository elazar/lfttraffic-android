package us.la.lft.traffic.http;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostFroyoHttpClient implements HttpGetRequest {
    public InputStream get(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            return urlConnection.getInputStream();
        } catch (Exception e) {
            return null;
        }
    }
}
