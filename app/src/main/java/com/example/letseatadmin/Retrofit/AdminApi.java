package com.example.letseatadmin.Retrofit;

import com.example.letseatadmin.Models.Admin;
import com.example.letseatadmin.Models.Offer;
import com.example.letseatadmin.Models.Product;
import com.example.letseatadmin.Models.Staff;
import com.example.letseatadmin.Models.adminLogin;
import com.example.letseatadmin.Models.deliveryBoy;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface AdminApi {

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/admin/add")
    Call<Admin> save(@Body Admin admin);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/admin/")
    Call<List<Admin>> getAllData();

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/admin/login")
    Call<String> adminVerify(@Body adminLogin adminLogin);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/admin/get")
    Call<Admin> getSingleUser(
            @Query("adminUId") String uid
    );

    @PUT("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/admin/update")
    Call<Admin> updateAdmin(
            @Query("adminUId") String uid,@Body Admin admin
    );

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/offer/add")
    Call<Offer> saveOffer(@Body Offer offer);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/offer/get-all")
    Call<List<Offer>> getAllOffer();

    @DELETE("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/offer/delete")
    Call<String> offerDelete(
            @Query("offerId") int id
    );

    @PUT("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/offer/update")
    Call<Offer> offerUpdate(
            @Query("offerId") int id,@Body Offer offer
    );

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/add")
    Call<Product> addProduct(@Body Product product);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/get-all")
    Call<List<Product>> getAllProduct();

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/get-by-category-id")
    Call<List<Product>> getSingleProduct(
            @Query("categoryId") int id
    );

    @PUT("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/update")
    Call<Product> updateProduct(
            @Query("productId") int id,@Body Product product
    );

    @DELETE("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/product/delete")
    Call<Product> deleteProduct(
            @Query("productId") int id
    );

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/staff/add")
    Call<Staff> addStaff(@Body Staff staff);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/staff/get-all")
    Call<List<Staff>> getAllStaff();

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/staff/get")
    Call<Staff> getSingleStaff(
            @Query("staffId") int id
    );

    @PUT("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/staff/update")
    Call<Staff> updateStaff(
            @Query("staffId") int id,@Body Staff staff
    );

    @DELETE("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/staff/delete")
    Call<String> deleteStaff(
            @Query("staffId") int id
    );

    @POST("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/delivery-boy/add")
    Call<deliveryBoy> addDeliveryBoy(@Body deliveryBoy boy);

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/delivery-boy/get-all")
    Call<List<deliveryBoy>> getAllDeliveryBoy();

    @GET("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/delivery-boy/get")
    Call<deliveryBoy> getSingleDeliveryBoy(
            @Query("deliveryBoyId") int id
    );

    @PUT("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/delivery-boy/update")
    Call<deliveryBoy> updateDeliveryBoy(
            @Query("deliveryBoyId") int id,@Body deliveryBoy boy
    );

    @DELETE("http://letseat-env.eba-mvj8pngz.eu-north-1.elasticbeanstalk.com/lets-eat/delivery-boy/delete")
    Call<String> deleteDeliveryBoy();
}
