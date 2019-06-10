package fangdichan.com.fangdichan.apther;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fangdichan.com.fangdichan.HomedeilsActivity;
import fangdichan.com.fangdichan.R;
import fangdichan.com.fangdichan.been.homebeen;

public class HomeAdpther extends RecyclerView.Adapter<HomeAdpther.ViewHolder> {
    List<homebeen> list;
    Context context;

    public HomeAdpther(List<homebeen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context==null){
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_mian_home, viewGroup, false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final homebeen home = list.get(i);
        viewHolder.text_home_address.setText(home.getAddress());
        viewHolder.text_home_money.setText(home.getMoney());
        if(home.getImgurl().equals("1")) {
            Glide.with(context).load(R.mipmap.home1).into(viewHolder.img_home_bg);
        }
        viewHolder.card_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,HomedeilsActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((Activity) context,
                                viewHolder.img_home_bg,"img_home_bg");
                intent.putExtra("homeId",home.getHomeid());
                context.startActivity(intent,options.toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_home_address,text_home_money;
        ImageView img_home_bg;
        CardView card_home;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_home = (CardView) itemView;
            text_home_address = itemView.findViewById(R.id.text_home_address);
            text_home_money = itemView.findViewById(R.id.text_home_money);
            img_home_bg = itemView.findViewById(R.id.img_home_bg);
        }
    }
}
