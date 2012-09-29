package us.la.lft.traffic.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

public class FroyoHttpClient implements HttpGetRequest {
	@Override
	public InputStream get(String urlString) {
		BasicHttpParams httpParams = new BasicHttpParams();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        DefaultHttpClient httpClient = new DefaultHttpClient(connectionManager, httpParams);
		HttpGet httpRequest = new HttpGet(urlString);
		HttpResponse httpResponse;
		BufferedHttpEntity httpEntity;
		Header contentEncoding;
		InputStream inputStream;
		try {
			httpRequest.addHeader("Accept-Encoding", "gzip");
			httpResponse = httpClient.execute(httpRequest);
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				return null;
			}
			httpEntity = new BufferedHttpEntity(httpResponse.getEntity());
			inputStream = httpEntity.getContent();
			contentEncoding = httpResponse.getFirstHeader("Content-Encoding");
			if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
				inputStream = new GZIPInputStream(inputStream);
			}
			return inputStream;
		} catch (IOException e) {
			return null;
		}
	}
}
