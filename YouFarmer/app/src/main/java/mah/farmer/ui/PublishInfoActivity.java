package mah.farmer.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.baidu.locTest.R;

import java.lang.reflect.Method;
import java.util.EventListener;

import mah.farmer.tools.PagerSlidingTabStrip;
import mah.farmer.fragament.publish.PublishHireLandFragament;
import mah.farmer.fragament.publish.PublishPurchaseOrderFragament;
import mah.farmer.fragament.PublishRentLandFragament;
import mah.farmer.fragament.publish.PublishSupplyInfoFragament;

/**发布信息的界面
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class PublishInfoActivity extends ActivityBase implements EventListener{

    private PagerSlidingTabStrip tabs;//控制碎片切换的tabs
    private DisplayMetrics dm;
    private ImageView back;

    //准备切换的4个碎片
    PublishPurchaseOrderFragament publishPurchaseOrderFragament;
    PublishHireLandFragament publishHireLandFragament;
    PublishRentLandFragament publishRentLandFragament;
    PublishSupplyInfoFragament publishSupplyInfoFragament;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_details);
        init();

    }

    /**
     * 初始化相关控件
     */
    public void init(){

        back= (ImageView) findViewById(R.id.farmer_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dm = getResources().getDisplayMetrics();

        //对tabs设置相关的adapter
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);
        setTabsValue();
        findViewById(R.id.farmer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    /**
     * 设置tabS的相关参数
     */
    private void setTabsValue() {
        // 设置tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));

        // 设置tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#fe8e8d"));

        // 设置选中tab文字的颜色 (这是我自定义的一个方法)
        tabs.setSelectedTextColor(Color.parseColor("#666666"));

        // 取消点击tab时的背景色
        tabs.setTabBackground(0);

    }


    /**
     * 4个碎切换时的pageAdapter
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "求购", "供应","出租","租用" };

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {

                //判断当前为哪个位置，则加载对应的碎片
                case 0:
                    if (publishPurchaseOrderFragament == null) {
                        publishPurchaseOrderFragament = new PublishPurchaseOrderFragament(PublishInfoActivity.this);
                    }
                    return publishPurchaseOrderFragament;
                case 1:
                    if (publishSupplyInfoFragament == null) {
                        publishSupplyInfoFragament = new PublishSupplyInfoFragament(PublishInfoActivity.this);
                    }
                    return publishSupplyInfoFragament;
                case 2:
                    if (publishRentLandFragament == null) {
                        publishRentLandFragament = new PublishRentLandFragament(PublishInfoActivity.this);
                    }
                    return publishRentLandFragament;
                case 3:
                    if (publishHireLandFragament == null) {
                        publishHireLandFragament = new PublishHireLandFragament(PublishInfoActivity.this);
                    }
                    return publishHireLandFragament;

                default:


            }
            return null;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }




}



