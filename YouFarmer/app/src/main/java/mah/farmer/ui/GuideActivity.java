package mah.farmer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.baidu.locTest.R;
import com.zdp.aseo.content.AseoZdpAseo;

import mah.farmer.adapter.ViewPagerAdapter;


/**第一次使用软件时弹出的引导界面
 * Created by 黑色野兽迈特祖 on 2016/4/18.
 */
public class GuideActivity extends ActivityBase {


    private ViewPager mViewPager;// 定义一个自己的viewpager


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_guide);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        swipeBackLayout.setEdgeTrackingEnabled(100);//设置界面不能侧滑退出
        mViewPager = (ViewPager) findViewById(R.id.MyViewPager);

        //初始化适配6个碎片的适配器
        ViewPagerAdapter myAdapter = new ViewPagerAdapter(
                this.getSupportFragmentManager(), GuideActivity.this);
        mViewPager.setAdapter(myAdapter);
        AseoZdpAseo.initTimer(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        return true;
    }
}
