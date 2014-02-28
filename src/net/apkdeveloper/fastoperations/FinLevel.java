package net.apkdeveloper.fastoperations;


import com.google.ads.*;

import net.apkdeveloper.fastoperations.R;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class FinLevel extends Activity {
	
    private AdView adView;
	Button level_b, puntuacion_b, menu_b, repetir_b, elegirLevel_b, desbloqueo_b, nuevorecord_b;
	Integer puntuacion=0, level=0;

	
	 protected void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
		 	requestWindowFeature(Window.FEATURE_NO_TITLE);
		 	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
		 	
		 	setContentView(R.layout.fin_level);
		 	
	        Bundle bundle = getIntent().getExtras();
	        puntuacion = bundle.getInt("puntuacion");
	        level = bundle.getInt("level"); 
	        Integer record = bundle.getInt("record");
	        Integer desbloqueo = bundle.getInt("desbloqueo");
	        
		 	DisplayMetrics metrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        float h = metrics.heightPixels;
	        float w = metrics.widthPixels;
	        
	        final Double ratio_h =  (h/800.000);
	        final Double ratio_w =  (w/480.000);
	        
	       
	        Double ratio1= 0.0;
	        Double ratio2= 0.0;
	        		
	        if(ratio_h<ratio_w){
	        	ratio2=ratio_h;
	        	ratio1=ratio_w;
	        }
	        else {
	        	ratio2=ratio_w;
	        	ratio1=ratio_h;
	        }
	        
	        
	       final Double ratio=  ratio1;
	       final Double min_ratio=  ratio2;
	       
	       
	        RelativeLayout.LayoutParams params;
	        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativFinLevel);  
	        rl.setBackgroundResource(R.drawable.background);
	        
	        AdView adView = (AdView)this.findViewById(R.id.adView);
	        AdRequest adRequest = new AdRequest();
	        adView.loadAd(adRequest);
	             
	        CountDownTimer countdown = new CountDownTimer(1200, 100) {				
				@Override
				public void onTick(long millisUntilFinished) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFinish() {
					menu_b.setEnabled(true);
					elegirLevel_b.setEnabled(true);
					repetir_b.setEnabled(true);
					
				}
			};
	        
			countdown.start();
			
	        level_b = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.80), (int)(h * 0.10));
	        params.leftMargin = (int)(w*0.1);
	        params.topMargin = (int)(h*0.08);
	        level_b.setBackgroundResource(R.drawable.button_flat_a);
	        level_b.setTextColor(Color.WHITE);
	        level_b.setTextSize((float) (25*min_ratio));
	        level_b.setTypeface(font);
	        rl.addView(level_b, params); 
	        level_b.setText("Level "+ level.toString());
	        
	        puntuacion_b = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.70), (int)(h * 0.08));
	        params.leftMargin = (int)(w*0.15);
	        params.topMargin = (int)(h*0.22);
	        puntuacion_b.setBackgroundResource(R.drawable.button_flat_y);
	        puntuacion_b.setTypeface(font);
	        puntuacion_b.setTextColor(Color.WHITE);
	        puntuacion_b.setTextSize((float) (24*min_ratio));
	        rl.addView(puntuacion_b, params);
	        puntuacion_b.setText("Score: "+puntuacion.toString());
	        
	        menu_b = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.80), (int)(h * 0.08));
	        params.leftMargin = (int)(w*0.1);
	        params.topMargin = (int)(h*0.58);
	        menu_b.setBackgroundResource(R.drawable.button_flat_c);
	        menu_b.setTypeface(font);
	        menu_b.setTextColor(Color.WHITE);
	        menu_b.setTextSize((float) (24*min_ratio));
	        rl.addView(menu_b, params);
	        menu_b.setText("Menu");
	        
	        repetir_b = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.80), (int)(h * 0.08));
	        params.leftMargin = (int)(w*0.1);
	        params.topMargin = (int)(h*0.67);
	        repetir_b.setBackgroundResource(R.drawable.button_flat_c);
	        repetir_b.setTypeface(font);
	        repetir_b.setTextColor(Color.WHITE);
	        repetir_b.setTextSize((float) (24*min_ratio));
	        rl.addView(repetir_b, params);
	        repetir_b.setText("Retry Level");
	        
	        elegirLevel_b = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.80), (int)(h * 0.08));
	        params.leftMargin = (int)(w*0.1);
	        params.topMargin = (int)(h*0.76);
	        elegirLevel_b.setBackgroundResource(R.drawable.button_flat_c);
	        elegirLevel_b.setTypeface(font);
	        elegirLevel_b.setTextColor(Color.WHITE);
	        elegirLevel_b.setTextSize((float) (24*min_ratio));
	        rl.addView(elegirLevel_b, params);
	        elegirLevel_b.setText("Select Level");
	        
			menu_b.setEnabled(false);
			level_b.setEnabled(false);
			elegirLevel_b.setEnabled(false);
			puntuacion_b.setEnabled(false);
	        
	        if(record==1){
	        nuevorecord_b = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.70), (int)(h * 0.08));
	        params.leftMargin = (int)(w*0.15);
	        params.topMargin = (int)(h*0.34);
	        nuevorecord_b.setTextSize((float) (28*min_ratio));
	        nuevorecord_b.setBackgroundResource(R.drawable.button_flat_y);
	        rl.addView(nuevorecord_b, params);
	        nuevorecord_b.setText("New Record!");
	        nuevorecord_b.setTextColor(Color.WHITE);
	        nuevorecord_b.setEnabled(false);
	        }
	       level++;
	      if(desbloqueo==1){
	        desbloqueo_b = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.70), (int)(h * 0.08));
	        params.leftMargin = (int)(w*0.15);
	        params.topMargin = (int)(h*0.46);
	        desbloqueo_b.setBackgroundResource(R.drawable.button_flat_y);
	        desbloqueo_b.setTextSize((float) (17*min_ratio));
	        rl.addView(desbloqueo_b, params);
	        desbloqueo_b.setTextColor(Color.WHITE);
	       desbloqueo_b.setText("You have Unlocked Level "+ level.toString());
	       nuevorecord_b.setEnabled(false);
	      }
	       
	       
	        menu_b.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	Intent myIntent = new Intent(FinLevel.this, Home.class);
	            	myIntent.putExtra("activity", 1);
	                FinLevel.this.startActivity(myIntent);
	                FinLevel.this.finish();
	            }
	        });
	        
	        elegirLevel_b.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	Intent myIntent = new Intent(FinLevel.this, LevelSelector.class);
	            	
	                FinLevel.this.startActivity(myIntent);
	                FinLevel.this.finish();
	            }
	        });
	        
	        repetir_b.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	level-=1;
	            	Intent myIntent = new Intent(FinLevel.this, MainActivity.class);
	            	myIntent.putExtra("dificultad", 0); //Optional parameters
	            	myIntent.putExtra("level", level);
	            	FinLevel.this.startActivity(myIntent);
	            	FinLevel.this.finish();
	            }
	        });
		 	
	 }
	 
	 public void onBackPressed() {
		  Intent myIntent = new Intent(FinLevel.this, LevelSelector.class);
		        FinLevel.this.finish();
		        FinLevel.this.startActivity(myIntent);
		 };
		 
}
