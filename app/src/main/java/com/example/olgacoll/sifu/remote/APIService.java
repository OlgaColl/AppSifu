package com.example.olgacoll.sifu.remote;

/**
 * Created by olgacoll on 15/5/17.
 */

import com.example.olgacoll.sifu.model.Incidencia;
import com.example.olgacoll.sifu.model.Solicitud;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @Multipart
    @POST("incidencia")
    Call<String> sendIncidencia(@Field("name") String name,
                                @Field("last_name") String last_name,
                                @Field("company") String company,
                                @Field("description") String description,
                                @Field("email") String email,
                                @Field("phone") String phone,
                                @Field("site") String site,
                                @Field("client") String client,
                                @Field("device_id") String uuid);

    @POST("send_mail")
    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<String> sendMail(@Field("name") String name,
                          @Field("lastname") String lastname,
                          @Field("email") String email,
                          @Field("phone") String phone,
                          @Field("province") String province,
                          @Field("message") String message);

    /*Parámetros FILES (mirando el código realmente da igual el nombre, siempre que venga por un $_FILES):
          image_01 -> file
          image_02 -> file
          image_03 -> file
          image_04 -> file*/
}
