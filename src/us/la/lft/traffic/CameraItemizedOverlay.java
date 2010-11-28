package us.la.lft.traffic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;

public class CameraItemizedOverlay extends ItemizedOverlay<CameraOverlayItem> {
	protected ArrayList<CameraOverlayItem> overlayItems = new ArrayList<CameraOverlayItem>();
	protected Activity activity;

	public CameraItemizedOverlay(Drawable defaultMarker, Activity activity) {
		super(boundCenterBottom(defaultMarker));
		this.activity = activity;
	}
	
	public void addOverlayItem(CameraOverlayItem overlayItem) {
		overlayItems.add(overlayItem);
		populate();
	}

	@Override
	protected CameraOverlayItem createItem(int i) {
		return overlayItems.get(i);
	}

	@Override
	public int size() {
		return overlayItems.size();
	}
	
	@Override
	protected boolean onTap(int index) {
		CameraOverlayItem overlayItem = overlayItems.get(index);
		Intent intent = new Intent(this.activity, CameraActivity.class);
		intent.putExtra("camera", overlayItem.getIndex());
		this.activity.startActivity(intent);
		return true;
	}
}
