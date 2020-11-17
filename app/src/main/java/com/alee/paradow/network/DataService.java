package com.alee.paradow.network;

import com.alee.paradow.model.CategoryRes;
import com.alee.paradow.model.Datum;
import com.alee.paradow.model.FavRes;
import com.alee.paradow.model.LikeRes;
import com.alee.paradow.model.LoginRes;
import com.alee.paradow.model.MostLike;
import com.alee.paradow.model.SignupRes;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DataService {

//    @POST("/auth/register/")
//    Call<User> signUp(@Body User user);

    @GET("/api/login")
    Call<LoginRes> login(@Query("email") String login, @Query("password") String password);

    @GET("/api/signup")
    Call<SignupRes> signUp(@Query("name") String name, @Query("email") String login, @Query("password") String password);

    @GET("/api/mostliked")
    Call<MostLike> mostliked();

    @GET("/api/mostviewed")
    Call<MostLike> Recommendations();

    @GET("/api/category")
    Call<CategoryRes> Categories();

    @GET("/api/catdetails/{id}")
    Call<Datum> ImageViewed(@Path("id") Long id);

    @GET("/api/user/{id}")
    Call<FavRes> Favs(@Path("id") Long id);

    @GET("/api/catfav")
    Call<LikeRes> Like(@Query("id") Long id, @Query("remember_token") String token);

    @GET("/api/catDelfav")
    Call<LikeRes> DisLike(@Query("id") Long id, @Query("remember_token") String token);



    //device connection
    @GET("/action_page")
    Call<Void> device(@Query("btn") int id);

    @Multipart
    @POST("/upload")
    Call<Void> uploadImage(@Part MultipartBody.Part image);

//    @GET("/api/category")
//    Call<CategoryRes> ImageReview();

//    @GET("/en_US/api/user/get_token")
//    Call<LoginRes> login(@Query("login") String login, @Query("password") String password);

//    @GET("/disasters/")
//    Call<List<Disease>> getPossibleDisease(@Header("token") String token, @Query("lat") double lat, @Query("lang") double lang);

}