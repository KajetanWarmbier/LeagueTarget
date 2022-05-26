package com.example.leaguetarget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener((view) -> {
            Intent intent = new Intent(MainActivity.this, SeriesActivity.class);
            startActivity(intent);
        });
    }
}