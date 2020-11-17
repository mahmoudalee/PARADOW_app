package com.alee.paradow.frags;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alee.paradow.R;
import com.alee.paradow.Activities.ReviewActivity;
import com.alee.paradow.adapters.HomeCardsAdapterMLikes;
import com.alee.paradow.adapters.HomeCardsAdapterMViewed;
import com.alee.paradow.model.FavRes;
import com.alee.paradow.model.MostLike;
import com.alee.paradow.network.DataService;
import com.alee.paradow.network.RetrofitInstance;
import com.alee.paradow.utils.Links;
import com.alee.paradow.utils.StringRefactor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment
        implements HomeCardsAdapterMLikes.ListItemClickListener , HomeCardsAdapterMViewed.ListItemClickListener{
    @BindView(R.id.rv_recommends)
    RecyclerView rvRecommends;

    @BindView(R.id.rv_popular)
    RecyclerView rvPopulars;

    @BindView(R.id.progress_bar_rec)
    ProgressBar recProgressBar;

    @BindView(R.id.progress_bar_pop)
    ProgressBar popProgressBar;

    private String userFav;
    private List<Long> userLikes;

    int flag = 0;

    private Long userID;
    private RecyclerView.LayoutManager mLayoutManager;

    private HomeCardsAdapterMViewed mRecAdapter;
    private HomeCardsAdapterMLikes mPopAdapter;

    private Unbinder unbinder;
    private List<MostLike.Message> homeCardsMLikes;
    private List<MostLike.Message> homeCardsMViewed;

    public HomeFragment() {
    }

    public HomeFragment(Long userID) {
        this.userID = userID;
    }

    //TODO: Impalement like save on home
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);


        callRecommended();
        callMostLike();

        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    private void callUserData() {
        if(flag != 2){
            return;
        }
        flag =0;
        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        Call<FavRes> call = service.Favs( userID );
        call.enqueue(new Callback<FavRes>() {
            @Override
            public void onResponse(Call<FavRes> call, Response<FavRes> response) {

                if (response.code()==400)
                {
                    Log.i("Response:","Favorites have a problem");
                    Toast.makeText(getContext(), "Favorites can't be loaded", Toast.LENGTH_LONG).show();
                }
                else if(response.code() == 200){
//                    SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
//                    preferences.edit().putString("token", response.body().getMessage().getRememberToken()).apply();
                    Log.i("Response Favs:", String.valueOf(response.body().getMessage().getUserFavourite()));

                    userFav = response.body().getMessage().getUserFavourite();
                    if (userFav!=null){
                        userLikes = StringRefactor.getuserFavList(userFav);
                        for (int i=0 ; i< homeCardsMViewed.size();i++){
                            if(userLikes.contains(homeCardsMViewed.get(i).getId())){
                                homeCardsMViewed.get(i).setLike(true);
                                Log.i("Response Favs:", homeCardsMViewed.get(i).getTitle());

                            }
                            else
                                homeCardsMViewed.get(i).setLike(false);
                        }
                        for (int i=0 ; i< homeCardsMLikes.size();i++){
                            if(userLikes.contains(homeCardsMLikes.get(i).getId())){
                                homeCardsMLikes.get(i).setLike(true);
                            }
                            else
                                homeCardsMLikes.get(i).setLike(false);
                        }
                        mRecAdapter.setData(homeCardsMViewed);
                        mPopAdapter.setData(homeCardsMLikes);

                    }

                }
                else{
                    Log.i("Response:","Server have a problem");
                }
            }

            @Override
            public void onFailure(Call<FavRes> call, Throwable t) {
                Log.i("Failure:",t.getMessage());
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        rvRecommends.setHasFixedSize(true);

        rvPopulars.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false);
        rvRecommends.setLayoutManager(mLayoutManager);

        mLayoutManager = new LinearLayoutManager(getContext() , LinearLayoutManager.HORIZONTAL , false);
        rvPopulars.setLayoutManager(mLayoutManager);

        mRecAdapter = new HomeCardsAdapterMViewed( this);
        rvRecommends.setAdapter(mRecAdapter);

        mPopAdapter = new HomeCardsAdapterMLikes( this);
        rvPopulars.setAdapter(mPopAdapter);
    }

    private void callRecommended() {
        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);

        Call<MostLike> call = service.Recommendations();
        call.enqueue(new Callback<MostLike>() {
            @Override
            public void onResponse(Call<MostLike> call, Response<MostLike> response) {

                if (response.code()==400)
                {
                    Log.i("Response:","Recommendations have a problem");
                    Toast.makeText(getContext(), "Recommendations can't be loaded", Toast.LENGTH_LONG).show();
                }
                else if (response.code() == 200){
//                    SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
//                    preferences.edit().putString("token", response.body().getMessage().getRememberToken()).apply();
                    Log.i("Response:",response.body().getMessage().get(0).getDescription());
                    homeCardsMViewed = response.body().getMessage();
                    publishRecommend(response.body());
                    flag += 1;
                    callUserData();
                }
                else{
                    Log.i("Response:","Server have a problem");
                }
            }

            @Override
            public void onFailure(Call<MostLike> call, Throwable t) {
                Log.i("Failure:",t.getMessage());
            }
        });
    }

    private void publishRecommend(MostLike body) {
        //TODO: Check for images
        Log.i("publishRecommend:","Start Publish");
        mRecAdapter.setData(body.getMessage());
        if(rvRecommends != null){
            rvRecommends.setVisibility(View.VISIBLE);
            recProgressBar.setVisibility(View.GONE);
        }

    }

    private void callMostLike(){
        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        Call<MostLike> call = service.mostliked();
        call.enqueue(new Callback<MostLike>() {
            @Override
            public void onResponse(Call<MostLike> call, Response<MostLike> response) {

                Log.i("Response:","Server  response code:"+response.code());

                if (response.code()==400)
                {
                    Log.i("Response:","MostLike have a problem");
                    Toast.makeText(getContext(), "MostLike can't be loaded", Toast.LENGTH_LONG).show();
                }
                else if(response.code() == 200){

//                    SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
//                    preferences.edit().putString("token", response.body().getMessage().getRememberToken()).apply();
                    Log.i("Response:",response.body().getMessage().get(0).getDescription());
                    homeCardsMLikes = response.body().getMessage();
                    publishMostLike(response.body());
                    flag += 1;
                    callUserData();

                }
                else{
                    Log.i("Response:","Server have a problem");
                }
            }

            @Override
            public void onFailure(Call<MostLike> call, Throwable t) {
                Log.i("Failure:",t.getMessage());
            }
        });

    }

    private void publishMostLike(MostLike body) {
        Log.i("publishMostLike:","Start Publish");
        mPopAdapter.setData(body.getMessage());
        if(rvPopulars != null){
            rvPopulars.setVisibility(View.VISIBLE);
            popProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onMostLikeClick(int clickedItemIndex) {
        //dTODO(4):open the image
        Log.i("Home ", "MostLike Clicked ");
        Log.i("Home ", "MostLike Clicked " +homeCardsMLikes.get(clickedItemIndex).getImage());
        Log.i("Home ", "MostLike Clicked " +homeCardsMLikes.get(clickedItemIndex).getTitle());

        Intent i = new Intent(getContext() , ReviewActivity.class);

        if(homeCardsMLikes.get(clickedItemIndex) != null){
            i.putExtra("id",homeCardsMLikes.get(clickedItemIndex).getId());
            i.putExtra("catName",homeCardsMLikes.get(clickedItemIndex).getCategoryName());
            i.putExtra("title",homeCardsMLikes.get(clickedItemIndex).getTitle());
            i.putExtra("price",homeCardsMLikes.get(clickedItemIndex).getPriceAfterOff());
            i.putExtra("image",homeCardsMLikes.get(clickedItemIndex).getImage());
            i.putExtra("image_on_wall",homeCardsMLikes.get(clickedItemIndex).getImageOnWall());
            i.putExtra("n_color",homeCardsMLikes.get(clickedItemIndex).getNoOfColor());
            i.putExtra("n_fav",homeCardsMLikes.get(clickedItemIndex).getFavProduct());

            //Just to add as a view
            Links.calImageView(homeCardsMLikes.get(clickedItemIndex).getId());
            startActivity(i);
        }


    }

    @Override
    public void onMostViewedClick(int clickedItemIndex) {
//dTODO(4):open the image
        Log.i("Home ", "MostViewed Clicked ");
        Log.i("Home ", "MostViewed Clicked " +homeCardsMViewed.get(clickedItemIndex).getImage());
        Log.i("Home ", "MostViewed Clicked " +homeCardsMViewed.get(clickedItemIndex).getTitle());

        Intent i = new Intent(getContext() , ReviewActivity.class);

        if(homeCardsMViewed.get(clickedItemIndex) != null){
            i.putExtra("id",homeCardsMViewed.get(clickedItemIndex).getId());
            i.putExtra("catName",homeCardsMViewed.get(clickedItemIndex).getCategoryName());
            i.putExtra("title",homeCardsMViewed.get(clickedItemIndex).getTitle());
            i.putExtra("price",homeCardsMViewed.get(clickedItemIndex).getPriceAfterOff());
            i.putExtra("image",homeCardsMViewed.get(clickedItemIndex).getImage());
            i.putExtra("image_on_wall",homeCardsMViewed.get(clickedItemIndex).getImageOnWall());
            i.putExtra("n_color",homeCardsMViewed.get(clickedItemIndex).getNoOfColor());
            i.putExtra("n_fav",homeCardsMViewed.get(clickedItemIndex).getFavProduct());
            //Just to add as a view
            Links.calImageView(homeCardsMViewed.get(clickedItemIndex).getId());

            startActivity(i);
        }
    }
}
