package com.example.olgacoll.sifu.remote;

/**
 * Created by olgacoll on 15/5/17.
 */

import com.example.olgacoll.sifu.model.Incidencia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {

    @POST("/incidencia")
    @FormUrlEncoded
    Call<Incidencia> sendIncidencia(@Field("name") String name,
                                    @Field("last_name") String last_name,
                                    @Field("company") String company,
                                    @Field("description") String description,
                                    @Field("email") String email,
                                    @Field("phone") String phone,
                                    @Field("site") String site,
                                    @Field("client") String client);
}
