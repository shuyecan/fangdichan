package fangdichan.com.fangdichan;

import android.app.Application;

import org.litepal.LitePal;
import org.xutils.x;

public class Myapplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        LitePal.initialize(this);
    }
}
