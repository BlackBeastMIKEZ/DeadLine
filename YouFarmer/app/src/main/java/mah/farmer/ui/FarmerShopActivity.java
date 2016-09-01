package mah.farmer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.locTest.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.adapter.FarmerShopAdapter;
import mah.farmer.bean.FarmerProduct;
import mah.farmer.view.CircleRefreshLayout;


/**  “淘农”信息界面
 * Created by 黑色野兽迈特祖 on 2016/4/19.
 */
public class FarmerShopActivity extends ActivityBase implements View.OnClickListener,Comparator<FarmerProduct>{

    //界面相关控件和相关adapter
    public static final int LOCAL=0;
    private CircleRefreshLayout mRefreshLayout;
    private ListView mList;
    private FarmerShopAdapter farmershopAdapter;
    private List<FarmerProduct> data;
    private List<FarmerProduct> sortdata;
    private TextView all, gua,yecai,qieguo,jiage,xiaoliang,shuiguo,shucai,local_name;
    private ImageView farmer_arrow,farmer_sort_arrow,tranlsete_arrow,farmer_research;
    private LinearLayout farmer_kinds_layout,farmer_sort_layout,
            farmer_kindandsort_detail_layout,blank_layout,
            farmer_sort_detail_layout,farmer_kind_detail_layout,
            farmer_kind_detail_more_layout;

    //判断旋转动画如何执行和相关控件位置的flag
    private boolean isrotate;
    private boolean isSortrotate;
    private boolean isUp, isDown;



    //定位所需要的网络变量
    private LocationClient mLocClient;
    public static String TAG = "LocTestDemo";
    public LocationClient mLocationClient = null;
    public GeofenceClient mGeofenceClient;

