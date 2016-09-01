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


/**搜索界面适配器
 * Created by 黑色野兽迈特祖 on 2016/4/22.
 */
public class HistorySearchAdapter extends BaseAdapter {
    private Context ct;
    private List<String> data;

    public HistorySearchAdapter(Context ct, List<String> datas) {
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
    public void updateListView(List<String> list) {
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
                    R.layout.item_farmershop_history_search, null);


            viewHolder = new ViewHolder();
            // viewHolder.alpha = (TextView) convertView.findViewById(R.id.alpha_group);

            viewHolder.historyname= (TextView) convertView.findViewById(R.id.history_name);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        //  User friend = data.get(position);
        String farmerShop=data.get(position);
        /**
         * 这里进行赋值还有点击事件的操作
         */
        viewHolder.historyname.setText(farmerShop.toString());

        return convertView;
    }

    static class ViewHolder {
      TextView historyname;

    }
}
