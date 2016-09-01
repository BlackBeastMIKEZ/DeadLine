package mah.farmer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.locTest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import mah.farmer.bean.LandRentInfo;
import mah.farmer.bean.SupplyInfo;
import mah.farmer.ui.PurchaseOrderDetailActivity;
import mah.farmer.ui.SupplyInfoDetailActivity;
import mah.farmer.util.ImageLoadOptions;

/**供应信息适配器
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class SupplyInfoAdapter extends BaseAdapter {
    private Context ct;
    private List<SupplyInfo> data;

    public SupplyInfoAdapter(Context ct, List<SupplyInfo> datas) {
        this.ct = ct;
        this.data = datas;
    }

    /** 当ListView数据发生变化时,调用此方法来更新ListView
     * @Title: updateListView
     * @Description: TODO
     * @param @param list
     * @return void
     * @throws
     */
    public void updateListView(List<SupplyInfo> list) {
        this.data = list;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(ct).inflate(
                    R.layout.item_supply_list, null);


            viewHolder = new ViewHolder();

            viewHolder.supplier_avatar= (ImageView) convertView.findViewById(R.id.info_supplier_avatar);
            viewHolder.supplier_name= (TextView) convertView.findViewById(R.id.info_supplier_name);
            viewHolder.product_danjia= (TextView) convertView.findViewById(R.id.info_product_danjia);
            viewHolder.product_location= (TextView) convertView.findViewById(R.id.info_product_location);
            viewHolder.product_name= (TextView) convertView.findViewById(R.id.info_supplier_product_name);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final SupplyInfo supplyInfo=data.get(position);
        /**
         * 这里进行赋值还有点击事件的操作
         */
        viewHolder.product_name.setText("供应："+supplyInfo.getProductNmae());
        viewHolder.product_location.setText("位置："+supplyInfo.getProductLocation());
        viewHolder.product_danjia.setText("单价："+supplyInfo.getSupplyPrice()+"元/斤");
        viewHolder.supplier_name.setText("供应方："+supplyInfo.getSupplier());


        if (supplyInfo.getSupplierImage()!= null && !supplyInfo.getSupplierImage().equals("")) {
            ImageLoader.getInstance().displayImage(supplyInfo.getSupplierImage(), viewHolder.supplier_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            viewHolder.supplier_avatar.setImageResource(R.drawable.default_default_head);
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct, SupplyInfoDetailActivity.class);
                i.putExtra("supplyInfo",supplyInfo);
                ct.startActivity(i);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView supplier_name;
        ImageView supplier_avatar;
        TextView product_danjia;
        TextView product_location;
       TextView product_name;
        ImageView more;

    }
}
