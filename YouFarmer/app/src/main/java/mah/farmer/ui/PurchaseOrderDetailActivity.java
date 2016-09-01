package mah.farmer.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.locTest.R;

import mah.farmer.bean.PurchaseOrderInfo;

/**
 * Created by 黑色野兽迈特祖 on 2016/5/3.
 */
public class PurchaseOrderDetailActivity extends  ActivityBase {
    private TextView name,num,location,phone,desc,dial;
    PurchaseOrderInfo purchaseOrderInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order_detial);

        initView();
    }

    private void initView() {
        name= (TextView) findViewById(R.id.purchase_name_detail);
        num= (TextView) findViewById(R.id.purchase_num_detail);
        location= (TextView) findViewById(R.id.purchase_location_detail);
        phone= (TextView) findViewById(R.id.purchase_tel_detail);
        desc= (TextView) findViewById(R.id.purchase_description_detaili);
        dial= (TextView) findViewById(R.id.purchase_dial);

         purchaseOrderInfo= (PurchaseOrderInfo) getIntent().getSerializableExtra("purchaseOrder");

        name.setText(purchaseOrderInfo.getProductName());
        num.setText(purchaseOrderInfo.getPurchaseNum()+"斤");
        location.setText(purchaseOrderInfo.getProductLocation());
        phone.setText(purchaseOrderInfo.getPurchaserPhoneNum());
        desc.setText(purchaseOrderInfo.getDescription());

        dial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
