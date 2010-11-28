package us.la.lft.traffic;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;

public class IncidentItemizedOverlay extends ItemizedOverlay<IncidentOverlayItem> {
	protected ArrayList<IncidentOverlayItem> overlayItems = new ArrayList<IncidentOverlayItem>();
	protected Activity activity;

	public IncidentItemizedOverlay(Drawable defaultMarker, Activity activity) {
		super(boundCenterBottom(defaultMarker));
		this.activity = activity;
	}
	
	public void addOverlayItem(IncidentOverlayItem overlayItem) {
		overlayItems.add(overlayItem);
		populate();
	}

	@Override
	protected IncidentOverlayItem createItem(int i) {
		return overlayItems.get(i);
	}

	@Override
	public int size() {
		return overlayItems.size();
	}
	
	@Override
	protected boolean onTap(int index) {
		IncidentOverlayItem overlayItem = overlayItems.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this.activity);
		dialog.setTitle(overlayItem.getTitle());
		dialog.setMessage(overlayItem.getSnippet());
		dialog.show();
		return true;
	}
}
