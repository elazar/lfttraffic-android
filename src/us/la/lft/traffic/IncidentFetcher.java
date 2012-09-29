package us.la.lft.traffic;

import android.app.Application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;

import us.la.lft.traffic.http.CachedHttpGetRequest;

public class IncidentFetcher {
	protected Application application = null;
	protected static IncidentFetcher incidentFetcher;

	public IncidentFetcher(Application application) {
		this.application = application;
	}

	public IncidentList getIncidentList() {
		IncidentList incidentList = new IncidentList();
		try {
			String content = new CachedHttpGetRequest().get(
						"http://laftraf.laughinglarkllc.com/accidents.json",
						this.application.getCacheDir().getPath() + "/accidents.json",
						300000L
					);
			JSONArray accidents = new JSONArray(content);
			int accidentCount = accidents.length();
			DateFormat dateParser = new SimpleDateFormat("MM/dd/yyyy - hh:mm a");
			DateFormat dateFormatter = new SimpleDateFormat("h:mm a");
			JSONObject accident;
			String address = "", description = "", time = "";
			int latitude = 0, longitude = 0;

			for (int index = 0; index < accidentCount; index++) {
				accident = accidents.getJSONObject(index);
				address = accident.getString("address") + ", " + accident.getString("city");
				description = accident.getString("due_to");
				time = dateFormatter.format(dateParser.parse(accident.getString("time")));
				latitude = (int) (Float.parseFloat(accident.getString("latitude")) * 1000000);
				longitude = (int) (Float.parseFloat(accident.getString("longitude")) * 1000000);

				incidentList.add(latitude, longitude, address, description, time);
			}
			incidentList.trimToSize();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return incidentList;
	}
}
