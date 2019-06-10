package fangdichan.com.fangdichan;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.LitePal;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private Homelistbeen homelistbeen;
    private UserBeen userBeen;
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
        userBeen = LitePal.findAll(UserBeen.class).get(0);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userBeen.getUserName()!=null&&!userBeen.getUserName().equals("")) {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + homelistbeen.getPhone()));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                }else {
                    Toast.makeText(HomedeilsActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        FloatingActionButton fab_float = findViewById(R.id.fab_float);
        fab_float.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams params = new RequestParams(getResources().getString(R.string.ip) + "/MybatisDemo/property/updateProperInfo");
                params.addQueryStringParameter("id", homeId);
            }
        });
    }

    private void initData() {

        RequestParams params = new RequestParams(getResources().getString(R.string.ip) + "/MybatisDemo/property/getProperInfo");
        params.addQueryStringParameter("id", homeId);
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
                    textDesc.setText(homelistbeen.getDesc());
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
