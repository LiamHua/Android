package com.example.practice_01.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.practice_01.R;

public class user extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
    }

    public void exit(View view) {
        Intent intent = new Intent(this, login.class);
        startActivity(intent);
    }

    public void to_personal_info(View view) {
        Intent intent = new Intent(this, Personal_info.class);
        startActivity(intent);
    }
}
