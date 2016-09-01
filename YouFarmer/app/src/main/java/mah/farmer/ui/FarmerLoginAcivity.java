package mah.farmer.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.util.CommonUtils;
import mah.farmer.http.FarmerConfig;

/**用户登录界面
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class FarmerLoginAcivity extends ActivityBase implements View.OnClickListener{
    private EditText username;
    private EditText userpassword;
    private Button login;
    TextView btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_famer_login);
        initViews();

    }

    /**
     * 初始化控件的方法
     */

    private void initViews() {
        swipeBackLayout.setEdgeTrackingEnabled(100);
        username= (EditText) findViewById(R.id.et_farmer_name);
        userpassword= (EditText) findViewById(R.id.et_farmer_password);

        login= (Button) findViewById(R.id.btn_farmer_login);
        btn_register = (TextView) findViewById(R.id.btn_register);
        login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_farmer_login:
                farmerlogin();
                break;
            case R.id.btn_register:
                /**
                 * 跳转到注册界面
                 */
                    Intent intent = new Intent(FarmerLoginAcivity.this,
                            FarmerRegisterActivity.class);
                    startActivity(intent);
                break;

        }
    }

    /**
     * 登录的网络擦欧总
     */
    public void farmerlogin(){

        //判断姓名是否为空
        if (TextUtils.isEmpty(username.getText().toString())) {
            new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("账号姓名不能为空")
                    .show();
            return;
        }

        //判断密码是否为空
        if (TextUtils.isEmpty(userpassword.getText().toString())) {
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("输入错误")
                    .setContentText("密码不能为空")
                    .show();
            return;
        }

        //判断当前是否有网络连接
        boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
        if(!isNetConnected){
            new SweetAlertDialog(this, cn.pedant.SweetAlert.SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("网络错误")
                    .setContentText("没有连接网络")
                    .show();
            return;
        }
        //生成进度条
        final ProgressDialog progress = new ProgressDialog(
                FarmerLoginAcivity.this);
        progress.setMessage("正在登陆...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        /**
         * 执行登录网络请求
         */

        RequestQueue requestQueue = Volley.newRequestQueue(FarmerLoginAcivity.this);
        JSONObject json = new JSONObject();//创建json对象
        try {
            //把数据put进json对象中
            json.put("userNickName",  username.getText().toString() );
            json.put("userPassword", userpassword.getText().toString() );
        }catch (Exception e){

        }

        //开始网络请求
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/login/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                FarmerConfig.cacheFarmerNickname(FarmerLoginAcivity.this, username.getText().toString());
                try {

                    //成功回调，打印后台传回来的提示信息
                    Toast.makeText(FarmerLoginAcivity.this, jsonObject.getString("reason"), Toast.LENGTH_LONG).show();

                    //判断登录成功则跳转到主界面
                    if(jsonObject.getString("reason").equals("Login successfully")) {
                        startActivity(new Intent(FarmerLoginAcivity.this, FarmerMainActivity.class));
                        progress.dismiss();
                        finish();
                    }
                    //如果用户不存在则报空
                    else {
                        new SweetAlertDialog(FarmerLoginAcivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("没有注册")
                                .setContentText("该用户不存在")
                                .show();
                        progress.dismiss();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                //失败回调，提示登录失败
                new SweetAlertDialog(FarmerLoginAcivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("登录失败")
                        .show();
                progress.dismiss();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }
}
