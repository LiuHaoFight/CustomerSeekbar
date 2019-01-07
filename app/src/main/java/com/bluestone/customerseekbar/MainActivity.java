package com.bluestone.customerseekbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    CustomerSeekbar mSeekbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekbar = findViewById(R.id.customerseekbar);
        mSeekbar.setMax(20);
        mSeekbar.setOnProgressChangeListener(new OnProgressChangeListener());
        mSeekbar.setProgress(10);
    }

    public class OnProgressChangeListener implements CustomerSeekbar.IOnProgressChangeListener {

        @Override
        public void onProgressChanged(int progress, boolean byUser) {
            Log.i(TAG, "onProgressChanged " + progress + " " + byUser);
        }
    }
}
