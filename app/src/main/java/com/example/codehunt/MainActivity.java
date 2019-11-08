package com.example.codehunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        int SPLASH_TIMEOUT = 1000;
        new Handler().postDelayed(() -> {
            Intent i = new Intent(MainActivity.this, CodehuntActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_TIMEOUT);

        Toast.makeText(this, "Please make sure you have a stable internet connection!", Toast.LENGTH_LONG).show();
    }
}
