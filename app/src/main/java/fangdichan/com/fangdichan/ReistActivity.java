package fangdichan.com.fangdichan;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class ReistActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.reg_userName)
    EditText regUserName;
    @BindView(R.id.reg_Pwd)
    EditText regPwd;
    @BindView(R.id.reg_Name)
    EditText regName;
    @BindView(R.id.reg_Sex)
    EditText regSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        inittooler();
    }

    private void inittooler() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("注册");
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
        String userName = regUserName.getText().toString();
        String password = regPwd.getText().toString();
        String sex = regSex.getText().toString();
        String name = regName.getText().toString();
        String type = "1";
        if(userName.isEmpty()&&password.isEmpty()&&sex.isEmpty()&&name.isEmpty()){
            Toast.makeText(this, "请把信息填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
//        else if(!sex.equals("男")&&!sex.equals("女")){
//            Toast.makeText(this, "性别类型错误！", Toast.LENGTH_SHORT).show();
//            return;
//        }
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/addUserInfo");
        params.addQueryStringParameter("userName",userName);
        params.addQueryStringParameter("password",password);
        params.addQueryStringParameter("sex",sex);
        params.addQueryStringParameter("name",name);
        params.addQueryStringParameter("type",type);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if(!"[]".equals(result)){
                    Gson gs = new Gson();
                    List<BaseBeen> list =  gs.fromJson(result,new TypeToken<List<BaseBeen>>(){}.getType());
                    BaseBeen baseBeen = list.get(0);
                    if(baseBeen.getStatus().equals("200")){
                        Toast.makeText(ReistActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(ReistActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(ReistActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("debug",ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
        
    }
}
