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

import mah.farmer.ui.LandRentInfoDetailActivity;
import mah.farmer.ui.PurchaseOrderDetailActivity;
import mah.farmer.util.ImageLoadOptions;
import mah.farmer.bean.LandRentInfo;

/**田地出租界面适配器
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class LandRentInfoAdapter extends BaseAdapter {
    private Context ct;
    private List<LandRentInfo> data;
    String avatar;

    public LandRentInfoAdapter(Context ct, List<LandRentInfo> datas) {
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
    public void updateListView(List<LandRentInfo> list) {
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
                    R.layout.item_land_rent_list, null);


            viewHolder = new ViewHolder();

            viewHolder.rent_land_avatar= (ImageView) convertView.findViewById(R.id.info_land_rent_avatar);
            viewHolder.renter_name= (TextView) convertView.findViewById(R.id.info_renter_name);
            viewHolder.rent_land_danjia= (TextView) convertView.findViewById(R.id.info_rent_land_danjia);
            viewHolder.rent_land_num= (TextView) convertView.findViewById(R.id.info_rent_land_num);
            viewHolder.rent_land_loaction= (TextView) convertView.findViewById(R.id.info_rent_land_location);


            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //  User friend = data.get(position);
        final LandRentInfo landRentInfo=data.get(position);
        /**
         * 这里进行赋值还有点击事件的操作
         */


        if (landRentInfo.getLandImag() != null && !landRentInfo.getLandImag() .equals("")) {
            ImageLoader.getInstance().displayImage(landRentInfo.getLandImag(),  viewHolder.rent_land_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            viewHolder.rent_land_avatar.setImageResource(R.drawable.default_default_head);
        }

//        viewHolder.rent_land_avatar.setImageResource(R.drawable.default_default_head);
        viewHolder.rent_land_loaction.setText("位置"+landRentInfo.getLandLocation());
        viewHolder.rent_land_danjia.setText("租金："+landRentInfo.getRentPrice()+"元/亩");
        viewHolder.rent_land_num.setText("亩数："+landRentInfo.getRentNum()+"亩");
        viewHolder.renter_name.setText("出租方："+landRentInfo.getRenter());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ct, LandRentInfoDetailActivity.class);
                i.putExtra("landRent",landRentInfo);
                ct.startActivity(i);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView renter_name;
        ImageView rent_land_avatar;
        TextView rent_land_num;
        TextView rent_land_danjia;
        TextView rent_land_loaction;

    }
}
