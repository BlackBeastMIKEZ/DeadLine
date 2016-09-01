package mah.farmer.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

/**用户注册界面
 * Created by 黑色野兽迈特祖 on 2016/4/27.
 */
public class FarmerRegisterActivity extends ActivityBase implements View.OnClickListener{
    //界面控件
    private EditText farmer_nick,farmer_turename,farmer_password,farmer_password_again,farmer_tel;
    private TextView farmer_register,
            farmer_location;

    //定位所需要网络变量
    private LocationClient mLocClient;
    public static String TAG = "LocTestDemo";
    public LocationClient mLocationClient = null;
    public GeofenceClient mGeofenceClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_register);
        initViews();
    }


    /**
     * 初始化控件
     */
    private void initViews() {
        farmer_nick= (EditText) findViewById(R.id.farmer_nick);
        farmer_turename= (EditText) findViewById(R.id.farmer_turename);
        farmer_password= (EditText) findViewById(R.id.farmer_password);
        farmer_password_again= (EditText) findViewById(R.id.farmer_password_again);
        farmer_tel= (EditText) findViewById(R.id.farmer_tel);
        farmer_location= (TextView) findViewById(R.id.farmer_location);
        farmer_register= (TextView) findViewById(R.id.farmer_register);


        //设置监听
        farmer_nick.setOnClickListener(this);
        farmer_turename.setOnClickListener(this);
        farmer_password.setOnClickListener(this);
        farmer_password_again.setOnClickListener(this);
        farmer_tel.setOnClickListener(this);
        farmer_location.setOnClickListener(this);
        farmer_register.setOnClickListener(this);

        findViewById(R.id.farmer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initLocal();

    }

    /**
     * 初始化定位所需要的网络变量和发出网络请求
     */
    public void initLocal(){
        mLocationClient = new LocationClient( this );//构建网络定位http类
        mLocationClient.setAK("697f50541f8d4779124896681cb6584d");//设置百度申请到的API KEY
        mLocationClient.registerLocationListener( new MyLocationListenner() );//注册http的监听器
        mGeofenceClient = new GeofenceClient(this);
        mLocClient = mLocationClient;
        LocationClientOption option = new LocationClientOption();//实例化定位的地址选择类
        option.setServiceName("com.baidu.location.service_v2.9");//设置服务名
        option.setPoiExtraInfo(true);
        option.setPriority(LocationClientOption.NetWorkFirst); //设置获取定位时网络优先
        option.setAddrType("all"); //设置得到所有的地址类型
        option.setPoiNumber(10);
        option.disableCache(true);//设置不缓存本地数据
        mLocClient.setLocOption(option);//添加option的地址选择到网络定位的http类
        mLocClient.start();//开始网络请求
        mLocClient.requestLocation();//请求地址
    }


    /**
     * 网络定位的监听器，得到数据后则显示数据到界面中
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            farmer_location.setText(location.getAddrStr());//将得到的地址数据显示到界面
        }
        @Override
        public void onReceivePoi(BDLocation arg0) {
            // TODO Auto-generated method stub

        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.farmer_nick:
                break;
            case R.id.farmer_turename:
                break;
            case R.id.farmer_password:
                break;
            case R.id.farmer_password_again:
                break;
            case R.id.farmer_tel:
                break;
            case R.id.farmer_location:
                break;
            case R.id.farmer_register:
                register();

                break;
        }
    }


    /**
     * 用户注册的网络请求
     */
    private void register() {

        //获取到用户输入的各项信息
        String nick=farmer_nick.getText().toString();
        String  turename=farmer_turename.getText().toString();
        String  password=farmer_password.getText().toString();
        String  passwordagain=farmer_password_again.getText().toString();
        String  tel=farmer_tel.getText().toString();
        String  location=farmer_location.getText().toString();

        //判断账号名是否为空
        if (TextUtils.isEmpty(nick)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("账号名不能为空")
                    .show();
            return;
        }

        //判断用户真实姓名是否为空
        if(TextUtils.isEmpty(turename)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("真实姓名不能为空")
                    .show();

            return;
        }

        //判断用户位置是否为空
        if(TextUtils.isEmpty(location)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("位置不能为空")
                    .show();

            return;
        }

        //判断用户联系方式是否为空
        if(TextUtils.isEmpty(tel)){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("联系方式不能为空")
                    .show();


            return;
        }

        //判断用户密码是否为空
        if (TextUtils.isEmpty(password)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("密码不能为空")
                    .show();

            return;
        }

        //判断两次密码输入是否一致
        if (!passwordagain.equals(password)) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("两次密码输入不一致")
                    .show();

            return;
        }

        //判断当前的网络连接
        boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
        if(!isNetConnected){
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("网络问题")
                    .setContentText("没有连接网络")
                    .show();

            return;
        }

        //生成进度条
        final ProgressDialog progress = new ProgressDialog(FarmerRegisterActivity.this);
        progress.setMessage("正在注册...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        /**
         * 执行注册网络请求
         */

        RequestQueue requestQueue = Volley.newRequestQueue(FarmerRegisterActivity.this);
        JSONObject json = new JSONObject();//创建json对象
        try {
            //把数据put进json对象中
            json.put("userNickName",  farmer_nick.getText().toString() );
            json.put("userTrueName", farmer_turename.getText().toString() );
            json.put("userAddress",  farmer_location.getText().toString() );
            json.put("userPhoneNum",  farmer_tel.getText().toString()   );
            json.put("userPassword",  farmer_password.getText().toString() );
        }catch (Exception e){

        }

        //开始执行
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/register/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                /**
                 * 成功回调，缓存数据到本地
                 */
                FarmerConfig.cacheFarmerPhone(FarmerRegisterActivity.this,farmer_tel.getText().toString());
                FarmerConfig.cacheFarmerTruename(FarmerRegisterActivity.this,farmer_turename.getText().toString());
                try {
                    Toast.makeText(FarmerRegisterActivity.this,jsonObject.getString("reason"),Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progress.dismiss();
                finish();

            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                /**
                 * 失败回调
                 */
                new SweetAlertDialog(FarmerRegisterActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("注册失败")
                        .show();
                progress.dismiss();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
