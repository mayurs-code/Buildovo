package com.InfinitySolutions.buildovo.interfaces;

import com.InfinitySolutions.buildovo.postData.loginPost;
import com.InfinitySolutions.buildovo.responseData.loginUserData.UserData;

import com.google.gson.annotations.JsonAdapter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public  interface retrofitPost {

    @POST("/api/user/login")
    Call<UserData> userDataResponse(@Body loginPost post);

}
