package com.example.letseatadmin.Retrofit;

import com.example.letseatadmin.Models.Admin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AdminApi {

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/admin")
    Call<Admin> save(@Body Admin admin);



}
