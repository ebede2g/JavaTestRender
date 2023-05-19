package com.example.oseg2;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor accelerometer;
    private GLSurfaceView glSurfaceView;
    private TextView scoreTextView;
    private TextView extralifeTextView;

    private Button button_resume;
    private Button button_again;
    private Button button_to_menu;


    private MediaPlayer mediaPlayer;
    static Boolean paused = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        glSurfaceView = findViewById(R.id.gl_surface_view);
        scoreTextView = findViewById(R.id.score_textview);
        extralifeTextView = findViewById(R.id.extralife_textview);

        button_resume = findViewById(R.id.button_resume);
        button_again = findViewById(R.id.button_again);
        button_to_menu = findViewById(R.id.button_to_menu);


        // Налаштування рендерера і прив'язка його до GLSurfaceView
        glSurfaceView.setRenderer(new DemoRenderer());

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //Тип
        sensorManager.registerListener((SensorEventListener) GameActivity.this, accelerometer, sensorManager.SENSOR_DELAY_GAME);


        //Button button_again = findViewById(R.id.button_again);
        //Button button_to_menu = findViewById(R.id.button_to_menu);

        button_resume.setVisibility(View.GONE);
        button_again.setVisibility(View.GONE);
        button_to_menu.setVisibility(View.GONE);


        button_resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused = false;
                DemoRenderer.speed = 0.15f;
                button_resume.setVisibility(View.GONE);
                button_again.setVisibility(View.GONE);
                button_to_menu.setVisibility(View.GONE);

                mediaPlayer.start();
            }
        });
        button_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paused = false;
                DemoRenderer.tick = 0;
                DemoRenderer.speed = 0.15f;
                button_resume.setVisibility(View.GONE);
                button_again.setVisibility(View.GONE);
                button_to_menu.setVisibility(View.GONE);
                DemoRenderer.updateQueTick();   //еперезапис початквих позицій перешкод

                mediaPlayer.start();
                extralifeTextView.setTextColor(Color.parseColor("#FFFFFF"));
                scoreTextView.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
        button_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glSurfaceView.setRenderer(new DemoRenderer());
                mediaPlayer = null;
            }

        });



        mediaPlayer = MediaPlayer.create(this, R.raw.osegment_ost);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start(); // Відтворити музику
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause(); // Призупинити відтворення музики
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Звільнити ресурси MediaPlayer
            mediaPlayer = null;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        //if(e.getAction()==MotionEvent.ACTION_DOWN) {
        //    DemoRenderer.duConst *= -1;
        //    Log.e("beb",DemoRenderer.duConst+" Main");
        //}
        paused = true;
        DemoRenderer.speed = 0;

        button_resume.setVisibility(View.VISIBLE);
        button_again.setVisibility(View.VISIBLE);
        button_to_menu.setVisibility(View.VISIBLE);
        mediaPlayer.pause();

        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        scoreTextView.setText("Рахунок: " + (int) -DemoRenderer.tick);
        extralifeTextView.setText("Життя : " + DemoRenderer.extraLife);
        if (DemoRenderer.speed == 0 || paused) {

            button_again.setVisibility(View.VISIBLE);
            button_to_menu.setVisibility(View.VISIBLE);



            extralifeTextView.setTextColor(Color.parseColor("#7d007d"));
            scoreTextView.setTextColor(Color.parseColor("#7d007d"));


            mediaPlayer.pause();
        }


        DemoRenderer.x_rot = -sensorEvent.values[1]; //враховуємо орієнтацю екрану
    }
}