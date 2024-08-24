package edu.msu.moranti1.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.security.Policy;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {

     String player1;
     String player2;

//    public int getTurn() {
//        return turn;
//    }
//
//    public void setTurn(int turn) {
//        this.turn = turn;
//        Log.d("TAG", "setTurn:1111 "+turn);
//    }

    int turn;

    TextView textView;
    Board board;

    public ArrayList<GamePiece> getGamePieces() {
        return gamePieces;
    }

    public void setGamePieces(ArrayList<GamePiece> gamePieces) {
        this.gamePieces = gamePieces;
    }

    public ArrayList<GamePiece> gamePieces = new ArrayList<GamePiece>();

    @Override
    protected void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);
        getGameView().saveInstanceState(bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textView = findViewById(R.id.PlayerTurn);
        SharedPreferences prefs = getSharedPreferences(getString(R.string.mypref), MODE_PRIVATE);
        player1 = prefs.getString(getResources().getString(R.string.player_1),"");
        player2 = prefs.getString(getResources().getString(R.string.player_2),"");
        turn = prefs.getInt(getResources().getString(R.string.turn),1);
        textView.setText(getString(R.string.player1)+player1);
        if(savedInstanceState != null){
            getGameView().loadInstanceState(savedInstanceState);
        }
    }


    public void onSurrender(View view) {
        SharedPreferences prefs = getSharedPreferences(getString(R.string.mypref), MODE_PRIVATE);
        turn = prefs.getInt("turn", 1);
        getGameView().getBoard().resetGame();
        getGameView().invalidate();
        Intent intent = new Intent(this, ResultActivity.class);
        if (turn == 1) {
            intent.putExtra("winner", player2);
        } else {
            intent.putExtra("winner", player1);
        }
        this.finish();
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_game, menu);
        return true;
    }

    public void onDone(View view) {

        int winner = getGameView().getBoard().onDone(this);
        getGameView().invalidate();
        if (winner != 0) {
            Intent intent = new Intent(this, ResultActivity.class);
            if (winner == 1) {
                intent.putExtra("winner", player1);
            } else if (winner == 2) {
                intent.putExtra("winner", player2);
            }
            getGameView().getBoard().resetGame();
            this.finish();
            startActivity(intent);
        }

    }

    public GameView getGameView() {
        return this.findViewById(R.id.boardView);
    }

    public void onBackPressed() {
        super.onBackPressed();
        gamePieces.clear();
    }

    /**
     * Undo button handler
     */
    public void onUndo(View view) {
        getGameView().getBoard().onUndo();
        getGameView().invalidate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_game:
                getGameView().getBoard().resetGame();
                getGameView().invalidate();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void checkForTurn(Context context, int turn) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.mypref), MODE_PRIVATE);
        player1 = prefs.getString(context.getResources().getString(R.string.player_1),"");
        player2 = prefs.getString(context.getResources().getString(R.string.player_2),"");
        textView = ((Activity)context).findViewById(R.id.PlayerTurn);
        if (turn == 1) {
            textView.setText(context.getString(R.string.player1)+player1);
        } else {

            textView.setText(context.getString(R.string.player2)+player2);
        }

    }
    public void saveturn(Context context, int turn){
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.mypref), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(context.getString(R.string.turn), turn);
        editor.apply();
    }
    public int put_turn(Context context){
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.mypref), MODE_PRIVATE);
        return prefs.getInt(context.getResources().getString(R.string.turn),1);
    }


}