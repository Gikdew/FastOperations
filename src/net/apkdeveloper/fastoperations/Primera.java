package net.apkdeveloper.fastoperations;

import net.apkdeveloper.fastoperations.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class Primera extends Activity {
	
	protected void onCreate(Bundle savedInstanceState) {
		
	 	super.onCreate(savedInstanceState);
	 	requestWindowFeature(Window.FEATURE_NO_TITLE);
	 	setContentView(R.layout.home);
		
		Intent myIntent = new Intent(getApplicationContext(), Home.class);
		myIntent.putExtra("activity",0);
        Primera.this.startActivity(myIntent);
        Primera.this.finish();
	}
	

}
