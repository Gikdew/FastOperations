package net.apkdeveloper.fastoperations;

import com.google.*;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import net.apkdeveloper.fastoperations.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.Visibility;
import android.os.Bundle;
import android.text.Layout.Alignment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class Home extends Activity{
	Button startBtn, easyBtn, hardBtn, mediumBtn, levelBtn, scoresBtn, easyRecord, mediumRecord, hardRecord;	
	//
	
	@Override
	public void onBackPressed(){
		if(startBtn.getVisibility() == View.GONE){			
        	mediumBtn.startAnimation(animBounce3);
        	easyBtn.startAnimation(animBounce3);
        	hardBtn.startAnimation(animBounce3);
        	easyRecord.startAnimation(animBounce3);
        	mediumRecord.startAnimation(animBounce3);
        	hardRecord.startAnimation(animBounce3);        	
			startBtn.startAnimation(animBounce);
			levelBtn.startAnimation(animBounce);
			scoresBtn.startAnimation(animBounce);
			easyRecord.setVisibility(View.GONE);
			mediumRecord.setVisibility(View.GONE);
			hardRecord.setVisibility(View.GONE);
			easyBtn.setVisibility(View.GONE);
        	mediumBtn.setVisibility(View.GONE);
        	hardBtn.setVisibility(View.GONE);
        	startBtn.setVisibility(View.VISIBLE);
        	levelBtn.setVisibility(View.VISIBLE );
        	scoresBtn.setVisibility(View.VISIBLE);
		}else{
			finish();
		}
	}
	
	 Animation animBounce, animBounce1, animBounce2, animBounce3, puntos_anim1;
	 private AdView adView;
	 protected void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
		 	requestWindowFeature(Window.FEATURE_NO_TITLE);

		 	
		 	setContentView(R.layout.home);		 		
		 		    
		 	Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
		 	animBounce = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.bounce);
		 	animBounce1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce1);
		 	animBounce2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce2);
		 	animBounce3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce3);
		 	puntos_anim1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.puntos_anim1);
		 	DisplayMetrics metrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        Double h = (double) metrics.heightPixels;
	        Double w = (double) metrics.widthPixels;
	        
	        final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	        final int sound2 = soundPool.load(this, R.raw.sound2, 1);
	        
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
	        
	        
	        Integer mostrar=0;
	        Bundle bundle = getIntent().getExtras();
	        mostrar = bundle.getInt("activity");
	       
	        RelativeLayout.LayoutParams params;
	        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativHome); 
	        rl.setBackgroundResource(R.drawable.background);
	        
	        AdView adView = (AdView)this.findViewById(R.id.adView);
	        AdRequest adRequest = new AdRequest();
	        adView.loadAd(adRequest);
	        
			float btnWidth = (float) 0.8;
			float btnHeight = (float) 0.08;
			float initPosX = (float) 0.5;
			float initPosY = (float) 0.47;
	        
	        final SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
	        final SharedPreferences difiRecords = getSharedPreferences("difiRecords", Context.MODE_PRIVATE);
	        final Editor editor = settings.edit();	
	               
	        
	        //editor.remove("username");
	        //editor.commit();
	        
	        if(!settings.contains("username")){
	        	editor.putString("username", "Unknown");
	        }
	        
	        /*
	        
	        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);

            // Setting Dialog Title
            alertDialog.setTitle("Username");
            final EditText input = new EditText(Home.this);  
            input.setText(settings.getString("username", "Unknown"));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                  LinearLayout.LayoutParams.MATCH_PARENT,
                                  LinearLayout.LayoutParams.MATCH_PARENT);
            alertDialog.setCancelable(false);            
            input.setLayoutParams(lp);
            alertDialog.setView(input); 

            alertDialog.setMessage("Enter a Valid Username");
            
            alertDialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int which) {                        	
                        	if(usernameValidator.validate(input.getText().toString()) && !input.getText().toString().equals("Unknown")){
                        		editor.putString("username", input.getText().toString());
                        		editor.commit();
                        		Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();                      		                     		
                        	}else{
                        		Toast.makeText(getApplicationContext(), "Enter a valid username, Please!", Toast.LENGTH_LONG).show();                     		
                        	}                        	
                        }
                    });                  
            
	        */

	        if(mostrar==0){
	        if(settings.getString("username","Unknown").equals("Unknown") || !settings.contains("username")){
	        	//alertDialog.show();
	        	Intent myIntent = new Intent(getApplicationContext(), createUsername.class);
	        	myIntent.putExtra("pantalla", 1);
                Home.this.startActivity(myIntent);
                Home.this.finish();
                
	        } 
	        }
	        	        
	        
	        final ImageView logo =  new ImageView(this);
     		params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.4));
	        params.leftMargin = (int)(w*0.1);
	        params.topMargin = (int)(h*0.1);
	        rl.addView(logo, params);
	        logo.setImageResource(R.drawable.logo);
	        logo.startAnimation(puntos_anim1);
	        

	        //startBtn.startAnimation(animBounce1);
	        
	        
	        
	        startBtn = new Button(this);        
			params = new RelativeLayout.LayoutParams((int)(w * btnWidth), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*initPosX-(w*btnWidth/2));
	        params.topMargin = (int)(h*initPosY);
	        rl.addView(startBtn, params);
	        startBtn.setText("Start");
	        startBtn.setBackgroundResource(R.drawable.button_flat_c);
	        startBtn.setTextColor(Color.WHITE);
	        startBtn.setTypeface(font);
	        startBtn.setTextSize((float) (22*min_ratio));
	        //startBtn.startAnimation(animBounce1);
	        
	        levelBtn = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * btnWidth), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*initPosX-(w*btnWidth/2));
	        params.topMargin = (int)(h*(initPosY+btnHeight+0.01));
	        rl.addView(levelBtn, params);
	        levelBtn.setText("Levels");
	        levelBtn.setBackgroundResource(R.drawable.button_flat_c);
	        levelBtn.setTextColor(Color.WHITE);
	        levelBtn.setTypeface(font);
	        levelBtn.setTextSize((float) (22*min_ratio));
	        //levelBtn.startAnimation(animBounce1);
	        
	        scoresBtn = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * btnWidth), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*initPosX-(w*btnWidth/2));
	        params.topMargin = (int)(h*(initPosY+(btnHeight+0.01)*2));
	        rl.addView(scoresBtn, params);
	        scoresBtn.setText("Scores");
	        scoresBtn.setBackgroundResource(R.drawable.button_flat_c);
	        scoresBtn.setTextColor(Color.WHITE);
	        scoresBtn.setTypeface(font);
	        scoresBtn.setTextSize((float) (22*min_ratio));
	        //scoresBtn.startAnimation(animBounce1);
	        
	        easyBtn = new Button(this);	        
			params = new RelativeLayout.LayoutParams((int)(w * 0.6), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*0.1);
	        params.topMargin = (int)(h*initPosY);
	        rl.addView(easyBtn, params);
	        easyBtn.setText("Easy");
	        easyBtn.setVisibility(View.GONE);
	        easyBtn.setBackgroundResource(R.drawable.button_flat_c);
	        easyBtn.setTextColor(Color.WHITE);
	        easyBtn.setTypeface(font);
	        easyBtn.setTextSize((float) (22*min_ratio));
	        
	        mediumBtn = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.6), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*0.1);
	        params.topMargin = (int)(h*(initPosY+btnHeight+0.01));
	        rl.addView(mediumBtn, params);
	        mediumBtn.setText("Medium");
	        mediumBtn.setVisibility(View.GONE);
	        mediumBtn.setBackgroundResource(R.drawable.button_flat_c);
	        mediumBtn.setTextColor(Color.WHITE);
	        mediumBtn.setTypeface(font);
	        mediumBtn.setTextSize((float) (22*min_ratio));
	        
	        hardBtn = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.6), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*0.1);
	        params.topMargin = (int)(h*(initPosY+(btnHeight+0.01)*2));
	        rl.addView(hardBtn, params);	        
	        hardBtn.setText("Hard");
	        hardBtn.setVisibility(View.GONE);
	        hardBtn.setBackgroundResource(R.drawable.button_flat_c);
	        hardBtn.setTextColor(Color.WHITE);
	        hardBtn.setTypeface(font);
	        hardBtn.setTextSize((float) (22*min_ratio));
	        
	        easyRecord = new Button(this);	        
			params = new RelativeLayout.LayoutParams((int)(w * 0.25), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*0.65);
	        params.topMargin = (int)(h*initPosY);
	        rl.addView(easyRecord, params);
	        easyRecord.setVisibility(View.GONE);
	        easyRecord.setText("" + difiRecords.getInt("easyRecord", 0));	        
	        easyRecord.setBackgroundResource(R.drawable.button_flat_y);
	        easyRecord.setTextColor(Color.WHITE);
	        easyRecord.setTypeface(font);
	        easyRecord.setTextSize((float) (18*min_ratio));
	        easyRecord.setClickable(false);
	        	        
	        mediumRecord = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.25), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*0.65);
	        params.topMargin = (int)(h*(initPosY+btnHeight+0.01));
	        rl.addView(mediumRecord, params);
	        mediumRecord.setVisibility(View.GONE);
	        mediumRecord.setText("" + difiRecords.getInt("mediumRecord", 0));	        
	        mediumRecord.setBackgroundResource(R.drawable.button_flat_y);
	        mediumRecord.setTextColor(Color.WHITE);
	        mediumRecord.setTypeface(font);
	        mediumRecord.setTextSize((float) (18*min_ratio));
	        mediumRecord.setClickable(false);
	        
	        hardRecord = new Button(this);	        
	        params = new RelativeLayout.LayoutParams((int)(w * 0.25), (int)(h * btnHeight));
	        params.leftMargin = (int)(w*0.65);
	        params.topMargin = (int)(h*(initPosY+(btnHeight+0.01)*2));
	        rl.addView(hardRecord, params);
	        hardRecord.setVisibility(View.GONE);
	        hardRecord.setText("" + difiRecords.getInt("hardRecord", 0));	        
	        hardRecord.setBackgroundResource(R.drawable.button_flat_y);
	        hardRecord.setTextColor(Color.WHITE);
	        hardRecord.setTypeface(font);
	        hardRecord.setTextSize((float) (18*min_ratio));
	        hardRecord.setClickable(false);
	        
	        
	        startBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	
	            	//soundPool.play(sound2, 1.0f, 1.0f, 0, 0, 1.0f);
	            	startBtn.startAnimation(animBounce2);
	            	levelBtn.startAnimation(animBounce2);
	            	scoresBtn.startAnimation(animBounce2);	            	
	            	mediumBtn.startAnimation(animBounce1);
	            	hardBtn.startAnimation(animBounce1);
	            	easyBtn.startAnimation(animBounce1);
	            	easyRecord.startAnimation(animBounce1);
	            	mediumRecord.startAnimation(animBounce1);
	            	hardRecord.startAnimation(animBounce1);
	            	easyRecord.setVisibility(View.VISIBLE);
	            	mediumRecord.setVisibility(View.VISIBLE);
	            	hardRecord.setVisibility(View.VISIBLE);
	            	easyBtn.setVisibility(View.VISIBLE);
	            	mediumBtn.setVisibility(View.VISIBLE);
	            	hardBtn.setVisibility(View.VISIBLE);
	            	startBtn.setVisibility(View.GONE);
	            	levelBtn.setVisibility(View.GONE);
	            	scoresBtn.setVisibility(View.GONE);
	            	
	            	
	            }
	        });
	        
	        easyBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	//soundPool.play(sound2, 1.0f, 1.0f, 0, 0, 1.0f);
	            	Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
	            	myIntent.putExtra("dificultad", 1); //Optional parameters
	            	myIntent.putExtra("level", 0);
	                Home.this.startActivity(myIntent);
	                Home.this.finish();
	                
	            }
	        });
	        
	        mediumBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	//soundPool.play(sound2, 1.0f, 1.0f, 0, 0, 1.0f);
	            	Intent myIntent = new Intent(Home.this, MainActivity.class);
	            	myIntent.putExtra("dificultad", 2); //Optional parameters
	            	myIntent.putExtra("level", 0);
	                Home.this.startActivity(myIntent);
	                Home.this.finish();
	            }
	        });
	        
	        hardBtn.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View view) {
	            	//soundPool.play(sound2, 1.0f, 1.0f, 0, 0, 1.0f);
	            	Intent myIntent = new Intent(Home.this, MainActivity.class);
	            	myIntent.putExtra("dificultad", 3); //Optional parameters
	            	myIntent.putExtra("level", 0);
	            	
	                Home.this.startActivity(myIntent);
	                
	                Home.this.finish();
	                
	            }
	        });
	        
	        levelBtn.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					//soundPool.play(sound2, 1.0f, 1.0f, 0, 0, 1.0f);
					Intent myIntent = new Intent(Home.this, LevelSelector.class);
					Home.this.startActivity(myIntent);
					Home.this.finish();			
				}
			});
	        
	        scoresBtn.setOnClickListener(new View.OnClickListener() {				
				@Override
				public void onClick(View v) {
					if(isOnline()){
						//soundPool.play(sound2, 1.0f, 1.0f, 0, 0, 1.0f);
						scoresBtn.setText("Loading...");						
						Intent myIntent = new Intent(Home.this, mandarServer.class);
						Home.this.startActivity(myIntent);
						Home.this.finish();
					}else{
						Toast.makeText(getApplicationContext(), "Internet Connection Not Detected", Toast.LENGTH_SHORT).show();
					}
					
	                				
				}

				private boolean isOnline() {
					ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				    NetworkInfo netInfo = cm.getActiveNetworkInfo();
				    if (netInfo != null && netInfo.isConnected()) {
				        return true;
				    }
				    return false;
				}
			});	        
	        
	        
	    }	
}
