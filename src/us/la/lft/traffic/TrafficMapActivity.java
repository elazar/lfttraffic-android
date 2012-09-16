package us.la.lft.traffic;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

public class TrafficMapActivity extends MapActivity {
	MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources resources = getResources();

        setContentView(R.layout.traffic_map);

        mapView = (MapView) findViewById(R.id.map_view);
        mapView.setBuiltInZoomControls(true);

        Intent intent = getIntent();
        GeoPoint center;
        MapController mapController = mapView.getController();
        if (intent.hasExtra("center")) {
        	center = new GeoPoint(
				intent.getIntExtra("latitude", 0),
				intent.getIntExtra("longitude", 0)
			);
        } else {
        	center = new GeoPoint(30222430, -92020450);
        }
    	mapController.setCenter(center);
        mapController.setZoom(15);

        List<Overlay> overlays = mapView.getOverlays();

        IncidentList incidentList = IncidentFetcher.getInstance().getIncidentList();
        if (incidentList.size() > 0) {
	        Drawable incidentIcon = resources.getDrawable(R.drawable.incident);
	        String incidentTitle = resources.getString(R.string.incident);
	        IncidentItemizedOverlay incidentOverlay = new IncidentItemizedOverlay(incidentIcon, this);
	        for (IncidentValueObject incidentValueObject: incidentList) {
		    	incidentOverlay.addOverlayItem(
	    			new IncidentOverlayItem(
						new GeoPoint(
							incidentValueObject.getLatitude(),
							incidentValueObject.getLongitude()
						),
						incidentTitle,
						incidentValueObject.toString()
					)
				);
	        }
	        overlays.add(incidentOverlay);
        }

        Drawable cameraIcon = resources.getDrawable(R.drawable.camera);
        String cameraTitle = resources.getString(R.string.camera);
        CameraItemizedOverlay cameraOverlay = new CameraItemizedOverlay(cameraIcon, this);
        CameraList cameraList = CameraFetcher.getInstance().getCameraList();
        for (CameraValueObject cameraValueObject : cameraList) {
        	cameraOverlay.addOverlayItem(
    			new CameraOverlayItem(
    				new GeoPoint(
						cameraValueObject.getLatitude(),
						cameraValueObject.getLongitude()
					),
					cameraTitle,
					cameraValueObject.getDescription(),
					cameraList.indexOf(cameraValueObject)
    			)
    		);
        }
        overlays.add(cameraOverlay);

        mapView.postInvalidate();
    }

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
}
