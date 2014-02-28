package net.apkdeveloper.fastoperations;
import java.util.ArrayList;

import net.apkdeveloper.fastoperations.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;

public class LevelSelector extends Activity{
	
	public static final int w = 0;
	ArrayList<Integer> levelsString = new ArrayList<Integer>();	
	
	@SuppressLint("UseValueOf")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	 	setContentView(R.layout.levels);
	 	DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int h = metrics.heightPixels;
        int w = metrics.widthPixels;
        
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
        
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
        

        RelativeLayout.LayoutParams params;
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativLevels);
        rl.setBackgroundResource(R.drawable.background);
        SharedPreferences levelsInfo = getSharedPreferences("levels", Context.MODE_PRIVATE);
        Editor editor = levelsInfo.edit();
        
        final Integer[] puntosNecesarios = new Integer[] { 0, 15, 30, 36, 52 ,45, 54, 56, 56, 54};

        int c = 1;		
        for(int i = 1; i < 11; i++){
        	if(levelsInfo.getInt("level1", 0)==-1){
        		editor.putInt("level1", 0);       		
        		editor.commit();
        	}
        	if(levelsInfo.contains("level" + Integer.toString(i))){
        		levelsString.add(levelsInfo.getInt("level" + Integer.toString(i), -1));
        	}else {
        		if(c == 1){
        			editor.putInt("level1", 0);       		
            		editor.commit();
        		}else{
        			editor.putInt("level" + Integer.toString(i), -1);       		
            		editor.commit();
        		}       		
			}
        	c++;
        }	
        
        int counter = 0;
        for(int i = 0; i < 5; i++){
        	for(int j = 0; j < 2; j++){
        		final Button levelBtn = new Button(this);
                params = new RelativeLayout.LayoutParams((int)(w * 0.875/2), (int)(h *0.86/5));
                params.leftMargin = (int)(0.05*w+((0.025+(0.875/2))*w*(j)));
                params.topMargin = (int)(0.035*h+((0.006+(0.86/5))*h*(i)));
                
                if(levelsInfo.getInt("level" + Integer.toString(counter + 1), -1) > puntosNecesarios[counter] && levelsInfo.getInt("level" + Integer.toString(counter + 2), -1)  == -1){
                	editor.putInt("level" + Integer.toString(counter + 2), 0);       		
            		editor.commit();
                }
                
                if(levelsInfo.getInt("level" + Integer.toString(counter + 1), -1) == -1){
                	levelBtn.setText("Level " + new Integer(counter + 1).toString() + '\n' + "Needed: " + puntosNecesarios[counter].toString());
                	levelBtn.setBackgroundResource(R.drawable.button_flat_r);
                	levelBtn.setClickable(false);
                	levelBtn.setEnabled(false);
                }else{
                	levelBtn.setBackgroundResource(R.drawable.button_flat_g);
                	levelBtn.setText("Level " + new Integer(counter + 1).toString() +  '\n' + "Best: " + levelsInfo.getInt("level" + Integer.toString(counter + 1), 10));
                }
                
                levelBtn.setTextSize((float) (18*min_ratio));
                levelBtn.setTextColor(Color.WHITE);
                levelBtn.setTypeface(font);
                levelBtn.setId(counter);
                levelBtn.setOnClickListener(new OnClickListener() {					
					@Override
					public void onClick(View v) {
						Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
						myIntent.putExtra("dificultad", 0); //Optional parameters
		            	myIntent.putExtra("level", levelBtn.getId()+1); //Optional parameters
		                LevelSelector.this.startActivity(myIntent);
		                LevelSelector.this.finish();						
					}
				});                
                rl.addView(levelBtn, params);
                counter ++;               
                
        	}
        }
        
        
        
	}
	
    public void onBackPressed(){


            	Intent myIntent = new Intent(LevelSelector.this, Home.class);
        		myIntent.putExtra("activity",1);
            	LevelSelector.this.finish();
                LevelSelector.this.startActivity(myIntent);           	

   	
    }
}
