package com.example.oseg2;

/* GraphicGlDemoActivity.java
 * Author: Yong Bakos
 * Since: 11/26/2012
 * Thanks to:
 * Cube: http://intransitione.com/blog/create-a-spinning-cube-with-opengl-es-and-android/
 * OpenGL Boilerplate: http://www.jayway.com/2009/12/03/opengl-es-tutorial-for-android-part-i/
 */

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends Activity {




    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        GLSurfaceView view = new GLSurfaceView(this);
        view.setRenderer(new DemoRenderer());
        setContentView(view);
        Log.e("main","Запущено он креейт");
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if(e.getAction()==MotionEvent.ACTION_DOWN) {
            DemoRenderer.duConst *= -1;
            Log.e("main",DemoRenderer.duConst+" Main");


        }
        return true;
    }





}