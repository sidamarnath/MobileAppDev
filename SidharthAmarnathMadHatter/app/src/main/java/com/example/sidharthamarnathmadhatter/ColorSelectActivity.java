package com.example.sidharthamarnathmadhatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ColorSelectActivity extends AppCompatActivity {

    public static final String COLOR = "edu.msu.cse.cse476.COLOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_select);
    }

    public void selectColor(int color) {
        Intent intent = new Intent(getApplicationContext(), HatterActivity.class);
        intent.putExtra(COLOR, color);
        setResult(HatterActivity.RESULT_OK, intent);
        finish();

    }
}