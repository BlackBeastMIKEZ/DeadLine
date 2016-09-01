package mah.farmer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.locTest.R;

import java.util.List;

import mah.farmer.bean.Delivery;
import mah.farmer.bean.LandHireInfo;

/**订单类适配器
 * Created by 黑色野兽迈特祖 on 2016/4/27.
 */
public class DeliveryAdapter extends BaseAdapter {
    private Context ct;
    private List<Delivery> data;

    public DeliveryAdapter(Context ct, List<Delivery> datas) {
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
    public void updateListView(List<Delivery> list) {
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
                    R.layout.item_delivery_list, null);


            viewHolder = new ViewHolder();
            viewHolder.product_avatar= (ImageView) convertView.findViewById(R.id.product_avatar);
            viewHolder.trade_danjia= (TextView) convertView.findViewById(R.id.trade_danjia);
            viewHolder.trade_num= (TextView) convertView.findViewById(R.id.trade_num);
            viewHolder.trade_product= (TextView) convertView.findViewById(R.id.trade_product);
            viewHolder.trade_type= (TextView) convertView.findViewById(R.id.trade_type);
            viewHolder.trade_total_money= (TextView) convertView.findViewById(R.id.trade_total_money);
            viewHolder.trade_time= (TextView) convertView.findViewById(R.id.trade_time);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //  User friend = data.get(position);
        Delivery delivery=data.get(position);
        /**
         * 这里进行赋值还有点击事件的操作
         */
        viewHolder.product_avatar.setImageResource(R.drawable.default_default_head);


        viewHolder.trade_type.setText(delivery.getTime());
        viewHolder.trade_num.setText("购买数目："+delivery.getTradeNum()+"斤");
        viewHolder.trade_product.setText( delivery.getProductName());
        viewHolder.trade_total_money.setText("交易总额："+delivery.getTradeMoney()+"元");
        viewHolder.trade_danjia.setText("单价："+delivery.getProductDanjia()+"元/斤");
        viewHolder.trade_time.setText(delivery.getType());

        return convertView;
    }

    static class ViewHolder {
        TextView trade_product;
        ImageView product_avatar;
        TextView  trade_danjia;
        TextView trade_num;
        TextView trade_total_money;
        TextView trade_type;
        TextView trade_time;

    }
}
