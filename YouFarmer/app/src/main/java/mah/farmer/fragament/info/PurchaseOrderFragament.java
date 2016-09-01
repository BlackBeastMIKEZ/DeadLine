package mah.farmer.fragament.info;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
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

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mah.farmer.adapter.PurchaseOrderAdapter;
import mah.farmer.bean.FarmerProduct;
import mah.farmer.bean.LandRentInfo;
import mah.farmer.bean.PurchaseOrderInfo;
import mah.farmer.view.CircleRefreshLayout;

/**显示求购信息的碎片
 * Created by 黑色野兽迈特祖 on 2016/4/25.
 */
public class PurchaseOrderFragament extends Fragment implements View.OnClickListener{
    private Context ctx;// 从activity当中得到的上下文
    private TextView remen,buxian,shuiguo ,shucai,naidan,luhua;
    private CircleRefreshLayout circleRefreshLayout;
    private ListView listView;
    private PurchaseOrderAdapter farmerPurchaseOrderAdapter;
    private List<PurchaseOrderInfo> data;

    public PurchaseOrderFragament(Context ctx) {
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
        View view = null;
        view = View.inflate(ctx, R.layout.fragament_purchaseorder, null);
        return view;
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        // activity创建时使用
        initView();

    }
    public void initView() {
        remen= (TextView) getActivity().findViewById(R.id.remen);
        buxian= (TextView) getActivity().findViewById(R.id.buxian);
        shuiguo= (TextView) getActivity().findViewById(R.id.shuiguo);
        shucai= (TextView) getActivity().findViewById(R.id.shucai);
        naidan= (TextView) getActivity().findViewById(R.id.naidan);
        luhua= (TextView) getActivity().findViewById(R.id.luhua);
        listView= (ListView) getActivity().findViewById(R.id.farmer_info_list);
        circleRefreshLayout= (CircleRefreshLayout) getActivity().findViewById(R.id.info_refresh_layout);

        remen.setOnClickListener(this);
        buxian.setOnClickListener(this);
        shuiguo.setOnClickListener(this);
        shucai.setOnClickListener(this);
        naidan.setOnClickListener(this);
        luhua.setOnClickListener(this);

        data=new ArrayList<>();
//        data.add(new PurchaseOrderInfo("2","2","2","2","2","2","2"));
        getData();
//        data.add(new PurchaseOrderInfo("新时代农民","求购：拉面","数量：100斤","位置：广东省广州市"));
        farmerPurchaseOrderAdapter=new PurchaseOrderAdapter(getActivity(),data);
        listView.setAdapter(farmerPurchaseOrderAdapter);


        circleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {
                refresh();
                farmerPurchaseOrderAdapter=new PurchaseOrderAdapter(getActivity(),data);
                listView.setAdapter(farmerPurchaseOrderAdapter);
            }
        });


    }

    /**
     * 下拉刷新的网络请求
     */

    public void refresh(){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showPurchaseInfo/",

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
                        data.add(new PurchaseOrderInfo(products.getString("purchaser"),
                                products.getString("purchaserPhoneNum"),
                                products.getString("purchaserImage"),
                                products.getString("productName"),
                                products.getString("purchaseNum"),
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
        switch (v.getId()){
            case R.id.remen:
                initTextColor();
                remen.setTextColor(getResources().getColor(R.color.farmer_info_selected));
                break;
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
            case R.id.naidan:
                initTextColor();
                naidan.setTextColor(getResources().getColor(R.color.farmer_info_selected));
                break;
            case R.id.luhua:
                initTextColor();
                luhua.setTextColor(getResources().getColor(R.color.farmer_info_selected));
                break;
            default:
                break;

        }

    }
    public void initTextColor(){
        remen.setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        buxian.setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        shuiguo .setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        shucai.setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        naidan.setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        luhua.setTextColor(getResources().getColor(R.color.farmer_info_noselected));

    }

    /**
     * 执行加载求购信息网络请求
     */
    public void getData() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showPurchaseInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Toast.makeText(getActivity(), "PurchaseInfo "+jsonObject.getString("reason"), Toast.LENGTH_LONG).show();

                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    data.clear();
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                        data.add(new PurchaseOrderInfo(products.getString("purchaser"),
                                products.getString("purchaserPhoneNum"),
                                products.getString("purchaserImage"),
                                products.getString("productName"),
                                products.getString("purchaseNum"),
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
                        .setContentText("获取求购信息失败")
                        .show();


            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
