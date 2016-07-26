package com.lzp.surface.surfaceviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AbsoluteLayout;

import com.lzp.surface.surfaceviewtest.test.mSurfaceview;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AbsoluteLayout main = (AbsoluteLayout) findViewById(R.id.activity_main);


        mSurfaceview m = new mSurfaceview(this,true);
        AbsoluteLayout.LayoutParams layoutParams = new AbsoluteLayout.LayoutParams(500,800,50,50);
        m.setLayoutParams(layoutParams);
        main.addView(m);
        Thread t = new Thread(m);
        t.start();



    }
}
