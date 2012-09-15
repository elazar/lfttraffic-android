package us.la.lft.traffic;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import us.la.lft.traffic.http.HttpGetRequestFactory;

public class IncidentFetcher {
	protected IncidentList incidentList = null;
	protected long lastFetched = 0;
	protected static IncidentFetcher incidentFetcher;

	public static IncidentFetcher getInstance() {
		if (incidentFetcher == null) {
			incidentFetcher = new IncidentFetcher();
		}
		return incidentFetcher;
	}

	public IncidentList getIncidentList() {
		// Cache results for 5 minutes (or 300000 milliseconds; getTime() returns milliseconds since UNIX epoch)
		if (new Date().getTime() - this.lastFetched < 300000) {
			return this.incidentList;
		}

		this.incidentList = new IncidentList();

		try {
            InputStream inputStream = HttpGetRequestFactory.getHttpGetRequest().get("http://laftraf.laughinglarkllc.com/accidents.json");
            if (inputStream == null) {
                return this.incidentList;
            }

            String content = new Scanner(inputStream).useDelimiter("\\A").next();
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

                this.incidentList.add(latitude, longitude, address, description, time);
            }

            this.lastFetched = new Date().getTime();
		} catch (Exception e) {
		}
		return this.incidentList;
	}
}
