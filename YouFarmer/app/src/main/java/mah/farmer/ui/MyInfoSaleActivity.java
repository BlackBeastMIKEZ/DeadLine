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
import mah.farmer.fragament.sale.SaleAllFragment;
import mah.farmer.fragament.sale.SaleFahuoFragment;
import mah.farmer.fragament.sale.SaleFailFragment;
import mah.farmer.fragament.sale.SaleNotFahuoFragment;
import mah.farmer.fragament.sale.SaleSucessFragment;

/**销售订单的界面
 * Created by 黑色野兽迈特祖 on 2016/4/27.
 */
public class MyInfoSaleActivity  extends ActivityBase{

    private PagerSlidingTabStrip tabs;//空值碎片切换的tabs
    private DisplayMetrics dm;
    private ImageView back;

    //5个需要显示的碎片
    SaleAllFragment saleAllFragment;
    SaleFailFragment saleFailFragment;
    SaleNotFahuoFragment saleNotsureFragment;
    SaleSucessFragment saleSucessFragment;
    SaleFahuoFragment saleFahuoFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_sale);

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


        //设置tabs的adapter
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setViewPager(pager);
        setTabsValue();
    }

    /**
     * 设置tabs的相关参数
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
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);

    }


    /**
     * 控制5个碎片的适配器
     */
    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = { "全部", "未发货","已发货","交易成功","交易失败" };

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

                //判断当前是哪个位置，则加载哪个碎片
                case 0:
                    if (saleAllFragment == null) {
                        saleAllFragment = new SaleAllFragment(MyInfoSaleActivity.this);
                    }
                    return saleAllFragment;
                case 1:
                    if (saleNotsureFragment == null) {
                        saleNotsureFragment = new SaleNotFahuoFragment(MyInfoSaleActivity.this);
                    }
                    return saleNotsureFragment;
                case 2:
                    if (saleFahuoFragment == null) {
                        saleFahuoFragment = new SaleFahuoFragment(MyInfoSaleActivity.this);
                    }
                    return saleFahuoFragment;
                case 3:
                    if (saleSucessFragment == null) {
                        saleSucessFragment = new SaleSucessFragment(MyInfoSaleActivity.this);
                    }
                    return saleSucessFragment;
                case 4:
                    if (saleFailFragment == null) {
                        saleFailFragment = new SaleFailFragment(MyInfoSaleActivity.this);
                    }
                    return saleFailFragment;

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
