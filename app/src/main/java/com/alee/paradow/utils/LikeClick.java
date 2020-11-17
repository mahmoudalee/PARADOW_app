package com.alee.paradow.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alee.paradow.Activities.MainActivity;
import com.alee.paradow.R;
import com.alee.paradow.model.LikeRes;
import com.alee.paradow.network.DataService;
import com.alee.paradow.network.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LikeClick {

    public static void like(ImageView fav, Long id, Context context) {


        fav.setImageResource(R.drawable.ic_favorite);

        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        Call<LikeRes> call = service.Like(id, MainActivity.token);
        call.enqueue(new Callback<LikeRes>() {
            @Override
            public void onResponse(Call<LikeRes> call, Response<LikeRes> response) {

                if(response.code() == 500)
                {
                    Toast.makeText(context, "Error Like", Toast.LENGTH_LONG).show();
                    fav.setImageResource(R.drawable.ic_favorite_border);
                }
                else{

                    Log.i("Response:", "Like done"+response.code());
                    Toast.makeText(context, "Liked", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LikeRes> call, Throwable t) {

                Toast.makeText(context, "Error Like", Toast.LENGTH_LONG).show();
                fav.setImageResource(R.drawable.ic_favorite_border);
            }

        });

    }

    public static void dislike(ImageView fav, Long id, Context context) {

        fav.setImageResource(R.drawable.ic_favorite_border);

        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
                        Call<LikeRes> call = service.DisLike(id, MainActivity.token);
        call.enqueue(new Callback<LikeRes>() {
            @Override
            public void onResponse(Call<LikeRes> call, Response<LikeRes> response) {

                Log.i("Response:", "DisLike done"+response.code());
                Toast.makeText(context, "DisLike", Toast.LENGTH_LONG).show();

                if (response.body().getMessage().equals("delete love successfully"))
                {
                    Toast.makeText(context, "DisLike Confirmed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LikeRes> call, Throwable t) {
                Toast.makeText(context, "Error Like", Toast.LENGTH_LONG).show();
                fav.setImageResource(R.drawable.ic_favorite);
            }

        });
    }

    public static void loveAction(View v, ImageView fav, Long id, Context context) {

        ImageView likeImage = (ImageView)v;
        int imgResource = R.drawable.ic_favorite_border;

        if ( likeImage != null && likeImage.getDrawable() != null) {
            Drawable.ConstantState constantState;

            constantState = context.getResources()
                    .getDrawable(imgResource, context.getTheme())
                    .getConstantState();

            if (likeImage.getDrawable().getConstantState() == constantState) {
                Log.i("Response:", id +"   "+MainActivity.token);
                LikeClick.like(fav,id, context);
//                        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
//                        Call<LikeRes> call = service.Like(mostLikes.get(holder.getAdapterPosition()).getId(), MainActivity.token);
//                        call.enqueue(new Callback<LikeRes>() {
//                            @Override
//                            public void onResponse(Call<LikeRes> call, Response<LikeRes> response) {
//
//                                if(response.code() == 500)
//                                {
//                                    Toast.makeText(context, "Error Like", Toast.LENGTH_LONG).show();
//                                    holder.fav.setImageResource(R.drawable.ic_favorite_border);
//                                }
//                                else{
//
//                                    Log.i("Response:", "Like done"+response.code());
//                                    Toast.makeText(context, "Liked", Toast.LENGTH_LONG).show();
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<LikeRes> call, Throwable t) {
//
//                                Toast.makeText(context, "Error Like", Toast.LENGTH_LONG).show();
//                                holder.fav.setImageResource(R.drawable.ic_favorite_border);
//                            }
//
//                        });
            }
            else {

                Toast.makeText(context, "Dislike", Toast.LENGTH_SHORT).show();
                LikeClick.dislike(fav,id,context);
//
//                        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
//                        Call<LikeRes> call = service.DisLike(mostLikes.get(holder.getAdapterPosition()).getId(), MainActivity.token);
//                        call.enqueue(new Callback<LikeRes>() {
//                            @Override
//                            public void onResponse(Call<LikeRes> call, Response<LikeRes> response) {
//
//                                Log.i("Response:", "DisLike done"+response.code());
//                                Toast.makeText(context, "DisLike", Toast.LENGTH_LONG).show();
//
//                                if (response.body().getMessage().equals("delete love successfully"))
//                                {
//                                    Toast.makeText(context, "DisLike Confirmed", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(Call<LikeRes> call, Throwable t) {
//                                Toast.makeText(context, "Error Like", Toast.LENGTH_LONG).show();
//                                holder.fav.setImageResource(R.drawable.ic_favorite);
//                            }
//
//                        });
            }
        }
    }
}
