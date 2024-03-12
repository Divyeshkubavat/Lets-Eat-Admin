package com.example.letseatadmin.Activities;

import static com.example.letseatadmin.Activities.MainActivity.listener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Models.Staff;
import com.example.letseatadmin.R;
import com.example.letseatadmin.Retrofit.AdminApi;
import com.example.letseatadmin.Retrofit.RetrofitServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Staff_Edit extends AppCompatActivity {

    CircleImageView Admin_Staff_Image;
    TextInputEditText Admin_Staff_Name,Admin_Staff_Mobile,Admin_Staff_Email,Admin_Staff_Salary;
    Button Admin_Staff_Button;
    public Uri imageuri=Uri.EMPTY;
    boolean isCheck = false;
    RetrofitServices retrofitServices;
    AdminApi adminApi;
    String image,name,email;
    double salary;
    long mobile;
    public FirebaseStorage firebaseStorage;
    ProgressDialog pg;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pg = new ProgressDialog(this);
        setContentView(R.layout.activity_admin_staff_edit);
        Admin_Staff_Button = findViewById(R.id.Admin_Staff_Edit_Button);
        Admin_Staff_Email =  findViewById(R.id.Admin_Staff_Edit_Email);
        Admin_Staff_Name=findViewById(R.id.Admin_Staff_Edit_Name);
        Admin_Staff_Salary=findViewById(R.id.Admin_Staff_Edit_Salary);
        Admin_Staff_Mobile=findViewById(R.id.Admin_Staff_Edit_Mobile);
        Admin_Staff_Image=findViewById(R.id.Admin_Staff_Edit_Image);

        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        setData();
        Admin_Staff_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
                image="";
            }
        });
        Admin_Staff_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }
    private void UploadImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 101);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK)
        {
            imageuri = data.getData();
            Admin_Staff_Image.setImageURI(imageuri);
        }
    }
    public boolean check()
    {
        if(Admin_Staff_Name.length() == 0)
        {
            Admin_Staff_Name.setError("*Required");
            return false;
        }
        if(Admin_Staff_Email.length() == 0)
        {
            Admin_Staff_Email.setError("*Required");
            return false;
        }
        if(Admin_Staff_Mobile.length() == 0)
        {
            Admin_Staff_Mobile.setError("*Required");
            return false;
        } else if (Admin_Staff_Mobile.length() < 10) {
            Admin_Staff_Mobile.setError("Mobile Must be 10 Digit");
        }
        if(Admin_Staff_Salary.length() > 8)
        {
            Admin_Staff_Salary.setError("Maximum 8 Digit");
        }
        return true;
    }
    public void setData(){
        Intent intent= getIntent();
        String name,email;
        double salary;
        long mobile;

        name = intent.getStringExtra("name");
        salary=intent.getDoubleExtra("salary",0);
        mobile=intent.getLongExtra("mobile",0);
        image=intent.getStringExtra("image");
        email=intent.getStringExtra("email");
        Admin_Staff_Email.setText(email);
        Admin_Staff_Name.setText(name);
        Admin_Staff_Salary.setText(String.valueOf(salary));
        Admin_Staff_Mobile.setText(String.valueOf(mobile));
        Glide.with(getApplicationContext()).load(image).into(Admin_Staff_Image);
    }
    public void updateData(){
        getData();
        isCheck=check();
        pg.setTitle("Loading..... ");
        pg.setMessage("Updating Staff ... ");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference reference = firebaseStorage.getReference().child(String.valueOf(System.currentTimeMillis()));
        if(isCheck)
        {
            if(!image.equals(""))
            {
                Staff staff = new Staff();
                staff.setImageUrl(image);
                staff.setName(name);
                staff.setEmail(email);
                staff.setMobileNo(mobile);
                staff.setSalary(salary);
                adminApi.updateStaff(id,staff).enqueue(new Callback<Staff>() {
                    @Override
                    public void onResponse(Call<Staff> call, Response<Staff> response) {
                        Toast.makeText(Admin_Staff_Edit.this, "Employee Profile Update Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Staff> call, Throwable t) {
                        Toast.makeText(Admin_Staff_Edit.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Staff staff = new Staff();
                                staff.setImageUrl(String.valueOf(uri));
                                staff.setName(name);
                                staff.setEmail(email);
                                staff.setMobileNo(mobile);
                                staff.setSalary(salary);
                                adminApi.updateStaff(id,staff).enqueue(new Callback<Staff>() {
                                    @Override
                                    public void onResponse(Call<Staff> call, Response<Staff> response) {
                                        Toast.makeText(Admin_Staff_Edit.this, "Staff Update Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<Staff> call, Throwable t) {
                                        Toast.makeText(Admin_Staff_Edit.this, "Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        }

    }
    private void getData(){
        name=Admin_Staff_Name.getText().toString();
        salary= Double.parseDouble(Admin_Staff_Salary.getText().toString());
        mobile= Long.parseLong(Admin_Staff_Mobile.getText().toString());
        email=Admin_Staff_Email.getText().toString();
        Intent i = getIntent();
        id=i.getIntExtra("id",0);
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