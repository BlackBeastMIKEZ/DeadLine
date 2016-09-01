package mah.farmer.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.locTest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import mah.farmer.util.ImageLoadOptions;
import mah.farmer.bean.FarmerProduct;
import mah.farmer.ui.ProductInfoActivity;


/**淘农界面适配器
 * Created by 黑色野兽迈特祖 on 2016/4/21.
 */
public class FarmerShopAdapter extends BaseAdapter {
    private Context ct;
    private List<FarmerProduct> data;

    public FarmerShopAdapter(Context ct, List<FarmerProduct> datas) {
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
    public void updateListView(List<FarmerProduct> list) {
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
                    R.layout.item_farmer_shop_list, null);


            viewHolder = new ViewHolder();
            viewHolder.fruit_avatar= (ImageView) convertView.findViewById(R.id.fruit_avatar_avatar);
            viewHolder.fruit_name= (TextView) convertView.findViewById(R.id.fruit_name);
            viewHolder.fruit_location=(TextView) convertView.findViewById(R.id.fruit_location);
            viewHolder.fruit_commomprice=(TextView) convertView.findViewById(R.id.fruit_commomprice);
            viewHolder.fruit_myprice=(TextView) convertView.findViewById(R.id.fruit_myprice);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        FarmerProduct farmerShop=data.get(position);
        /**
         * 这里进行赋值还有点击事件的操作
         */
        Log.i("图像",position+farmerShop.getAvata_md5().toString());
        if (farmerShop.getAvata_md5() != null && !farmerShop.getAvata_md5().equals("")) {
            ImageLoader.getInstance().displayImage(farmerShop.getAvata_md5(),  viewHolder.fruit_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            viewHolder.fruit_avatar.setImageResource(R.drawable.default_default_head);
        }
//        viewHolder.fruit_avatar.setImageResource(R.drawable.default_default_head);

        viewHolder.fruit_name.setText(farmerShop.getName());
        viewHolder.fruit_location.setText("位置："+farmerShop.getLocation());
        viewHolder.fruit_commomprice.setText("普通门市批发价："+(Float.parseFloat(farmerShop.getMyprice())+1)+"元/斤");
        viewHolder.fruit_myprice.setText("优农批发销售价："+farmerShop.getMyprice()+"元/斤");
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ct, ProductInfoActivity.class);
                intent.putExtra("products", data.get(position));
                ct.startActivity(intent);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView fruit_name;
        ImageView fruit_avatar;
        TextView fruit_location;
        TextView fruit_commomprice;
        TextView fruit_myprice;

    }
}
