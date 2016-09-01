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

import mah.farmer.tools.PagerSlidingTabStrip;
import mah.farmer.fragament.delivery.DeliveryAllFragament;
import mah.farmer.fragament.delivery.DeliveryFailFragament;
import mah.farmer.fragament.delivery.DeliveryFahuoFragament;
import mah.farmer.fragament.delivery.DeliveryNotfahuoFragament;
import mah.farmer.fragament.delivery.DeliverySuccessFragament;
import mah.farmer.fragament.sale.SaleNotFahuoFragment;

/**我的订单界面
 * Created by 黑色野兽迈特祖 on 2016/4/27.
 */
public class MyInfoDeliveryActivity extends ActivityBase  {

    private PagerSlidingTabStrip tabs;//控制碎片切换的tab
    private DisplayMetrics dm;
    private ImageView back;

    //下面是5个碎片
    DeliveryAllFragament deliveryAllFragament;
    DeliveryFailFragament deliveryFailFragament;
    DeliverySuccessFragament deliverySuccessFragament;
    DeliveryFahuoFragament deliveryNotSureFragament;
    SaleNotFahuoFragment saleNotsureFragment;
    DeliveryNotfahuoFragament deliveryNotfahuoFragament;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_purchase_delivery);

        init();

    }

    /**
     * 初始化控件
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

        //设置tabs的adapter
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);
        setTabsValue();
    }

    /**
     * 对tabs进行相关参数的设置
     */
    private void setTabsValue() {
        // 设置tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 0, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#fe8e8d"));
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        tabs.setSelectedTextColor(Color.parseColor("#666666"));
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);

    }

    /**
     *五个碎片切换的适配器
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "全部", "未发货","已发货","交易成功","交易失败" };//设置tabs标题

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

                //判断当前为哪个位置，则实例化哪个碎片
                case 0:
                    if (deliveryAllFragament == null) {
                        deliveryAllFragament = new DeliveryAllFragament(MyInfoDeliveryActivity.this);
                    }
                    return deliveryAllFragament;
                case 1:
                    if (deliveryNotfahuoFragament == null) {
                        deliveryNotfahuoFragament = new DeliveryNotfahuoFragament(MyInfoDeliveryActivity.this);
                    }
                    return deliveryNotfahuoFragament;
                case 2:
                    if (deliveryNotSureFragament == null) {
                        deliveryNotSureFragament = new DeliveryFahuoFragament(MyInfoDeliveryActivity.this);
                    }
                    return deliveryNotSureFragament;
                case 3:
                    if (deliverySuccessFragament == null) {
                        deliverySuccessFragament = new DeliverySuccessFragament(MyInfoDeliveryActivity.this);
                    }
                    return deliverySuccessFragament;
                case 4:
                    if (deliveryFailFragament == null) {
                        deliveryFailFragament = new DeliveryFailFragament(MyInfoDeliveryActivity.this);
                    }
                    return deliveryFailFragament;

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
