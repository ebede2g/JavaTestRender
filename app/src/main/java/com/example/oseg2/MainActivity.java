package com.example.oseg2;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.nfc.Tag;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener{



    private SensorManager sensorManager;
    Sensor accelerometer;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new DemoRenderer());
        setContentView(view);

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
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        System.out.println("x : "+sensorEvent.values[0]/*+"  y : "+sensorEvent.values[1]+"  z : "+sensorEvent.values[2]*/);

        DemoRenderer.x_rot=sensorEvent.values[0];
        DemoRenderer.y_rot=sensorEvent.values[1];
        DemoRenderer.z_rot=sensorEvent.values[2];

    }
}