package com.example.olgacoll.sifu.remote;

/**
 * Created by olgacoll on 15/5/17.
 */

public class ApiUtils {

    private ApiUtils(){}

    public static final String BASE_URL = "http://quercus.deideasmarketing.solutions/wp-json/dms/v1/";

    public static APIService getAPIService(){

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
