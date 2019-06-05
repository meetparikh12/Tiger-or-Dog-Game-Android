package com.example.tigerordoggame;

import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {
        ONE, TWO,
        NO
    }
    int playerOneScore=0;
    int playerTwoScore=0;
    Player currentPlayer = Player.ONE;
    Player[] playerChoice = new Player[9];
    int[][] identifyWinner = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    private boolean gameOver = false;
    private GridLayout gridLayout;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            gridLayout = findViewById(R.id.gridLayout);
            button = findViewById(R.id.button1);
            setInitialPlayer();
    }
    private void setInitialPlayer() {
        for (int i = 0; i < playerChoice.length;i++){
            playerChoice[i]=Player.NO;
        }
    }
    public void tappedImage(View imageView){
        ImageView tappedImagedView = (ImageView) imageView;
        int tagID = Integer.parseInt(tappedImagedView.getTag().toString());
        if (playerChoice[tagID] ==Player.NO && !gameOver) {
            tappedImagedView.setTranslationX(-2000);
            playerChoice[tagID] = currentPlayer;
            if (currentPlayer == Player.ONE) {
                tappedImagedView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.TWO;
            } else if (currentPlayer == Player.TWO) {
                tappedImagedView.setImageResource(R.drawable.dog);
                currentPlayer = Player.ONE;
            }
            tappedImagedView.animate().translationXBy(2000).rotation(360*2).alpha(1).setDuration(500);

            for (int[] winnerChoice : identifyWinner) {
                if (playerChoice[winnerChoice[0]] == playerChoice[winnerChoice[1]] &&
                        playerChoice[winnerChoice[1]] == playerChoice[winnerChoice[2]] &&
                        playerChoice[winnerChoice[0]] != Player.NO) {
                    gameOver = true;
                    button.setVisibility(View.VISIBLE);
                    if (currentPlayer == Player.ONE) {
                        playerTwoScore++;
                    } else if (currentPlayer == Player.TWO) {
                        playerOneScore++;
                    }
                    Toast.makeText(MainActivity.this, "Score : " +"Player One - " +playerOneScore +" & " +"Player Two - " +playerTwoScore, Toast.LENGTH_LONG).show();
                }
            }
            boolean emptySquare = false;
            for (Player squareState : playerChoice) {
                if (squareState == Player.NO) {
                    emptySquare = true;
                    break;
                }
            }
            if (!emptySquare && !gameOver) {
                // Game is a draw
                gameOver = true;
                // Set draw message here...
                Toast.makeText(MainActivity.this,"Uh, Match is draw!",Toast.LENGTH_SHORT).show();
                button.setVisibility(View.VISIBLE);

            }
        }
    }
    public void resetGame(View view){
        reset();
    }
    private void reset(){
        for(int i=0; i<gridLayout.getChildCount();i++) {
            ImageView imageView = (ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);
            imageView.setAlpha(0.2f);
        }
        setInitialPlayer();
        gameOver = false;
        currentPlayer = Player.ONE;
        button.setVisibility(View.GONE);
        }
    }

