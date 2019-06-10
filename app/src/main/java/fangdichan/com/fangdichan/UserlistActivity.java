package fangdichan.com.fangdichan;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fangdichan.com.fangdichan.apther.UserAdpther;
import fangdichan.com.fangdichan.been.Homelistbeen;
import fangdichan.com.fangdichan.been.UserBeen;
import fangdichan.com.fangdichan.been.homebeen;

public class UserlistActivity extends AppCompatActivity {

    @BindView(R.id.recy_user)
    RecyclerView recyUser;
    List<UserBeen> userlist = new ArrayList<>();
    UserAdpther adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlist);
        ButterKnife.bind(this);
        inittooler();
        initData();
        adapter = new UserAdpther(userlist, UserlistActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(UserlistActivity.this, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyUser.setLayoutManager(layoutManager);
        recyUser.setAdapter(adapter);
    }

    private void initData() {
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/getUserInfo");
        params.addQueryStringParameter("type","1");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                if(!result.equals("[]")) {
                    List<UserBeen> list = gs.fromJson(result, new TypeToken<List<UserBeen>>() {
                    }.getType());
                    userlist.clear();
                    userlist.addAll(list);
                    adapter.notifyDataSetChanged();
                }
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


    private void inittooler() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("用户管理");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}
