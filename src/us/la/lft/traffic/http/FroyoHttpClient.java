package us.la.lft.traffic.http;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.HttpResponse;

import java.io.InputStream;
import java.io.IOException;

public class FroyoHttpClient implements HttpGetRequest {
	public InputStream get(String urlString) {
		BasicHttpParams httpParams = new BasicHttpParams();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager, httpParams);
		HttpGet httpRequest = new HttpGet(urlString);
		HttpResponse httpResponse;
		BufferedHttpEntity httpEntity;
		try {
			httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				return null;
			}
			httpEntity = new BufferedHttpEntity(httpResponse.getEntity());
			return httpEntity.getContent();
		} catch (IOException e) {
			return null;
		}
	}
}
