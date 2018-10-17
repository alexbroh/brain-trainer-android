package com.github.android.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private android.support.v7.widget.GridLayout choicesBoard;
    private TextView timerTV;
    private TextView problemTV;
    private TextView scoreTV;
    private int correctAnswer=0;
    private int questionCount=0;
    private int score=0;
    private CountDownTimer countDownTimer;
    private GameState gameState;
    enum GameState {LIVE,FINISHED};

    public void initializeGame(View view){
        Log.i("Game Status","Initializing!");
        gameState=GameState.LIVE;

        //get rid of Go button
        Button initButton = (Button) view;
        initButton.setVisibility(View.GONE);
        boolean initialized = choicesBoard!=null&&timerTV!=null&problemTV!=null&&scoreTV!=null;
        if(initialized){
            choicesBoard.setVisibility(View.VISIBLE);
            timerTV.setVisibility(View.VISIBLE);
            problemTV.setVisibility(View.VISIBLE);
            scoreTV.setVisibility(View.VISIBLE);
        }
        setUpQuestion();
    }

    private void gameOver(){
        Log.i("Game Status","Game Over!");
        gameState=GameState.FINISHED;
        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.VISIBLE);

        TextView gameOverTV = findViewById(R.id.gameOverTV);
        gameOverTV.setVisibility(View.VISIBLE);
    }

    public void restartGame(View view){
        Log.i("Game Status","Restarting Game!");
        //make game over UI elements invisible
        Button playAgainButton = findViewById(R.id.playAgainButton);
        playAgainButton.setVisibility(View.GONE);

        TextView gameOverTV = findViewById(R.id.gameOverTV);
        gameOverTV.setVisibility(View.GONE);

        //reset score, timer, question,gamestate
        gameState=GameState.LIVE;
        score=0; correctAnswer=0; questionCount=0;
        scoreTV.setText("0/0");
        setUpQuestion();
    }

    public void selectAnswer(View view){
        if(gameState==GameState.LIVE) {
            Log.i("Game Status", "User Selected Answer!");

            Button answerSelected = (Button) view;

            questionCount++;
            //check answer
            boolean correct = (Integer.parseInt(answerSelected.getText().toString()) == correctAnswer);
            //update score
            if (correct) score++;
            //update TV
            scoreTV.setText(score + "/" + questionCount);

            if((questionCount)>=10) gameOver(); //quit if over 10
            else setUpQuestion(); //nextQuestion
        }
    }

    public void setUpQuestion(){
        if(gameState==GameState.LIVE) {
            Log.i("Game Status", "Setting Up Question!");

            //set up new question
            Random rand = new Random();
            int firstNum = rand.nextInt(99) + 1;
            int secondNum = rand.nextInt(99) + 1;
            correctAnswer = firstNum + secondNum;
            problemTV.setText(Integer.toString(firstNum) + " + " + Integer.toString(secondNum));

            //find random answer locatoin
            int answerLocation = rand.nextInt(4);

            //set up text of choice buttons
            for (int i = 0; i < choicesBoard.getChildCount(); i++) {
                Button choice = (Button) choicesBoard.getChildAt(i);
                if (choice.getTag().toString().equals(Integer.toString(answerLocation)))
                    choice.setText(Integer.toString(correctAnswer));
                else {
                    int wrongAnswer = rand.nextInt(99) + firstNum;
                    while(wrongAnswer==correctAnswer){
                        wrongAnswer=rand.nextInt(99) + firstNum;
                    }
                    choice.setText(Integer.toString(wrongAnswer));
                }
            }

            //set up timer
            if(countDownTimer!=null) countDownTimer.cancel();
            countDownTimer = new CountDownTimer(5300, 500) {
                @Override
                public void onTick(long l) {
                    String second = Integer.toString((int) l / 1000);
                    timerTV.setText(second + "s");
                }

                @Override
                public void onFinish() {
                    gameOver();
                }
            };
            countDownTimer.start();
        }
    }


    private void wireViewsByID(){
        Log.i("Game Status","Wiring Views by ID!");

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
