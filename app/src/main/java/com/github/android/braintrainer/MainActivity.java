package com.github.android.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private GridLayout choicesBoard;
    private TextView timerTV;
    private TextView problemTV;
    private TextView scoreTV;
    private int correctAnswer=0;
    private int questionCount=0;
    private int score=0;

    private void initializeGame(View view){
        Button startButton = findViewById(R.id.goButton);
        startButton.setVisibility(View.GONE);
        setUpGame();
    }

    private void gameOver(){
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.VISIBLE);

        TextView gameOverTV = findViewById(R.id.gameOverTV);
        gameOverTV.setVisibility(View.VISIBLE);
    }

    public void restartGame(View view){
        //make game over UI elements invisible
        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.GONE);

        TextView gameOverTV = findViewById(R.id.gameOverTV);
        gameOverTV.setVisibility(View.GONE);

        //reset score, timer, question
    }

    private void setUpGame(){
        boolean initialized = choicesBoard!=null&&timerTV!=null&problemTV!=null&&scoreTV!=null;
        if(initialized){
            choicesBoard.setVisibility(View.VISIBLE);
            timerTV.setVisibility(View.VISIBLE);
            problemTV.setVisibility(View.VISIBLE);
            scoreTV.setVisibility(View.VISIBLE);
        }
    }

    public void selectAnswer(View view){
        questionCount++;
        Button answerSelected = (Button) view;

        //check answer
        boolean correct = (Integer.parseInt(answerSelected.getText().toString()) == correctAnswer);
        //update score
        if(correct) score++;
        //update TV
        scoreTV.setText(score+"/"+questionCount);

        //nextQuestion
        setUpQuestion();
    }

    public void setUpQuestion(){
        //set up new question
        Random rand = new Random();
        int firstNum=rand.nextInt()*100;
        int secondNum=rand.nextInt()*100;

        //set up timer
        new CountDownTimer(5000, 1000){
            @Override
            public void onTick(long l) {
                String second  = Integer.toString((int) l/1000);
                scoreTV.setText(second + "s");
            }

            @Override
            public void onFinish() {
                gameOver();
            }
        };
    }


    public void wireViewsByID(){
        choicesBoard = findViewById(R.id.choicesBoard);
        timerTV = findViewById(R.id.timerTV);
        problemTV = findViewById(R.id.problemTV);
        scoreTV = findViewById(R.id.scoreTV);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireViewsByID();
    }
}
