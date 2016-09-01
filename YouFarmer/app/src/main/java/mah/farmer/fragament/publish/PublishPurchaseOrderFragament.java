package mah.farmer.fragament.publish;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.util.CommonUtils;
import mah.farmer.http.FarmerConfig;

/**发布求购信息的碎片
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class PublishPurchaseOrderFragament extends Fragment implements View.OnClickListener{
    private Context ctx;// 从activity当中得到的上下文

    private EditText purchase_name, purchase_num, purchase_desc, purchase_tel;
     private TextView purchase_location,purchase_publish;

    private LocationClient mLocClient;
    public static String TAG = "LocTestDemo";
    public LocationClient mLocationClient = null;
    public GeofenceClient mGeofenceClient;
    public PublishPurchaseOrderFragament(Context ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        // 初始化fragment时使用
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fragment创建时使用
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = View.inflate(ctx, R.layout.fragament_publish_purchase, null);
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // activity创建时使用
        initView();
        initLocal();

    }
    public void initView() {
        purchase_name = (EditText) getActivity().findViewById(R.id.info_purchase_publish_product_name);
        purchase_num = (EditText) getActivity().findViewById(R.id.info_purchase_publish_product_num);
        purchase_location = (TextView) getActivity().findViewById(R.id.info_purchase_publish_location);
        purchase_desc = (EditText) getActivity().findViewById(R.id.info_purchase_publish_description);
        purchase_tel = (EditText) getActivity().findViewById(R.id.info_publish_purchaser_phone);
        purchase_publish= (TextView) getActivity().findViewById(R.id.info_purchase_publish);
        purchase_publish.setOnClickListener(this);
    }

    public void initLocal(){
        mLocationClient = new LocationClient( getActivity() );

        mLocationClient.setAK("697f50541f8d4779124896681cb6584d");
        mLocationClient.registerLocationListener( new MyLocationListenner() );
        mGeofenceClient = new GeofenceClient(getActivity());

        mLocClient = mLocationClient;
        LocationClientOption option = new LocationClientOption();
//		option.setOpenGps(true);
        option.setServiceName("com.baidu.location.service_v2.9");
        option.setPoiExtraInfo(true);
        option.setPriority(LocationClientOption.NetWorkFirst);
        option.setAddrType("all");
        option.setPoiNumber(10);
        option.disableCache(true);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mLocClient.requestLocation();
    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            purchase_location.setText(location.getAddrStr());
        }
        @Override
        public void onReceivePoi(BDLocation arg0) {
            // TODO Auto-generated method stub

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.info_purchase_publish:
                publish();
                break;

        }

    }

    /**
     * 执行发布的网络请求
     */
    private void publish() {
        if (TextUtils.isEmpty(purchase_name.getText().toString())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("求购名称不能为空")
                    .show();
            return;
        }

        if (TextUtils.isEmpty(purchase_num.getText().toString())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("求购数量不能为空")
                    .show();

            return;
        }
        if (TextUtils.isEmpty(purchase_tel.getText().toString())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("联系方式不能为空")
                    .show();

            return;
        }
        if (TextUtils.isEmpty(purchase_desc.getText().toString())) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("详情描述不能为空")
                    .show();

            return;
        }


        boolean isNetConnected = CommonUtils.isNetworkAvailable(getActivity());
        if (!isNetConnected) {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("网络问题")
                    .setContentText("没有连接网络")
                    .show();

            return;
        }
        final ProgressDialog progress = new ProgressDialog(getActivity());
        progress.setMessage("正在发布...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();//创建json对象
        try {
            json.put("purchaser", FarmerConfig.getCachedFarmerNickname(getActivity()));//使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("purchaserPhoneNum", purchase_tel.getText().toString());//把数据put进json对象中
            json.put("productName", purchase_name.getText().toString());//使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("purchaseNum", purchase_num.getText().toString());//把数据put进json对象中
            json.put("description", purchase_desc.getText().toString());//使用URLEncoder.encode对特殊和不可见字符进行编码
            json.put("productLocation", purchase_location.getText().toString());//使用URLEncoder.encode对特殊和不可见字符进行编码
        }catch (Exception e){

        }
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/releasePurchaseInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    progress.dismiss();
                    Toast.makeText(getActivity(), "PurchasePublish " + jsonObject.getString("reason"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().finish();


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("发布求购信息失败")
                        .show();
            progress.dismiss();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
    }



