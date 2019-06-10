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

import org.litepal.LitePal;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fangdichan.com.fangdichan.apther.HomeAdpther;
import fangdichan.com.fangdichan.been.Homelistbeen;
import fangdichan.com.fangdichan.been.UserBeen;
import fangdichan.com.fangdichan.been.homebeen;

public class LikehomeActivity extends AppCompatActivity {

    @BindView(R.id.recy_list)
    RecyclerView recyList;
    List<homebeen> homelists = new ArrayList<>();
    HomeAdpther adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_likehome);
        ButterKnife.bind(this);
        inittooler();

        initView();
        adapter = new HomeAdpther(homelists, LikehomeActivity.this);
        GridLayoutManager layoutManager = new GridLayoutManager(LikehomeActivity.this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setLayoutManager(layoutManager);
        recyList.setAdapter(adapter);
    }

    private void initView() {
        List<UserBeen> userBeenList = LitePal.findAll(UserBeen.class);
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/getProperInfo");
        params.addQueryStringParameter("collectionUserId",userBeenList.get(0).getUserId());
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                if(!result.equals("[]")) {
                    List<Homelistbeen> list = gs.fromJson(result, new TypeToken<List<Homelistbeen>>() {
                    }.getType());
                    for (int i =0;i<list.size();i++){
                        homebeen home =  new homebeen();
                        home.setAddress(list.get(i).getPropertyName());
                        home.setHomeid(list.get(i).getHomeId());
                        home.setMoney(list.get(i).getPrice()+"m²");
                        home.setImgurl(list.get(i).getPictureName());
                        homelists.add(home);
                    }
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
        toolbar.setTitle("收藏的房子");
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
