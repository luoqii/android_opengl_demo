
package org.bangbang.song.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import org.bangbang.song.android.commonlib.LogRender;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

public class TriangleActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        GLSurfaceView v = new GLSurfaceView(this);
        v.setRenderer(new TriangleRender());
        setContentView(v);
    }
    
    static class TriangleRender  extends LogRender  {
        
        private Triangle mTriangle;

        public TriangleRender(){
            mTriangle = new Triangle();
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            super.onSurfaceCreated(gl, config);
            gl.glClearColor(1f, 0, 0, 0);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            super.onSurfaceChanged(gl, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            super.onDrawFrame(gl);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT);     
            
            mTriangle.draw(gl);
        }
        
    }
    
    static class Triangle{
        
        private static final int BYTES_PER_FLOAT = 4;
        private FloatBuffer mVertexBuffer;
        private FloatBuffer mColorBuffer;
        private ShortBuffer mIndexrBuffer;
     // number of coordinates per vertex in this array
        static final int COORDS_PER_VERTEX = 3;
        static float sTriangleCoords[] = {   // in counterclockwise order:
                 0.0f,  0.622008459f, 0.0f, // top
                -0.5f, -0.311004243f, 0.0f, // bottom left
                 0.5f, -0.311004243f, 0.0f  // bottom right
        };

        // Set scolor with red, green, blue and alpha (opacity) values
        static float scolor[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
        
        public Triangle() {
            // initialize vertex byte buffer for shape coordinates
            ByteBuffer vbb = ByteBuffer.allocateDirect(
                    // (number of coordinate values * 4 bytes per float)
                    sTriangleCoords.length * BYTES_PER_FLOAT);
            // use the device hardware's native byte order
            vbb.order(ByteOrder.nativeOrder());

            // create a floating point buffer from the ByteBuffer
            mVertexBuffer = vbb.asFloatBuffer();
            // add the coordinates to the FloatBuffer
            mVertexBuffer.put(sTriangleCoords);
            // set the buffer to read the first coordinate
            mVertexBuffer.position(0);
            
            ByteBuffer ibb = ByteBuffer.allocateDirect(scolor.length * BYTES_PER_FLOAT);
            ibb.order(ByteOrder.nativeOrder());
            mIndexrBuffer = ibb.asShortBuffer();
            mIndexrBuffer.put((short) 0);
            mIndexrBuffer.put((short) 1);
            mIndexrBuffer.put((short) 2);
            mIndexrBuffer.position(0);
            
            ByteBuffer cbb = ByteBuffer.allocateDirect(scolor.length * BYTES_PER_FLOAT);
            cbb.order(ByteOrder.nativeOrder());
            mColorBuffer = cbb.asFloatBuffer();
            mColorBuffer.put(scolor);
            mColorBuffer.position(0);
        }
        
       void draw(GL10 gl) {
            gl.glEnable(GL10.GL_VERTEX_ARRAY);
            gl.glVertexPointer(COORDS_PER_VERTEX, GL10.GL_FLOAT, 0, mVertexBuffer);
//            gl.glEnable(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(1, GL10.GL_UNSIGNED_BYTE, 0, mColorBuffer);
//            gl.glColor4f(0f, 1f, 0f, 1);
            gl.glDrawElements(GL10.GL_TRIANGLES, 3, GL10.GL_UNSIGNED_SHORT, mIndexrBuffer);
//            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
        }
    }
}
