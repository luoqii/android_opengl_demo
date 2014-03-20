
package org.bangbang.song.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import org.bangbang.song.android.commonlib.GLUtil;
import org.bangbang.song.android.commonlib.LogRender;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.GLWrapper;
import android.os.Bundle;

public class TriangleActivity extends Activity {
    private static final String TAG = TriangleActivity.class.getSimpleName();
    private GLSurfaceView mGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setRenderer(new TriangleRender());
        mGLSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        setContentView(mGLSurfaceView);
        
//        mGLSurfaceView.setGLWrapper(new GLWrapper() {
//            
//            @Override
//            public GL wrap(GL gl) {
//                // TODO Auto-generated method stub
//                return null;
//            }
//        });
        mGLSurfaceView.requestRender();
        
        mGLSurfaceView.post(new Requester(mGLSurfaceView));
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        mGLSurfaceView.onResume();     
        mGLSurfaceView.requestRender();
    }
     @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mGLSurfaceView.onPause();
    }
    
    static void logError(GL10 gl, String methodName) {
            GLUtil.logError(gl, TAG, methodName);
    }

    static class Triangle{
            
            private static final int BYTES_PER_FLOAT = 4;
            private FloatBuffer mVertexBuffer;
            private IntBuffer mColorBuffer;
            private ShortBuffer mIndexrBuffer;
         // number of coordinates per vertex in this array
            static final int COORDS_PER_VERTEX = 3;
            static float sTriangleCoords[] = {   // in counterclockwise order:
                     0.0f,  0.622008459f, 0.0f, // top
                    -0.5f, -0.311004243f, 0.0f, // bottom left
                     0.5f, -0.311004243f, 0.0f  // bottom right
            };
    
            // Set scolor with red, green, blue and alpha (opacity) values
            static final int COLORS_PER_VERTEX = 4;
            static int scolor[] = { 
                0, 0, 100000, 1, 
                0, 100000, 0, 0, 
                100000, 0, 0, 0, 
                };
            //XXX how to use float color???
            static float scolorF[] = { 
                0.2f, 0.2f, 0.2f, 1.0f, 
                0.2f, 0.2f, 0.2f, 1.0f, 
                0.2f, 0.2f, 0.2f, 1.0f, 
                };
            
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
                mColorBuffer = cbb.asIntBuffer();
                mColorBuffer.put(scolor);
                mColorBuffer.position(0);
            }
            
           void draw(GL10 gl) {
                gl.glEnable(GL10.GL_VERTEX_ARRAY);
                logError(gl, "glEnable(GL10.GL_VERTEX_ARRAY)");
                gl.glEnable(GL10.GL_FLAT);
                logError(gl, "glEnable(GL10.GL_FLAT)");
                
                gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                logError(gl, "glEnableClientState(GL10.GL_VERTEX_ARRAY)");
                gl.glVertexPointer(COORDS_PER_VERTEX, GL10.GL_FLOAT, 0, mVertexBuffer);
                logError(gl, "glVertexPointer()");
                
                gl.glEnable(GL10.GL_SMOOTH);
                logError(gl, "glEnable(GL10.GL_SMOOTH)");
                gl.glShadeModel(GL10.GL_SMOOTH);
                logError(gl, "glEnable(GL10.GL_FLAT)");
                gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
                logError(gl, "glEnableClientState(GL10.GL_COLOR_ARRAY)");
                // XXX why GL10.GL_FIXED
                gl.glColorPointer(COLORS_PER_VERTEX, GL10.GL_FIXED, 0, mColorBuffer);
                logError(gl, "glColorPointer()");
//                gl.glColor4f(0f, 1f, 1f, 1);
                
                gl.glDrawElements(GL10.GL_TRIANGLES, 3, GL10.GL_UNSIGNED_SHORT, mIndexrBuffer);
                logError(gl, "glDrawElements()");
                //            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
            }
           
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
    
    class Requester implements Runnable {
        
        private static final int REQUEST_RENDER_DELAY_MILLIS = 10 * 1000;
        private GLSurfaceView mGlSurfaceView;

        public Requester(GLSurfaceView view){
            mGlSurfaceView = view;
        }
        
        @Override
        public void run() {
            mGLSurfaceView.requestRender();
            
            mGLSurfaceView.postDelayed(this, REQUEST_RENDER_DELAY_MILLIS);
        }
    }
}
