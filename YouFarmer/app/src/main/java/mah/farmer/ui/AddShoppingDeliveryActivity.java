package mah.farmer.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.util.ImageLoadOptions;
import mah.farmer.bean.FarmerProduct;
import mah.farmer.http.FarmerConfig;

/**生成订单的界面
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class AddShoppingDeliveryActivity extends ActivityBase implements View.OnClickListener {
    private LinearLayout addshop;//生成订单
    private FarmerProduct farmerProduct;
    private ImageView plus,minus,product_avatar;
    private TextView purchaser_name,purchaser_address,
            purchaser_phone,product_name,product_price,delivery_num,delivery__totalMoney;
    private float num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_add_delivery);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        farmerProduct= (FarmerProduct) getIntent().getSerializableExtra("product");
        plus= (ImageView) findViewById(R.id.plus);
        minus= (ImageView) findViewById(R.id.minus);
        purchaser_name= (TextView) findViewById(R.id.purchaser_name);
        purchaser_address= (TextView) findViewById(R.id.purchaser_location);
        purchaser_phone= (TextView) findViewById(R.id.purchaser_phone);
        product_name= (TextView) findViewById(R.id.product_name);
        delivery_num= (TextView) findViewById(R.id.delivery_num);
        delivery__totalMoney= (TextView) findViewById(R.id.delivery_total_money);
        product_price= (TextView) findViewById(R.id.product_price);
        addshop= (LinearLayout) findViewById(R.id.add_shopp_delivery);
        product_avatar= (ImageView) findViewById(R.id.product_avatar);


        //接受上一个界面传过来的数据，并且显示
        purchaser_name.setText("买家："+FarmerConfig.getCachedFarmerNickname(this));
        purchaser_phone.setText("联系方式："+FarmerConfig.getCachedFarmerPhone(this));
        purchaser_address.setText("收货地址："+FarmerConfig.getCachedFarmerAddress(this));
        product_name.setText("产品："+farmerProduct.getName());
        product_price.setText("单价："+farmerProduct.getMyprice()+"元/斤");
        delivery__totalMoney.setOnClickListener(this);
        delivery_num.setOnClickListener(this);
        addshop.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        findViewById(R.id.farmer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /**
         * 加载产品头像
         */
        if (farmerProduct.getAvata_md5() != null && !farmerProduct.getAvata_md5().equals("")) {
            ImageLoader.getInstance().displayImage(farmerProduct.getAvata_md5(),  product_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            product_avatar.setImageResource(R.drawable.default_default_head);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_shopp_delivery:
                addDelvery();
                break;
            case R.id.plus:

                /**
                 * 用户点击增加一斤购买量的操作
                 */
                num++;
                delivery_num.setText(num+"");
                delivery__totalMoney.setText(num*Float.parseFloat(farmerProduct.getMyprice())+"");
                break;
            case R.id.minus:
                /**
                 * 用户点击减少一斤购买量操作
                 */
                if(num==0){
                    new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("操作错误")
                            .setContentText("购买数量不能为0")
                            .show();
                }
                else {
                    num--;
                    delivery_num.setText(num + "");
                    delivery__totalMoney.setText(num*Float.parseFloat(farmerProduct.getMyprice())+"");
                }
                break;



        }

    }

    /**
     * 生成订单的网络操作
     */
    private void addDelvery() {

        final ProgressDialog progress = new ProgressDialog(AddShoppingDeliveryActivity.this);
        progress.setMessage("正在生成订单...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();           //创建进度条

        RequestQueue requestQueue = Volley.newRequestQueue(AddShoppingDeliveryActivity.this);
        JSONObject json = new JSONObject();//创建json对象
        try {
            //把数据put进json对象中
            json.put("user",  FarmerConfig.getCachedFarmerNickname(this) );//用户名数据
            json.put("productName", farmerProduct.getName() );//产品名数据
            json.put("purchaseNum",  delivery_num.getText().toString() );//产品数量数据
            json.put("purchasePrice",  delivery__totalMoney.getText().toString());//购买总花费数据
            json.put("purchaseInfo",  "未发货" );//订单状态数据
            json.put("datetime",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) );//购买时间
        }catch (Exception e){

        }

        //开始进行json网络的请求
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/purchaseProduct/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {


                //成功回调，打印后台返回的数据查看生成订单是否成功
                try {
                    Toast.makeText(AddShoppingDeliveryActivity.this, "add "+jsonObject.getString("reason"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //取消进度条
                progress.dismiss();
                finish();

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                //失败回调，对话框提示用户网络原因
                new SweetAlertDialog(AddShoppingDeliveryActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("生成订单失败")
                        .show();
                //取消进度条
                progress.dismiss();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
