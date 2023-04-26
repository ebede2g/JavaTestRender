package com.example.oseg2;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class DemoRenderer implements GLSurfaceView.Renderer {


    static int duConst =-1;
    static float x_rot=0;
    static float y_rot=0;
    static float z_rot=0;





    private Object_cube1 cube1 = new Object_cube1();
    private Object_cube2 cube2 = new Object_cube2();
    private Object_tunel tunel = new Object_tunel();


    private static float tick_rot;
    private static float tick_duConst;
    private static float tick;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        // Depth buffer setup.
        gl.glClearDepthf(1.0f);
        // Enables depth testing.
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // The type of depth testing to do.
        gl.glDepthFunc(GL10.GL_LEQUAL);
        // Really nice perspective calculations.
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
        gl.glLoadIdentity();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, tick_duConst *0.14f);
        gl.glTranslatef(0.0f, 0.81f, 0.0f);
        gl.glRotatef(tick_rot, 0.0f, 0.0f, 1.0f);
        tunel.draw(gl);

        gl.glLoadIdentity();
        gl.glTranslatef(1.0f, 0.0f, -13f);
        gl.glRotatef(7* tick, 1.0f, 0.4f, 1.0f);
        cube2.draw(gl);

        tick_duConst -= duConst*0.15f;
        tick -= 0.15f;
        tick_rot+=x_rot;



    }




}