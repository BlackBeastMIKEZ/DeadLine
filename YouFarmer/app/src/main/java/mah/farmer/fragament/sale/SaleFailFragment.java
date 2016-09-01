package mah.farmer.fragament.sale;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.baidu.locTest.R;

import java.util.ArrayList;
import java.util.List;

import mah.farmer.adapter.DeliveryAdapter;
import mah.farmer.bean.Delivery;
import mah.farmer.view.CircleRefreshLayout;

/**显示交易失败的销售订单的碎片
 * Created by 黑色野兽迈特祖 on 2016/4/27.
 */
public class SaleFailFragment extends Fragment {
    private Context ctx;// 从activity当中得到的上下文
    private List<Delivery> data;
    private ListView listView;
    private CircleRefreshLayout circleRefreshLayout;
    private DeliveryAdapter deliveryAdapter;
    private LinearLayout layout_all;

    public SaleFailFragment(Context ctx) {
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
        view = View.inflate(ctx, R.layout.fragment_sale_fail, null);
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
        circleRefreshLayout= (CircleRefreshLayout) getActivity().findViewById(R.id.sale_fail_refresh_layout);
        data=new ArrayList<>();
        listView= (ListView) getActivity().findViewById(R.id.sale_fail_supply_list);
        deliveryAdapter=new DeliveryAdapter(getActivity(),data);
        listView.setAdapter(deliveryAdapter);
        layout_all= (LinearLayout) getActivity().findViewById(R.id.layout_fail_all);
        if(data.size()==0){
            layout_all.setVisibility(View.VISIBLE);
            circleRefreshLayout.setVisibility(View.GONE);
        }
        else{
            layout_all.setVisibility(View.GONE);
            circleRefreshLayout.setVisibility(View.VISIBLE);
        }


    }



    public void initTextColor(){

    }
}
