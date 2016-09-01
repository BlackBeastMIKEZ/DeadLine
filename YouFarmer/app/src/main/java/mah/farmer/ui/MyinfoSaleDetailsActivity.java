package mah.farmer.ui;

import android.os.Bundle;
import android.view.View;

import com.baidu.locTest.R;

/**用户销售订单详情界面，后台没有接口，就没有完善这个界面
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class MyinfoSaleDetailsActivity extends ActivityBase {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_sale_detail);
        findViewById(R.id.farmer_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
