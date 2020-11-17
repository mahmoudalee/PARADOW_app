package com.alee.paradow.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.alee.paradow.R;
import com.alee.paradow.adapters.ViewPagerAdapter;
import com.alee.paradow.databinding.ActivityReviewBinding;
import com.alee.paradow.utils.StringRefactor;


public class ReviewActivity extends AppCompatActivity {

    Long id;
    String catName,title,price,image,nColor,nFavs,image_on_wall;

    private int dotscount;
    private ImageView[] dots;


    ActivityReviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);
        catName = intent.getStringExtra("catName");
        title = intent.getStringExtra("title");
        price =intent.getStringExtra("price");
        image = intent.getStringExtra("image");
        image_on_wall = intent.getStringExtra("image_on_wall");
        nColor = intent.getStringExtra("n_color");
        nFavs = intent.getStringExtra("n_fav");

//        Toast.makeText(this, "ReviewActivity "+id+":id , "+title+":title", Toast.LENGTH_LONG).show();
        makeViewPager();

        binding.reviewCatNameTxt.setText(StringRefactor.getENstring(title));
        binding.imageNameTxt.setText(catName);

    }

    private void makeViewPager() {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this,image, image_on_wall);

        binding.viewPager.setAdapter(viewPagerAdapter);

        //viewPagerAdapter.getCount() returns the number of images that add in ViewPagerAdapter class
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        // used to create the dots that indicate which photo shown in home
        for(int i = 0; i < dotscount; i++){
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 4);

            binding.SliderDots.addView(dots[i], params);
        }
        //set the default active to the first dot
        dots[0].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                //used to change the position of the active dot by set all of them non-active
                //  then set the active one useing the position
                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(ReviewActivity.this, R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(ReviewActivity.this, R.drawable.active_dot));
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void choose(View view) {
        //TODO:Implement device connection
        Intent intent = new Intent(ReviewActivity.this,ControlActivity.class);
        intent.putExtra("image" , image);
        startActivity(intent);

    }

    public void Augmented(View view) {
        //TODO:AR over here
        Intent intent = new Intent(ReviewActivity.this,AR_Activity.class);
        intent.putExtra("image" , image);
        startActivity(intent);
    }
}
