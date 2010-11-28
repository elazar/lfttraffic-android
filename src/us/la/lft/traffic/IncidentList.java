package us.la.lft.traffic;

import java.util.ArrayList;

public class IncidentList extends ArrayList<IncidentValueObject> {
	private static final long serialVersionUID = 2573672313874189792L;

	public void add(int latitude, int longitude, String address, String description, String reportTime) {
		add(new IncidentValueObject(latitude, longitude, address, description, reportTime));
	}
}
