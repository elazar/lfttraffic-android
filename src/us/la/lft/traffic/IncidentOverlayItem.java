package us.la.lft.traffic;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class IncidentOverlayItem extends OverlayItem {
	public IncidentOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}
}
