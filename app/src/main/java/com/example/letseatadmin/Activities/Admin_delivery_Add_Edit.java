package com.example.letseatadmin.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class Admin_delivery_Add_Edit extends AppCompatActivity {

    CircleImageView Admin_Delivery_Image;
    TextInputEditText Admin_Delivery_Name,Admin_Delivery_Mobile,Admin_Delivery_Email,Admin_Delivery_Salary,Admin_Delivery_Pass;
    Button Admin_Delivery_Add_Button;
    RetrofitServices retrofitServices;
    AdminApi adminApi;

    boolean isCheck = false;
    public Uri imageuri=Uri.EMPTY;
    public FirebaseStorage firebaseStorage;
    ProgressDialog pg;
    String name,email,pass;
    long mobile;
    double salary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pg = new ProgressDialog(this);
        setContentView(R.layout.activity_admin_delivery_add_edit);
        Admin_Delivery_Add_Button = findViewById(R.id.Admin_Delivery_Add_Button);
        Admin_Delivery_Email=findViewById(R.id.Admin_Delivery_Add_Email);
        Admin_Delivery_Image = findViewById(R.id.Admin_Delivery_Add_Image);
        Admin_Delivery_Name=findViewById(R.id.Admin_Delivery_Add_Name);
        Admin_Delivery_Salary=findViewById(R.id.Admin_Delivery_Add_Salary);
        Admin_Delivery_Mobile=findViewById(R.id.Admin_Delivery_Add_Mobile);
        Admin_Delivery_Pass=findViewById(R.id.Admin_Delivery_Add_Password);
        retrofitServices = new RetrofitServices();
        adminApi = retrofitServices.getRetrofit().create(AdminApi.class);

        Admin_Delivery_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImage();
            }
        });
        Admin_Delivery_Add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDeliveryBoy();
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
        String ImageUri = imageuri.toString();
        if(ImageUri.equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please Select Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void setDeliveryBoy(){
        pg.setTitle("Loading..... ");
        pg.setMessage("Adding Delivery Boy ... ");
        pg.setCanceledOnTouchOutside(false);
        pg.show();
        getData();
        isCheck=check();
        firebaseStorage = FirebaseStorage.getInstance();
        final StorageReference reference = firebaseStorage.getReference().child(String.valueOf(System.currentTimeMillis()));
        if(isCheck)
        {
            reference.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            deliveryBoy boy = new deliveryBoy();
                            boy.setEmail(email);
                            boy.setName(name);
                            boy.setImageUrl(String.valueOf(uri));
                            boy.setPassword(pass);
                            boy.setSalary(salary);
                            boy.setMobileNo(mobile);
                            adminApi.addDeliveryBoy(boy).enqueue(new Callback<deliveryBoy>() {
                                @Override
                                public void onResponse(Call<deliveryBoy> call, Response<deliveryBoy> response) {
                                    Toast.makeText(Admin_delivery_Add_Edit.this, "Delivery Boy Added Successfully", Toast.LENGTH_SHORT).show();
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<deliveryBoy> call, Throwable t) {
                                    Toast.makeText(Admin_delivery_Add_Edit.this, "Failed", Toast.LENGTH_SHORT).show();
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
    private  void getData(){
        name = Admin_Delivery_Name.getText().toString();
        mobile= Long.parseLong(Admin_Delivery_Mobile.getText().toString());
        email=Admin_Delivery_Email.getText().toString();
        pass=Admin_Delivery_Pass.getText().toString();
        salary= Double.parseDouble(Admin_Delivery_Salary.getText().toString());
    }
}