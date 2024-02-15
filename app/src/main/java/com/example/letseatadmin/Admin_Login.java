package com.example.letseatadmin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Admin_Login extends AppCompatActivity {
    TextInputEditText Admin_Login_Password,Admin_Login_Mobile;
    Button Admin_Login_Button;
    LottieAnimationView Admin_Login_Animation;
    TextView Admin_Login_Signup;
    boolean ischeck = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        Admin_Login_Button = findViewById(R.id.Admin_Login_Button);
        Admin_Login_Mobile=findViewById(R.id.Admin_Login_Mobile);
        Admin_Login_Password=findViewById(R.id.Admin_Login_Password);
        Admin_Login_Animation=findViewById(R.id.Admin_Login_Animation);
        Admin_Login_Signup=findViewById(R.id.Admin_Login_Signup);
        Admin_Login_Animation.animate().translationX(0).setDuration(200000).setStartDelay(0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        },0);

        Admin_Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ischeck = check();
                if(ischeck)
                {
                    startActivity(new Intent(getApplicationContext(),Admin_Registration.class));
                }
                else {
                    Toast.makeText(Admin_Login.this, "Please enter valid mobile or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Admin_Login_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Admin_Registration.class));
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
    private boolean check()
    {
        if(Admin_Login_Mobile.length() == 0)
        {
            Admin_Login_Mobile.setError("This Field Is Required");
            return false;
        }
        if(Admin_Login_Password.length() == 0)
        {
            Admin_Login_Password.setError("This Field Is Required");
            return false;
        }
        else if (Admin_Login_Password.length() < 8) {
            Admin_Login_Password.setError("password minimum length is 8 characters");
            return false;
        }
        return true;
    }
}