    //handler异步加载网络定位
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOCAL:
                    LocationClientOption option = new LocationClientOption();//实例化地址选择器
                    option.setServiceName("com.baidu.location.service_v2.9");//设置选择器服务名
                    option.setPoiExtraInfo(true);
                    option.setPriority(LocationClientOption.NetWorkFirst);//设置网络优先
                    option.setAddrType("all");//设置加载所有地址类型
                    option.setPoiNumber(10);
                    option.disableCache(true);
                    mLocClient.setLocOption(option);//将网络定位HTTP类加载地址选择器
                    mLocClient.start();//开始定位请求
                    mLocClient.requestLocation();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_shop);
        initView();
        initLocal();

    }

    /**
     * 初始化控件
     */
    private void initView() {
        swipeBackLayout.setEdgeTrackingEnabled(100);//设置界面不能侧滑退出

        //指定ID
        mRefreshLayout = (CircleRefreshLayout) findViewById(R.id.shop_refresh_layout);
        mList = (ListView) findViewById(R.id.farmer_shop_list);
        farmer_arrow= (ImageView) findViewById(R.id.farmer_kind_arrow);
        farmer_sort_arrow= (ImageView) findViewById(R.id.farmer_sort_arrow);
        tranlsete_arrow= (ImageView) findViewById(R.id.translate_arrow);
        farmer_research= (ImageView)findViewById(R.id.shop_search);
        farmer_kinds_layout= (LinearLayout) findViewById(R.id.farmer_kinds_layout);
        farmer_sort_layout= (LinearLayout) findViewById(R.id.farmer_sort_layout);
        farmer_kindandsort_detail_layout = (LinearLayout) findViewById(R.id.farmer_kindandsort_detail_layout);
        farmer_sort_detail_layout= (LinearLayout) findViewById(R.id.farmer_sort_details_layout);
        blank_layout= (LinearLayout) findViewById(R.id.blank_layout);
        farmer_kind_detail_layout= (LinearLayout) findViewById(R.id.farmer_kind_detail_layout);
        farmer_kind_detail_more_layout= (LinearLayout) findViewById(R.id.farmer_kind_detail_more_layout);
        all= (TextView) findViewById(R.id.all);
        gua = (TextView) findViewById(R.id.gua);
        yecai= (TextView) findViewById(R.id.yecai);
        qieguo= (TextView) findViewById(R.id.qieguo);
        jiage= (TextView) findViewById(R.id.jiage);
        xiaoliang= (TextView) findViewById(R.id.xiaoliang);
        shuiguo= (TextView) findViewById(R.id.shuiguo);
        shucai= (TextView) findViewById(R.id.shucai);

        //初始化产品数组列表
        data=new ArrayList<FarmerProduct>();
        sortdata=new ArrayList<FarmerProduct>();

        getData();//网络请求得到数据

        //设置listview对应的adapter
        farmershopAdapter=new FarmerShopAdapter(this,data);
        mList.setAdapter(farmershopAdapter);

        //设置监听
        farmer_kinds_layout.setOnClickListener(this);
        farmer_sort_layout.setOnClickListener(this);
        blank_layout.setOnClickListener(this);
        all.setOnClickListener(this);
        gua.setOnClickListener(this);
        yecai.setOnClickListener(this);
        qieguo.setOnClickListener(this);
        jiage.setOnClickListener(this);
        xiaoliang.setOnClickListener(this);
        shuiguo.setOnClickListener(this);
        shucai.setOnClickListener(this);
        farmer_research.setOnClickListener(this);

        //设置下拉刷新操作的监听器
        mRefreshLayout.setOnRefreshListener(
                new CircleRefreshLayout.OnCircleRefreshListener() {

                    //刷新时的操作
                    @Override
                    public void refreshing() {

                        //刷新时进行网络请求最新数据
                        refresh();
                        farmershopAdapter=new FarmerShopAdapter(FarmerShopActivity.this,data);
                        mList.setAdapter(farmershopAdapter);

                    }

                    //刷新完成时的操作
                    @Override
                    public void completeRefresh() {
                    }
                });

    }

    /**
     * 刷新时候的网络操作请求
     */
    public void refresh(){

        //开始执行网络请求
        RequestQueue requestQueue = Volley.newRequestQueue(FarmerShopActivity.this);
        JSONObject json = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showProducts/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {

                    /**
                     * 成功回调，得到后台返回来的数据
                     */
                    Toast.makeText(FarmerShopActivity.this, "加载成功！", Toast.LENGTH_LONG).show();
                    mRefreshLayout.finishRefreshing();//停止刷新
                    JSONArray jsonarray=jsonObject.getJSONArray("data");//得到数据
                    JSONObject products;
                    data.clear();
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);

                        /**
                         * 存储数据
                         */
                        data.add(new FarmerProduct(products.getString("productName"),
                                products.getString("productLocation"),products.getString("commonPrice"),
                                products.getString("youNongPrice"),
                                products.getString("productImage"),
                                products.getString("productTagOne"),
                                products.getString("productTagTwo"),
                                products.getString("productNum"),
                                products.getString("productSales"),
                                products.getString("productTotalSalesNum"),
                                products.getString("productTotalSalesPrice"),
                                products.getString("description"),
                                products.getString("productSupplier"),
                                products.getString("supplierPhone"),
                                products.getString("supplierAddress")));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                /**
                 * 失败回调，提示用户失败刷新
                 */
                Toast.makeText(FarmerShopActivity.this,"无法获取数据，请检查网络",Toast.LENGTH_LONG).show();

                mRefreshLayout.finishRefreshing();//停止刷新

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    /**
     * 初始化网络定位
     */
    public void initLocal(){

        //网络定位相关的初始化
        mLocationClient = new LocationClient( this );
        mLocationClient.setAK("697f50541f8d4779124896681cb6584d");//设置申请到的百度API KEY
        mLocationClient.registerLocationListener( new MyLocationListenner() );//注册网络监听器
        mGeofenceClient = new GeofenceClient(this);
        local_name = (TextView)findViewById(R.id.location_name);
        mLocClient = mLocationClient;

        //handler发送消息进行异步加载定位
        Message message=new Message();
        message.what=LOCAL;
        handler.sendMessage(message);

    }

    /**
     * 获取淘农的网络请求
     */
    public void getData() {
        //生成进度条
        final ProgressDialog progress = new ProgressDialog(FarmerShopActivity.this);
        progress.setMessage("正在获取...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        RequestQueue requestQueue = Volley.newRequestQueue(FarmerShopActivity.this);
        JSONObject json = new JSONObject();//创建json对象

        //开始执行
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showProducts/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {

                    /**
                     * 成功回调，加载后台传回的数据
                     */
                    Toast.makeText(FarmerShopActivity.this, jsonObject.getString("reason"), Toast.LENGTH_LONG).show();
                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    data.clear();
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);

                        /**
                         * 存储数据
                         */
                        data.add(new FarmerProduct(products.getString("productName"),
                                products.getString("productLocation"),
                                products.getString("commonPrice"),
                                products.getString("youNongPrice"),
                                products.getString("productImage"),
                                products.getString("productTagOne"),
                                products.getString("productTagTwo"),
                                products.getString("productNum"),
                                products.getString("productSales"),
                                products.getString("productTotalSalesNum"),
                                products.getString("productTotalSalesPrice"),
                                products.getString("description"),
                                products.getString("productSupplier"),
                                products.getString("supplierPhone"),
                                products.getString("supplierAddress")));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progress.dismiss();


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                /**
                 *失败回调，提示用户加载失败
                 */
                new SweetAlertDialog(FarmerShopActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("获取淘农信息失败")
                        .show();
                progress.dismiss();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    //排序用到的接口
    @Override
    public int compare(FarmerProduct lhs, FarmerProduct rhs) {
        return 0;
    }


    /**
     * 网络定位的监听接口，显示到界面
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            local_name.setText(location.getCity()+location.getDistrict());//将得到的地址显示到界面上
        }
        @Override
        public void onReceivePoi(BDLocation arg0) {
            // TODO Auto-generated method stub

        }


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.farmer_kinds_layout:
                toggleKinds();
                break;
            case R.id.farmer_sort_layout:
                toggleSort();
                break;
            case R.id.blank_layout:
                break;

            case R.id.all:
                if(isDown) {
                    filterByname("水果",all.getText().toString());
                }
                else{
                    filterByname("蔬菜",all.getText().toString());
                }
                initBackground( );
                all.setBackgroundResource(R.drawable.course_info_blue);

                break;


            case R.id.gua:
                if(isDown) {
                    filterByname("水果",gua.getText().toString());
                }
                else{
                    filterByname("蔬菜",gua.getText().toString());
                }
                initBackground( );
                gua.setBackgroundResource(R.drawable.course_info_blue);
                break;
            case R.id.yecai:
                if(isDown) {
                    filterByname("水果",yecai.getText().toString());
                }
                else{
                    filterByname("蔬菜",yecai.getText().toString());
                }
                initBackground( );
                yecai.setBackgroundResource(R.drawable.course_info_blue);
                break;
            case R.id.qieguo:
                if(isDown) {
                    filterByname("水果",qieguo.getText().toString());
                }
                else{
                    filterByname("蔬菜",qieguo.getText().toString());
                }
                initBackground( );
                qieguo.setBackgroundResource(R.drawable.course_info_blue);

                break;

            case R.id.jiage:
                filterByprice();
                toggleSort();
                break;
            case R.id.xiaoliang:
                toggleSort();
                break;
            case R.id.shuiguo:
                if(isDown){

                }
                else {
                    tranlsete_arrow.startAnimation(translateArrowDown());
                    isDown=true;
                    isUp=false;
                    gua.setText("浆果类");
                    yecai.setText("瓜果类");
                    qieguo.setText("橘果类");
                    initUpDownBack();


                }
                break;
            case R.id.shucai:
                if(isUp){

                }
                else{
                    tranlsete_arrow.startAnimation( translateArrowUp());
                    isDown=false;
                    isUp=true;
                    gua.setText("瓜类");
                    yecai.setText("叶菜类");
                    qieguo.setText("茄果类");
                    initUpDownBack();

                }

                break;
            case R.id.shop_search:
                Intent intent=new Intent(this, FarmerShopSearchActivity.class);
                this.startActivity(intent);
                break;
            default:
                break;

        }
    }

    /**
     * 用户选择类别时进行的过滤操作
     * @param tag1Content
     * @param tag2Content
     */
    public void  filterByname(String tag1Content,String tag2Content){

        //data保存原有的产品数据，sortdata保存用户执行类别选择后的数据
        sortdata.clear();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getTagOne().equals(tag1Content)){
                if(tag2Content.equals("全部")){
                    sortdata.add(data.get(i));

                }
                else {
                    if (data.get(i).getTagTwo().equals(tag2Content)) {
                        sortdata.add(data.get(i));
                    }
                }
            }
        }
        //将得到的sortdata数据加载到adapter上
        farmershopAdapter=new FarmerShopAdapter(this,sortdata);
        mList.setAdapter(farmershopAdapter);


    }

    /**
     * 用户选择价格排序时，对产品数据进行有低到高的排序
     */
    public void filterByprice(){
        sortdata.clear();
        for (int i=0;i<data.size();i++){
            sortdata.add(data.get(i));
        }

        //选择collections类进行快速排序
        Collections.sort(sortdata,new Comparator<FarmerProduct>() {
            @Override
            public int compare(FarmerProduct lhs, FarmerProduct rhs) {
                return lhs.getMyprice().compareTo(rhs.getMyprice());
            }
        });

        //将得到的sortdata数据加载到adapter
        farmershopAdapter=new FarmerShopAdapter(this,sortdata);
        mList.setAdapter(farmershopAdapter);

    }
    public void filterBynum(){

    }

    /**
     *  控件消失时的动画效果
     * @return
     */

    private Animation smallAnimation() {

        AlphaAnimation animation1=new AlphaAnimation(1.0f,0.0f);
        animation1.setFillAfter(true);
        animation1.setDuration(300);
        return animation1;

    }

    /**
     * 控件出现时的动画效果
     * @return
     */
    private Animation bigAnimation() {

        AlphaAnimation animation1=new AlphaAnimation(0.0f,1.0f);
        animation1.setFillAfter(true);
        animation1.setDuration(800);
        return animation1;
    }

    /**
     * 控件的向下旋转动画效果
     * @return
     */
    public Animation rotate(){
        RotateAnimation anim=new RotateAnimation(0f,90f, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(300);
        anim.setFillAfter(true);
        return anim;
    }

    /**
     * 控件向上旋转的动画效果
     * @return
     */
    public Animation rotateback(){
        RotateAnimation anim=new RotateAnimation(90f,0f, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        anim.setDuration(300);
        anim.setFillAfter(true);
        return anim;
    }

    /**
     * 控件向下平移的动画效果
     * @return
     */
    public Animation translateArrowDown(){
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 150);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(300);
        return translateAnimation;


    }

    /**
     * 控件向上平移的动画效果
     * @return
     */
    public Animation translateArrowUp(){
        TranslateAnimation translateAnimation=new TranslateAnimation(0,0,150,0);
        translateAnimation.setFillAfter(true);
        translateAnimation.setDuration(300);
        return translateAnimation;

    }

    /**
     * 控件向下平移时对相关控件进行界面初始化
     */
    public void initUpDownBack(){
        all.setBackgroundResource(0);
        gua.setBackgroundResource(0);
        qieguo.setBackgroundResource(0);
        yecai.setBackgroundResource(0);
        all.setBackgroundResource(R.drawable.course_info_blue);
    }

    /**
     * 点击类别时对相关控件进行界面初始化
     */
    public void initBackground( ){

        all.setBackgroundResource(0);
        gua.setBackgroundResource(0);
        qieguo.setBackgroundResource(0);
        yecai.setBackgroundResource(0);
        toggleKinds();

    }

    /**
     * 分类选项的动画效果
     */
    public void toggleKinds(){
        //判断是否已经旋转
        if(isrotate) {
            farmer_arrow.startAnimation(rotateback());
            isrotate=false;
            mList.setVisibility(View.VISIBLE);
            farmer_kindandsort_detail_layout.setVisibility(View.GONE);
            farmer_kind_detail_layout.setVisibility(View.GONE);
            farmer_kind_detail_more_layout.setVisibility(View.GONE);
            tranlsete_arrow.setVisibility(View.GONE);
            tranlsete_arrow.startAnimation(smallAnimation());
            farmer_kindandsort_detail_layout.startAnimation(smallAnimation());
            farmer_kind_detail_layout.startAnimation(smallAnimation());
            farmer_kind_detail_more_layout.startAnimation(smallAnimation());


        }
        else {
            farmer_arrow.startAnimation(rotate());
            isrotate=true;
            mList.setVisibility(View.GONE);

            if(farmer_sort_detail_layout.getVisibility()==View.VISIBLE){
                farmer_sort_arrow.startAnimation(rotateback());
                isSortrotate=false;
                farmer_sort_detail_layout.setVisibility(View.GONE);
                farmer_sort_detail_layout.startAnimation(smallAnimation());
            }
            farmer_kind_detail_layout.setVisibility(View.VISIBLE);
            farmer_kind_detail_more_layout.setVisibility(View.VISIBLE);
            farmer_kind_detail_layout.startAnimation(bigAnimation());
            farmer_kind_detail_more_layout.startAnimation(bigAnimation());
            farmer_kindandsort_detail_layout.setVisibility(View.VISIBLE);
            farmer_kindandsort_detail_layout.startAnimation(bigAnimation());
            tranlsete_arrow.setVisibility(View.VISIBLE);
            tranlsete_arrow.startAnimation(bigAnimation());






        }
    }

    /**
     * 智能排序的选项动画效果
     */
    public void toggleSort() {

        //判断是否已经旋转
        if (isSortrotate) {
            farmer_sort_arrow.startAnimation(rotateback());
            isSortrotate = false;
            mList.setVisibility(View.VISIBLE);

            farmer_sort_detail_layout.setVisibility(View.GONE);
            farmer_kindandsort_detail_layout.setVisibility(View.GONE);
            farmer_kindandsort_detail_layout.startAnimation(smallAnimation());
            farmer_sort_detail_layout.startAnimation(smallAnimation());



        } else {
            farmer_sort_arrow.startAnimation(rotate());
            isSortrotate = true;
            mList.setVisibility(View.GONE);
            if(farmer_kind_detail_layout.getVisibility()==View.VISIBLE){
                farmer_arrow.startAnimation(rotateback());
                farmer_kind_detail_layout.setVisibility(View.GONE);
                isrotate=false;
                farmer_kind_detail_layout.startAnimation(smallAnimation());
                farmer_kind_detail_more_layout.setVisibility(View.GONE);
                farmer_kind_detail_more_layout.startAnimation(smallAnimation());
                tranlsete_arrow.setVisibility(View.GONE);
                tranlsete_arrow.startAnimation(smallAnimation());
            }

            farmer_kindandsort_detail_layout.setVisibility(View.VISIBLE);
            farmer_kindandsort_detail_layout.startAnimation(bigAnimation());
            farmer_sort_detail_layout.setVisibility(View.VISIBLE);
            farmer_sort_detail_layout.startAnimation(bigAnimation());

        }

    }
}
