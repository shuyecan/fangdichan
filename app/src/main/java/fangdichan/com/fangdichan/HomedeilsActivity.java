package fangdichan.com.fangdichan;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fangdichan.com.fangdichan.been.BaseBeen;
import fangdichan.com.fangdichan.been.Homelistbeen;
import fangdichan.com.fangdichan.been.UserBeen;

public class HomedeilsActivity extends AppCompatActivity {

    String homeId;
    @BindView(R.id.text_propeSize)
    TextView textPropeSize;
    @BindView(R.id.text_propeIntrote)
    TextView textPropeIntrote;
    @BindView(R.id.text_desc)
    TextView textDesc;
    @BindView(R.id.img_bg)
    ImageView img_bg;
    private Homelistbeen homelistbeen;
    private  FloatingActionButton fab_float;
    private UserBeen userBeen;
    boolean ischeck = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homedeils);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("房源信息");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        homeId = getIntent().getStringExtra("homeId");
        initData();
        List<UserBeen> list  = LitePal.findAll(UserBeen.class);
        if(list.size()>0){
            userBeen = list.get(0);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userBeen!=null&&userBeen.getUserName()!=null&&!userBeen.getUserName().equals("")) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + homelistbeen.getPhone()));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                }else {
                    Toast.makeText(HomedeilsActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                }
            }
        });
         fab_float = findViewById(R.id.fab_float);
        fab_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userBeen==null){
                    Toast.makeText(HomedeilsActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestParams params = new RequestParams(getResources().getString(R.string.ip) + "/MybatisDemo/property/updateProperInfo");
                params.addQueryStringParameter("homeId", homeId);
                if(ischeck){
                    params.addQueryStringParameter("collectionUserId","0");
                }else {
                    params.addQueryStringParameter("collectionUserId", userBeen.getUserId());
                }
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        if(!"[]".equals(result)){
                            Gson gs = new Gson();
                            List<BaseBeen> list =  gs.fromJson(result,new TypeToken<List<BaseBeen>>(){}.getType());
                            BaseBeen baseBeen = list.get(0);
                            if(baseBeen.getStatus().equals("200")){
                                if(ischeck){
                                    fab_float.setImageResource(R.drawable.ic_favorite_black_24dp);
                                    ischeck=false;
                                }else {
                                    fab_float.setImageResource(R.drawable.ic_favorite_red_24dp);
                                    ischeck=true;
                                }

                            }else {
                                Toast.makeText(HomedeilsActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(HomedeilsActivity.this, "收藏失败！", Toast.LENGTH_SHORT).show();
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
        });
    }

    private void initData() {

        RequestParams params = new RequestParams(getResources().getString(R.string.ip) + "/MybatisDemo/property/getProperInfo");
        params.addQueryStringParameter("homeId", homeId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != "[]") {
                    Gson gson = new Gson();
                    List<Homelistbeen> list = gson.fromJson(result, new TypeToken<List<Homelistbeen>>() {
                    }.getType());
                     homelistbeen = list.get(0);
                    textPropeSize.setText(homelistbeen.getPropeSize());
                    textPropeIntrote.setText(homelistbeen.getPropeIntrote());
                    textDesc.setText(homelistbeen.getDescSrc());

                    if(homelistbeen.getPictureName().equals("1")) {
                        Glide.with(HomedeilsActivity.this).load(R.mipmap.home1).into(img_bg);
                    }else if(homelistbeen.getPictureName().equals("2")){
                        Glide.with(HomedeilsActivity.this).load(R.mipmap.home2).into(img_bg);
                    }else if(homelistbeen.getPictureName().equals("3")){
                        Glide.with(HomedeilsActivity.this).load(R.mipmap.home3).into(img_bg);
                    }
                    if(userBeen!=null) {
                        if (homelistbeen.getCollectionUserId() .equals( userBeen.getUserId())) {
                           fab_float.setImageResource(R.drawable.ic_favorite_red_24dp);
                            ischeck = true;
                        }
                    }
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
