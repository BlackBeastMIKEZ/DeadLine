package mah.farmer.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.locTest.R;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.http.FarmerConfig;

/**该界面是联系我们界面
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class FarmerContactUsActivity extends ActivityBase {
    private TextView save;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_contact_us);
        save= (TextView) findViewById(R.id.feedback_publish);
        content= (EditText) findViewById(R.id.edittext);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /**
                 * 点击提交反馈意见后，发送短信到开发者手机
                 */
                SmsManager smsmanager=SmsManager.getDefault();  //得到信息类的管理Manager类
                Intent sendintent=new Intent("SEND_SMS_ACTION");
                PendingIntent pi=PendingIntent.getBroadcast(FarmerContactUsActivity.this,
                        0, sendintent, 0);
                smsmanager.sendTextMessage("18826101638", null, "优农用户"+ FarmerConfig.getCachedFarmerNickname(FarmerContactUsActivity.this)+":"+content.getText().toString(), pi, null);//发送信息到开发者
                content.setText("");
                /**
                 * 发送成功后生成对话框
                 */
                new SweetAlertDialog(FarmerContactUsActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("感谢！")
                        .setContentText("已收到您的反馈！我们会及时处理")
                        .setCustomImage(R.drawable.custom_img)
                        .show();
            }
        });

        /**
         * 回退界面的操作
         */
        findViewById(R.id.farmer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
