package com.example.letseatadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.example.swipebutton_library.OnActiveListener;
import com.example.swipebutton_library.SwipeButton;

public class AdminSplashScreen extends AppCompatActivity {
    TextView Admin_Splash_Title;
    LottieAnimationView Admin_Splash_Animation;
    SwipeButton Admin_Splash_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_splash_screen);
        getSupportActionBar().hide();
        Admin_Splash_Title = findViewById(R.id.Admin_Splash_Title);
        Admin_Splash_Animation = findViewById(R.id.Admin_Splash_Animation);
        Admin_Splash_Button = findViewById(R.id.Admin_Splash_Swipebutton);
        Admin_Splash_Title.animate().translationY(200).setDuration(2000).setStartDelay(10);
        Admin_Splash_Animation.animate().translationX(0).setDuration(200000).setStartDelay(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },0);
        Admin_Splash_Button.setOnActiveListener(new OnActiveListener() {
            @Override
            public void onActive() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                },100);
            }
        });
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Do you want to Exit 🥺🥺🥺 ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                finishAffinity();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user select "No", just cancel this dialog and continue with app
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
