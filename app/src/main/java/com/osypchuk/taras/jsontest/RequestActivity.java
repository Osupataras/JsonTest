package com.osypchuk.taras.jsontest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import info.androidhive.jsonparsing.R;

/**
 * Created by Taras on 15.11.2016.
 */

public class RequestActivity extends Activity implements View.OnClickListener{

    EditText editText;
    Button okeybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acc);
        editText = (EditText) findViewById(R.id.acc);
        okeybtn = (Button) findViewById(R.id.okey);
        okeybtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("acc",editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();

    }
}
