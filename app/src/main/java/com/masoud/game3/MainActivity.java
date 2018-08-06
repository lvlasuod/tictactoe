package com.masoud.game3;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // silver coin = 0 ; gold coin = 1 ; empty state = 2
    public static int PLAYER_ONE=0;
    public static int PLAYER_TWO=1;
    public static int EMPTY_STATE=2;

    int[] gameState={EMPTY_STATE,EMPTY_STATE,EMPTY_STATE,
            EMPTY_STATE,EMPTY_STATE,EMPTY_STATE,
            EMPTY_STATE,EMPTY_STATE,EMPTY_STATE};
    int[][] winningState={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public int activePlayer=PLAYER_ONE;

    TextView title;
    TextView textViewActivePlayer;
    ImageView imageViewActivePlayer;
    String winner;

    public boolean gameActive=true;

    public void dropIn(View view){

        ImageView counter=(ImageView) view;



        // saving game state
        int tappedCounter= Integer.parseInt(counter.getTag().toString());

        // check if cell is full already
        if(gameState[tappedCounter] == EMPTY_STATE && gameActive) {
            // put coin out off the board
            counter.setTranslationY(-1500);
        gameState[tappedCounter] = activePlayer;

        // set resource of image view by detecting Active player
        if (activePlayer == PLAYER_ONE) {

            counter.setImageResource(R.drawable.coin_silver);
            activePlayer = PLAYER_TWO;
            textViewActivePlayer.setText("PLAYER TWO");
            imageViewActivePlayer.setImageResource(R.drawable.coin_gold);

        } else {

            counter.setImageResource(R.drawable.coin_gold);
            activePlayer = PLAYER_ONE;
            textViewActivePlayer.setText("PLAYER ONE");
            imageViewActivePlayer.setImageResource(R.drawable.coin_silver);
        }

        counter.animate().translationYBy(1500).rotation(3600).setDuration(300);
        for (int[] winningPosition : winningState) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != EMPTY_STATE)
            {
                gameActive=false;
                title.setText("*** CONGRATULATIONS ***");
                winner = "";
                if (activePlayer == PLAYER_TWO) {

                    winner = " PLAYER ONE WON [silver coin] ";



                } else {

                    winner = " PLAYER TWO WON  [gold coin]";
                    //someone has won

                    imageViewActivePlayer.setImageResource(R.drawable.coin_gold);
                }
                textViewActivePlayer.setText(winner);
                imageViewActivePlayer.setImageResource(R.drawable.replay);
                imageViewActivePlayer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // do reset game
                        resetGame();

                    }
                });
                Toast.makeText(this, winner, Toast.LENGTH_SHORT).show();

            }

        }

    }

    }

    public void resetGame(){
        title.setText("[ ACTIVE PLAYER ]");
        activePlayer = PLAYER_ONE;
        textViewActivePlayer.setText("PLAYER ONE");
        imageViewActivePlayer.setImageResource(R.drawable.coin_silver);

        GridLayout gridLayout=(GridLayout)findViewById(R.id.gridLayout);

        for(int i=0;i< gridLayout.getChildCount();i++) {
            gameState[i]=EMPTY_STATE;
            ImageView imageView=(ImageView) gridLayout.getChildAt(i);
            imageView.setImageDrawable(null);

        }

        gameActive=true;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewActivePlayer=(TextView)findViewById(R.id.textview_active_player);
        title=(TextView)findViewById(R.id.active_title);
        imageViewActivePlayer=(ImageView)findViewById(R.id.imageViewActivePlayer);

    }


}
