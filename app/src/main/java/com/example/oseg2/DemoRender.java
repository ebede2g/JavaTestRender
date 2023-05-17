package com.example.oseg2;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import com.example.oseg2.Objects.Object_triangle_Obstacle;
import com.example.oseg2.Objects.Object_tunel;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class DemoRenderer implements GLSurfaceView.Renderer {


    static int duConst = -1;
    static float x_rot = 0;


    private Object_tunel tunel = new Object_tunel();
    private Object_triangle_Obstacle triangle_obstacle = new Object_triangle_Obstacle();


    private static float scaleFctor = 1;
    private static float speed = 0.0521f;


    private static int obs_counter = 0;
    private static float tick_rot;
    private static float tick;
    private static float deadline;

    private static int next_tick_of_obs=-10;


    private void createObs(GL10 gl, Integer obs_dist, Integer obs_pos) {
        if (Math.signum(obs_dist + 1 + tick) == 1) {
            gl.glLoadIdentity();
            gl.glTranslatef(0.0f, 0.81f, -(obs_dist + tick));
            gl.glRotatef((float) (22.5 * obs_pos + tick_rot), 0.0f, 0.0f, 1.0f);
            triangle_obstacle.draw(gl);

            deadline = (float) Math.sqrt(Math.pow((obs_dist + 0.5 + tick), 2) +
                    Math.pow(Math.cos((2 * Math.PI / 360) * ((22.5 * obs_pos + tick_rot + 371.25) % 360)) - 1, 2));
            if (deadline < 0.06) {
                speed = 0;
            }
        }
    }

    {int min_loop_1_type = 3;
        int max_loop_1_type = 12;
        int min_loop_2_type = 2;
        int max_loop_2_type = 7;
        int min_rand_3_type = 6;
        int max_rand_3_type = 3;} // еонстанти
    private void randomGenObs(GL10 gl){
        if(tick<next_tick_of_obs) {
            switch (randNum(1,3)){
                case (1): {
                    int start_pos = randNum(0,15);
                    for(int gen = start_pos;gen<randNum(start_pos+3,start_pos+12);gen++){
                        createObs(gl,next_tick_of_obs,gen);
                    }
                }
            }


            next_tick_of_obs = -randNum(-2,2)-(int) ((int) 24 + 16 * Math.sin(Math.sin(Math.pow(-tick, 1.1) - 2 * -tick))) + next_tick_of_obs; //майбутні координати перешкоди
            Log.d("beb","next = "+next_tick_of_obs);
        }
    }

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
        gl.glTranslatef(0.0f, 0.81f, -tick);
        gl.glRotatef(tick_rot, 0.0f, 0.0f, 1.0f);
        tunel.draw(gl);


        createObs(gl, 10, 0);
        createObs(gl, 20, 0);
        createObs(gl, 30, 0);
        createObs(gl, 40, 0);




        //Log.d("beb","tick = "+tick);

        tick += duConst * speed;
        tick_rot += x_rot;

        randomGenObs(gl);

    }


    public static int randNum(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

}