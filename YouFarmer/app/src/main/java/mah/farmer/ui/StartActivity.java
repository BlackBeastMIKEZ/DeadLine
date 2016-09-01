package mah.farmer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.ListView;

import com.baidu.locTest.R;


import cn.bmob.v3.Bmob;
import mah.farmer.http.FarmerConfig;
import mah.farmer.view.CircleRefreshLayout;


/**用户启动页界面
 * Created by 黑色野兽迈特祖 on 2016/4/18.
 */
public class StartActivity extends ActivityBase {
    private CircleRefreshLayout mRefreshLayout;
    private ListView mList;
    private Button mStop;
    public static final int GO_GUIDE=0;
    public static final int GO_HOME=1;
    public static final int GO_LOGIN=2;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GO_GUIDE:
                    startActivity(new Intent(StartActivity.this,GuideActivity.class));
                    finish();
                    break;
                case GO_HOME:
                    startActivity(new Intent(StartActivity.this,FarmerMainActivity.class));
                    finish();
                    break;
                case GO_LOGIN:
                    startActivity(new Intent(StartActivity.this,FarmerLoginAcivity.class));
                  finish();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_start);
        Bmob.initialize(StartActivity.this, FarmerConfig.APP_ID);


        /**
         * 根据当前缓存判断是否该跳转到登陆界面，还是引导界面还是主界面
         */
        if(FarmerConfig.getCachedISfirstRun(StartActivity.this)==true){
            handler.sendEmptyMessageDelayed(GO_GUIDE, 2000);
        }
        else {
            if (FarmerConfig.getCachedFarmerNickname(StartActivity.this)!=null) {

                handler.sendEmptyMessageDelayed(GO_HOME, 2000);
            } else {
                handler.sendEmptyMessageDelayed(GO_LOGIN, 2000);
            }
        }


    }
}
