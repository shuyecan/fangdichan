package fangdichan.com.fangdichan;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
import butterknife.OnClick;
import fangdichan.com.fangdichan.been.BaseBeen;
import fangdichan.com.fangdichan.been.UserBeen;

public class UpdateUserActivity extends AppCompatActivity {

    @BindView(R.id.update_password)
    EditText updatePassword;
    @BindView(R.id.update_password2)
    EditText updatePassword2;
    @BindView(R.id.submit)
    Button submit;
    String uid="";
    String username="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);
        ButterKnife.bind(this);
        inittooler();
        uid = getIntent().getStringExtra("uid");
        username = getIntent().getStringExtra("username");
    }


    private void inittooler() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("修改密码");
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

    @OnClick(R.id.submit)
    public void onViewClicked() {
        String pwd = updatePassword.getText().toString();
        String pwd2 = updatePassword2.getText().toString();
        if(pwd.equals(pwd2)){
            List<UserBeen> userBeenList = LitePal.findAll(UserBeen.class);
            RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/updaUserInfo");

            params.addQueryStringParameter("password",pwd2);
            if(uid==null||uid.equals("")){
                params.addQueryStringParameter("userId",userBeenList.get(0).getUserId());
                params.addQueryStringParameter("userName",userBeenList.get(0).getUserName());
            }else {
                params.addQueryStringParameter("userId",uid);
                params.addQueryStringParameter("userName",username);
            }
            x.http().get(params, new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    if(!"[]".equals(result)){
                        Gson gs = new Gson();
                        List<BaseBeen> list =  gs.fromJson(result,new TypeToken<List<BaseBeen>>(){}.getType());
                        BaseBeen baseBeen = list.get(0);
                        if(baseBeen.getStatus().equals("200")){
                            Toast.makeText(UpdateUserActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(UpdateUserActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(UpdateUserActivity.this, "修改失败！", Toast.LENGTH_SHORT).show();
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
        }else {
            Toast.makeText(this, "两次输入密码不一致！", Toast.LENGTH_SHORT).show();
        }
    }
}
