package fangdichan.com.fangdichan;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fangdichan.com.fangdichan.been.BaseBeen;
import fangdichan.com.fangdichan.been.Homelistbeen;

public class UpdateHomeActivity extends AppCompatActivity {

    @BindView(R.id.propertyName)
    EditText edpropertyName;
    @BindView(R.id.price)
    EditText edprice;
    @BindView(R.id.phone)
    EditText edphone;
    @BindView(R.id.propeSize)
    EditText edpropeSize;
    @BindView(R.id.propeIntrote)
    EditText edpropeIntrote;
    @BindView(R.id.desc)
    EditText eddesc;
    @BindView(R.id.submit)
    Button submit;
    String homeId;
    Homelistbeen homelistbeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        ButterKnife.bind(this);
        inittooler();
        homeId = getIntent().getStringExtra("homeId");
        initdata();
    }

    private void initdata() {
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/getProperInfo");
        params.addQueryStringParameter("homeId",homeId);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (result != "[]") {
                    Gson gson = new Gson();
                    List<Homelistbeen> list = gson.fromJson(result, new TypeToken<List<Homelistbeen>>() {
                    }.getType());
                    homelistbeen = list.get(0);
                    edpropertyName.setText(homelistbeen.getPropertyName());
                    edprice.setText(homelistbeen.getPrice());
                    edphone.setText(homelistbeen.getPhone());
                    edpropeSize.setText(homelistbeen.getPropeSize());
                    edpropeIntrote.setText(homelistbeen.getPropeIntrote());
                    eddesc.setText(homelistbeen.getDescSrc());
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

    @OnClick(R.id.submit)
    public void onViewClicked() {

        String propertyName  = edpropertyName.getText().toString();
        String price = edprice.getText().toString();
        String phone = edphone.getText().toString();
        String propeSize = edpropeSize.getText().toString();
        String propeIntrote = edpropeIntrote.getText().toString();
        String desc = eddesc.getText().toString();
        if(propertyName.isEmpty()||price.isEmpty()||phone.isEmpty()||propeSize.isEmpty()||propeIntrote.isEmpty()||desc.isEmpty()){
            Toast.makeText(UpdateHomeActivity.this, "请填写完整信息！", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/updateProperInfo");
        params.addQueryStringParameter("homeId", homeId);
        params.addQueryStringParameter("propertyName",propertyName);
        params.addQueryStringParameter("price",price);
        params.addQueryStringParameter("phone",phone);
        params.addQueryStringParameter("propeSize",propeSize);
        params.addQueryStringParameter("propeIntrote",propeIntrote);
        params.addQueryStringParameter("descSrc",desc);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(!"[]".equals(result)){
                    Gson gs = new Gson();
                    List<BaseBeen> list =  gs.fromJson(result,new TypeToken<List<BaseBeen>>(){}.getType());
                    BaseBeen baseBeen = list.get(0);
                    if(baseBeen.getStatus().equals("200")){
                        Toast.makeText(UpdateHomeActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(UpdateHomeActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(UpdateHomeActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
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
        toolbar.setTitle("修改信息");
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
