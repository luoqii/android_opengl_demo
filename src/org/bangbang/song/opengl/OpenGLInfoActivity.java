package org.bangbang.song.opengl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.example.org.bangbang.song.opengl.R;

public class OpenGLInfoActivity extends Activity {
    private static final String TAG = OpenGLInfoActivity.class.getSimpleName();
    private TextView mText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        mText = (TextView)findViewById(R.id.text);
        
        final ActivityManager activityManager = 
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = 
                activityManager.getDeviceConfigurationInfo();
        final int version = configurationInfo.reqGlEsVersion;
        int major = version>> 16 & 0xffff;
        int minor = version & 0xffff;
        String vHex = Integer.toHexString(version);
        CharSequence versionStr = "gl verison: " + version
                + "[0x" + vHex + "]"
                + "\nmajor: " + major + " minor: " + minor;
        mText.setText(versionStr);
        
//        String version = javax.microedition.khronos.opengles.GL10.glGetString(
//                GL10.GL_VERSION);
    }
}
