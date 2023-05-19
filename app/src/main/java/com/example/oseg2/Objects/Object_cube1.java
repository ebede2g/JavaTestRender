package com.example.oseg2.Objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Object_cube1 {
    public static int position = -4;
    private static FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;

    private float vertices[] = {
            (float) (0.1+Math.cos(position * Math.PI / 8)),         0.1f+(float) Math.sin(position * Math.PI / 8),       0f,
            (float) (-0.1+Math.cos((position + 1) * Math.PI / 8)),   0.1f+(float) Math.sin((position + 1) * Math.PI / 8), 0f,
            (float) (0.1+Math.cos(position * Math.PI / 8)),         0.1f+(float) Math.sin(position * Math.PI / 8),       -1f,
            (float) (-0.1+Math.cos((position + 1) * Math.PI / 8)),   (0.1f+(float) Math.sin((position + 1) * Math.PI / 8)), -1f,

            (float) (0.1+-0.1+Math.cos(position * Math.PI / 8)),         -0.1f+(float) (0.39+Math.sin(position * Math.PI / 8)),       0f,
            (float) (-0.1+-0.1+Math.cos((position + 1) * Math.PI / 8)),  -0.1f+ (float) (0.39+Math.sin((position + 1) * Math.PI / 8)), 0f,
            (float) (0.1+-0.1+Math.cos(position * Math.PI / 8)),         -0.1f+(float) (0.39+Math.sin(position * Math.PI / 8)),       -1f,
            (float) (-0.1+-0.1+Math.cos((position + 1) * Math.PI / 8)),  -0.1f+ (float) (0.39+Math.sin((position + 1) * Math.PI / 8)), -1f,
    };
    private float colors[] = {
            0.0f,  1.0f,  0.0f,  1.0f,
            0.0f,  1.0f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.5f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            1.0f,  0.0f,  0.0f,  1.0f,
            0.0f,  0.0f,  1.0f,  1.0f,
            1.0f,  0.0f,  1.0f,  1.0f
    };

    private byte indices[] = {
            0, 1, 2, 1, 2, 3,
            1, 5, 3, 5, 3, 7,
            7, 6, 3, 2, 6, 3,
            2, 6, 4, 2, 4, 0,
            4, 0, 1, 4, 1, 5,
            6, 7, 5, 6, 4, 5
    };

    public Object_cube1() {
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

    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CW);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}