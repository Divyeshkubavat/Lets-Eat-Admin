package com.example.letseatadmin;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;


public class AdminProfile extends Fragment {

    TextView Admin_Account_Name,Admin_Account_Id,Admin_Account_Email,Admin_Account_Mobile,Admin_Account_DOB;
    Button Admin_Account_Logout,Admin_Account_Payment,Admin_Account_FAQ,Admin_Account_Edit;
    CircleImageView Admin_Account_Image;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_profile, container, false);
        Admin_Account_Name=view.findViewById(R.id.Admin_Account_Name);
        Admin_Account_Id=view.findViewById(R.id.Admin_Account_ID);
        Admin_Account_Email=view.findViewById(R.id.Admin_Account_Email);
        Admin_Account_Mobile=view.findViewById(R.id.Admin_Account_Mobile);
        Admin_Account_DOB=view.findViewById(R.id.Admin_Account_DOB);
        Admin_Account_Logout=view.findViewById(R.id.Admin_Account_Logout_Button);
        Admin_Account_Payment=view.findViewById(R.id.Admin_Account_Payment_button);
        Admin_Account_Edit=view.findViewById(R.id.Admin_Account_Edit_Button);
        Admin_Account_Image=view.findViewById(R.id.Admin_Account_Image);

        Admin_Account_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext().getApplicationContext(),Admin_Login.class));
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
}