package com.example.oseg2;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
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

public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    Sensor accelerometer;
    private GLSurfaceView glSurfaceView;
    private TextView scoreTextView;


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

        // Налаштування рендерера і прив'язка його до GLSurfaceView
        glSurfaceView.setRenderer(new DemoRenderer());

        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //Тип
        sensorManager.registerListener((SensorEventListener) GameActivity.this,accelerometer,sensorManager.SENSOR_DELAY_GAME);


        Button button_again = findViewById(R.id.button_again);
        Button button_to_menu = findViewById(R.id.button_to_menu);

        button_again.setVisibility(View.GONE);
        button_to_menu.setVisibility(View.GONE);

        button_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoRenderer.tick=0;
                DemoRenderer.speed=0.15f;
                button_again.setVisibility(View.GONE);
                button_to_menu.setVisibility(View.GONE);
                DemoRenderer.updateQueTick();   //еперезапис початквих позицій перешкод
            }
        });
        button_to_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                glSurfaceView.setRenderer(new DemoRenderer());
            }
        });


    }




    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction()==MotionEvent.ACTION_DOWN) {
            DemoRenderer.duConst *= -1;
            Log.e("main",DemoRenderer.duConst+" Main");

        }
        return true;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {  }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        scoreTextView.setText("Рахунок: " + (int) -DemoRenderer.tick);
        if(DemoRenderer.speed==0){
            Button button_again = findViewById(R.id.button_again);
            Button button_to_menu = findViewById(R.id.button_to_menu);

            button_again.setVisibility(View.VISIBLE);
            button_to_menu.setVisibility(View.VISIBLE);
        }


        DemoRenderer.x_rot=-sensorEvent.values[1]; //враховуємо орієнтацю екрану
    }
}