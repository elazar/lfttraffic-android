package us.la.lft.traffic;

import org.json.JSONArray;
import org.json.JSONObject;

import us.la.lft.traffic.http.CachedHttpGetRequest;
import android.app.Application;

public class CameraFetcher {
	protected Application application = null;
	protected static CameraFetcher cameraFetcher;

	public CameraFetcher(Application application) {
		this.application = application;
	}

	public CameraList getCameraList() {
		CameraList cameraList = new CameraList();
		String content;
		try {
			content = new CachedHttpGetRequest().get(
						"http://laftraf.laughinglarkllc.com/cameras.json",
						this.application.getCacheDir().getPath() + "/cameras.json",
						86400000L
					);
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
				cameraList.add(latitude, longitude, description, image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cameraList;
	}
}
