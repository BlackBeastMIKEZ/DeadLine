package mah.farmer.ui;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.locTest.R;
import com.zdp.aseo.content.AseoZdpAseo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.im.BmobChat;
import mah.farmer.http.Config;

/**主界面
 * Created by 黑色野兽迈特祖 on 2016/4/18.
 */
public class FarmerMainActivity  extends ActivityGroup{
    //界面最下方四个按钮布局
    private LinearLayout mMyBottemSearchBtn, mMyBottemTuanBtn,
            mMyBottemCheckinBtn, mMyBottemMyBtn;
    //界面最下方四个按钮上的图片
    private ImageView mMyBottemSearchImg, mMyBottemTuanImg,
            mMyBottemCheckinImg, mMyBottemMyImg;
    //界面最下方四个按钮的文字
    private TextView mMyBottemSearchTxt, mMyBottemTuanTxt, mMyBottemCheckinTxt,
            mMyBottemMyTxt;

    private List<View> list = new ArrayList<View>();// 存储四个activity数据源
    private View view = null;
    private View view1 = null;
    private View view2 = null;
    private View view3 = null;
    private ViewPager mViewPager;
    private PagerAdapter pagerAdapter = null;// 切换四个activity的adapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_farmer_main);
        BmobChat.DEBUG_MODE = true;
        BmobChat.getInstance(this).init(Config.applicationId);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {

        mViewPager = (ViewPager) findViewById(R.id.FramePager);
        // 查找以linearlayout为按钮作用的控件
        mMyBottemSearchBtn = (LinearLayout) findViewById(R.id.FarmerShopBtn);
        mMyBottemTuanBtn = (LinearLayout) findViewById(R.id.FarmerInfoBtn);
        mMyBottemCheckinBtn = (LinearLayout) findViewById(R.id.FarmerPublishBtn);
        mMyBottemMyBtn = (LinearLayout) findViewById(R.id.FarmerMyInfoBtn);

        // 查找linearlayout中的imageview
        mMyBottemSearchImg = (ImageView) findViewById(R.id.FarmerShopBtnImg);
        mMyBottemTuanImg = (ImageView) findViewById(R.id.FarmerInfoBtnImg);
        mMyBottemCheckinImg = (ImageView) findViewById(R.id.FarmerPublishBtnImg);
        mMyBottemMyImg = (ImageView) findViewById(R.id.FarmerMyInfoBtnImg);

        // 查找linearlayout中的textview
        mMyBottemSearchTxt = (TextView) findViewById(R.id.FarmerShopTxt);
        mMyBottemTuanTxt = (TextView) findViewById(R.id.FarmerInfoTxt);
        mMyBottemCheckinTxt = (TextView) findViewById(R.id.FarmerPublishTxt);
        mMyBottemMyTxt = (TextView) findViewById(R.id.FarmerMyInfoTxt);

        createView();
        AseoZdpAseo.initType(this, AseoZdpAseo.INSERT_TYPE);
        // 写一个内部类pageradapter
        pagerAdapter = new PagerAdapter() {
            // 判断再次添加的view和之前的view 是否是同一个view
            // arg0新添加的view，arg1之前的
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            // 返回数据源长度
            @Override
            public int getCount() {
                return list.size();
            }

            // 销毁被滑动走的view

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                // 移除view
                container.removeView(list.get(position));
            }

            //得到当前的view,即activity
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                // 获取view添加到容器当中，并返回
                View v = list.get(position);
                container.addView(v);
                return v;
            }
        };
        // 设置我们的adapter
        mViewPager.setAdapter(pagerAdapter);

        //设置监听
        MyBtnOnclick mytouchlistener = new MyBtnOnclick();
        mMyBottemSearchBtn.setOnClickListener(mytouchlistener);
        mMyBottemTuanBtn.setOnClickListener(mytouchlistener);
        mMyBottemCheckinBtn.setOnClickListener(mytouchlistener);
        mMyBottemMyBtn.setOnClickListener(mytouchlistener);


        // 设置viewpager界面切换监听,监听viewpager切换第几个界面以及滑动的
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            // arg0 获取 viewpager里面的界面切换到第几个的
            @Override
            public void onPageSelected(int arg0) {
                // 先清除按钮样式
                initBottemBtn();
                // 按照对应的view的tag来判断到底切换到哪个界面。
                // 更改对应的button状态
                int flag = (Integer) list.get((arg0)).getTag();
                if (flag == 0) {
                    mMyBottemSearchImg
                            .setImageResource(R.drawable.farmer_shop_prespress);
                    mMyBottemSearchTxt.setTextColor(Color.parseColor("#6495e2"));
                } else if (flag == 1) {
                    mMyBottemTuanImg
                            .setImageResource(R.drawable.farmer_info_presspress);
                    mMyBottemTuanTxt.setTextColor(Color.parseColor("#6495e2"));
                } else if (flag == 2) {
                    mMyBottemCheckinImg
                            .setImageResource(R.drawable.farmer_publish_presspress);
                    mMyBottemCheckinTxt.setTextColor(Color
                            .parseColor("#6495e2"));
                } else if (flag == 3) {
                    mMyBottemMyImg
                            .setImageResource(R.drawable.farmer_myinfo_presspress);
                    mMyBottemMyTxt.setTextColor(Color.parseColor("#6495e2"));
                }
            }

            /**
             * 监听页面滑动的 arg0 表示当前滑动的view arg1 表示滑动的百分比 arg2 表示滑动的距离
             * */
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            /**
             * 监听滑动状态 arg0 表示我们的滑动状态 0:默认状态 1:滑动状态 2:滑动停止
             * */
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

    }

    // 把viewpager里面要显示的view实例化出来，并且把相关的view添加到一个list当中
    private void createView() {
        view = this
                .getLocalActivityManager()
                .startActivity("search",
                        new Intent(FarmerMainActivity.this, FarmerShopActivity.class))
                .getDecorView();
        // 用来更改下面button的样式的标志
        view.setTag(0);
        list.add(view);
        view1 = FarmerMainActivity.this
                .getLocalActivityManager()
                .startActivity("tuan",
                        new Intent(FarmerMainActivity.this, FarmerInfoActivity.class))
                .getDecorView();
        view1.setTag(1);
        list.add(view1);
        view2 = FarmerMainActivity.this
                .getLocalActivityManager()
                .startActivity("sign",
                        new Intent(FarmerMainActivity.this, FarmerPublishActivity.class))
                .getDecorView();
        view2.setTag(2);
        list.add(view2);
        view3 = FarmerMainActivity.this
                .getLocalActivityManager()
                .startActivity("my",
                        new Intent(FarmerMainActivity.this, FarmerMyInfoActivity.class))
                .getDecorView();
        view3.setTag(3);
        list.add(view3);

    }

    /**
     * 用linearlayout作为按钮的监听事件
     * */
    private class MyBtnOnclick implements View.OnClickListener {

        @Override
        public void onClick(View arg0) {
            int mBtnid = arg0.getId();
            switch (mBtnid) {
                case R.id.FarmerShopBtn:
                    // //设置我们的viewpager跳转那个界面0这个参数和我们的list相关,相当于list里面的下标
                    mViewPager.setCurrentItem(0);
                    initBottemBtn();
                    mMyBottemSearchImg
                            .setImageResource(R.drawable.farmer_shop_prespress);
                    mMyBottemSearchTxt.setTextColor(Color.parseColor("#6495e2"));
                    break;
                case R.id.FarmerInfoBtn:
                    mViewPager.setCurrentItem(1);
                    initBottemBtn();
                    mMyBottemTuanImg
                            .setImageResource(R.drawable.farmer_info_presspress);
                    mMyBottemTuanTxt.setTextColor(Color.parseColor("#6495e2"));
                    break;
                case R.id.FarmerPublishBtn:
                    mViewPager.setCurrentItem(2);
                    initBottemBtn();
                    mMyBottemCheckinImg
                            .setImageResource(R.drawable.farmer_publish_presspress);
                    mMyBottemCheckinTxt.setTextColor(Color.parseColor("#6495e2"));
                    break;
                case R.id.FarmerMyInfoBtn:
                    mViewPager.setCurrentItem(3);
                    initBottemBtn();
                    mMyBottemMyImg
                            .setImageResource(R.drawable.farmer_myinfo_presspress);
                    mMyBottemMyTxt.setTextColor(Color.parseColor("#6495e2"));
                    break;

            }

        }

    }

    /**
     * 初始化控件的颜色
     * */
    private void initBottemBtn() {
        mMyBottemSearchImg.setImageResource(R.drawable.farmer_shop_normalnormal);
        mMyBottemTuanImg.setImageResource(R.drawable.farmer_info_normalnormal);
        mMyBottemCheckinImg.setImageResource(R.drawable.farmer_publish_normalnormal);
        mMyBottemMyImg.setImageResource(R.drawable.farmer_myinfo_normalnormal);

        mMyBottemSearchTxt.setTextColor(getResources().getColor(
                R.color.farmer_normal_txt));
        mMyBottemTuanTxt.setTextColor(getResources().getColor(
                R.color.farmer_normal_txt));
        mMyBottemCheckinTxt.setTextColor(getResources().getColor(
                R.color. farmer_normal_txt));
        mMyBottemMyTxt.setTextColor(getResources().getColor(
                R.color.farmer_normal_txt));

    }


    private static long firstTime;

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (firstTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(FarmerMainActivity.this,"再按一次退出程序",Toast.LENGTH_LONG).show();
        }
        firstTime = System.currentTimeMillis();
    }

}
