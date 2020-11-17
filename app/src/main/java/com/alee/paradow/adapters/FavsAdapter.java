package com.alee.paradow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alee.paradow.R;
import com.alee.paradow.model.UserFav;
import com.alee.paradow.utils.LikeClick;
import com.alee.paradow.utils.StringRefactor;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.alee.paradow.utils.Links.IMAGE_BASE_URL;

public class FavsAdapter extends RecyclerView.Adapter<FavsAdapter.ViewHolder> {

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    private List<UserFav> favs;
    private Context context;

    public FavsAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        int layoutIdForListItem = R.layout.fav_item;

        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
//        holder.imageView.setImageResource(mostLikes.get(i).getImage());

        GlideApp.with(context)
                .load(IMAGE_BASE_URL + favs.get(i).getImage())
                .placeholder(R.drawable.loading)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView);

        //dTODO:(2) make the string more readable
        holder.mName.setText(StringRefactor.getENstring(favs.get(i).getTitle()));

        holder.mDiscription.setText(favs.get(i).getRate());

        holder.fav.setOnClickListener(v -> {
            //TODO:(1) Implement the love action
            Long id = favs.get(holder.getAdapterPosition()).getId();
            LikeClick.loveAction(v, holder.fav, id, context );

            //TODO: Check to maje dislike disappear
//                favs.remove(holder.getAdapterPosition());
//                notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        return favs==null?0:favs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{


        @BindView(R.id.image_card_view)
        ImageView imageView;

        // that 's liked
        @BindView(R.id.fav)
        ImageView fav;

        @BindView(R.id.name_card_view)
        TextView mName;
        @BindView(R.id.description_card_view)
        TextView mDiscription;

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

    public void setData(List<UserFav> favs) {
        this.favs = favs;
        notifyDataSetChanged();
    }
}
