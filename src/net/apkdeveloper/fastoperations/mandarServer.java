package net.apkdeveloper.fastoperations;

import org.json.JSONArray;
import org.json.JSONObject;

import net.apkdeveloper.fastoperations.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class mandarServer extends Activity {
	
	Integer puntuacion=0,i=0, puntuacion1=0, puntuacion2=0, puntuacion3=0;
	String nombre="",s="2",t="3",z="BB";
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		 Intent myIntent = new Intent(mandarServer.this, Home.class);
         myIntent.putExtra("activity", 1);
         mandarServer.this.finish();
         mandarServer.this.startActivity(myIntent);
	}
	
	 protected void onCreate(Bundle savedInstanceState) {
		 	super.onCreate(savedInstanceState);
		 	requestWindowFeature(Window.FEATURE_NO_TITLE);
		 	
		 	setContentView(R.layout.mandar_server);
		 	
		 	DisplayMetrics metrics = new DisplayMetrics();
	        getWindowManager().getDefaultDisplay().getMetrics(metrics);
	        Double h = (double) metrics.heightPixels;
	        final Double w = (double) metrics.widthPixels;      
	        
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
	        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativMandar);  
	        rl.setBackgroundResource(R.drawable.background);

	        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");
	        
	        final SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
	        String nombre = settings.getString("username", "Unknown");
	        
	        if(settings.getString("username","Unknown").equals("Unknown") || !settings.contains("username")){
	        	//alertDialog.show();
	        	Intent myIntent = new Intent(getApplicationContext(), createUsername.class);
	        	myIntent.putExtra("pantalla", 2);
                mandarServer.this.startActivity(myIntent);
                mandarServer.this.finish();
                
	        }
	        
	        final SharedPreferences difiRecords = getSharedPreferences("difiRecords", Context.MODE_PRIVATE);
	        puntuacion1 = difiRecords.getInt("easyRecord", 0);
	        puntuacion2 = difiRecords.getInt("mediumRecord", 0);
	        puntuacion3 = difiRecords.getInt("hardRecord", 0);
	        
	        if(puntuacion1>puntuacion2 && puntuacion1>puntuacion3)
	        	puntuacion=puntuacion1;
	        else{
	        	if(puntuacion2>puntuacion3)
	        		puntuacion=puntuacion2;
	        	else puntuacion=puntuacion3;
	        }
		 	
	        
	        RestClient client = new RestClient("http://apkdeveloper.net/operaciones/questions.php?s="+s.toString());


  		   try {
  		       client.Execute(RestClient.RequestMethod.GET);
  		   } catch (Exception e) {
  		       //e.printStackTrace();
  			  
  		   }

  		   String response = client.getResponse();
  		   
  		   
 		   try {
 			   
				
				
 	            JSONObject jObject=new JSONObject(response);
 	            JSONArray players = new JSONArray(jObject.getString("questions"));
 	            
 	            //if(players.getJSONObject(0).getString("Jugador").toString() == ""){
 	           
 	            i=players.getJSONObject(0).getString("Jugador").toString().length();
 	            	//desbloqueo_b.setText(i.toString());
 	            //}
 	            
 		      } catch (Exception e) {
 		            //e.printStackTrace();
 		    	  
 		            
 		        }
	        
 		   if(i==7){   
 			  
 			   
 			   try {
 	  		       client.Execute(RestClient.RequestMethod.GET);
 	  		       
 	  		   } catch (Exception e) {
 	  		       //e.printStackTrace();
 	  			  
 	  		   }
 			   
 			   	s="5";
 			   
 			   client = new RestClient("http://apkdeveloper.net/operaciones/questions.php?s="+s.toString()+"&j="+nombre.toString()+"&puntos="+puntuacion);
 			   
 			  
 			   
 			   try {
 	  		       client.Execute(RestClient.RequestMethod.GET);
 	  		       
 	  		   } catch (Exception e) {
 	  		       //e.printStackTrace();
 	  			  
 	  		   }
 			   
 			   s="3";
 			  
 			  RestClient client2 = new RestClient("http://apkdeveloper.net/operaciones/questions.php?s="+s.toString()+"&j="+z.toString());
 			 // i=6;
 			 s="6";
 			 RestClient client3 = new RestClient("http://apkdeveloper.net/operaciones/questions.php?s="+s.toString()+"&puntos="+puntuacion);
 			   
 			   
 			  try {
				client2.Execute(RestClient.RequestMethod.GET);
				client3.Execute(RestClient.RequestMethod.GET);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
 			  
 			  response = client2.getResponse();
 			String response2 = client3.getResponse();
 			   
 			   try {
 	 			   
 					
 					
 	 	            JSONObject jObject=new JSONObject(response);
 	 	            JSONArray players = new JSONArray(jObject.getString("questions"));
 	 	            
 	 	            JSONObject jObject2=new JSONObject(response2);
 	 	            JSONArray players2 = new JSONArray(jObject2.getString("questions"));
 	 	            
 	 	            String pos_aux="";
					String punt_aux="100000";
 	 	            
 	 	            //if(players.getJSONObject(0).getString("Jugador").toString() == ""){
 	 	           for(Integer j=1;j<12;j++){
 	 	        	   
 	 	        	   if(j<11){
 	 	        		   //if(players.getJSONObject(j-1).getString("Usuario").toString()!=null || players.getJSONObject(j-1).getString("A").toString()!=null){
 	 	        	   Button b = new Button(this);
 	 	                params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.06));
 	 	                params.leftMargin = (int)(w * 0.10);
 	 	                params.topMargin = (int)(h * ((j-1) * 0.07)+(0.05*h));
 	 	                b.setBackgroundResource(R.drawable.button_flat_c);
	 	 	            b.setTextColor(Color.WHITE);
	 	 	            b.setTypeface(font);
	 	 	            b.setEnabled(false);
	 	 	            b.setTextSize((float) (18*min_ratio));
 	 	                if(players.getJSONObject(j-1).getString("Puntoss").toString().equals(punt_aux.toString())){
 	 	                	pos_aux=" ";
 	 	                } else {punt_aux=players.getJSONObject(j-1).getString("Puntoss").toString();
 	 	                pos_aux=j.toString();
 	 	                b.setTextSize((float) (18*min_ratio));
 	 	                }
 	 	                b.setText(pos_aux);
 	 	                rl.addView(b, params);
 	 	                
  	 	        	   b = new Button(this);
	 	                params = new RelativeLayout.LayoutParams((int)(w * 0.46), (int)(h * 0.06));
	 	                params.leftMargin = (int)(w * 0.22);
	 	                params.topMargin = (int)(h * ((j-1) * 0.07)+(0.05*h));
	 	                b.setBackgroundResource(R.drawable.button_flat_c);
	 	 	            b.setTextColor(Color.WHITE);
	 	 	            b.setTypeface(font);
	 	 	            b.setEnabled(false);
	 	 	            b.setTextSize((float) (18*min_ratio));
	 	 	            b.setText(players.getJSONObject(j-1).getString("Usuario").toString());
	 	                rl.addView(b, params);
	 	                
	  	 	        	   b = new Button(this);
		 	                params = new RelativeLayout.LayoutParams((int)(w * 0.29), (int)(h * 0.06));
		 	                params.leftMargin = (int)(w * 0.61);
		 	                params.topMargin = (int)(h * ((j-1) * 0.07)+(0.05*h));
		 	                b.setBackgroundResource(R.drawable.button_flat_c);
		 	 	            b.setTextColor(Color.WHITE);
		 	 	            b.setTypeface(font);
		 	 	            b.setEnabled(false);
		 	 	          b.setTextSize((float) (18*min_ratio));
		 	 	          b.setText(punt_aux);
		 	                rl.addView(b, params);
 	 	        		   }
 	 	        	   //}
 	 	        	   else{ 
 	 	        		   
 	 	        		   
 	 	        		 Button b = new Button(this);
	 	 	                params = new RelativeLayout.LayoutParams((int)(w * 0.15), (int)(h * 0.06));
	 	 	                params.leftMargin = (int)(w * 0.10);
	 	 	                params.topMargin = (int)(h * ((j-1) * 0.07)+(0.05*h));
	 	 	                b.setBackgroundResource(R.drawable.button_flat_r);
		 	 	            b.setTextColor(Color.WHITE);
		 	 	            b.setText(players2.getJSONObject(0).getString("A").toString());
		 	 	            b.setTypeface(font);
		 	 	            b.setEnabled(false);
		 	 	            b.setTextSize((float) (18*min_ratio));
		 	 	            rl.addView(b, params);
		 	 	            
	  	 	        	   b = new Button(this);
		 	                params = new RelativeLayout.LayoutParams((int)(w * 0.46), (int)(h * 0.06));
		 	                params.leftMargin = (int)(w * 0.22);
		 	                params.topMargin = (int)(h * ((j-1) * 0.07)+(0.05*h));
		 	                b.setBackgroundResource(R.drawable.button_flat_r);
		 	 	            b.setTextColor(Color.WHITE);
		 	 	            b.setTypeface(font);
		 	 	            b.setEnabled(false);
		 	 	            b.setTextSize((float) (18*min_ratio));
		 	 	            b.setText(nombre.toString());
		 	                rl.addView(b, params);
		 	                
	  	 	        	   b = new Button(this);
		 	                params = new RelativeLayout.LayoutParams((int)(w * 0.29), (int)(h * 0.06));
		 	                params.leftMargin = (int)(w * 0.61);
		 	                params.topMargin = (int)(h * ((j-1) * 0.07)+(0.05*h));
		 	                b.setBackgroundResource(R.drawable.button_flat_r);
		 	 	            b.setTextColor(Color.WHITE);
		 	 	            b.setTypeface(font);
		 	 	            b.setEnabled(false);
		 	 	            b.setTextSize((float) (18*min_ratio));
		 	 	            b.setText(puntuacion.toString());
		 	                rl.addView(b, params);
	 	        		   

 	 	        	   }
 	 	                
 	 	                
 	 	        	   
 	 	           }
 	 	            //i=players.getJSONObject(0).getString("Jugador").toString().length();
 	 	            	//desbloqueo_b.setText(i.toString());
 	 	            //}
 	 	            
 	 		      } catch (Exception e) {
 	 		            //e.printStackTrace();
 	 		    	  
 	 		            
 	 		        }
 			   Button b = new Button(this);
               params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.08));
               params.leftMargin = (int)(w * 0.1);
               params.topMargin = (int)(h * (0.82));
               b.setBackgroundResource(R.drawable.button_flat_g);
               b.setTextSize((float) (25*min_ratio));
               b.setText("Menu");
               b.setTextColor(Color.WHITE);
               b.setTypeface(font);
               rl.addView(b, params);
               
              
    	       b.setOnClickListener(new View.OnClickListener() {
   	            @Override
   	            public void onClick(View view) {
   	            	Intent myIntent = new Intent(mandarServer.this, Home.class);
   	           		myIntent.putExtra("activity",1);
   	           	mandarServer.this.finish();
   	         mandarServer.this.startActivity(myIntent);  
   	            }
   	        });

             	

      	
       
 			   
	        	   
 		   }else{
 			   
 			  Toast.makeText(getApplicationContext(), "Internet Connection Not Detected", Toast.LENGTH_SHORT).show();
 			   
 			   Button b = new Button(this);
                params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.08));
                params.leftMargin = (int)(w * 0.1);
                params.topMargin = (int)(h * (0.1));
                b.setBackgroundResource(R.drawable.button_flat_g);
                b.setText("Menu");
                rl.addView(b, params);
                
    	       b.setOnClickListener(new View.OnClickListener() {
    	            @Override
    	            public void onClick(View view) {
    	            	Intent myIntent = new Intent(mandarServer.this, Home.class);
    	        		myIntent.putExtra("activity",1);
    	            	mandarServer.this.startActivity(myIntent);
    	            	mandarServer.this.finish();
    	            }
    	        });
 		   }
	 
	 }

}
