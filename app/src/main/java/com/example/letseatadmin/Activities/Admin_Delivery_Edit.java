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
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.letseatadmin.Models.deliveryBoy;
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

public class Admin_Delivery_Edit extends AppCompatActivity {

    CircleImageView Admin_Delivery_Image;
    TextInputEditText Admin_Delivery_Name,Admin_Delivery_Mobile,Admin_Delivery_Email,Admin_Delivery_Salary,Admin_Delivery_Pass;
    Button Admin_Delivery_Edit_Button;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    boolean isCheck = false;
    public Uri imageuri=Uri.EMPTY;
    public FirebaseStorage firebaseStorage;
    ProgressDialog pg;
    String image;
    int id;
    String name,email,pass;
    long mobile;
    double salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delivery_edit);
        pg = new ProgressDialog(this);
        Admin_Delivery_Edit_Button = findViewById(R.id.Admin_Delivery_Edit_Button);
        Admin_Delivery_Email=findViewById(R.id.Admin_Delivery_Edit_Email);
        Admin_Delivery_Image= findViewById(R.id.Admin_Delivery_Edit_Image);
        Admin_Delivery_Name=findViewById(R.id.Admin_Delivery_Edit_Name);
        Admin_Delivery_Salary=findViewById(R.id.Admin_Delivery_Edit_Salary);
        Admin_Delivery_Mobile=findViewById(R.id.Admin_Delivery_Edit_Mobile);
        Admin_Delivery_Pass=findViewById(R.id.Admin_Delivery_Edit_Password);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);
        setData();
        Admin_Delivery_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
                image="";
            }
        });
        Admin_Delivery_Edit_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDeliveryBoy();
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
            Admin_Delivery_Image.setImageURI(imageuri);
        }
    }
    public boolean check()
    {
        if(Admin_Delivery_Name.length() == 0)
        {
            Admin_Delivery_Name.setError("*Required");
            return false;
        }
        if(Admin_Delivery_Email.length() == 0)
        {
            Admin_Delivery_Email.setError("*Required");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Admin_Delivery_Email.getText().toString()).matches()) {
            Admin_Delivery_Email.setError("* Email Must Be in Email Format");
            return false;
        }
        if(Admin_Delivery_Mobile.length() == 0)
        {
            Admin_Delivery_Mobile.setError("*Required");
            return false;
        } else if (Admin_Delivery_Mobile.length() < 10) {
            Admin_Delivery_Mobile.setError("Mobile Must be 10 Digit");
        }
        if(Admin_Delivery_Salary.length() > 8)
        {
            Admin_Delivery_Salary.setError("Maximum 8 Digit");
        }
        return true;
    }
    public void setData(){
        String name,pass,email,mobile,salary;
        Intent i = getIntent();
        name=i.getStringExtra("name");
        email=i.getStringExtra("email");
        mobile= String.valueOf(i.getLongExtra("mobile",0));
        salary= String.valueOf(i.getDoubleExtra("salary",0));
        image=i.getStringExtra("image");
        id=i.getIntExtra("id",0);
        pass=i.getStringExtra("pass");
        Glide.with(getApplicationContext()).load(image).into(Admin_Delivery_Image);
        Admin_Delivery_Email.setText(email);
        Admin_Delivery_Name.setText(name);
        Admin_Delivery_Mobile.setText(mobile);
        Admin_Delivery_Salary.setText(salary);
        Admin_Delivery_Pass.setText(pass);
    }
    private void updateDeliveryBoy(){
        pg.setTitle("Loading..... ");
        pg.setMessage("Adding Delivery Boy ... ");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        getData();
        isCheck=check();
        firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference reference = firebaseStorage.getReference().child(String.valueOf(System.currentTimeMillis()));
        if(isCheck){
            if(!image.equals(""))
            {
                deliveryBoy boy = new deliveryBoy();
                boy.setMobileNo(mobile);
                boy.setSalary(salary);
                boy.setName(name);
                boy.setEmail(email);
                boy.setPassword(pass);
                boy.setImageUrl(image);
                adminApi.updateDeliveryBoy(id,boy).enqueue(new Callback<deliveryBoy>() {
                    @Override
                    public void onResponse(Call<deliveryBoy> call, Response<deliveryBoy> response) {
                        Toast.makeText(Admin_Delivery_Edit.this, "Information Updated Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    @Override
                    public void onFailure(Call<deliveryBoy> call, Throwable t) {

                    }
                });
            }else{
                reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                deliveryBoy boy = new deliveryBoy();
                                boy.setMobileNo(mobile);
                                boy.setSalary(salary);
                                boy.setName(name);
                                boy.setEmail(email);
                                boy.setPassword(pass);
                                boy.setImageUrl(String.valueOf(uri));
                                adminApi.updateDeliveryBoy(id,boy).enqueue(new Callback<deliveryBoy>() {
                                    @Override
                                    public void onResponse(Call<deliveryBoy> call, Response<deliveryBoy> response) {
                                        Toast.makeText(Admin_Delivery_Edit.this, "Information Updated Successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                    @Override
                                    public void onFailure(Call<deliveryBoy> call, Throwable t) {

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
    private  void getData(){
        name = Admin_Delivery_Name.getText().toString();
        mobile= Long.parseLong(Admin_Delivery_Mobile.getText().toString());
        email=Admin_Delivery_Email.getText().toString();
        pass=Admin_Delivery_Pass.getText().toString();
        salary= Double.parseDouble(Admin_Delivery_Salary.getText().toString());
    }
    @Override
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