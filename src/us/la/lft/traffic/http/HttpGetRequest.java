package us.la.lft.traffic.http;

import java.io.InputStream;

public interface HttpGetRequest {
    public InputStream get(String urlString);
}
