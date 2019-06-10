package fangdichan.com.fangdichan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fangdichan.com.fangdichan.LoginActivity;
import fangdichan.com.fangdichan.R;
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
            if(userBeen.getType().equals("1")){
                driverType.setText("普通用户");
            }else if(userBeen.getType().equals("2")){
                driverType.setText("管理员");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            List<UserBeen> list = LitePal.findAll(UserBeen.class);
            if (list.size() > 0) {
                UserBeen userBeen = list.get(0);
                driverUsername.setText(userBeen.getUserName());
                if(userBeen.getType().equals("1")){
                    driverType.setText("普通用户");
                }else if(userBeen.getType().equals("2")){
                    driverType.setText("管理员");
                }
            }
        }
    }

    @OnClick({R.id.linear_user,R.id.user_loginout})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.user_loginout:

                LitePal.deleteAll(UserBeen.class);
                driverUsername.setText("请先登录");
                driverType.setText("未登录");
                break;
            case R.id.linear_user:
                if(driverUsername.getText().equals("请先登录")) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivityForResult(intent, 102);
                }
                break;
        }

    }

}
