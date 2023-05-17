package com.example.oseg2;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

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
    private static float speed = 0.121f;


    private static int obs_counter = 0;
    private static float tick_rot;
    private static float tick;
    private static float deadline;

    private static int next_tick_of_obs = -10;


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


        int que1;int que1_pos;int que1_amnt_t1;int que1_amnt_t2;int que1_amnt_t3;

        int que2;int que2_pos;int que2_amnt_t1;int que2_amnt_t2;int que2_amnt_t3;



    private void randomGenObs(GL10 gl) {
        if (tick < next_tick_of_obs - 1) {
            next_tick_of_obs = -randNum(-2, 2) - (int) ((int) 24 + 16 * Math.sin(Math.sin(Math.pow(-tick, 1.1) - 2 * -tick))) + next_tick_of_obs; //майбутні координати перешкоди

            que1 = randNum(1, 3);
            que1_pos = randNum(0, 15);

            que1_amnt_t1 = randNum(que1_pos + 3, que1_pos + 12);

            que1_amnt_t2 = randNum(que1_pos + 2, que1_pos + 5);

            que1_amnt_t3 = randNum(que1_pos + 1, 8);



        }


        switch (que1) {
            case (1): {
                for (int gen = que1_pos; gen < que1_amnt_t1; gen++) {
                    createObs(gl, -next_tick_of_obs, gen);
                }
            }
            case (2): {
                for (int gen = que1_pos; gen < que1_amnt_t2; gen++) {
                    createObs(gl, -next_tick_of_obs, gen);
                }
                for (int gen = que1_pos + que1_amnt_t2 + 2; gen < que1_amnt_t2 * 2 + 1; gen++) {
                    createObs(gl, -next_tick_of_obs, gen);
                }
            }
            case (3): {
                for (int gen = que1_pos; gen < que1_amnt_t3 * 2; gen += 2) {
                    createObs(gl, -next_tick_of_obs, gen);
                }
            }
            default: ;
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

        tick += duConst * speed;
        tick_rot += x_rot;

        randomGenObs(gl);

    }


    public static int randNum(int min, int max) {
        int range = max - min + 1;
        return (int) (Math.random() * range) + min;
    }

}