package mah.farmer.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.locTest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.adapter.FarmerPublishHistroyAdapter;
import mah.farmer.bean.PublishHistory;
import mah.farmer.http.FarmerConfig;
import mah.farmer.view.CircleRefreshLayout;

/**发布历史界面
 * Created by 黑色野兽迈特祖 on 2016/4/19.
 */
public class FarmerPublishActivity extends ActivityBase implements View.OnClickListener{
    /**
     *界面相关控件
     */
    private CircleRefreshLayout mRefreshLayout;
    private ListView mList;
    private List<PublishHistory> data;
    private FarmerPublishHistroyAdapter farmerPublishHistroyAdapter;
    private ImageView publish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_publish);
        initViews();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        swipeBackLayout.setEdgeTrackingEnabled(100);
        mRefreshLayout= (CircleRefreshLayout) findViewById(R.id.publish_refresh_layout);
        mList= (ListView) findViewById(R.id.farmer_publish_list);
        publish= (ImageView) findViewById(R.id.farmer_publish);
        data=new ArrayList<>();

        getdata();//得到发布历史数据

        farmerPublishHistroyAdapter =new FarmerPublishHistroyAdapter(this,data);
        mList.setAdapter(farmerPublishHistroyAdapter);

        publish.setOnClickListener(this);  //设置监听

        /**
         * 设置下拉刷新的监听回掉
         */
        mRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            //刷新完成时进行的操作
            @Override
            public void completeRefresh() {

            }

            //刷新时的操作
            @Override
            public void refreshing() {

                //更新界面
                refresh();
                //重新设置adapter适配数据
                farmerPublishHistroyAdapter =new FarmerPublishHistroyAdapter(FarmerPublishActivity.this,data);
                mList.setAdapter(farmerPublishHistroyAdapter);
            }
        });
    }


    /**
     * 下拉刷新时候的网络操作
     */
    private void refresh() {

        RequestQueue requestQueue = Volley.newRequestQueue(FarmerPublishActivity.this);

        JSONObject json = new JSONObject();//创建json对象
        try {
            //将数据put进json对象
            json.put("userNickName", FarmerConfig.getCachedFarmerNickname(FarmerPublishActivity.this));
        }
        catch (Exception e){

        }
        //开始执行网络请求
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,
                "http://192.168.235.22:8080/showReleaseProductHistory/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {

                    /**
                     * 成功回调。打印后台返回的原因和存储数据
                     */
                    Toast.makeText(FarmerPublishActivity.this, "加载成功！", Toast.LENGTH_LONG).show();

                    mRefreshLayout.finishRefreshing();//停止刷新

                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    data.clear();

                    /**
                     * 获取数据
                     */
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                        data.add(new PublishHistory(
                                products.getString("productName"),
                                products.getString("productImage"),
                                products.getString("productTagOne"),
                                products.getString("productTagTwo"),
                                products.getString("youNongPrice"),
                                products.getString("productNum"),
                                products.getString("productLocation"),
                                products.getString("description"),
                                products.getString("supplierPhone"),
                                products.getString("datetime")
                        ));
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                /**
                 * 失败回调
                 */
                Toast.makeText(FarmerPublishActivity.this,"无法获取数据，请检查网络",Toast.LENGTH_LONG).show();

                mRefreshLayout.finishRefreshing();//停止刷新

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.farmer_publish:
                startActivity(new Intent(this,PublishProductActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 获取发布历史的网络请求
     */
    public void getdata() {

        RequestQueue requestQueue = Volley.newRequestQueue(FarmerPublishActivity.this);
        JSONObject json = new JSONObject();//创建json对象
        try {
            //将数据put进jiso对象
              json.put("userNickName", FarmerConfig.getCachedFarmerNickname(FarmerPublishActivity.this));
        }
        catch (Exception e){

        }

        //开始执行
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showReleaseProductHistory/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    /**
                     * 成功回调，打印后台返回的信息，获取数据
                     */
                    Toast.makeText(FarmerPublishActivity.this, "PublishHistory"+jsonObject.getString("reason"), Toast.LENGTH_LONG).show();
                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    data.clear();
                    /**
                     * 获取数据
                     */
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                          data.add(new PublishHistory(
                                  products.getString("productName"),
                                  products.getString("productImage"),
                                  products.getString("productTagOne"),
                                  products.getString("productTagTwo"),
                                  products.getString("youNongPrice"),
                                  products.getString("productNum"),
                                  products.getString("productLocation"),
                                  products.getString("description"),
                                  products.getString("supplierPhone"),
                                  products.getString("datetime")
                          ));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /**
                 * 失败回调，打印失败原因
                 */
                new SweetAlertDialog(FarmerPublishActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("获取发布历史失败")
                        .show();

            }
        });
        requestQueue.add(jsonObjectRequest);


    }
}