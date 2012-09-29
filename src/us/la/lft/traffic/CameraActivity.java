package us.la.lft.traffic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

public class CameraActivity extends Activity {
	CameraList cameraList;
	int cameraIndex;
	CameraImageFetcher cameraImageFetcher;
	ImageView cameraImageView;
	TextView cameraDescriptionView;
	Handler handler;
	Runnable cameraImageUpdater;
	boolean progressDialogShown = false;

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
			handler.postDelayed(cameraImageUpdater, 2000);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		cameraImageView = (ImageView) findViewById(R.id.camera_image_view);
		cameraImageView.setAlpha(0);

		cameraIndex = getIntent().getIntExtra("camera", 0);
		cameraList = new CameraFetcher(getApplication()).getCameraList();
		CameraValueObject cameraValueObject = cameraList.get(cameraIndex);

		String description = cameraValueObject.getDescription();
		cameraDescriptionView = (TextView) findViewById(R.id.camera_description_view);
		cameraDescriptionView.setText(description);

		String imageUrl = CameraImageUrlGenerator.getUrl(cameraValueObject.getImage());
		cameraImageFetcher = new CameraImageFetcher(imageUrl);

		handler = new Handler();
		cameraImageUpdater = new Runnable() {
			@Override
			public void run() {
				new UpdateCameraImageTask().execute();
				progressDialogShown = true;
			}
		};
		runOnUiThread(cameraImageUpdater);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		handler.removeCallbacks(cameraImageUpdater);
	}
}