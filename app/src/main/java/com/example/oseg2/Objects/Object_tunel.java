package com.example.oseg2.Objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import javax.microedition.khronos.opengles.GL10;

public class Object_tunel {
    public Object_tunel() {
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

        cirq_vertices();

    }

    private static FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;



    public float[] cirq_vertices(){

        float[] vert = new float[16 * 2 * 3];

        for(int i = 0;i<16;i+=1){
            double ang = (i*Math.PI/8);
            vert[6*i]= (float) Math.cos(ang);
            vert[6*i+1]= (float) Math.sin(ang);
            vert[6*i+2]=0;

            vert[6*i+3]= (float) Math.cos(ang);
            vert[6*i+4]= (float) Math.sin(ang);
            vert[6*i+5]=-150;
        }


        return vert;
    }
    public float[] cirq_colors(){

        float[] color = new float[16 * 2 * 4];

        for(int i = 0;i<16;i+=1){
            double ang = (i*Math.PI/8);
            color[8*i+0]= (float) 0.3;
            color[8*i+1]= (float) Math.sin(4+ang);
            color[8*i+2]= (float) 0.3;
            color[8*i+3]= (float) Math.cos(2*ang);

            color[8*i+4]= (float) 0.1;
            color[8*i+5]= (float) Math.sin(3*ang);
            color[8*i+6]= (float) ((float) Math.sin(5*ang)*Math.cos(ang));
            color[8*i+7]= (float) Math.cos(0.2*ang);
        }

        return color;
    }
    public byte[] cirq_vindices(){

        byte[] ind = new byte[16 * 2 * 3];

        for(int i = 0;i<30;i+=1){
            ind[3*i+0]= (byte) (i);
            ind[3*i+1]= (byte) (i+1);
            ind[3*i+2]= (byte) (i+2);
        }

        ind[90] = 30;   ind[91] = 31;   ind[92] = 0;
                        ind[93] = 31;   ind[94] = 0;   ind[95] = 1;

        return ind;
    }

    private float vertices[] = cirq_vertices();
    private float colors[] = cirq_colors();
    private byte[] indices = cirq_vindices();



    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CW);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawElements(GL10.GL_TRIANGLES, 96, GL10.GL_UNSIGNED_BYTE, mIndexBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }


}
