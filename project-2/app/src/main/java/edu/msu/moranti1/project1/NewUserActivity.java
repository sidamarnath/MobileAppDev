package edu.msu.moranti1.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {
    EditText name, pass, pass2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        name = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        pass2 = findViewById(R.id.pass2);
    }
    public void onNewUser(View view){
        if(TextUtils.isEmpty(name.getText().toString())){
            Toast.makeText(this, "User Id cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass.getText().toString())){
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
        }
        else if(!TextUtils.equals(pass.getText().toString(),pass2.getText().toString())){
            Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();
        }
        else {
            Cloud c = new Cloud();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String msg = c.getcreateuser(name.getText().toString(), pass.getText().toString(), view);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(NewUserActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (Exception e) {
                        Log.e("TAG", "run" + e);
                    }
                }
            }).start();
        }
    }
}