package us.la.lft.traffic;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TrafficListActivity extends ListActivity {
	protected CameraList cameraList;
	protected int lastIncidentPosition = -1;
	
	private class FetchIncidentsTask extends AsyncTask<Void, Void, IncidentList> {
		ProgressDialog progressDialog;
		final Activity self = TrafficListActivity.this;
		
		@Override
		protected IncidentList doInBackground(Void... params) {
			return IncidentFetcher.getInstance().getIncidentList();
		}
		
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(
				self,
				"", 
                getResources().getString(R.string.loading),
                true,
                false
            );
		}
		
		protected void onPostExecute(IncidentList result) {
			lastIncidentPosition = result.size();
		
			SeparatedListAdapter listAdapter = new SeparatedListAdapter(self, R.layout.list_header);
			Resources resources = getResources();
			
			if (result.size() > 0) {
				ArrayAdapter<IncidentValueObject> incidentSectionAdapter = new ArrayAdapter<IncidentValueObject>(self, R.layout.list_item);
				for (IncidentValueObject valueObject : result) {
					incidentSectionAdapter.add(valueObject);
				}
				listAdapter.addSection(resources.getString(R.string.incidents), incidentSectionAdapter);
			}
			
			ArrayAdapter<CameraValueObject> cameraSectionAdapter = new ArrayAdapter<CameraValueObject>(self, R.layout.list_item);
			for (CameraValueObject valueObject : cameraList) {
				cameraSectionAdapter.add(valueObject);
			}
			listAdapter.addSection(resources.getString(R.string.cameras), cameraSectionAdapter);
			
			setListAdapter(listAdapter);
			
			progressDialog.dismiss();
		}
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        cameraList = new CameraList();
	}
	
	public void onResume() {
		super.onResume();
		
		new FetchIncidentsTask().execute();
	}
	
	public void onListItemClick(ListView l, View v, int position, long id) {
		if (position <= lastIncidentPosition) {
			IncidentValueObject valueObject = (IncidentValueObject) getListView().getItemAtPosition(position);
			Intent intent = new Intent(this, TrafficMapActivity.class);
			intent.putExtra("center", true);
			intent.putExtra("latitude", valueObject.getLatitude());
			intent.putExtra("longitude", valueObject.getLongitude());
			startActivity(intent);
			return;
		}
		
		CameraValueObject valueObject = (CameraValueObject) getListView().getItemAtPosition(position);
		Intent intent = new Intent(this, CameraActivity.class);
		intent.putExtra("camera", cameraList.indexOf(valueObject));
		startActivity(intent);
	}
}
