package us.la.lft.traffic;

import java.io.InputStream;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import us.la.lft.traffic.http.HttpGetRequestFactory;

public class CameraFetcher {
	protected CameraList cameraList = null;
	protected long lastFetched = 0;
	protected static CameraFetcher cameraFetcher;

	public static CameraFetcher getInstance() {
		if (cameraFetcher == null) {
			cameraFetcher = new CameraFetcher();
		}
		return cameraFetcher;
	}

	public CameraList getCameraList() {
		// Cache results for 24 hours (or 86400000000 milliseconds; getTime() returns milliseconds since UNIX epoch)
		if (new Date().getTime() - this.lastFetched < 86400000000L) {
			return this.cameraList;
		}

		this.cameraList = new CameraList();

		try {
            InputStream inputStream = HttpGetRequestFactory.getHttpGetRequest().get("http://laftraf.laughinglarkllc.com/cameras.json");
            if (inputStream == null) {
                return this.cameraList;
            }

            String content = new Scanner(inputStream).useDelimiter("\\A").next();
            JSONArray cameras = new JSONArray(content);
            int cameraCount = cameras.length();
            JSONObject camera;
            String description = "", image = "";
            int latitude = 0, longitude = 0;

            for (int index = 0; index < cameraCount; index++) {
                camera = cameras.getJSONObject(index);
                description = camera.getString("main_road") + " @ " + camera.getString("cross_road");
                latitude = (int) (Float.parseFloat(camera.getString("latitude")) * 1000000);
                longitude = (int) (Float.parseFloat(camera.getString("longitude")) * 1000000);
                image = camera.getString("ptzid");
                this.cameraList.add(latitude, longitude, description, image);
            }

            this.lastFetched = new Date().getTime();
		} catch (Exception e) {
		}
		return this.cameraList;
	}
}