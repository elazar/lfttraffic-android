package us.la.lft.traffic;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CameraImageFetcher {
	protected DefaultHttpClient httpClient;
	protected HttpGet httpRequest;
	
	public CameraImageFetcher(String imageUrl) {
		HttpParams httpParams = new BasicHttpParams();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(httpParams, schemeRegistry);
        this.httpClient = new DefaultHttpClient(connectionManager, httpParams);
        this.httpRequest = new HttpGet(imageUrl);
	}

	public Bitmap getImageBitmap() {
		HttpResponse httpResponse;
    	try {
    		httpResponse = this.httpClient.execute(this.httpRequest);
    		if (httpResponse.getStatusLine().getStatusCode() != 200) {
        		return null;
        	}
    		if (httpResponse.getFirstHeader("Content-Length").getValue().equals("0")) {
    			return null;
    		}
    		return BitmapFactory.decodeStream(httpResponse.getEntity().getContent());
    	} catch (IOException e) { }
		return null;
	}
}
