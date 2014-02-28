package net.apkdeveloper.fastoperations;

import org.json.JSONArray;
import org.json.JSONObject;

import net.apkdeveloper.fastoperations.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class createUsername extends Activity {

    private UsernameValidator usernameValidator = new UsernameValidator();

    //Add the back button
    //Add a popup when the back buton is clicked and the username is not checked

    @
    Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.create_username);
        

        Bundle bundle = getIntent().getExtras();
        final Integer pantalla = bundle.getInt("pantalla");
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

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

        RelativeLayout.LayoutParams params;
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.RelativUsername);
        rl.setBackgroundResource(R.drawable.background);

        final SharedPreferences settings = getSharedPreferences("settings", Context.MODE_PRIVATE);
        final Editor editor = settings.edit();

        final EditText input =new EditText(this);
        input.setTextSize((float) (20*min_ratio));
        //sec.setBackgroundColor(Color.TRANSPARENT);
        params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.08));
        params.leftMargin = (int)(w *0.1);
        params.topMargin = (int)(h * 0.30);
        input.setText(settings.getString("username", "Unknown"));
        rl.addView(input, params);
        
        final Button textView;
        textView = new Button(this);
        params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.10));
        params.leftMargin = (int)(w*0.1);
        params.topMargin = (int)(h * 0.2);
        rl.addView(textView, params);
        textView.getBackground().setAlpha(0);
        textView.setText("Create User");
        textView.setTextSize((float) (28*min_ratio));
        textView.setTypeface(font);
        textView.setTextColor(Color.BLACK);

        final Button checkBtn;
        checkBtn = new Button(this);
        params = new RelativeLayout.LayoutParams((int)(w * 0.8), (int)(h * 0.08));
        params.leftMargin = (int)(w*0.1);
        params.topMargin = (int)(h * 0.4);
        rl.addView(checkBtn, params);
        checkBtn.setText("Check Availability");
        checkBtn.setTextSize( (float) (20*min_ratio) );
        checkBtn.setBackgroundResource(R.drawable.button_flat_g);
        checkBtn.setTypeface(font);
        checkBtn.setTextColor(Color.WHITE);

        final Button saveBtn = new Button(this);
        params = new RelativeLayout.LayoutParams((int)(w * 0.80), (int)(h * 0.08));
        params.leftMargin = (int)(w *0.1);
        params.topMargin = (int)(h * 0.49);
        rl.addView(saveBtn, params);
        saveBtn.setText("Save");
        saveBtn.setTextSize( (float) (20*min_ratio) );
        saveBtn.setBackgroundResource(R.drawable.button_flat_r);
        saveBtn.setEnabled(false);
        saveBtn.setTypeface(font);
        saveBtn.setTextColor(Color.WHITE);

        final Button laterBtn = new Button(this);
        params = new RelativeLayout.LayoutParams((int)(w * 0.80), (int)(h * 0.08));
        params.leftMargin = (int)(w * 0.1);
        params.topMargin = (int)(h * 0.58);
        rl.addView(laterBtn, params);
        laterBtn.setText("Later");
        laterBtn.setTextSize( (float) (20*min_ratio) );
        laterBtn.setTextColor(Color.WHITE);
        laterBtn.setBackgroundResource(R.drawable.button_flat_a);
        laterBtn.setTypeface(font);

        input.addTextChangedListener(new TextWatcher() {@
            Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
        		saveBtn.setBackgroundResource(R.drawable.button_flat_r);
                saveBtn.setEnabled(false);

            }@
            Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                int after) {
                // TODO Auto-generated method stub

            }@
            Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        checkBtn.setOnClickListener(new OnClickListener() {@
            Override
            public void onClick(View v) {
        	if(isOnline()){
                if (!input.getText().toString().equals("Unknown") && usernameValidator.validate(input.getText().toString())) {
                    Integer res = checkAvailable();
                    if (res == 3) {
                    	saveBtn.setBackgroundResource(R.drawable.button_flat_g);
                        saveBtn.setEnabled(true);
                    } else if (res == 2)
                        Toast.makeText(getApplicationContext(), "The name is already used. Try again!", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getApplicationContext(), "Internet Connection Not Detected", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getApplicationContext(), "Invalid name! Try again...", Toast.LENGTH_SHORT).show();

                }

            }else{
        		Toast.makeText(getApplicationContext(), "Internet Connection not Detected", Toast.LENGTH_SHORT).show();
        	}
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
        	}

            private boolean isOnline() {
            	ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo netInfo = cm.getActiveNetworkInfo();
			    if (netInfo != null && netInfo.isConnected()) {
			        return true;
			    }
			    return false;
            }

			private Integer checkAvailable() {
                // TODO Auto-generated method stub				
                Integer devolver = 1;

                String s = "2";
                Integer i = 0;

                String c = input.getText().toString();

                RestClient client = new RestClient("http://apkdeveloper.net/operaciones/questions.php?s=" + s.toString());

                try {
                    client.Execute(RestClient.RequestMethod.GET);
                } catch (Exception e) {
                    //e.printStackTrace();

                }

                String response = client.getResponse();

                try {

                    JSONObject jObject = new JSONObject(response);
                    JSONArray players = new JSONArray(jObject.getString("questions"));

                    //if(players.getJSONObject(0).getString("Jugador").toString() == ""){

                    i = players.getJSONObject(0).getString("Jugador").toString().length();
                    //desbloqueo_b.setText(i.toString());
                    //}

                } catch (Exception e) {
                    //e.printStackTrace();

                }

                if (i == 7) {

                    s = "4";

                    RestClient client2 = new RestClient("http://apkdeveloper.net/operaciones/questions.php?s=" + s.toString() + "&j=" + c.toString());

                    try {
                        client2.Execute(RestClient.RequestMethod.GET);
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                    response = client2.getResponse();

                    try {

                        JSONObject jObject = new JSONObject(response);
                        JSONArray players = new JSONArray(jObject.getString("questions"));

                        if (players.getJSONObject(0).getString("A").toString().equals("0")) {
                            devolver = 3;

                        } else devolver = 2;

                    } catch (Exception e) {
                        //e.printStackTrace();

                    }

                }

                return devolver;

            }
			
        });

        saveBtn.setOnClickListener(new OnClickListener() {@
            Override
            public void onClick(View v) {

                Integer i = 0;
                String s = "2";
                String c = input.getText().toString();

                RestClient client = new RestClient("http://apkdeveloper.net/operaciones/questions.php?s=" + s.toString());

                try {
                    client.Execute(RestClient.RequestMethod.GET);
                } catch (Exception e) {
                    //e.printStackTrace();

                }

                String response = client.getResponse();

                try {

                    JSONObject jObject = new JSONObject(response);
                    JSONArray players = new JSONArray(jObject.getString("questions"));

                    //if(players.getJSONObject(0).getString("Jugador").toString() == ""){

                    i = players.getJSONObject(0).getString("Jugador").toString().length();
                    //desbloqueo_b.setText(i.toString());
                    //}

                } catch (Exception e) {
                    //e.printStackTrace();

                }
                if (i == 7) {
                    s = "1";

                    client = new RestClient("http://apkdeveloper.net/operaciones/questions.php?s=" + s.toString() + "&j=" + c.toString());

                    try {
                        client.Execute(RestClient.RequestMethod.GET);

                    } catch (Exception e) {
                        //e.printStackTrace();

                    }

                    editor.putString("username", input.getText().toString());
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();

                    if (pantalla == 1) {
                        Intent myIntent = new Intent(getApplicationContext(), Home.class);
                        myIntent.putExtra("activity", 0);
                        createUsername.this.startActivity(myIntent);
                        createUsername.this.finish();
                    } else {
                        Intent myIntent = new Intent(getApplicationContext(), mandarServer.class);
                        createUsername.this.startActivity(myIntent);
                        createUsername.this.finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Internet Connection Not Detected", Toast.LENGTH_SHORT).show();
                }

            }
        });

        laterBtn.setOnClickListener(new OnClickListener() {

            @
            Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), Home.class);
                myIntent.putExtra("activity", 1);
                createUsername.this.startActivity(myIntent);
                createUsername.this.finish();
            }

        });

    }

}