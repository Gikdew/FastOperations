package net.apkdeveloper.fastoperations;

import com.google.ads.*;

import java.util.Random;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import net.apkdeveloper.fastoperations.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    public Integer resultado = 0, level = 0, dificultad = 0, puntuacion1, puntuacion2, puntuacion3, puntuacion =0, c=0;
    public Integer resultado_bueno = 0;
    public Integer puntos = 0, puntos1 = 0, transcurrido=0,multiplicador=0;
    public Operacion numbers = null;
    public CountDownTimer countdown, startCountdown, countdownDialog;
    private AdView adView;


    @
    Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        dificultad = bundle.getInt("dificultad");
        level = bundle.getInt("level");
        
        final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        final int sound = soundPool.load(this, R.raw.sound1, 1);
        
        final Integer[] puntosNecesarios = new Integer[] { 0, 15, 30, 36, 52 ,45, 54, 56, 56, 54};

        final AlertDialog.Builder dialog = new AlertDialog.Builder(this)
            .setTitle("Online Highscore")
            .setMessage("Do you want to upload your highscore?" + '\n' + "Score: " + puntos.toString() + '\n' + "HighScore: " + puntuacion.toString())
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if(isOnline()){
                    	Intent myIntent = new Intent(MainActivity.this, mandarServer.class);
                    	MainActivity.this.finish();
                        MainActivity.this.startActivity(myIntent);
                    }else{
                    	 Toast.makeText(getApplicationContext(), "Internet Connection Not Detected", Toast.LENGTH_SHORT).show();
                    	 Intent myIntent = new Intent(MainActivity.this, Home.class);
                         myIntent.putExtra("activity", 0);
                         MainActivity.this.finish();
                         MainActivity.this.startActivity(myIntent);
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
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent myIntent = new Intent(MainActivity.this, Home.class);
                    myIntent.putExtra("activity", 0);
                    MainActivity.this.finish();
                    MainActivity.this.startActivity(myIntent);
                }
            });

        DisplayMetrics metrics = new DisplayMetrics();
        //getWindowManager().getDefaultDisplay().getMetrics(metrics);
        
        final Animation puntosAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.puntos_anim), 
        		puntosAnim2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.puntos_anim2);
        
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
        
        final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Lato-Regular.ttf");

        ((WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE))
            .getDefaultDisplay().getMetrics(metrics);

        //final float h2 = (float) metrics.xdpi;
        //final float w2 = (float) metrics.ydpi;
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
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.Relativ);
        rl.setBackgroundResource(R.drawable.background);
        
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest();
        adView.loadAd(adRequest);

        final SharedPreferences levelsInfo = getSharedPreferences("levels", Context.MODE_PRIVATE);
        final Editor editor = levelsInfo.edit();
        final SharedPreferences difiRecord = getSharedPreferences("difiRecords", Context.MODE_PRIVATE);
        final Editor editor1 = difiRecord.edit();

        final Button levelText = new Button(this);
        levelText.setBackgroundResource(R.drawable.button_flat_b);
        levelText.setTextColor(Color.WHITE);
        
        if(level != 0){
        	levelText.setText("Level " + level);
        }else{
        	switch (dificultad) {
			case 1:
				levelText.setText("Easy");
				break;
			case 2:
				levelText.setText("Medium");
				break;
			case 3:
				levelText.setText("Hard");
				break;
			
			}
        }

        //BOTONES
        final Button sec;
        sec = new Button(this);
        sec.setClickable(false);
        //sec.setBackgroundColor(Color.TRANSPARENT);
        //params = new RelativeLayout.LayoutParams((int)(w * 0.18), (int)(h * 0.11));
        params = new RelativeLayout.LayoutParams((int)(85*ratio), (int)(86.42*ratio));
        //params.leftMargin = (int)(w * 0.05);
        //params.topMargin = (int)(h * 0.035);
        params.leftMargin = (int)(24*ratio);
        params.topMargin = (int)(21*ratio);
        sec.setTextColor(Color.WHITE);
        sec.setTextSize((float) (21*min_ratio));
        sec.setTypeface(font);
        sec.setBackgroundResource(R.drawable.button_flat_p);        
        rl.addView(sec, params);
        
        //BOTONES
        final ImageButton sec1;
        sec1 = new ImageButton(this);
        sec1.setClickable(false);        
        params = new RelativeLayout.LayoutParams((int)(ratio*32), (int)(ratio*32));
        //params.leftMargin = (int)(w * 0.05);
        //params.topMargin = (int)(h * 0.035);
        params.leftMargin = (int)(24*ratio);
        params.topMargin = (int)(18*ratio);
        sec1.setBackgroundResource(R.drawable.ic_action_alarms);                
        rl.addView(sec1, params);

        //BOTONES
        final TextView startCountdownBt = new TextView(this);
        startCountdownBt.setBackgroundResource(R.drawable.button_flat_r);
        startCountdownBt.setGravity(Gravity.CENTER);
       // startCountdownBt.setTextSize(w/2);
        startCountdownBt.setTextSize((float) (300*min_ratio));
        startCountdownBt.setTypeface(font);
        startCountdownBt.setTextColor(Color.WHITE);
        params = new RelativeLayout.LayoutParams(metrics.widthPixels, metrics.heightPixels);
        
        
        //TEMPORIZADOR
        countdownDialog = new CountDownTimer(1000, 100) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish() {
				if(dificultad != 0){
					dialog.setMessage("Do you want to upload your highscore?" + '\n' + '\n'+ "Score: " + puntos.toString() + '\n' + "HighScore: " + puntuacion.toString());
					dialog.show();
					countdownDialog.cancel();
				}else{
					Intent myIntent = new Intent(MainActivity.this, FinLevel.class);
                    myIntent.putExtra("puntuacion", puntos); //Optional parameters
                    myIntent.putExtra("level", level);

                    SharedPreferences levelsInfo = getSharedPreferences("levels", Context.MODE_PRIVATE);
                    Editor editor = levelsInfo.edit();
                    if (puntos > levelsInfo.getInt("level" + Integer.toString(level), -1)) {
                        //Toast.makeText(getApplicationContext(), "PUNTOS level" + level.toString() + " :" + puntos.toString(), Toast.LENGTH_LONG).show();
                        editor.putInt("level" + Integer.toString(level), puntos);
                        editor.commit();
                        myIntent.putExtra("record", 1);
                    } else myIntent.putExtra("record", 0);
                    level++;
                    if(level!=11){
                    	if(levelsInfo.getInt("level" + Integer.toString(level), -1)==-1 && puntos>=puntosNecesarios[level-1]){
                    		editor.putInt("level" + Integer.toString(level), 0);
                    		editor.commit();
                    		myIntent.putExtra("desbloqueo", 1);
                    	}
                    	else myIntent.putExtra("desbloqueo", 0);
                    } else myIntent.putExtra("desbloqueo", 0);

                    MainActivity.this.finish();
                    MainActivity.this.startActivity(myIntent);
                    }
											
			}
		};
        countdown = new CountDownTimer(31000, 100) {

            public void onTick(long millisUntilFinished) {
                sec.setText("" + millisUntilFinished / 1000);
                if(millisUntilFinished%1000 < 100){
                	transcurrido++;
                }
                
            }
            public void onFinish() {
            	c=1;
            	sec.setBackgroundResource(R.drawable.button_flat_b);
            	countdown.cancel();
                
                if (dificultad != 0) {
                	countdownDialog.start();
                    switch (dificultad) {
                    case 1:
                        if (puntos > difiRecord.getInt("easyRecord", 0)) {
                            editor1.putInt("easyRecord", puntos);
                            editor1.commit();
                        }
                        break;
                    case 2:
                        if (puntos > difiRecord.getInt("mediumRecord", 0)) {
                            editor1.putInt("mediumRecord", puntos);
                            editor1.commit();
                        }
                        break;
                    case 3:
                        if (puntos > difiRecord.getInt("hardRecord", 0)) {
                            editor1.putInt("hardRecord", puntos);
                            editor1.commit();
                        }
                        break;
                    default:
                        break;
                    }

                    
                    
                    /*Intent myIntent = new Intent(MainActivity.this, mandarServer.class);
            	MainActivity.this.finish();
                MainActivity.this.startActivity(myIntent);*/
                } else {
                	countdownDialog.start();
                }
            }
        };
        
        
        //TEMPORIZADOR DE INICIO
        if (level == 0) {
            rl.addView(startCountdownBt, params);
            startCountdown = new CountDownTimer(4000, 100) {
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished < 1000) {
                        startCountdownBt.setText("Go!");
                        startCountdownBt.setTextSize((float) (160*min_ratio));
                    } else {
                        startCountdownBt.setText("" + millisUntilFinished / 1000);
                        
                    }

                }
                public void onFinish() {
                    startCountdownBt.setVisibility(View.GONE);
                    countdown.start();
                }
            }.start();
        } else {
            countdown.start();
        }

        //BOTONES
        final Button puntos_b;
        puntos_b = new Button(this);
        //puntos.setBackgroundColor(Color.TRANSPARENT);
       // params = new RelativeLayout.LayoutParams((int)(w*0.18), (int)(h * 0.11));
        params = new RelativeLayout.LayoutParams((int)(85*ratio), (int)(86.42*ratio));
       // params.leftMargin = (int)(w*0.77);
       // params.topMargin = (int)(h * 0.035);
        params.leftMargin = (int)(370.83*ratio);
        params.topMargin = (int)(21.36*ratio);
        puntos_b.setText(puntos.toString());        
        puntos_b.setTypeface(font);
        puntos_b.setTextColor(Color.WHITE);
        puntos_b.setTextSize((float) (21*min_ratio));
        puntos_b.setBackgroundResource(R.drawable.button_flat_p);
        puntos_b.setClickable(false);
        rl.addView(puntos_b, params);
        
        
        final Button puntos_b1;
        puntos_b1 = new Button(this);
        
        //params = new RelativeLayout.LayoutParams((int)(w*0.18), (int)(h * 0.11));
        params = new RelativeLayout.LayoutParams((int)(85*ratio), (int)(86.42*ratio));
        // params.leftMargin = (int)(w*0.77);
        // params.topMargin = (int)(h * 0.035);
         params.leftMargin = (int)(370.83*ratio);
         params.topMargin = (int)(21.36*ratio);       
        puntos_b1.setTypeface(font);
        puntos_b1.setTextColor(Color.WHITE);
       // puntos_b1.setTextSize(h/33);
        puntos_b1.setTextSize((float) (21*min_ratio));
        puntos_b1.setText("0");
        puntos_b1.setBackgroundResource(R.drawable.button_flat_p);
        puntos_b1.setClickable(false);
        rl.addView(puntos_b1, params);
        
        //BOTONES        
        //params = new RelativeLayout.LayoutParams((int)(w * 0.5), (int)(h * 0.11));
        params = new RelativeLayout.LayoutParams((int)(239*ratio), (int)(86.42*ratio));
       // params.leftMargin = (int)(w * 0.25);
       // params.topMargin = (int)(h * 0.035);
        params.leftMargin = (int)(120*ratio);
        params.topMargin = (int)(21*ratio);
        levelText.setTypeface(font);        
        //levelText.setTextSize(h/25);  
        levelText.setTextSize((float) (38*min_ratio));
        levelText.setClickable(false);
        rl.addView(levelText, params); 
        
        //BOTONES
        final Button operacion;
        operacion = new Button(this);
        //operacion.setBackgroundColor(Color.TRANSPARENT);
        //params = new RelativeLayout.LayoutParams((int)(w * 0.90), (int)(h * 0.14));
        params = new RelativeLayout.LayoutParams((int)(431.83*ratio), (int)(110*ratio));
        params.leftMargin = (int)(24*ratio);
        //params.leftMargin = (int)(w * 0.05);
       // params.topMargin = (int)(h * 0.155);
        params.topMargin = (int)(122*ratio);
        operacion.setClickable(false);
        operacion.setTypeface(font);
        operacion.setTextColor(Color.WHITE);
        //operacion.setTextSize(h/15);
        operacion.setTextSize((float) (47*min_ratio));
        operacion.setBackgroundResource(R.drawable.button_flat_a);
        rl.addView(operacion, params);  
        
        
        //BOTONES
        final Button resultado_b;
        resultado_b = new Button(this);
        //params = new RelativeLayout.LayoutParams((int)(w * 0.90), (int)(h*0.14));
        params = new RelativeLayout.LayoutParams((int)(431.83*ratio), (int)(110*ratio));
        params.leftMargin = (int)(24*ratio);
        //params.leftMargin = (int)(w * 0.05);
        params.topMargin = (int)(238*ratio);
       // params.topMargin = (int)(h * 0.305);
        rl.addView(resultado_b, params);
        resultado_b.setClickable(false);
        resultado_b.setBackgroundResource(R.drawable.button_flat_a);
        resultado_b.setTypeface(font);
        resultado_b.setTextColor(Color.WHITE);
        //resultado_b.setTextSize(h/15);   
        resultado_b.setTextSize((float) (47*min_ratio)); 
        
        //BOTONES
        final Button resultado_b1;
        resultado_b1 = new Button(this);
       // params = new RelativeLayout.LayoutParams((int)(w * 0.90), (int)(w*0.24));
        params = new RelativeLayout.LayoutParams((int)(431.83*ratio), (int)(110*ratio));
       // params.leftMargin = (int)(w * 0.05);
        params.leftMargin = (int)(24*ratio);
        params.topMargin = (int)(238*ratio);
        rl.addView(resultado_b1, params);
        resultado_b1.setClickable(false);
        resultado_b1.getBackground().setAlpha(0);
        resultado_b1.setTypeface(font);
        resultado_b1.setTextColor(Color.WHITE);
        //resultado_b1.setTextSize(h/15);      
        resultado_b1.setTextSize((float) (47*min_ratio));

        //CREACION CUADRO DE BOTONES
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                final Button num = new Button(this);
              //  params = new RelativeLayout.LayoutParams((int)(w * 0.85/3), (int)(h * 0.09));
                params = new RelativeLayout.LayoutParams((int)(408/3*ratio), (int)(65*ratio));
               // params.leftMargin = (int)(0.05*w+((0.025+(0.85/3))*w*(j-1)));
                params.leftMargin = (int)(24*ratio+148*ratio*(j-1));
                params.topMargin = (int)((358*ratio) +  (75*ratio)* i );
                num.setTypeface(font);
                num.setTextSize((float) (25*min_ratio));
                
                if (i < 3) {
                    num.setText(new Integer(i * 3 + j).toString());
                    num.setBackgroundResource(R.drawable.button_flat_c);
                    
                } else {

                    switch (j) {
                    case 1:
                        num.setText("<");
                        num.setBackgroundResource(R.drawable.button_flat_c);
                       
                        break;
                    case 2:
                        num.setText("0");
                        num.setBackgroundResource(R.drawable.button_flat_c);
                       
                        break;
                    case 3:
                        num.setText("New");
                        num.setBackgroundResource(R.drawable.button_flat_r);
                        
                       
                        break;
                    }
                }
                
                num.setTextColor(Color.WHITE);
                num.setId(i * 3 + j);
                num.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                    	
                        if (num.getId() < 10) {
                        	if(resultado < 2000 && c==0){
	                            resultado = resultado * 10 + num.getId();
	                            comprobarResultado();
                            }
                        } else {
                            switch (num.getId()) {
                            case 10:
                                resultado = resultado / 10;
                                comprobarResultado();
                                break;
                            case 11:
                                resultado = resultado * 10;
                                comprobarResultado();
                                break;
                            case 12:
                                if (dificultad != 0) {
                                    switch (dificultad) {
                                    case 1:
                                        puntos--;
                                        break;
                                    case 2:
                                        puntos -= 2;
                                        break;
                                    case 3:
                                        puntos -= 3;
                                        break;
                                    }
                                } else puntos--;
                                if (puntos < 0){
                                    puntos = 0;
                                    }
                                puntos_b.setText(puntos.toString());
                                resultado = 0;
                                numbers = generaOperacion(generaTipoOperacion(dificultad, level), dificultad, level);
                                operacion.setText(Integer.toString(numbers.getOperando1()) + " " + numbers.getOperador() + " " + Integer.toString(numbers.getOperando2()));
                                break;
                            }
                        }

                        if (resultado == 0) resultado_b.setText(" ");
                        else resultado_b.setText(resultado.toString());

                    }
                    private void comprobarResultado() {
                        if (resultadoOperacion(numbers.getOperando1(), numbers.getOperando2(), numbers.getOperador()) == resultado) {
                        	
                        	//soundPool.play(sound, 1.0f, 1.0f, 0, 0, 1.0f);
                        	
                        	resultado_b1.setText(resultado.toString());
                        	resultado_b1.startAnimation(puntosAnim);
                        	//resultado_b1.setTextColor(Color.parseColor("#27ae60"));
                        	
                        	//ESTE ES EL BOTON DE +PUNTUACION
                        	
                            if (dificultad != 0) {
                                switch (dificultad) {
                                case 1:
                                	multiplicador = 5-transcurrido;
                                	if(multiplicador<1)
                                		multiplicador=1;
                                    puntos=puntos+(multiplicador*100)+numbers.getOperando1()+numbers.getOperando2();
                                    break;
                                case 2:
                                	multiplicador = 5-transcurrido;
                                	if(multiplicador<1)
                                		multiplicador=1;
                                    puntos=puntos+(multiplicador*200)+numbers.getOperando1()+numbers.getOperando2();
                                    break;
                                case 3:
                                	multiplicador = 5-transcurrido;
                                	if(multiplicador<1)
                                		multiplicador=1;
                                    puntos=puntos+(multiplicador*400)+numbers.getOperando1()+numbers.getOperando2();
                                    break;
                                }
                               transcurrido=0;

                            } else puntos+=level;
                            puntos_b.setText(puntos.toString());
                            if(puntos > 999) {puntos_b.setTextSize((float) (17*min_ratio)); puntos_b1.setTextSize((float) (17*min_ratio));}
                            if(puntos > 10000) {puntos_b.setTextSize((float) (15*min_ratio)); puntos_b1.setTextSize((float) (15*min_ratio));}
                            if(puntos != puntos1){
                            	puntos_b1.startAnimation(puntosAnim2);
                            	puntos_b1.setText(puntos.toString());
                            	//puntos_b1.setText("+" + new Integer(puntos-puntos1).toString());
                            	puntos1 = puntos;
                            }
                        	
                            numbers = generaOperacion(generaTipoOperacion(dificultad, level), dificultad, level);
                            operacion.setText(Integer.toString(numbers.getOperando1()) + " " + numbers.getOperador() + " " + Integer.toString(numbers.getOperando2()));
                            resultado = 0;
                        }

                    }
                });

                rl.addView(num, params);
                
                //BOTON NEW
                final ImageButton newOp;
                newOp = new ImageButton(this);
                newOp.setClickable(false);     
                params = new RelativeLayout.LayoutParams((int)(ratio * 32), (int)(ratio*32));
                params.leftMargin = (int)(24*ratio+148*ratio*(2));
                params.topMargin = (int)((358*ratio) +  (75*ratio)* 3);
                newOp.setBackgroundResource(R.drawable.ic_action_refresh);                
                rl.addView(newOp, params);
                startCountdownBt.bringToFront();
            }
            
        }       
        numbers = generaOperacion(generaTipoOperacion(dificultad, level), dificultad, level);
        operacion.setText(Integer.toString(numbers.getOperando1()) + " " + numbers.getOperador() + " " + Integer.toString(numbers.getOperando2()));
    }

    public int randomNum(int min, int max) {
        Random rand = new Random();
        int random = (int) Math.floor(Math.random()*(max-min+1)+min);
        return random;
    }

    public Operacion generaOperacion(int aleatorio, int dificultad, int level) {
        int operando1 = 0;
        int operando2 = 0;
        Operacion op = null;
        switch (aleatorio) {

            //SUMA
        case 1:
            if (dificultad != 0) {

                switch (dificultad) {
                case 1:
                    operando1 = randomNum(1, 20);
                    operando2 = randomNum(1, 20);
                    break;
                case 2:
                    operando1 = randomNum(20, 100);
                    operando2 = randomNum(20, 100);
                    break;
                case 3:
                    operando1 = randomNum(100, 500);
                    operando2 = randomNum(100, 500);
                    break;
                }
            } else {
                switch (level) {
                case 1:
                    operando1 = randomNum(1, 10);
                    operando2 = randomNum(1, 10);
                    break;

                case 2:
                    operando1 = randomNum(10, 20);
                    operando2 = randomNum(10, 20);
                    break;

                case 3:
                    operando1 = randomNum(20, 40);
                    operando2 = randomNum(20, 40);
                    break;

                case 4:
                    operando1 = randomNum(20, 80);
                    operando2 = randomNum(20, 80);
                    break;

                case 5:
                    operando1 = randomNum(30, 150);
                    operando2 = randomNum(30, 150);
                    break;

                case 6:
                    operando1 = randomNum(40, 160);
                    operando2 = randomNum(40, 160);
                    break;

                case 7:
                    operando1 = randomNum(60, 180);
                    operando2 = randomNum(60, 180);
                    break;

                case 8:
                    operando1 = randomNum(80, 250);
                    operando2 = randomNum(80, 250);
                    break;

                case 9:
                    operando1 = randomNum(120, 300);
                    operando2 = randomNum(120, 300);
                    break;

                case 10:
                    operando1 = randomNum(200, 600);
                    operando2 = randomNum(200, 600);
                    break;
                }
            }

            op = new Operacion(operando1, operando2, '+');
            break;

            //RESTA
        case 2:
            if (dificultad != 0) {
                switch (dificultad) {
                case 1:
                    operando1 = randomNum(1, 20);
                    operando2 = randomNum(1, 20);
                    break;
                case 2:
                    operando1 = randomNum(20, 150);
                    operando2 = randomNum(20, 150);
                    break;
                case 3:
                    operando1 = randomNum(150, 600);
                    operando2 = randomNum(150, 600);
                    break;
                }
            } else {
                switch (level) {
                case 2:
                    operando1 = randomNum(1, 20);
                    operando2 = randomNum(1, 20);
                    break;

                case 3:
                    operando1 = randomNum(20, 40);
                    operando2 = randomNum(20, 40);
                    break;

                case 4:
                    operando1 = randomNum(20, 80);
                    operando2 = randomNum(20, 80);
                    break;

                case 5:
                    operando1 = randomNum(30, 150);
                    operando2 = randomNum(30, 150);
                    break;

                case 6:
                    operando1 = randomNum(40, 160);
                    operando2 = randomNum(40, 160);
                    break;

                case 7:
                    operando1 = randomNum(60, 180);
                    operando2 = randomNum(60, 180);
                    break;

                case 8:
                    operando1 = randomNum(80, 250);
                    operando2 = randomNum(80, 250);
                    break;

                case 9:
                    operando1 = randomNum(120, 300);
                    operando2 = randomNum(120, 300);
                    break;

                case 10:
                    operando1 = randomNum(200, 600);
                    operando2 = randomNum(200, 600);
                    break;
                }
            }
            if (operando1 > operando2) {
                op = new Operacion(operando1, operando2, '-');
            } else {
                op = new Operacion(operando2, operando1, '-');
            }
            break;

            //MULTIPLICACION
        case 3:
            if (dificultad != 0) {
                switch (dificultad) {
                case 2:
                    operando1 = randomNum(1, 10);
                    operando2 = randomNum(1, 10);
                    break;
                case 3:
                    operando1 = randomNum(10, 20);
                    operando2 = randomNum(5, 10);
                    break;
                }
            } else {
                switch (level) {
                case 4:
                    operando1 = randomNum(1, 10);
                    operando2 = randomNum(1, 10);
                    break;

                case 5:
                    operando1 = randomNum(1, 10);
                    operando2 = randomNum(1, 10);
                    break;

                case 6:
                    operando1 = randomNum(1, 10);
                    operando2 = randomNum(1, 10);
                    break;

                case 7:
                    operando1 = randomNum(5, 15);
                    operando2 = randomNum(5, 15);
                    break;

                case 8:
                    operando1 = randomNum(5, 15);
                    operando2 = randomNum(5, 10);
                    break;

                case 9:
                    operando1 = randomNum(10, 20);
                    operando2 = randomNum(5, 10);
                    break;

                case 10:
                    operando1 = randomNum(10, 20);
                    operando2 = randomNum(5, 10);
                    break;
                }

            }
            op = new Operacion(operando1, operando2, '*');
            break;

            //DIVISION
        case 4:
            if (dificultad != 0) {
                switch (dificultad) {
                case 3:
                    do {
                        operando1 = randomNum(1, 80);
                        operando2 = randomNum(2, 10);
                    } while (operando1 % operando2 != 0);
                }
            } else {
                switch (level) {
                case 6:
                    do {
                        operando1 = randomNum(1, 20);
                        operando2 = randomNum(2, 10);
                    } while (operando1 % operando2 != 0);

                    break;

                case 7:
                    do {
                        operando1 = randomNum(1, 30);
                        operando2 = randomNum(2, 10);
                    } while (operando1 % operando2 != 0);

                    break;

                case 8:
                    do {
                        operando1 = randomNum(1, 40);
                        operando2 = randomNum(2, 10);
                    } while (operando1 % operando2 != 0);
                    break;

                case 9:
                    do {
                        operando1 = randomNum(1, 60);
                        operando2 = randomNum(2, 10);
                    } while (operando1 % operando2 != 0);
                    break;

                case 10:
                    do {
                        operando1 = randomNum(1, 80);
                        operando2 = randomNum(2, 10);
                    } while (operando1 % operando2 != 0);
                    break;

                }
            }

            op = new Operacion(operando1, operando2, '/');
            break;

        }
        return op;

    }

    public boolean esPrimo(int n) {
        int a = 0, i;
        for (i = 1; i < (n + 1); i++) {
            if (n % i == 0) {
                a++;
            }
        }
        if (a != 2) {
            return false;
        } else {
            return true;
        }
    }
    public int generaTipoOperacion(int dificultad, int level) {

        int operacion = 0;
        if (dificultad != 0) {
            switch (dificultad) {
            case 1:
                operacion = randomNum(1, 2);
                break;
            case 2:
                operacion = randomNum(1, 3);
                break;
            case 3:
                operacion = randomNum(1, 4);
                break;
            }
        } else {
            //COMPLETAR LOS CASES DE LOS LEVELS
            if (level < 2)
                operacion = 1;
            else if (level < 4)
                operacion = randomNum(1, 2);
            else if (level < 6)
                operacion = randomNum(1, 3);
            else operacion = randomNum(1, 4);
        }
        return operacion;

    }

    public int resultadoOperacion(int operador1, int operador2, char c) {
        int result = 0;
        switch (c) {
        case '+':
            result = operador1 + operador2;
            break;
        case '-':
            result = operador1 - operador2;
            break;
        case '*':
            result = operador1 * operador2;
            break;
        case '/':
            result = operador1 / operador2;
            break;

        }
        return result;
    }

    @
    Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                	    if(dificultad!= 0 )startCountdown.cancel();
                        countdown.cancel();
                		countdownDialog.cancel();
                    Intent myIntent = new Intent(MainActivity.this, Home.class);
                    myIntent.putExtra("activity", 1);
                    MainActivity.this.finish();
                    MainActivity.this.startActivity(myIntent);
                }
            })
            .setNegativeButton("No", null)
            .show();
    }
}