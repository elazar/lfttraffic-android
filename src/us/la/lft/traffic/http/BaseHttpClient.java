package us.la.lft.traffic.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;

import java.io.InputStream;
import java.io.IOException;

public abstract class BaseHttpClient implements HttpGetRequest {
    protected abstract HttpClient getHttpClient();

    public InputStream get(String urlString) {
		HttpGet httpRequest = new HttpGet(urlString);
		HttpResponse httpResponse;
		try {
			httpResponse = this.getHttpClient().execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() != 200
				|| httpResponse.getFirstHeader("Content-Length").getValue().equals("0")) {
				return null;
			}
			return httpResponse.getEntity().getContent();
		} catch (IOException e) {
			return null;
		}
    }
}
