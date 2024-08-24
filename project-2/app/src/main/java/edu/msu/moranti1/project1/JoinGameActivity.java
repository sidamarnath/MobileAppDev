package edu.msu.moranti1.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class JoinGameActivity extends AppCompatActivity {

    private FirebaseService FCMService = new FirebaseService();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);

        ListView list = findViewById(R.id.list);
        final Cloud.CatalogAdapter adapter = new Cloud.CatalogAdapter(list,"moranti1","password");
        list.setAdapter(adapter);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        String msg = "Token: "+token;
                        Log.d("FCM", msg);
                        Toast.makeText(JoinGameActivity.this, msg, Toast.LENGTH_SHORT).show();
                        FCMService.onNewToken(token);
                    }
                });
    }
    public void onbtnClick(View v){
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);

    }
}