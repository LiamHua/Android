package com.example.practice_01.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.practice_01.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double rowweight = 1.0;
    }

    public void info(View view) {
        Intent intent = new Intent(this, user.class);
        startActivity(intent);
    }

    public void add_course(View view) {
        Intent intent = new Intent(this, add_course.class);
        startActivity(intent);
    }
}
