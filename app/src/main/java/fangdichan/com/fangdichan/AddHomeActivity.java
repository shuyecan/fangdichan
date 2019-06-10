package fangdichan.com.fangdichan;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import fangdichan.com.fangdichan.been.BaseBeen;
import fangdichan.com.fangdichan.been.UserBeen;

public class AddHomeActivity extends AppCompatActivity {

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
    UserBeen userBeen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home);
        ButterKnife.bind(this);
        inittooler();
        initdata();
        userBeen = LitePal.findAll(UserBeen.class).get(0);
    }

    private void initdata() {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String propertyName  = edpropertyName.getText().toString();
                    String price = edprice.getText().toString();
                    String phone = edphone.getText().toString();
                    String propeSize = edpropeSize.getText().toString();
                    String propeIntrote = edpropeIntrote.getText().toString();
                    String desc = eddesc.getText().toString();
                    if(propertyName.isEmpty()||price.isEmpty()||phone.isEmpty()||propeSize.isEmpty()||propeIntrote.isEmpty()||desc.isEmpty()){
                        Toast.makeText(AddHomeActivity.this, "请填写完整信息！", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/addProperInfo");
                    params.addQueryStringParameter("propertyName",propertyName);
                    params.addQueryStringParameter("price",price);
                    params.addQueryStringParameter("phone",phone);
                    params.addQueryStringParameter("propeSize",propeSize);
                    params.addQueryStringParameter("propeIntrote",propeIntrote);
                    params.addQueryStringParameter("pictureName", String.valueOf((int) (1+Math.random()*(3-1+1))));
                    params.addQueryStringParameter("userId",userBeen.getUserId());
                    params.addQueryStringParameter("collectionUserId","0");
                    params.addQueryStringParameter("desc",desc);
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if(!"[]".equals(result)){
                                Gson gs = new Gson();
                                List<BaseBeen> list =  gs.fromJson(result,new TypeToken<List<BaseBeen>>(){}.getType());
                                BaseBeen baseBeen = list.get(0);
                                if(baseBeen.getStatus().equals("200")){
                                    Toast.makeText(AddHomeActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(AddHomeActivity.this, "发布失败！", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(AddHomeActivity.this, "发布失败！", Toast.LENGTH_SHORT).show();
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




    private void inittooler() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("发布新房");
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
