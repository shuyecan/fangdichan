package fangdichan.com.fangdichan.Fragment;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fangdichan.com.fangdichan.HomelistActivity;
import fangdichan.com.fangdichan.LikehomeActivity;
import fangdichan.com.fangdichan.LoginActivity;
import fangdichan.com.fangdichan.R;
import fangdichan.com.fangdichan.UpdateUserActivity;
import fangdichan.com.fangdichan.UserlistActivity;
import fangdichan.com.fangdichan.apther.HomelistAdpther;
import fangdichan.com.fangdichan.been.UserBeen;

public class MyFragment extends Fragment {
    View view;
    @BindView(R.id.linear_user)
    RelativeLayout linearUser;
    @BindView(R.id.driver_type)
    TextView driverType;
    @BindView(R.id.driver_username)
    TextView driverUsername;
    @BindView(R.id.user_loginout)
    Button user_loginout;
    UserBeen userBeen;
    @BindView(R.id.line_password)
    LinearLayout linePassword;
    @BindView(R.id.line_likehome)
    LinearLayout lineLikehome;
    @BindView(R.id.line_usersett)
    LinearLayout lineUsersett;
    @BindView(R.id.line_homesett)
    LinearLayout lineHomesett;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.from(getActivity()).inflate(R.layout.fragment_my, null);
            ButterKnife.bind(this, view);
            initView();
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    private void initView() {
        List<UserBeen> list = LitePal.findAll(UserBeen.class);
        if (list.size() > 0) {
            userBeen = list.get(0);
            driverUsername.setText(userBeen.getUserName());
            if (userBeen.getType().equals("1")) {
                driverType.setText("普通用户");
            } else if (userBeen.getType().equals("2")) {
                driverType.setText("管理员");
            }
        }
        linearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (driverUsername.getText().equals("请先登录")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 102);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            List<UserBeen> list = LitePal.findAll(UserBeen.class);
            if (list.size() > 0) {
                userBeen = list.get(0);
                driverUsername.setText(userBeen.getUserName());
                if (userBeen.getType().equals("1")) {
                    driverType.setText("普通用户");
                } else if (userBeen.getType().equals("2")) {
                    driverType.setText("管理员");
                }
            }
        }
    }

    @OnClick({R.id.line_password, R.id.line_likehome, R.id.line_usersett, R.id.line_homesett, R.id.user_loginout})
    public void onViewClicked(View view) {
        if(userBeen==null){
            Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent;
        switch (view.getId()) {

            case R.id.line_password:
                intent = new Intent(getActivity(), UpdateUserActivity.class);
                startActivity(intent);
                break;
            case R.id.line_likehome:
                intent = new Intent(getActivity(), LikehomeActivity.class);
                startActivity(intent);
                break;
            case R.id.line_usersett:
                if(userBeen.getType().equals("2")){
                    intent = new Intent(getActivity(), UserlistActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "您不是管理员", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.line_homesett:
                if(userBeen.getType().equals("2")){
                    intent = new Intent(getActivity(), HomelistActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getActivity(), "您不是管理员", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.user_loginout:

                LitePal.deleteAll(UserBeen.class);
                driverUsername.setText("请先登录");
                driverType.setText("未登录");
                break;
        }
    }
}
