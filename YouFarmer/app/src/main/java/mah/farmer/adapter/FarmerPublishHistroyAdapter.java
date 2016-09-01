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

import mah.farmer.util.ImageLoadOptions;
import mah.farmer.bean.PublishHistory;

/**发布历史适配器
 * Created by 黑色野兽迈特祖 on 2016/4/25.
 */
public class FarmerPublishHistroyAdapter extends BaseAdapter {
    private Context ct;
    private List<PublishHistory> data;

    public FarmerPublishHistroyAdapter(Context ct, List<PublishHistory> datas) {
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
    public void updateListView(List<PublishHistory> list) {
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
                    R.layout.item_farmer_publish_list, null);


            viewHolder = new ViewHolder();
            // viewHolder.alpha = (TextView) convertView.findViewById(R.id.alpha_group);
//            viewHolder.name = (TextView) convertView
//                    .findViewById(R.id.tv_group_name);
//
//            viewHolder.avatar = (ImageView) convertView
//                    .findViewById(R.id.img_group_avatar);

            viewHolder.fruit_avatar= (ImageView) convertView.findViewById(R.id.fruit_avatar);
            viewHolder.fruit_name= (TextView) convertView.findViewById(R.id.fruit_name);
            viewHolder.fruit_location=(TextView) convertView.findViewById(R.id.fruit_location);
            viewHolder.fruit_num=(TextView) convertView.findViewById(R.id.fruit_commomprice);
            viewHolder.fruit_myprice=(TextView) convertView.findViewById(R.id.fruit_myprice);
            viewHolder.publish_time= (TextView) convertView.findViewById(R.id.publish_time);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //  User friend = data.get(position);
        PublishHistory publishHistory=data.get(position);
        /**
         * 这里进行赋值还有点击事件的操作
         */
        if ( publishHistory.getProductImage()!= null && !publishHistory.getProductImage().equals("")) {
            ImageLoader.getInstance().displayImage(publishHistory.getProductImage(), viewHolder.fruit_avatar,
                    ImageLoadOptions.getOptions());
        } else {
            viewHolder.fruit_avatar.setImageResource(R.drawable.default_head);
        }
        viewHolder.fruit_name.setText("产品："+publishHistory.getProductName());
        viewHolder.fruit_location.setText("位置："+publishHistory.getProductLocation());
        viewHolder.fruit_num.setText("数量："+publishHistory.getProductNum()+"斤");
        viewHolder.fruit_myprice.setText("单价："+publishHistory.getYouNongPrice()+"元/斤");
        viewHolder.publish_time.setText(publishHistory.getDatetime());

        return convertView;
    }

    static class ViewHolder {
        TextView fruit_name;
        ImageView fruit_avatar;
        TextView fruit_location;
        TextView fruit_num;
        TextView fruit_myprice;
        TextView publish_time;

    }
}
