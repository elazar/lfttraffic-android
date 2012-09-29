package us.la.lft.traffic;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
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
	float lastTouchX, lastTouchY;
	
	private class UpdateCameraImageTask extends AsyncTask<Void, Void, Bitmap> {
		ProgressDialog progressDialog;
		
		@Override
		protected Bitmap doInBackground(Void... params) {
			return cameraImageFetcher.getImageBitmap();
		}
		
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
		
		protected void onPostExecute(Bitmap result) {
			cameraImageView.setImageBitmap(result);
			cameraImageView.setAlpha(255);
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		}
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        
        cameraIndex = getIntent().getIntExtra("camera", 0);
        
        cameraList = new CameraFetcher(getApplication()).getCameraList();
        
        cameraDescriptionView = (TextView) findViewById(R.id.camera_description_view);
        cameraImageView = (ImageView) findViewById(R.id.camera_image_view);

    	handler = new Handler();
    	cameraImageUpdater = new Runnable() {
    		public void run() {
    			new UpdateCameraImageTask().execute();
				progressDialogShown = true;
    			handler.postDelayed(this, 2000);
    		}
    	};
        
        updateCamera();
    }
	
	private void updateCamera() {
		handler.removeCallbacks(cameraImageUpdater);
		cameraImageView.setAlpha(0);
		
		CameraValueObject cameraValueObject = cameraList.get(cameraIndex);
        
        String description = cameraValueObject.getDescription();
    	cameraDescriptionView.setText(description);
    	
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
	public boolean onTouchEvent(MotionEvent event) {
		final int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			lastTouchX = event.getX();
			lastTouchY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			float newX = event.getX();
			float newY = event.getY();
			float diffX = Math.abs(lastTouchX - newX);
			float diffY = Math.abs(lastTouchY - newY);
			// Horizontal swipe
			if (diffX >= diffY) {
				// Left swipe
				if (lastTouchX > newX) {
					cameraIndex++;
				// Right swipe
				} else {
					cameraIndex--;
				}
			// Vertical swipe
			} else {
				// Up swipe
				if (lastTouchY > newY) {
					cameraIndex++;
				// Down swipe
				} else {
					cameraIndex--;
				}
			}
			int lastCameraIndex = cameraList.size() - 1;
			if (cameraIndex < 0) {
				cameraIndex = 0;
			} else if (cameraIndex > lastCameraIndex) {
				cameraIndex = lastCameraIndex;
			} else {
				updateCamera();
			}
		}
		return true;
	}
}