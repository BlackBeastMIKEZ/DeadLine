package mah.farmer.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.locTest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import mah.farmer.bean.LandRentInfo;
import mah.farmer.bean.PurchaseOrderInfo;
import mah.farmer.http.FarmerConfig;
import mah.farmer.util.ImageLoadOptions;

/**土地出租的详情页面
 * Created by 黑色野兽迈特祖 on 2016/5/3.
 */
public class LandRentInfoDetailActivity extends ActivityBase {

    //界面相关控件
    private TextView num,location,phone,desc,dial;
    ImageView photo;
    LandRentInfo landRentInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_rent_detail);

        initView();
    }

    /**
     * 初始化相关控件
     */
    private void initView() {

        num= (TextView) findViewById(R.id.rent_detail_num);
        location= (TextView) findViewById(R.id.rent_location_detail);
        phone= (TextView) findViewById(R.id.rent_tel_detail);
        desc= (TextView) findViewById(R.id.rent_desc_detail);
        dial= (TextView) findViewById(R.id.rent_dial);
        photo= (ImageView) findViewById(R.id.rent_avatar_detail);

        landRentInfo= (LandRentInfo) getIntent().getSerializableExtra("landRent");

       //加载土地图片
        if (landRentInfo.getLandImag() != null && !landRentInfo.getLandImag() .equals("")) {
            ImageLoader.getInstance().displayImage(landRentInfo.getLandImag(),  photo,
                    ImageLoadOptions.getOptions());
        } else {
            photo.setImageResource(R.drawable.default_default_head);
        }


        //将传递来的数据显示在界面上
        num.setText(landRentInfo.getRentNum()+"亩");
        location.setText(landRentInfo.getLandLocation());
        phone.setText(landRentInfo.getRenterPhoneNum());
        /**
         * 这里并没有传值
         */
        desc.setText("");

        //设置监听，点击即可拨号
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
