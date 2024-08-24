package edu.msu.moranti1.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText1,editText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.player1Name);
        editText2 = findViewById(R.id.Player2Name);

    }
    public void onMovetoGame(View view) {
        onSaveName();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void onSaveName(){
        SharedPreferences prefs = getSharedPreferences(getString(R.string.mypref), MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getString(R.string.player_1), editText1.getText().toString());
        editor.putString(getString(R.string.player_2), editText2.getText().toString());
        editor.putInt(getString(R.string.turn), 1);
        editor.apply();
    }


}