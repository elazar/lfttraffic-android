package us.la.lft.traffic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;
import java.io.IOException;

import us.la.lft.traffic.http.HttpGetRequestFactory;
import us.la.lft.traffic.http.HttpGetRequest;

public class CameraImageFetcher {
    protected String imageUrl;
	
	public CameraImageFetcher(String imageUrl) {
        this.imageUrl = imageUrl;
	}

	public Bitmap getImageBitmap() {
        HttpGetRequest httpGetRequest = HttpGetRequestFactory.getHttpGetRequest();
        InputStream inputStream = httpGetRequest.get(this.imageUrl);
        return BitmapFactory.decodeStream(inputStream);
	}
}
