package com.alee.paradow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alee.paradow.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp;

import org.jetbrains.annotations.NotNull;

import static com.alee.paradow.utils.Links.IMAGE_BASE_URL;


public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private String image , image_on_wall;
    //this photos that going to be shown in the ViewPager
//    private Integer[] images = {R.drawable.slide1,R.drawable.slide2,R.drawable.slide3};

    public ViewPagerAdapter(Context context, String image, String image_on_wall) {
        this.context = context;
        this.image = image;
        this.image_on_wall = image_on_wall;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NotNull
    @Override
    public Object instantiateItem(@NotNull ViewGroup container, final int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.review_pager_layout, container ,false);
        ImageView imageView = view.findViewById(R.id.imageView);
//        imageView.setImageResource(images[position]);

        GlideApp.with(context)
                .load(IMAGE_BASE_URL +( (position==0)?image:image_on_wall ) )
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher)
                .into(imageView);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}