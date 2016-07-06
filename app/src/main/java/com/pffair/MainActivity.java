package com.pffair;

import com.xiaomi.mipush.sdk.MiPushClient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView=(TextView) findViewById(R.id.tv_registerId);
        MiPushClient.setAlias(MainActivity.this,"pffair",null);
    }
}
