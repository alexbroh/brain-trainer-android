package com.github.android.braintrainer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public void startGame(View view){
        Button startButton = findViewById(R.id.goButton);
        startButton.setVisibility(View.GONE);
        setUpGame();
    }

    public void setUpGame(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
