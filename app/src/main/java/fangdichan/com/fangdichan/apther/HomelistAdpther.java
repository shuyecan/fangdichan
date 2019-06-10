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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import fangdichan.com.fangdichan.R;
import fangdichan.com.fangdichan.UpdateHomeActivity;
import fangdichan.com.fangdichan.been.homebeen;

public class HomelistAdpther extends RecyclerView.Adapter<HomelistAdpther.ViewHolder> {
    List<homebeen> list;
    Context context;

    public HomelistAdpther(List<homebeen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context==null){
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_homelist, viewGroup, false);
        final HomelistAdpther.ViewHolder holder = new HomelistAdpther.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final homebeen home = list.get(i);
        viewHolder.text_home_address.setText(home.getAddress());
        viewHolder.text_home_money.setText(home.getMoney());
        if(home.getImgurl().equals("1")) {
            Glide.with(context).load(R.mipmap.home1).into(viewHolder.img_home_bg);
        }else if(home.getImgurl().equals("2")){
            Glide.with(context).load(R.mipmap.home2).into(viewHolder.img_home_bg);
        }else if(home.getImgurl().equals("3")){
            Glide.with(context).load(R.mipmap.home3).into(viewHolder.img_home_bg);
        }

        viewHolder.text_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dele(home.getHomeid(),i);
            }
        });

        viewHolder.text_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateHomeActivity.class);
                intent.putExtra("homeId",home.getHomeid());
                context.startActivity(intent);
            }
        });
    }



    private void dele(String homeId, final int p){
        RequestParams params = new RequestParams(context.getResources().getString(R.string.ip)+"/MybatisDemo/property/deleteProperInfo");
        params.addQueryStringParameter("homeId",homeId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                list.remove(p);
                notifyItemRemoved(p);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView text_home_address,text_home_money,text_bianji,text_shanchu;
        ImageView img_home_bg;
        CardView card_home;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card_home = (CardView) itemView;
            text_home_address = itemView.findViewById(R.id.text_home_address);
            text_home_money = itemView.findViewById(R.id.text_home_money);
            img_home_bg = itemView.findViewById(R.id.img_home_bg);
            text_bianji = itemView.findViewById(R.id.text_bianji);
            text_shanchu = itemView.findViewById(R.id.text_shanchu);
        }
    }
}
