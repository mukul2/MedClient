package com.winkcoo.medx.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.winkcoo.medx.R;

public class CodeVerificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verification);
    }

    public void OpenHomeActivity(View view) {
        startActivity(new Intent(this,SignUpActivity.class));
    }
}
