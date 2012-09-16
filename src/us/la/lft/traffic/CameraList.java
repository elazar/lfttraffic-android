package us.la.lft.traffic;

import java.util.ArrayList;

public class CameraList extends ArrayList<CameraValueObject> {
	private static final long serialVersionUID = -4158264483621151016L;
	
	public void add(int latitude, int longitude, String description, String image) {
		add(new CameraValueObject(latitude, longitude, description, image));
	}
}
