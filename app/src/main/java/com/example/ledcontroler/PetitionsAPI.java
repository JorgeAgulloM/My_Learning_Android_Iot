package com.example.ledcontroler;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface PetitionsAPI {

    @FormUrlEncoded
    @POST("ledControler.php")
    //Call<DataResponseAPI> enviarDatos(@Field("gpio") String gpio, @Field("status") String status);
    //Call<DataResponseAPI> enviarDatos(@Body RequestBody requestBody);
    Call<DataResponseAPI> enviarDatos(@Field("gpio") String gpio, @Field("status") String status);

}
