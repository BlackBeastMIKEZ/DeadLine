package mah.farmer.fragament.info;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import mah.farmer.adapter.LandHireInfoAdapter;
import mah.farmer.adapter.LandRentInfoAdapter;
import mah.farmer.adapter.SupplyInfoAdapter;
import mah.farmer.bean.FarmerProduct;
import mah.farmer.bean.LandHireInfo;
import mah.farmer.bean.LandRentInfo;
import mah.farmer.bean.SupplyInfo;
import mah.farmer.view.CircleRefreshLayout;

/**显示土地出租和租用信息的碎片
 * Created by 黑色野兽迈特祖 on 2016/4/25.
 */
public class LandRentHireFragament extends Fragment implements View.OnClickListener{
    private Context ctx;// 从activity当中得到的上下文
    private CircleRefreshLayout circleRefreshLayout;
    private ListView listView;
    private LandHireInfoAdapter landHireInfoAdapter;
    private LandRentInfoAdapter landRentInfoAdapter;
    private List<LandRentInfo> rent_data;
    private List<LandHireInfo> hire_data;
    boolean iszuyong;
    public static final  int STOP=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case STOP:
                    circleRefreshLayout.finishRefreshing();
                    break;
            }
        }
    };

    private TextView chuzu,zuyong;

    public LandRentHireFragament(Context ctx) {
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
        view = View.inflate(ctx, R.layout.fragament_land_renthire, null);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        initViews();

    }

    private void initViews() {
        chuzu= (TextView) getActivity().findViewById(R.id.chuzu);
        zuyong= (TextView) getActivity().findViewById(R.id.zuyong);
        chuzu.setOnClickListener(this);
        zuyong.setOnClickListener(this);

        circleRefreshLayout= (CircleRefreshLayout) getActivity().findViewById(R.id.farmer_info_rent_refresh);
        listView= (ListView) getActivity().findViewById(R.id.farmer_info_rent_list_farmer);
        circleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() {

                refresh();
                if (iszuyong==true){
                    landRentInfoAdapter.notifyDataSetChanged();
                }
                else{

                   landHireInfoAdapter.notifyDataSetChanged();
                }
            }
        });
        rent_data=new ArrayList<>();
        hire_data=new ArrayList<>();
        landRentInfoAdapter=new LandRentInfoAdapter(getActivity(),rent_data);
        landHireInfoAdapter=new LandHireInfoAdapter(getActivity(),hire_data);
        listView.setAdapter(landRentInfoAdapter);
        getData();


    }

    /**
     * 下拉刷新的网络请求
     */
    public void refresh(){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showLandRentInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Toast.makeText(getActivity(), "加载成功！", Toast.LENGTH_LONG).show();
                    Message message=new Message();
                    message.what=STOP;
                    handler.sendMessage(message);
//                    circleRefreshLayout.finishRefreshing();
                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    rent_data.clear();

                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                        rent_data.add(new LandRentInfo(products.getString("renter"),
                                products.getString("renterPhoneNum"),
                                products.getString("renterImage"),
                                products.getString("landLocation"),
                                products.getString("landImage"),
                                products.getString("rentPrice"),
                                products.getString("rentNum")));
                    }
                    landRentInfoAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"无法获取数据，请检查网络",Toast.LENGTH_LONG).show();

                Message message=new Message();
                message.what=STOP;
                handler.sendMessage(message);

            }
        });
        requestQueue.add(jsonObjectRequest);


        JSONObject json1 = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest1=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showLandHireInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Toast.makeText(getActivity(), "加载成功！", Toast.LENGTH_LONG).show();
                    Message message=new Message();
                    message.what=STOP;
                    handler.sendMessage(message);
//                    circleRefreshLayout.finishRefreshing();
                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    hire_data.clear();
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                        hire_data.add(new LandHireInfo(products.getString("hirer"),
                                products.getString("hirerImage"),
                                products.getString("hirerPhoneNum"),
                                products.getString("landLocation"),
                                products.getString("hireNum"),
                                products.getString("description")
                        ));



                    }
                    landHireInfoAdapter.notifyDataSetInvalidated();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                circleRefreshLayout.finishRefreshing();

                Message message=new Message();
                message.what=STOP;
                handler.sendMessage(message);

            }
        });
        requestQueue.add(jsonObjectRequest1);

    }


    /**
     * 执行加载土地出租和租用信息网络请求
     */
    public void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JSONObject json = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showLandRentInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Toast.makeText(getActivity(), "LandRentInfo " + jsonObject.getString("reason"), Toast.LENGTH_LONG).show();

                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    rent_data.clear();
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                        rent_data.add(new LandRentInfo(products.getString("renter"),
                                products.getString("renterPhoneNum"),
                                products.getString("renterImage"),
                                products.getString("landLocation"),
                                products.getString("landImage"),
                                products.getString("rentPrice"),
                                products.getString("rentNum")
                        ));

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
                        .setContentText("获取出租信息失败")
                        .show();


            }
        });
        requestQueue.add(jsonObjectRequest);


        JSONObject json1 = new JSONObject();//创建json对象
        JsonObjectRequest jsonObjectRequest1=new JsonObjectRequest(Request.Method.POST,"http://192.168.235.22:8080/showLandHireInfo/",

                json,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Toast.makeText(getActivity(), "LandHireInfo " + jsonObject.getString("reason"), Toast.LENGTH_LONG).show();

                    JSONArray jsonarray=jsonObject.getJSONArray("data");
                    JSONObject products;
                    hire_data.clear();
                    for(int i=0;i<jsonarray.length();i++){
                        products=jsonarray.getJSONObject(i);
                        hire_data.add(new LandHireInfo(products.getString("hirer"),
                                products.getString("hirerImage"),
                                products.getString("hirerPhoneNum"),
                                products.getString("landLocation"),
                                products.getString("hireNum"),
                                products.getString("description")
                        ));



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
                        .setContentText("获取租用信息失败")
                        .show();


            }
        });
        requestQueue.add(jsonObjectRequest1);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chuzu:
                initBackground();

                iszuyong=false;
                chuzu.setTextColor(getResources().getColor(R.color.base_bg));
                chuzu.setBackgroundResource(R.drawable.course_info_red);
                listView.setAdapter(landRentInfoAdapter);
                break;
            case R.id.zuyong:
                initBackground();
                iszuyong=true;
                zuyong.setTextColor(getResources().getColor(R.color.base_bg));
                zuyong.setBackgroundResource(R.drawable.course_info_red);
                listView.setAdapter(landHireInfoAdapter);
                break;

        }
    }
    public void initBackground(){
        chuzu.setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        chuzu.setBackgroundResource(0);
        zuyong.setTextColor(getResources().getColor(R.color.farmer_info_noselected));
        zuyong.setBackgroundResource(0);


    }


}
