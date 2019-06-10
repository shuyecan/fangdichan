package fangdichan.com.fangdichan.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.litepal.LitePal;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fangdichan.com.fangdichan.AddHomeActivity;
import fangdichan.com.fangdichan.R;
import fangdichan.com.fangdichan.apther.HomeAdpther;
import fangdichan.com.fangdichan.been.Homelistbeen;
import fangdichan.com.fangdichan.been.UserBeen;
import fangdichan.com.fangdichan.been.homebeen;

public class HomeFragment extends Fragment {
    View view;
    @BindView(R.id.recy_list)
    RecyclerView recyList;
    HomeAdpther adapter;
    List<homebeen> homelists = new ArrayList<>();
    @BindView(R.id.text_cityname)
    TextView textCityname;
    @BindView(R.id.rela_city)
    RelativeLayout relaCity;
    @BindView(R.id.float_btnadd)
    FloatingActionButton float_btnadd;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.from(getActivity()).inflate(R.layout.fragemnt_home, null);
            ButterKnife.bind(this, view);
            initView();
            adapter = new HomeAdpther(homelists, getActivity());
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyList.setLayoutManager(layoutManager);
            recyList.setAdapter(adapter);
            swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    initView();
                    swipe.setRefreshing(false);
                }
            });
        } else {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        return view;
    }

    private void initView() {
        RequestParams params = new RequestParams(getResources().getString(R.string.ip)+"/MybatisDemo/property/getProperInfo");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gs = new Gson();
                if(!result.equals("[]")) {
                    List<Homelistbeen> list = gs.fromJson(result, new TypeToken<List<Homelistbeen>>() {
                    }.getType());
                    homelists.clear();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initView();
    }

    @OnClick({R.id.rela_city,R.id.float_btnadd})
    public void onViewClicked(View view) {
        switch (view.getId()){

            case R.id.rela_city:
                List<HotCity> hotCities = new ArrayList<>();
                hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
                hotCities.add(new HotCity("上海", "上海", "101020100"));
                hotCities.add(new HotCity("广州", "广东", "101280101"));
                hotCities.add(new HotCity("深圳", "广东", "101280601"));
                hotCities.add(new HotCity("杭州", "浙江", "101210101"));

                CityPicker.from(HomeFragment.this) //activity或者fragment
                        .setLocatedCity(new LocatedCity("广州", "广东", "101280101"))  //APP自身已定位的城市，传null会自动定位（默认）
                        .setHotCities(hotCities)	//指定热门城市
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                textCityname.setText(data.getName());
                            }

                            @Override
                            public void onLocate() {

                            }

                            @Override
                            public void onCancel(){

                            }
                        })
                        .show();
                break;

            case R.id.float_btnadd:
                List<UserBeen> list = LitePal.findAll(UserBeen.class);
                if(list.size()==0){
                    Toast.makeText(getActivity(), "请先登录！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(getActivity(),AddHomeActivity.class);
                startActivityForResult(intent,100);
                break;
        }

    }
}
