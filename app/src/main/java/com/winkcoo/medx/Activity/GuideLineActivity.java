package com.winkcoo.medx.Activity;

import android.os.Bundle;
import android.view.View;

import com.winkcoo.medx.R;

public class GuideLineActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_line);
    }

    public void back(View view) {
        onBackPressed();
    }
}
