package com.test.stripe_integration.api;

import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface StripeApi {
    @FormUrlEncoded
    @POST("ephemeral_keys")
    Single<ResponseBody> createEphemeralKey(@FieldMap Map<String, String> apiVersionMap);


//    @POST("api/v2/users/socialSignup")
//    Single<GenericResponse<User>> socialSignup(@Body SocialMediaUser socialMediaUser);
//
//    @POST("api/v1/users/signup")
//    Single<GenericResponse<User>> signUp(@Body SignUpRequest signUpRequest);
//
//    @POST("api/v1/users/updateUserBasicProfileInfo")
//    Single<GenericResponse<UserBasicInfo>> basicInfoUpdate(@Query("currentMode") String currentMode, @Body UserBasicInfo signUpRequest, @Query("isSignup") boolean isSignup);
//
//    @GET("api/v1/description/{id}")
//    Single<GenericResponse<ArrayList<String>>> getDescription(@Path("id") String query);
//
//    @GET("api/v1/users/getLocation")
//    Single<GenericResponse<ArrayList<Object>>> getLocation(@Query("query") String query);
//
////    @POST("api/v1/users/updateUserProfileActiveHashTags")
////    Single<GenericResponse<UserDetail>> hashTagsUpdate(@Query("currentMode") String currentMode, @Query("hashtag") ArrayList<String> hashtag);
////
////    @POST("api/v1/users/addUserAutoPopulateHashTag")
////    Single<GenericResponse<UserDetail>> addUserAutoPopulateHashTag(@Query("currentMode") String currentMode);
//
//    @POST("api/v1/users/addUserAutoPopulateHashTag2")
//    Single<GenericResponse<UserDetail>> addUserAutoPopulateHashTag2(@Query("currentMode") String currentMode, @Query("selTags") String tags);
}
