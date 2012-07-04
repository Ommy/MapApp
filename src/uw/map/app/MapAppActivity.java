package uw.map.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class MapAppActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Button go = new Button(this);
        go.setText("Browse");
        setContentView(go);
        go.setOnClickListener(new OnClickListener(){
        		public void onClick (View v)
        		{
        			Intent i = new Intent(MapAppActivity.this,Browse.class);
        			MapAppActivity.this.startActivity(i);
        		}
        });
        
    }
}