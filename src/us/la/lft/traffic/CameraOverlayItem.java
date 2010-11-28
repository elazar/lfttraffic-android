package us.la.lft.traffic;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class CameraOverlayItem extends OverlayItem {
	protected int index;
	
	public CameraOverlayItem(GeoPoint point, String title, String snippet, int index) {
		super(point, title, snippet);
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}
}
