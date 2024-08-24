package edu.msu.moranti1.project1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {
    EditText ed1,ed2;
    CheckBox chkbox;

    //Request permission variable for firebase
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    // TODO: Inform user that that your app will not show notifications.
                }
            });
    //Dialog pop up for permissions, only needed for API 33
    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed1 = findViewById(R.id.player1Name);
        ed2 = findViewById(R.id.Player2Name);
        chkbox = findViewById(R.id.chkbox);
        SharedPreferences shared = getSharedPreferences(getString(R.string.mypref), MODE_PRIVATE);
        String user = shared.getString(getString(R.string.saveuser), "");
        String pass = shared.getString(getString(R.string.savepass), "");
        String checked = shared.getString(getString(R.string.checked), "");
        ed1.setText(user);
        ed2.setText(pass);
        //TODO
        ed1.setText("moranti1");
        ed2.setText("password");
        chkbox.setChecked(checked.equalsIgnoreCase("yes"));
        //askNotificationPermission();
        //This code gets the firebase token associated with the device.
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w("FCM", "Fetching FCM registration token failed", task.getException());
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
//                        String msg = "Token: "+token;
//                        Log.d("FCM", msg);
//                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
    public void onLogin(final View view) throws IOException {
        Cloud c = new Cloud();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    String msg = c.getuserlogin(ed1.getText().toString(),ed2.getText().toString(),view);
                    runOnUiThread(new Runnable() {
                        public void run() {
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    Log.e("TAG", "run"+e );
                }
            }
        }).start();


    }
    public void onNewUser(View view) {
        Intent intent = new Intent(this, NewUserActivity.class);
        startActivity(intent);
    }
    public void onRemember(View view) {
        SharedPreferences shared = getSharedPreferences(getString(R.string.mypref), MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        if(chkbox.isChecked()){
            editor.putString(getString(R.string.saveuser), ed1.getText().toString());
            editor.putString(getString(R.string.savepass), ed2.getText().toString());
            editor.putString(getString(R.string.checked), "yes");
            editor.apply();
        }
        else{
            editor.putString(getString(R.string.saveuser), "");
            editor.putString(getString(R.string.savepass), "");
            editor.putString(getString(R.string.checked), "no");
            editor.apply();
        }
    }
}