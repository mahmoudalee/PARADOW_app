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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alee.paradow.Activities.CategoryActivity;
import com.alee.paradow.R;
import com.alee.paradow.adapters.CategoriesAdapter;
import com.alee.paradow.model.CategoryRes;
import com.alee.paradow.model.Datum;
import com.alee.paradow.network.DataService;
import com.alee.paradow.network.RetrofitInstance;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragment extends Fragment
        implements CategoriesAdapter.ListItemClickListener{

    @BindView(R.id.rv_categories)
    RecyclerView mRecyclerView;

    @BindView(R.id.nav)
    LinearLayout nav;

    GridLayoutManager gridLayoutManager;

    CategoriesAdapter mAdapter;

    private Unbinder unbinder;
    List<CategoryRes.Message> categories;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);

        callCategories();
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(getContext() ,2);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mAdapter = new CategoriesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                if(dy > 0){
//                    nav.setVisibility(View.GONE);
//                }
//                else{
//                    nav.setVisibility(View.VISIBLE);
//                }
//            }
//        });

    }


    private void callCategories() {
        DataService service = RetrofitInstance.getRetrofitInstance().create(DataService.class);
        Call<CategoryRes> call = service.Categories();
        call.enqueue(new Callback<CategoryRes>() {
            @Override
            public void onResponse(Call<CategoryRes> call, Response<CategoryRes> response) {

                if (response.code()==400)
                {
                    Log.i("Response:","Categories have a problem");
                    Toast.makeText(getContext(), "Categories can't be loaded", Toast.LENGTH_LONG).show();
                }
                else if(response.code() == 200 & response.body() != null){
//                    SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
//                    preferences.edit().putString("token", response.body().getMessage().getRememberToken()).apply();

                    categories = response.body().getMessage();

                    Log.i("Response:",categories.get(0).getName());
                    publishCategories(response.body());
                }
                else{
                    Log.i("Response:","Server have a problem");
                }
            }

            @Override
            public void onFailure(@NotNull Call<CategoryRes> call, @NotNull Throwable t) {
                Log.i("Failure:", Objects.requireNonNull(t.getMessage()));
            }
        });

    }

    private void publishCategories(CategoryRes body) {
        Log.i("publishCategories:","Start Publish");
        mAdapter.setData(body.getMessage());
        if (mRecyclerView != null)
            mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        //dTODO(5):open the category
        Intent intent = new Intent(getContext(), CategoryActivity.class);

        ArrayList<Datum> catItems = (ArrayList<Datum>) categories.get(clickedItemIndex).getData();
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("catItems", catItems);
        intent.putExtras(bundle);

        startActivity(intent);

    }
}
