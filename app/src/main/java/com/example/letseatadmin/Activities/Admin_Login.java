package com.example.letseatadmin.Activities;

import static com.example.letseatadmin.Activities.MainActivity.listener;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.letseatadmin.Models.adminLogin;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Login extends AppCompatActivity {
    TextInputEditText Admin_Login_Password,Admin_Login_UID;
    Button Admin_Login_Button;

    private ProgressDialog Admin_Login_Progressbar;
    LottieAnimationView Admin_Login_Animation;
    TextView Admin_Login_Signup,Forget_Password;
    boolean ischeck = false;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        Admin_Login_Progressbar = new ProgressDialog(this);
        Admin_Login_Progressbar.setTitle("Loading..... ");
        Admin_Login_Progressbar.setMessage("Login Your Account ... ");
        Admin_Login_Progressbar.setCanceledOnTouchOutside(false);
        Admin_Login_Button = findViewById(R.id.Admin_Login_Button);
        Admin_Login_UID=findViewById(R.id.Admin_Login_ID);
        Admin_Login_Password=findViewById(R.id.Admin_Login_Password);
        Admin_Login_Animation=findViewById(R.id.Admin_Login_Animation);
        Admin_Login_Signup=findViewById(R.id.Admin_Login_Signup);
        Forget_Password=findViewById(R.id.fp);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        Admin_Login_Animation.animate().translationX(0).setDuration(200000).setStartDelay(0);

        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
        String id = preferences.getString("Login_UID","");
        if(id.equals(""))
        {
        }
        else {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }


        Admin_Login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ischeck = check();
                if(ischeck)
                {
                    verify();
                }
                else {
                    Toast.makeText(Admin_Login.this, "Please enter valid Admin ID or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Admin_Login_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Admin_Registration.class));
            }
        });
        Forget_Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Forgot_Password.class));
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
    private boolean check() {
        if(Admin_Login_UID.length() == 0)
        {
            Admin_Login_UID.setError("This Field Is Required");
            return false;
        } else if (Admin_Login_UID.length() < 18) {
            Admin_Login_UID.setError("Admin ID Must Be 18 Character");
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

    private void verify()
    {
        String id = Admin_Login_UID.getText().toString();
        String pass = Admin_Login_Password.getText().toString();
        ischeck = check();
        if(ischeck)
        {
            adminLogin login = new adminLogin();
            login.setAdminUId(id);
            login.setPassword(pass);

            adminApi.adminVerify(login).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    String temp = String.valueOf(response.code());
                    if(temp.equals("404"))
                    {
                        Toast.makeText(Admin_Login.this, "User Not Found ", Toast.LENGTH_SHORT).show();
                        Admin_Login_Progressbar.cancel();

                    } else if (temp.equals("401")) {
                        Toast.makeText(Admin_Login.this, "Password Incorrect", Toast.LENGTH_SHORT).show();
                        Admin_Login_Progressbar.cancel();
                    }else
                    {
                        SharedPreferences preferences = getSharedPreferences("Login",MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("Login_UID",id);
                        editor.commit();
                        Admin_Login_Progressbar.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }
                        },1000);
                    }
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(Admin_Login.this, "failed "+t, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(listener,filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(listener);
        super.onStop();
    }
}