package fangdichan.com.fangdichan.apther;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import fangdichan.com.fangdichan.R;
import fangdichan.com.fangdichan.UpdateUserActivity;
import fangdichan.com.fangdichan.been.UserBeen;

public class UserAdpther extends RecyclerView.Adapter<UserAdpther.ViewHolder>{

    List<UserBeen> list;
    Context context;

    public UserAdpther(List<UserBeen> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public UserAdpther.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(context==null){
            context = viewGroup.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, viewGroup, false);
        final UserAdpther.ViewHolder holder = new UserAdpther.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdpther.ViewHolder viewHolder, final int i) {
        final UserBeen userBeen = list.get(i);
        viewHolder.text_username.setText(userBeen.getUserName());
        viewHolder.text_bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateUserActivity.class);
                intent.putExtra("uid",userBeen.getUserId());
                intent.putExtra("username",userBeen.getUserName());
                context.startActivity(intent);
            }
        });

        viewHolder.text_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dele(userBeen.getUserId(),i);
            }
        });
    }


    private void dele(String uid, final int p){
        RequestParams params = new RequestParams(context.getResources().getString(R.string.ip)+"/MybatisDemo/property/deleteUser");
        params.addQueryStringParameter("userId",uid);
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
        TextView text_username,text_bianji,text_shanchu;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView;
            text_username = itemView.findViewById(R.id.text_username);
            text_bianji = itemView.findViewById(R.id.text_bianji);
            text_shanchu = itemView.findViewById(R.id.text_shanchu);
        }
    }
}
