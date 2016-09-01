package mah.farmer.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.locTest.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**该界面是用户更改优农账号名
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class ChangeNameActivity extends ActivityBase {
    private TextView save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);
        save= (TextView) findViewById(R.id.save);
        findViewById(R.id.farmer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(ChangeNameActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("无法修改")
                        .setContentText("敬请期待优农2.0")
                        .setCustomImage(R.drawable.nice)
                        .show();
            }
        });
    }
}
