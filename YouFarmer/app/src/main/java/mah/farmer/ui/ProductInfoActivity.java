/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package mah.farmer.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.locTest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.util.ImageLoadOptions;
import mah.farmer.bean.FarmerProduct;
import mah.farmer.http.FarmerConfig;

/**淘农产品详情界面
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class ProductInfoActivity extends ActivityBase implements AppBarLayout.OnOffsetChangedListener {

    //界面相关控件
    private static final int PERCENTAGE_TO_SHOW_IMAGE = 20;
    private View mFab;
    private int mMaxScrollSize;
    private boolean mIsImageHidden;
    private TextView danjia,xiaoliang,desc,supplier_name,supplier_location,product_leibie;
    private String supplierPhone;
    private ImageView supplier_avatar,produt_avatar;
    private FarmerProduct farmerProduct;
    private LinearLayout addshoppingcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);
        initAppBarLayout();

    }

    /**
     * 用到的是较新控件APPLayout,初始化相关控件
     */
    private void initAppBarLayout() {

        //得到上一个界面传过来的产品数据
        farmerProduct= (FarmerProduct) getIntent().getSerializableExtra("products");

        //指定ID
        produt_avatar= (ImageView) findViewById(R.id.productinfo_avatar);
        mFab = findViewById(R.id.flexible_example_fab);
        danjia= (TextView) findViewById(R.id.product_danjia);
        desc= (TextView) findViewById(R.id.productinfo_desc);
        xiaoliang= (TextView) findViewById(R.id.product_xiaoliang);
        supplier_name= (TextView) findViewById(R.id.supplier_name);
        supplier_location= (TextView) findViewById(R.id.supplier_location);
        supplier_avatar= (ImageView) findViewById(R.id.supplier_avatar);
        addshoppingcart= (LinearLayout) findViewById(R.id.add_shopping_cart);
        product_leibie= (TextView) findViewById(R.id.product_leibie);


        //将数据显示到界面上
        danjia.setText("单价："+farmerProduct.getMyprice()+"元/斤");
        desc.setText(farmerProduct.getDescription());
        if(farmerProduct.getSalesNum()==null||farmerProduct.getSalesNum().equals("")) {
            xiaoliang.setText("销量：当前暂无销量");

        }
        else{
            xiaoliang.setText("销量："+farmerProduct.getSalesNum()+"斤");
        }

        product_leibie.setText("大类："+farmerProduct.getTagOne()+"     小类："+farmerProduct.getTagTwo());
        supplier_location.setText("地址："+farmerProduct.getSupplierAddress());
        supplier_name.setText("昵称: "+farmerProduct.getSupplier());

        //加载产品图片
        if ( farmerProduct.getAvata_md5()!= null && !farmerProduct.getAvata_md5().equals("")) {
            ImageLoader.getInstance().displayImage(farmerProduct.getAvata_md5(), produt_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            produt_avatar.setImageResource(R.drawable.default_head);
        }

        //加载卖方图片
        getSupplierAvatar();


        //悬浮按钮设置监听器，拨打电话
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //拨号程序
                Uri uri=Uri.parse("tel:"+farmerProduct.getSupplierPhone());
                Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);

            }
        });

        //跳转到生成订单界面
        addshoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //判断是否为用户自己发布的产品
                if(FarmerConfig.getCachedFarmerNickname(ProductInfoActivity.this).equals(farmerProduct.getSupplier())){
                    new SweetAlertDialog(ProductInfoActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("操作错误")
                            .setContentText("用户不能购买自己的产品")
                            .show();
                }
                else {
                    Intent intent = new Intent(ProductInfoActivity.this, AddShoppingDeliveryActivity.class);
                    intent.putExtra("userTrueName", FarmerConfig.getCachedFarmerPhoTruename(ProductInfoActivity.this));
                    intent.putExtra("userAddress", FarmerConfig.getCachedFarmerAddress(ProductInfoActivity.this));
                    intent.putExtra("userPhone", FarmerConfig.getCachedFarmerPhone(ProductInfoActivity.this));
                    intent.putExtra("product", farmerProduct);
                    startActivity(intent);
                }
            }
        });

        //对toolbar设置可以回退
        Toolbar toolbar = (Toolbar) findViewById(R.id.flexible_example_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        AppBarLayout appbar = (AppBarLayout) findViewById(R.id.flexible_example_appbar);
        appbar.addOnOffsetChangedListener(this);

        /**
         * 这里设置大标题的颜色和内容
         */
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.flexible_example_collapsing);
        collapsingToolbarLayout.setTitle(farmerProduct.getName());
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.farmer_shop_list_back)); // transperent color = #00000000
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(232, 247, 239));
    }


    /**
     * 当applayout上拉或者下拉时的动画操作
     * @param appBarLayout
     * @param i
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
            mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int currentScrollPercentage = (Math.abs(i)) * 100
                / mMaxScrollSize;

        //判断当前toolbar的长度所占百分比来决定要不要隐藏图片
        if (currentScrollPercentage >= PERCENTAGE_TO_SHOW_IMAGE) {
            if (!mIsImageHidden) {
                mIsImageHidden = true;

                ViewCompat.animate(mFab).scaleY(0).scaleX(0).start();
            }
        }

        if (currentScrollPercentage < PERCENTAGE_TO_SHOW_IMAGE) {
            if (mIsImageHidden) {
                mIsImageHidden = false;
                ViewCompat.animate(mFab).scaleY(1).scaleX(1).start();
            }
        }
    }

    public static void start(Context c) {
        c.startActivity(new Intent(c, ProductInfoActivity.class));
    }

    /**
     * 获取卖方头像的网络请求
     */
    public void getSupplierAvatar() {
        RequestQueue requestQueue = Volley.newRequestQueue(ProductInfoActivity.this);
        JSONObject json = new JSONObject();//创建json对象
        try {
            //将数据put斤json对象
            json.put("userNickName", farmerProduct.getSupplier());
        }
        catch (Exception e){

        }

        //开始执行
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showPersonality/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {



                try {
                    /**
                     * 成功回调，加载数据
                     */
                    Toast.makeText(ProductInfoActivity.this, "myinfo" + jsonObject.getString("reason"), Toast.LENGTH_LONG).show();
                    JSONObject json=jsonObject.getJSONObject("data");
                    String avatar=json.getString("userImage");

                    //加载卖方头像
                    if (avatar!= null && !avatar.equals("")) {
                        ImageLoader.getInstance().displayImage(avatar, supplier_avatar,
                                ImageLoadOptions.getOptions());
                    } else {
                        supplier_avatar.setImageResource(R.drawable.default_default_head);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        },new Response.ErrorListener() {

            /**
             * 失败回调，提示用户出错
             * @param volleyError
             */
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                new SweetAlertDialog(ProductInfoActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("获取用户头像信息失败")
                        .show();


            }
        });
        requestQueue.add(jsonObjectRequest);


    }
}
