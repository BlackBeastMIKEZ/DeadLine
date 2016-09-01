package mah.farmer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.locTest.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import mah.farmer.bean.FarmerProduct;
import mah.farmer.bean.LandHireInfo;
import mah.farmer.util.ImageLoadOptions;

/**田地租用界面适配器
 * Created by 黑色野兽迈特祖 on 2016/4/26.
 */
public class LandHireInfoAdapter extends BaseAdapter{
    private Context ct;
    private List<LandHireInfo> data;

    public LandHireInfoAdapter(Context ct, List<LandHireInfo> datas) {
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
    public void updateListView(List<LandHireInfo> list) {
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
                    R.layout.item_land_hire_list, null);


            viewHolder = new ViewHolder();

            viewHolder.hire_num= (TextView) convertView.findViewById(R.id.info_hire_land_num);
            viewHolder.hire_location= (TextView) convertView.findViewById(R.id.info_hire_land_location);
            viewHolder.hirer_name= (TextView) convertView.findViewById(R.id.info_hirer_name);
            viewHolder.hirer_avatar= (ImageView) convertView.findViewById(R.id.info_hirer_avatar);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //  User friend = data.get(position);
        LandHireInfo landHireInfo=data.get(position);
        /**
         * 这里进行赋值还有点击事件的操作
         */
//        viewHolder.hirer_avatar.setImageResource(R.drawable.default_default_head);
        viewHolder.hirer_name.setText(landHireInfo.getHirer());
        viewHolder.hire_num.setText(landHireInfo.getHireNum()+"亩");
        viewHolder.hire_location.setText("位置："+landHireInfo.getLandLocation());

        if (landHireInfo.getHirerImage()!= null && !landHireInfo.getHirerImage().equals("")) {
            ImageLoader.getInstance().displayImage(landHireInfo.getHirerImage(), viewHolder.hirer_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            viewHolder.hirer_avatar.setImageResource(R.drawable.default_default_head);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView hirer_name;
        ImageView hirer_avatar;
        TextView hire_num;
        TextView hire_location;

    }

}
