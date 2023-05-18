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
    private static float speed = 0.151f;


    private static int obs_counter = 0;
    private static float tick_rot;
    private static float tick;
    private static float deadline;

    private static final int[] que_tick = {-10,-40,-70,-100};
    QueRandData[] queue = {new QueRandData(), new QueRandData(), new QueRandData()};

    int teta = 70;                              //відстань між перешкодами


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

    private void draw_typeObs(GL10 gl,QueRandData queue){
        switch (queue.que) {
            case (1): {
                for (int gen = queue.que_pos; gen < queue.que_amnt_t1; gen++) {
                    createObs(gl, -queue.que_tick, gen);
                }
            }
            case (2): {
                for (int gen = queue.que_pos; gen < queue.que_amnt_t2; gen++) {
                    createObs(gl, -queue.que_tick, gen);
                }
                for (int gen = queue.que_pos + queue.que_amnt_t2 + 2; gen < queue.que_amnt_t2 * 2 + 1; gen++) {
                    createObs(gl, -queue.que_tick, gen);
                }
            }
            case (3): {
                for (int gen = queue.que_pos; gen < queue.que_amnt_t3 * 2; gen += 2) {
                    createObs(gl, -queue.que_tick, gen);
                }
            }
            default: ;
        }
    }


    private void setQueTick(GL10 gl,int beta){
        if (tick < que_tick[beta] - 1) {
            que_tick[beta] =(int) (-randNum(-5, 5) -( teta-que_tick[beta]+tick + 16 * Math.sin((tick* Math.sin(tick/1500))/150)+6*Math.sin(tick)*Math.sin(tick))) + que_tick[beta];
            //майбутні координати перешкоди
            Log.d("beb","que1tick = "+que_tick[beta]);
            queue[beta].setRand(que_tick[beta]);
        }
        draw_typeObs(gl, queue[beta]);
    }

    private void randomGenObs(GL10 gl) {
        setQueTick(gl,0);
        setQueTick(gl,1);
        setQueTick(gl,2);
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
        gl.glTranslatef(0.0f, 0.81f, 0f);
        gl.glRotatef(tick_rot, 0.0f, 0.0f, 1.0f);
        tunel.draw(gl);

        tick += duConst * speed;
        tick_rot += x_rot;

        randomGenObs(gl);

    }


    public static int randNum(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

}