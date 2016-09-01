package mah.farmer.fragament.info;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.locTest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.adapter.PurchaseOrderAdapter;
import mah.farmer.adapter.SupplyInfoAdapter;
import mah.farmer.bean.PurchaseOrderInfo;
import mah.farmer.bean.SupplyInfo;
import mah.farmer.view.CircleRefreshLayout;

/**显示供应出租信息的碎片
 * Created by 黑色野兽迈特祖 on 2016/4/25.
 */
public class SupplyFragament extends Fragment implements View.OnClickListener {
    private CircleRefreshLayout circleRefreshLayout;
    private ListView listView;
    private SupplyInfoAdapter supplyInfoAdapter;
    private List<SupplyInfo> data;
    private Context ctx;// 从activity当中得到的上下文
    private TextView buxian, shuiguo, shucai;


    public SupplyFragament(Context ctx) {
        super();
        this.ctx = ctx;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        // 初始化fragment时使用
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // fragment创建时使用
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // fragment创建view时使用
        // 返回的是一个view
        /**
         * LayoutInflater inflater 找我们的fragmentxml时实用的 ViewGroup
         * 使用inflater时宽高相对条件 bundler 可以通过我们的bundle在fragment创建view时传递参数
         * */
        View view = null;
        view = View.inflate(ctx, R.layout.fragament_supply, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // activity创建时使用

        initViews();
    }

    private void initViews() {
        circleRefreshLayout = (CircleRefreshLayout) getActivity().findViewById(R.id.supply_refresh_layout);
        listView = (ListView) getActivity().findViewById(R.id.farmer_info_supply_list_farmer);
        data = new ArrayList<>();
//        data.add(new SupplyInfo("1","1","1","1","1","1","1","1"));
//        data.add(new SupplyInfo("55","55","55","55","55","55","55","55"));
        getdata();
        supplyInfoAdapter = new SupplyInfoAdapter(getActivity(), data);
        listView.setAdapter(supplyInfoAdapter);

        buxian = (TextView) getActivity().findViewById(R.id.supply_buxian);
        shuiguo = (TextView) getActivity().findViewById(R.id.supply_shuiguo);
        shucai = (TextView) getActivity().findViewById(R.id.supply_shucai);

        buxian.setOnClickListener(this);
        shuiguo.setOnClickListener(this);
        shucai.setOnClickListener(this);
        circleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {

                refresh();
                supplyInfoAdapter = new SupplyInfoAdapter(getActivity(), data);
                listView.setAdapter(supplyInfoAdapter);
            }
        });

    }


    /**
     * 下拉刷新的网络请求
     */
    private void refresh() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showSupplyInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Toast.makeText(getActivity(), "加载成功！", Toast.LENGTH_LONG).show();

                    circleRefreshLayout.finishRefreshing();
                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    data.clear();

                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                        data.add(new SupplyInfo(products.getString("supplier"),
                                products.getString("supplierPhoneNum"),
                                products.getString("supplierImage"),
                                products.getString("productName"),
                                products.getString("supplyPrice"),
                                products.getString("supplyNum"),
                                products.getString("productLocation"),
                                products.getString("description")));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"无法获取数据，请检查网络",Toast.LENGTH_LONG).show();

                circleRefreshLayout.finishRefreshing();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buxian:
                initTextColor();
                buxian.setTextColor(getResources().getColor(R.color.farmer_info_selected));
                break;
            case R.id.shuiguo:
                initTextColor();
                shuiguo.setTextColor(getResources().getColor(R.color.farmer_info_selected));
                break;
            case R.id.shucai:
                initTextColor();
                shucai.setTextColor(getResources().getColor(R.color.farmer_info_selected));
                break;

            default:
                break;

        }
    }
    public void initTextColor(){
        buxian.setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        shuiguo .setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        shucai.setTextColor(getResources().getColor(R.color.farmer_info_noselected));

    }

    /**
     * 执行加载供应信息网络请求
     */
    public void getdata() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showSupplyInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Toast.makeText(getActivity(), "SupplyInfo " + jsonObject.getString("reason"), Toast.LENGTH_LONG).show();

                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
//                    data.clear();
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                        data.add(new SupplyInfo(products.getString("supplier"),
                                products.getString("supplierPhoneNum"),
                                products.getString("supplierImage"),
                                products.getString("productName"),
                                products.getString("supplyPrice"),
                                products.getString("supplyNum"),
                                products.getString("productLocation"),
                                products.getString("description")));


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("网络错误")
                        .setContentText("获取供应信息失败")
                        .show();


            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
