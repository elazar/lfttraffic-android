package us.la.lft.traffic;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class CameraActivity extends Activity {
	CameraList cameraList;
	CameraImageFetcher cameraImageFetcher;
	ImageView cameraImageView;
	SeekBar cameraSeekBar;
	TextView cameraDescriptionView;
	Handler handler;
	Runnable cameraImageUpdater;
	boolean progressDialogShown = false;
	float lastTouchX, lastTouchY;

	private class UpdateCameraImageTask extends AsyncTask<Void, Void, Bitmap> {
		ProgressDialog progressDialog;

		@Override
		protected Bitmap doInBackground(Void... params) {
			return cameraImageFetcher.getImageBitmap();
		}

		@Override
		protected void onPreExecute() {
			if (!progressDialogShown) {
				progressDialog = ProgressDialog.show(
					CameraActivity.this,
					"",
					getResources().getString(R.string.loading),
					true,
					false
				);
			}
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			cameraImageView.setImageBitmap(result);
			cameraImageView.setAlpha(255);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}

	private class SeekBarListener implements SeekBar.OnSeekBarChangeListener {
		@Override
		public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			updateCamera(progress);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) { }

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) { }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		cameraList = new CameraList();

		cameraDescriptionView = (TextView) findViewById(R.id.camera_description_view);
		cameraImageView = (ImageView) findViewById(R.id.camera_image_view);
		cameraSeekBar = (SeekBar) findViewById(R.id.camera_seek_bar);
		cameraSeekBar.setMax(cameraList.size() - 1);
		cameraSeekBar.setOnSeekBarChangeListener(new SeekBarListener());
		cameraSeekBar.requestFocus();

		handler = new Handler();
		cameraImageUpdater = new Runnable() {
			public void run() {
				new UpdateCameraImageTask().execute();
				progressDialogShown = true;
				handler.postDelayed(this, 2000);
			}
		};

		int cameraIndex = getIntent().getIntExtra("camera", 0);
		updateCamera(cameraIndex);
		cameraSeekBar.setProgress(cameraIndex);
	}

	private void updateCamera(int cameraIndex) {
		handler.removeCallbacks(cameraImageUpdater);
		cameraImageView.setAlpha(0);

		CameraValueObject cameraValueObject = cameraList.get(cameraIndex);

		String description = cameraValueObject.getDescription();
		cameraDescriptionView.setText(description);

		HashMap<String, String> eventArgs = new HashMap<String, String>();
		eventArgs.put("camera", description);
		FlurryAgent.onEvent("CameraActivity.updateCamera", eventArgs);

		String imageUrl = CameraImageUrlGenerator.getUrl(cameraValueObject.getImage());
		cameraImageFetcher = new CameraImageFetcher(imageUrl);

		runOnUiThread(cameraImageUpdater);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(cameraImageUpdater);
	}

	@Override
	public void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, getResources().getString(R.string.flurry_key));
	}

	@Override
	public void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}
}
