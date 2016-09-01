package mah.farmer.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.locTest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import mah.farmer.bean.PurchaseOrderInfo;
import mah.farmer.ui.PurchaseOrderDetailActivity;
import mah.farmer.util.ImageLoadOptions;

/**求购信息界面适配器
 * Created by 黑色野兽迈特祖 on 2016/4/25.
 */
public class PurchaseOrderAdapter extends BaseAdapter {
    private Context ct;
    private List<PurchaseOrderInfo> data;

    public PurchaseOrderAdapter(Context ct, List<PurchaseOrderInfo> datas) {
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
    public void updateListView(List<PurchaseOrderInfo> list) {
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
                    R.layout.item_farmer_purchase_order_list, null);


            viewHolder = new ViewHolder();
            // viewHolder.alpha = (TextView) convertView.findViewById(R.id.alpha_group);
//            viewHolder.name = (TextView) convertView
//                    .findViewById(R.id.tv_group_name);
//
//            viewHolder.avatar = (ImageView) convertView
//                    .findViewById(R.id.img_group_avatar);
            viewHolder.avatar= (ImageView) convertView.findViewById(R.id.info_avatar);
            viewHolder.name= (TextView) convertView.findViewById(R.id.info_name);
            viewHolder.location=(TextView) convertView.findViewById(R.id.info_location);
            viewHolder.num=(TextView) convertView.findViewById(R.id.info_num);
            viewHolder.product_name=(TextView) convertView.findViewById(R.id.info_product);

            viewHolder.more= (ImageView) convertView.findViewById(R.id.message_more);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //  User friend = data.get(position);
        final PurchaseOrderInfo farmerPurchaseOrder=data.get(position);
        /**
         * 这里进行赋值还有点击事件的操作
         */
        viewHolder.avatar.setImageResource(R.drawable.default_default_head);

        if (farmerPurchaseOrder.getPurchaserImage()!= null && !farmerPurchaseOrder.getPurchaserImage().equals("")) {
            ImageLoader.getInstance().displayImage(farmerPurchaseOrder.getPurchaserImage(), viewHolder.avatar,
                    ImageLoadOptions.getOptions());
        } else {
            viewHolder.avatar.setImageResource(R.drawable.default_default_head);
        }



        viewHolder.name.setText("求购方：" + farmerPurchaseOrder.getPurchaser());
        viewHolder.location.setText("位置："+farmerPurchaseOrder.getProductLocation());
        viewHolder.product_name.setText("求购："+farmerPurchaseOrder.getProductName());
        viewHolder.num.setText("数量："+farmerPurchaseOrder.getPurchaseNum()+"斤");


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct, PurchaseOrderDetailActivity.class);
                i.putExtra("purchaseOrder",farmerPurchaseOrder);
                ct.startActivity(i);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView name;
        ImageView avatar;
        TextView num;
        TextView location;
        TextView product_name;
        ImageView more;

    }

}
