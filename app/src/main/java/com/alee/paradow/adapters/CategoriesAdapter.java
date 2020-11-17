package com.alee.paradow.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alee.paradow.R;
import com.alee.paradow.model.CategoryRes;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alee.paradow.utils.Links.IMAGE_BASE_URL;


public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }


    private List<CategoryRes.Message> categories ;
    private Context context;

    public CategoriesAdapter(ListItemClickListener itemClickListener) {
        mOnClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.categories_list_item, parent, false);

        return  new CategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
/*
        Glide.with(context)
                .load(IMAGE_BASE_URL + categories.get(i).getImage())
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher)
//                .transform(new BlurTransformation())
                .into(holder.imageView);
*/

        Log.i("Catigory Frag"  , categories.get(i).getImage() );



        GlideApp.with(context)
                .load(IMAGE_BASE_URL + categories.get(i).getImage())
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);

        holder.mName.setText(categories.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return categories==null?0:categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.r_img)
        ImageView imageView;
        @BindView(R.id.r_txt)
        TextView mName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickPosition);
        }
    }
    public void setData(List<CategoryRes.Message> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

}
