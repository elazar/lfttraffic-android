package us.la.lft.traffic.http;

import android.os.Build;

public class HttpGetRequestFactory {
    public static HttpGetRequest getHttpGetRequest() {
        return (Build.VERSION.SDK_INT <= 8) ? new FroyoHttpClient() : new PostFroyoHttpClient();
    }
}
