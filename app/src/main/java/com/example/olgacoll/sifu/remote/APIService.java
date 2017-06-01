package com.example.olgacoll.sifu.remote;

/**
 * Created by olgacoll on 15/5/17.
 */

import com.example.olgacoll.sifu.model.Incidencia;

import java.io.File;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @FormUrlEncoded
    @POST("incidencia")
    Call<Incidencia> sendIncidencia(@Field("name") String name,
                                @Field("last_name") String last_name,
                                @Field("company") String company,
                                @Field("description") String description,
                                @Field("email") String email,
                                @Field("phone") String phone,
                                @Field("site") String site,
                                @Field("client") String client,
                                @Field("device_id") String uuid);

    @FormUrlEncoded
    @POST("send_mail")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<String> sendMail(@Field("name") String name,
                          @Field("lastname") String lastname,
                          @Field("email") String email,
                          @Field("phone") String phone,
                          @Field("province") String province,
                          @Field("message") String message);

    @POST("send_files")
    @FormUrlEncoded
    Call<String> sendFiles(@Field("image_01") File image_01,
                           @Field("image_02") File image_02,
                           @Field("image_03") File image_03,
                           @Field("image_04") File image_04);
}
