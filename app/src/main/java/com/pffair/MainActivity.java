package com.pffair;

import com.xiaomi.mipush.sdk.MiPushClient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;

    private EditText mEditText;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
    }

    private void initView() {
        mTextView = (TextView) findViewById(R.id.tv_registerId);
        mEditText = (EditText) findViewById(R.id.et_set_alias);
        mButton = (Button) findViewById(R.id.bt_confirm);
    }

    private void setListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiPushClient.setAlias(MainActivity.this, mEditText.getText().toString(), null);
            }
        });
    }

}
