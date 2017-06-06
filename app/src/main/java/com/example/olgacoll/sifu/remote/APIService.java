package com.example.olgacoll.sifu.remote;

/**
 * Created by olgacoll on 15/5/17.
 */

import com.example.olgacoll.sifu.model.Incidencia;

import java.io.File;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIService {

    @Multipart
    @POST("incidencia")
    Call<Incidencia> sendIncidence(
                                    @Part("file\"; filename=\"pp.png\" ") RequestBody file,
                                    @Part("name") RequestBody name,
                                    @Part("last_name") RequestBody last_name,
                                    @Part("company") RequestBody company,
                                    @Part("description") RequestBody description,
                                    @Part("email") RequestBody email,
                                    @Part("phone") RequestBody phone,
                                    @Part("client") RequestBody client,
                                    @Part("device_id") RequestBody device_id);

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
