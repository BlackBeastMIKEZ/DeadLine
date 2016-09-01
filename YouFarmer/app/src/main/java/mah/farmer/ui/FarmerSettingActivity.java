package mah.farmer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.locTest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.util.ImageLoadOptions;
import mah.farmer.http.FarmerConfig;

/**设置用户信息界面
 * Created by 黑色野兽迈特祖 on 2016/4/27.
 */
public class FarmerSettingActivity extends ActivityBase {
    //界面相关控件
    private ImageView farmerAvatar;
    private TextView farmer_nick,farmer_truename,farmer_location,farmer_tel,exit;
    private RelativeLayout changePassword,changetel,changenick,exit_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_setting);
        initViews();
    }

    /**
     * 初始化控件
     */
    private void initViews() {
        //指定id
        farmerAvatar= (ImageView) findViewById(R.id.farmer_avatar);
        farmer_nick= (TextView) findViewById(R.id.farmer_nickname);
        farmer_tel= (TextView) findViewById(R.id.farmer_tel);
        farmer_location= (TextView) findViewById(R.id.farmer_location);
        farmer_truename= (TextView) findViewById(R.id.farmer_truename);
        exit= (TextView) findViewById(R.id.exit);
        changePassword= (RelativeLayout) findViewById(R.id.changePassword);
        changetel= (RelativeLayout) findViewById(R.id.changeTel);
        changenick= (RelativeLayout) findViewById(R.id.changeNick);
        exit_layout= (RelativeLayout) findViewById(R.id.exit_layout);


        //加载用户头像
        if (FarmerConfig.getCachedFarmerImage(this)!= null && !FarmerConfig.getCachedFarmerImage(this).equals("")) {
            ImageLoader.getInstance().displayImage(FarmerConfig.getCachedFarmerImage(this),  farmerAvatar,
                    ImageLoadOptions.getOptions());
        } else {
            farmerAvatar.setImageResource(R.drawable.default_default_head);
        }

        //加载用户信息
        farmer_nick.setText(FarmerConfig.getCachedFarmerNickname(this));
        farmer_truename.setText(FarmerConfig.getCachedFarmerPhoTruename(this));
        farmer_location.setText(FarmerConfig.getCachedFarmerAddress(this));
        farmer_tel.setText(FarmerConfig.getCachedFarmerPhone(this));

        //设置监听
        changenick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(FarmerSettingActivity.this,ChangeNameActivity.class));//跳转到更改用户账号名界面
            }
        });

        changetel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FarmerSettingActivity.this,ChangeTelActivity.class));//跳转到更改用户联系方式界面

            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FarmerSettingActivity.this,ChangePasswordActivity.class));//跳转到用户更改密码界面

            }
        });


        /**
         * 注销操作
         */
        exit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //弹出对话框
                new SweetAlertDialog(FarmerSettingActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("注销操作")
                        .setContentText("你确定退出该账号？")
                        .setConfirmText("确认")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {


                                //将本地的数据缓存清空
                                FarmerConfig.cacheFarmerImage(FarmerSettingActivity.this,null);
                                FarmerConfig.cacheFarmerAddress(FarmerSettingActivity.this, null);
                                FarmerConfig.cacheFarmerNickname(FarmerSettingActivity.this, null);
                                FarmerConfig.cacheFarmerTruename(FarmerSettingActivity.this, null);
                                FarmerConfig.cacheFarmerPhone(FarmerSettingActivity.this, null);

                                //跳转到登录界面
                                startActivity(new Intent(FarmerSettingActivity.this,FarmerLoginAcivity.class));
                                finish();

                            }
                        })
                        .show();
            }
        });

        findViewById(R.id.farmer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
