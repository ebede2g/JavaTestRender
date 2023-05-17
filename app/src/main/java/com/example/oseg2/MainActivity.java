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
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


    private SensorManager sensorManager;
    Sensor accelerometer;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new DemoRenderer());
        setContentView(view);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        sensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); //Тип
        sensorManager.registerListener((SensorEventListener) MainActivity.this,accelerometer,sensorManager.SENSOR_DELAY_GAME);
        Log.e("main","Запущено он креейт І створено акселерометр");

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
        DemoRenderer.x_rot=-sensorEvent.values[1]; //враховуємо орієнтацю екрану
    }
}