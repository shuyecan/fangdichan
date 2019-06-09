package fangdichan.com.fangdichan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import butterknife.OnClick;
import fangdichan.com.fangdichan.been.BaseBeen;
import fangdichan.com.fangdichan.been.UserBeen;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.text_zhuce)
    TextView textZhuce;
    @BindView(R.id.login_phone)
    EditText loginPhone;
    @BindView(R.id.login_pass)
    EditText loginPass;
    @BindView(R.id.submit)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        inittooler();
    }

    private void inittooler() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("登录");
        setSupportActionBar(toolbar);
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

    @OnClick({R.id.text_zhuce,R.id.submit})
    public void onViewClicked(View view) {
        Intent intent;
            switch (view.getId()){
                case R.id.text_zhuce:
                intent = new Intent(LoginActivity.this,ReistActivity.class);
                startActivity(intent);
                    break;

                case R.id.submit:
                    String userName = loginPhone.getText().toString();
                    String password = loginPass.getText().toString();
                    String type = "1";
                    RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/getUserInfo");
                    params.addQueryStringParameter("userName",userName);
                    params.addQueryStringParameter("password",password);
                    params.addQueryStringParameter("type",type);
                    x.http().get(params, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            if(!"[]".equals(result)){
                                Gson gs = new Gson();
                                List<UserBeen> list =  gs.fromJson(result,new TypeToken<List<UserBeen>>(){}.getType());
                                UserBeen userBeen = list.get(0);
                                boolean is = userBeen.save();
                                Toast.makeText(LoginActivity.this, "登陆成功！", Toast.LENGTH_SHORT).show();
                                setResult(200);
                                finish();
                            }else {
                                Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
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
                    break;
            }
    }

}
