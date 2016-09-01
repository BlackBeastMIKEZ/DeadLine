package mah.farmer.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.baidu.locTest.R;

import java.util.ArrayList;
import java.util.List;

import mah.farmer.adapter.FarmerShopAdapter;
import mah.farmer.adapter.HistorySearchAdapter;
import mah.farmer.bean.FarmerProduct;


/**
 * Created by 黑色野兽迈特祖 on 2016/4/22.
 */
public class FarmerShopSearchActivity extends ActivityBase implements View.OnClickListener{
    private ImageView back , search;
    private EditText editText;
    private LinearLayout history_hot_layout,hot_search_layout,hitory_search_layout,t1_layout,t2_layout,t3_layout;
    private ListView histoyList,nowlist;
    private TextView t1,t2,t3;
    private List<String> history_data;
    private HistorySearchAdapter historySearchAdapter;
    private List<FarmerProduct> now_data;
    private FarmerShopAdapter farmerShopAdapter;
    private AutoCompleteTextView auto;
    String str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_shop_search);
        initViews();

    }

    private void initViews() {
        back= (ImageView) findViewById(R.id.farmer_back);
        search= (ImageView) findViewById(R.id.shop_search_search);
        editText= (EditText) findViewById(R.id.serach_name);
        hot_search_layout= (LinearLayout) findViewById(R.id.hot_search_layout);
        hitory_search_layout= (LinearLayout) findViewById(R.id.history_search_layout);
        history_hot_layout= (LinearLayout) findViewById(R.id.history_hot_layout);
        t1_layout= (LinearLayout) findViewById(R.id.t1_layout);
        t2_layout= (LinearLayout) findViewById(R.id.t2_layout);
        t3_layout= (LinearLayout) findViewById(R.id.t3_layout);
        histoyList= (ListView) findViewById(R.id.history_list);
        nowlist= (ListView) findViewById(R.id.NowSearchList);
        t1= (TextView) findViewById(R.id.t1);
        t2= (TextView) findViewById(R.id.t2);
        t3= (TextView) findViewById(R.id.t3);
        auto= (AutoCompleteTextView) findViewById(R.id.auto);
        history_data=new ArrayList<>();
        now_data=new ArrayList<>();


        back.setOnClickListener(this);
        search.setOnClickListener(this);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);


        history_data.add("橘子");
        history_data.add("荔枝");
        history_data.add("火龙果");
        history_data.add("香蕉");
        historySearchAdapter=new HistorySearchAdapter(this,history_data);
        histoyList.setAdapter(historySearchAdapter);

//        now_data.add(new FarmerProduct("11111111","2222222222","3333333333333","44444444444444"));
        farmerShopAdapter=new FarmerShopAdapter(this,now_data);
        nowlist.setAdapter(farmerShopAdapter);

        String[] auto_data={"水果","蔬菜","橙子","荔枝","火龙果","asfdsd","brtrtr","cwewe"};
        auto.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,auto_data));

        auto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
              str=auto.getText().toString();
                try {
                    Integer.parseInt(str);
                }catch (Exception e){
                    showdiaog();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void showdiaog(){
//        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
//                .setTitleText("输入格式错误")
//                .setContentText("请输入数字")
//                .show();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.farmer_back:
                finish();
                break;
            case R.id.shop_search_search:
                if(TextUtils.isEmpty(auto.getText().toString())){
                    ShowToast("搜索内容不能为空");
                    return;
                }
                history_hot_layout.setVisibility(View.GONE);
                nowlist.setVisibility(View.VISIBLE);

                break;
            case R.id.t1:

                break;
            case R.id.t2:

                break;
            case R.id.t3:

                break;

        }
    }
}
