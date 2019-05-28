package fangdichan.com.fangdichan.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import fangdichan.com.fangdichan.R;
import fangdichan.com.fangdichan.apther.HomeAdpther;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.from(getActivity()).inflate(R.layout.fragemnt_home, null);
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
        for (int i = 0; i < 50; i++) {
            homebeen home = new homebeen();
            home.setAddress("广州天河");
            home.setMoney("39000/㎡");
            home.setImgurl(R.mipmap.home1);
            homelists.add(home);
        }

        adapter = new HomeAdpther(homelists, getActivity());

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyList.setLayoutManager(layoutManager);
        recyList.setAdapter(adapter);
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
                Toast.makeText(getActivity(), "dd", Toast.LENGTH_SHORT).show();
                break;
        }

    }
}