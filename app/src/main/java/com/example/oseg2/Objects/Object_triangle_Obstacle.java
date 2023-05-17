package com.example.oseg2.Objects;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Object_triangle_Obstacle {

    public static int position = -4;
    public Object_triangle_Obstacle() {
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mVertexBuffer = byteBuf.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        byteBuf = ByteBuffer.allocateDirect(colors.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        mColorBuffer = byteBuf.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);


    }

    static FloatBuffer mVertexBuffer;
    FloatBuffer mColorBuffer;
    ByteBuffer mIndexBuffer;



    private float vertices[] = {
            (float) Math.cos(position * Math.PI / 8), (float) Math.sin(position * Math.PI / 8), 0f,
            (float) Math.cos((position + 1) * Math.PI / 8), (float) Math.sin((position + 1) * Math.PI / 8), 0f,
            (float) (0.5 * Math.cos((position + 0.5) * Math.PI / 8)), (float) (0.5 * Math.sin((position + 0.5) * Math.PI / 8)), 0f,

            (float) Math.cos(position * Math.PI / 8), (float) Math.sin(position * Math.PI / 8), -1f,
            (float) Math.cos((position + 1) * Math.PI / 8), (float) Math.sin((position + 1) * Math.PI / 8), -1f,
            (float) (0.5 * Math.cos((position + 0.5) * Math.PI / 8)), (float) (0.5 * Math.sin((position + 0.5) * Math.PI / 8)), -1f,
    };
    private float colors[] = {
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,

    };
    private byte[] indices = {
            0, 1, 2,
            1, 2, 4,
            4, 2, 5,
            2, 0, 3,

            3, 2, 5,
            3, 4, 5,
            4, 3, 0,
            4, 1, 0
    };


    public void draw(GL10 gl) {

        gl.glFrontFace(GL10.GL_CW);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLES, 24, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }


}
