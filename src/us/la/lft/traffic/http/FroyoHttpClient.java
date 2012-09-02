package us.la.lft.traffic.http;

import org.apache.http.client.HttpClient;

public class FroyoHttpClient extends BaseHttpClient {
    public HttpClient getHttpClient() {
        try {
            return (HttpClient) Class.forName("android.net.http.AndroidHttpClient")
                .getMethod("newInstance", String.class)
                .invoke(null, "Android " + android.os.Build.VERSION.RELEASE);
        } catch (Exception e) {
            return null;
        }
    }
}
