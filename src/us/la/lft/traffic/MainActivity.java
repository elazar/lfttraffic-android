package us.la.lft.traffic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
	final Activity self = this;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button listViewButton = (Button) findViewById(R.id.list_view);
        listViewButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		startActivity(new Intent(self, TrafficListActivity.class));
        	}
        });
        
        Button mapViewButton = (Button) findViewById(R.id.map_view);
        mapViewButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		startActivity(new Intent(self, TrafficMapActivity.class));
        	}
        });
        
        Button exitButton = (Button) findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View view) {
        		self.finish();
        	}
        });
	}
}