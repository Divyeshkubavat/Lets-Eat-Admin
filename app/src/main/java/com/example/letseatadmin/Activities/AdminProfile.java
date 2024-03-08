package com.example.letseatadmin.Activities;

import static android.content.Context.MODE_PRIVATE;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.letseatadmin.Models.Admin;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdminProfile extends Fragment {

    TextView Admin_Account_Name,Admin_Account_Id,Admin_Account_Email,Admin_Account_Mobile,Admin_Account_DOB;
    Button Admin_Account_Logout,Admin_Account_Payment,Admin_Account_FAQ,Admin_Account_Edit;
    CircleImageView Admin_Account_Image;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    ProgressDialog pg;
    String Login_Uid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_profile, container, false);
        pg = new ProgressDialog(getActivity());
        pg.setTitle("Loading..... ");
        pg.setMessage("Please wait we fetch your data... ");
        pg.setCanceledOnTouchOutside(false);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        getAdminData();
        pg.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                pg.dismiss();
            }
        },1500);
        Admin_Account_Name=view.findViewById(R.id.Admin_Account_Name);
        Admin_Account_Id=view.findViewById(R.id.Admin_Account_ID);
        Admin_Account_Email=view.findViewById(R.id.Admin_Account_Email);
        Admin_Account_Mobile=view.findViewById(R.id.Admin_Account_Mobile);
        Admin_Account_DOB=view.findViewById(R.id.Admin_Account_DOB);
        Admin_Account_Logout=view.findViewById(R.id.Admin_Account_Logout_Button);
        Admin_Account_Payment=view.findViewById(R.id.Admin_Account_Payment_button);
        Admin_Account_Edit=view.findViewById(R.id.Admin_Account_Edit_Button);
        Admin_Account_Image=view.findViewById(R.id.Admin_Account_Image);
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        Login_Uid = preferences.getString("Login_UID","");



        Admin_Account_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Logout ?");
                builder.setMessage("Are you sure you want to logout ?");
                builder.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.commit();
                        editor.apply();
                        startActivity(new Intent(getContext().getApplicationContext(), Admin_Login.class));
                    }
                });
                builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(view.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        Admin_Account_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Admin_Edit.class));
            }
        });
        Admin_Account_Payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),Admin_Payment.class));
            }
        });
        return view;
    }
    private void getAdminData()
    {
        SharedPreferences preferences = getActivity().getSharedPreferences("Login",MODE_PRIVATE);
        String uid = preferences.getString("Login_UID","");
        adminApi.getSingleUser(uid).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {

                String id = response.body().getAdminUId();
                String dob = response.body().getDateOfBirth();
                String email = response.body().getEmail();
                String mobile = String.valueOf(response.body().getMobileNo());
                String name = response.body().getName();
                Admin_Account_DOB.setText(dob);
                Admin_Account_Id.setText(id);
                Admin_Account_Email.setText(email);
                Admin_Account_Mobile.setText(mobile);
                Admin_Account_Name.setText(name);
            }
            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        getAdminData();
    }
}