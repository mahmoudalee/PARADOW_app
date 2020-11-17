package com.alee.paradow.frags;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alee.paradow.R;
import com.alee.paradow.Activities.ReviewActivity;
import com.alee.paradow.adapters.FavsAdapter;
import com.alee.paradow.model.FavRes;
import com.alee.paradow.model.UserFav;
import com.alee.paradow.network.DataService;
import com.alee.paradow.network.RetrofitInstance;
import com.alee.paradow.utils.Links;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesFragment extends Fragment implements FavsAdapter.ListItemClickListener{


    @BindView(R.id.favorites)
    RecyclerView mRecyclerView;

    //TODO(6) some edit if needed to random hight
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    FavsAdapter mAdapter;

    private Unbinder unbinder;
    private Long userID;
    List<UserFav> userFavs;

    public FavoritesFragment() {
    }

    public FavoritesFragment(Long id) {
        userID = id;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);


        callFavs();
        unbinder = ButterKnife.bind(this, rootView);


        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mRecyclerView.setHasFixedSize(true);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL);

        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);

        mAdapter = new FavsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }



    private void callFavs() {
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
                    Log.i("Response Favs:", String.valueOf(response.body().getMessage().getUserFav()));
                    userFavs = response.body().getMessage().getUserFav();
                    publishFavs();
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

    private void publishFavs() {
        Log.i("publishFavorites:","Start Publish");
        while (userFavs.remove(null)) {
        }
        mAdapter.setData(userFavs);
        if(mRecyclerView != null){
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        //TODO(5):on image in Category clicked

        Log.i("Home ", "categoryItem Clicked ");
        Log.i("Home ", "categoryItem Clicked " +userFavs.get(clickedItemIndex).getImage());
        Log.i("Home ", "categoryItem Clicked " +userFavs.get(clickedItemIndex).getTitle());

        Intent i = new Intent(getContext() , ReviewActivity.class);

        if(userFavs.get(clickedItemIndex) != null){
            i.putExtra("id",userFavs.get(clickedItemIndex).getId());
            i.putExtra("catName",userFavs.get(clickedItemIndex).getCategoryName());
            i.putExtra("title",userFavs.get(clickedItemIndex).getTitle());
            i.putExtra("price",userFavs.get(clickedItemIndex).getPriceAfterOff());
            i.putExtra("image",userFavs.get(clickedItemIndex).getImage());
            i.putExtra("image_on_wall",userFavs.get(clickedItemIndex).getImageOnWall());
            i.putExtra("n_color",userFavs.get(clickedItemIndex).getNoOfColor());
            i.putExtra("n_fav",userFavs.get(clickedItemIndex).getFavProduct());
            //Just to add as a view
            Links.calImageView(userFavs.get(clickedItemIndex).getId());

            startActivity(i);
        }
    }
}
