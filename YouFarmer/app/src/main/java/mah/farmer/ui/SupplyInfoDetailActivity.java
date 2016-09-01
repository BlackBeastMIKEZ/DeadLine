package mah.farmer.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.baidu.locTest.R;

import mah.farmer.bean.PurchaseOrderInfo;
import mah.farmer.bean.SupplyInfo;

/**供应信息详情界面
 * Created by 黑色野兽迈特祖 on 2016/5/3.
 */
public class SupplyInfoDetailActivity extends ActivityBase {
    private TextView name,num,location,phone,desc,dial,danjia;
    SupplyInfo purchaseOrderInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_info_detail);

        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        name= (TextView) findViewById(R.id.supply_name_detail);
        num= (TextView) findViewById(R.id.supply_num_detail);
        location= (TextView) findViewById(R.id.supply_location_detail);
        phone= (TextView) findViewById(R.id.supply_tel_detail);
        desc= (TextView) findViewById(R.id.supply_description_detaili);
        dial= (TextView) findViewById(R.id.supply_dial);
        danjia= (TextView) findViewById(R.id.supply_danjia_detail);

        purchaseOrderInfo= (SupplyInfo) getIntent().getSerializableExtra("supplyInfo");

        //将上个界面传来的数据进行显示
        name.setText(purchaseOrderInfo.getProductNmae());
        num.setText(purchaseOrderInfo.getSupplyNum()+"斤");
        location.setText(purchaseOrderInfo.getProductLocation());
        phone.setText(purchaseOrderInfo.getSupplierPhoneNum());
        desc.setText(purchaseOrderInfo.getDescription());
        danjia.setText(purchaseOrderInfo.getSupplyPrice()+"元/斤");

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowToast("tel:"+phone.getText().toString());
                Uri uri=Uri.parse("tel:"+phone.getText().toString());
                Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                startActivity(intent);
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
