package us.la.lft.traffic;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.concurrent.CountDownLatch;

public class TrafficListActivity extends ListActivity {
	protected int lastIncidentPosition = -1;
	protected IncidentList incidentList = null;
	protected CameraList cameraList = null;

	private class FetchIncidentsTask extends AsyncTask<Void, Void, IncidentList> {
		protected CountDownLatch latch;

		public FetchIncidentsTask(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		protected IncidentList doInBackground(Void... params) {
			return IncidentFetcher.getInstance().getIncidentList();
		}

		protected void onPostExecute(IncidentList result) {
			incidentList = result;
			this.latch.countDown();
		}
	}

	private class FetchCamerasTask extends AsyncTask<Void, Void, CameraList> {
		protected CountDownLatch latch;

		public FetchCamerasTask(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		protected CameraList doInBackground(Void... params) {
			return CameraFetcher.getInstance().getCameraList();
		}

		protected void onPostExecute(CameraList result) {
			cameraList = result;
			this.latch.countDown();
		}
	}

	private class ProgressDialogTask extends AsyncTask<Void, Void, Void> {
		protected CountDownLatch latch;
		protected ProgressDialog progressDialog;

		public ProgressDialogTask(CountDownLatch latch) {
			this.latch = latch;
		}

		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(
					TrafficListActivity.this,
					"",
		            getResources().getString(R.string.loading),
		            true,
		            false
		        );
		}

		@Override
		protected Void doInBackground(Void... params) {
			try {
				this.latch.await();
			} catch (InterruptedException e) { }
			return null;
		}

		protected void onPostExecute(Void result) {
			TrafficListActivity self = TrafficListActivity.this;

			lastIncidentPosition = incidentList.size();

			SeparatedListAdapter listAdapter = new SeparatedListAdapter(self, R.layout.list_header);
			Resources resources = getResources();

			if (incidentList.size() > 0) {
				ArrayAdapter<IncidentValueObject> incidentSectionAdapter = new ArrayAdapter<IncidentValueObject>(self, R.layout.list_item);
				for (IncidentValueObject valueObject : incidentList) {
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

	public void onResume() {
		super.onResume();

		CountDownLatch latch = new CountDownLatch(2);
		FetchIncidentsTask fetchIncidentsTask = new FetchIncidentsTask(latch);
		FetchCamerasTask fetchCamerasTask = new FetchCamerasTask(latch);
		ProgressDialogTask progressDialogTask = new ProgressDialogTask(latch);

		fetchIncidentsTask.execute();
		fetchCamerasTask.execute();
		progressDialogTask.execute();
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent;
		if (position <= lastIncidentPosition) {
			IncidentValueObject valueObject = (IncidentValueObject) getListView().getItemAtPosition(position);
			intent = new Intent(this, TrafficMapActivity.class);
			intent.putExtra("center", true);
			intent.putExtra("latitude", valueObject.getLatitude());
			intent.putExtra("longitude", valueObject.getLongitude());
		} else {
			CameraValueObject valueObject = (CameraValueObject) getListView().getItemAtPosition(position);
			intent = new Intent(this, CameraActivity.class);
			intent.putExtra("camera", cameraList.indexOf(valueObject));
		}
		startActivity(intent);
	}
}
