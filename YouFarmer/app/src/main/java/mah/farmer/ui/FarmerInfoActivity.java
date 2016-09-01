package mah.farmer.ui;

import android.content.Intent;
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

import cn.bmob.im.bean.BmobInvitation;
import cn.bmob.im.bean.BmobMsg;
import cn.bmob.im.inteface.EventListener;
import mah.farmer.tools.PagerSlidingTabStrip;
import mah.farmer.fragament.info.LandRentHireFragament;
import mah.farmer.fragament.info.PurchaseOrderFragament;
import mah.farmer.fragament.info.SupplyFragament;

import java.lang.reflect.Method;

/**该界面为信息大厅界面
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class FarmerInfoActivity extends ActivityBase {


    private PurchaseOrderFragament purchaseOrderFragament;//求购信息界面
    private SupplyFragament supplyFragament;//供应信息界面
    private LandRentHireFragament landRentHireFragament;//田地信息界面
    private PagerSlidingTabStrip tabs;//控制三个碎片切换的tab
    private DisplayMetrics dm;
    private ImageView messagepublish;//发布信息控件




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_info);
        swipeBackLayout.setEdgeTrackingEnabled(100);
        init();

    }

    /**
     * 初始化控件和tab
     */
    public void init(){
        messagepublish= (ImageView) findViewById(R.id.message_publish);
        messagepublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FarmerInfoActivity.this,PublishInfoActivity.class));
            }
        });

        dm = getResources().getDisplayMetrics();
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        /**
         * 设定三个碎片滑动的adapter
         */
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);
        setTabsValue();
    }


    /**
     * 设置tab的一些规格参数
     */
    private void setTabsValue() {
        // 设置tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,0, dm));

        // 设置tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 20, dm));
        // 设置tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#fe8e8d"));

        // 设置选中tab文字的颜色 (这是我自定义的一个方法)
        tabs.setSelectedTextColor(Color.parseColor("#666666"));

        // 取消点击tab时的背景色
        tabs.setTabBackground(0);
    }


    /**
     * 三个碎片切换的adapter
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "求购", "供应","田地" };

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
                case 0:
                    if (purchaseOrderFragament == null) {
                        purchaseOrderFragament = new PurchaseOrderFragament(FarmerInfoActivity.this);
                    }
                    return purchaseOrderFragament;
                case 1:
                    if (supplyFragament == null) {
                        supplyFragament = new SupplyFragament(FarmerInfoActivity.this);
                    }
                    return supplyFragament;
                case 2:
                    if (landRentHireFragament == null) {
                        landRentHireFragament = new LandRentHireFragament(FarmerInfoActivity.this);
                    }
                    return landRentHireFragament;

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


