package us.la.lft.traffic.http;

import android.os.Build;

public class HttpGetRequestFactory {
    public static HttpGetRequest getHttpGetRequest() {
        int currentVersion = Build.VERSION.SDK_INT;
        int froyoVersion = 8;
        if (currentVersion < froyoVersion) {
            return new PreFroyoHttpClient();
        } else if (currentVersion == froyoVersion) {
            return new FroyoHttpClient();
        }
        return new PostFroyoHttpClient();
    }
}
