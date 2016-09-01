package mah.farmer.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.locTest.R;

import java.util.List;

import mah.farmer.ui.PublishProductActivity;
import mah.farmer.view.MyPopupWindow;
/**下拉选择框适配器
 * Created by 黑色野兽迈特祖 on 2016/4/25.
 */
public class SpinnerAdapter extends BaseAdapter {
	
	private int mPosition;
	private List<String> mItems;
	private MyPopupWindow mWindow;
	private PublishProductActivity mActivity;
	private onItemClickListener mListener;
	
	public SpinnerAdapter(MyPopupWindow window, PublishProductActivity activity, List<String> items){
		this.mActivity=activity;
		this.mItems=items;
		this.mWindow=window;
	}
	
	public int getCount() {
		return mItems.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder { // item的4个控件
		public TextView text;
	}
			
	public View getView(final int arg0, final View arg1, ViewGroup arg2) {
		//获取设置好的listener
		mListener=mWindow.getListener();
		View view=arg1;
		ViewHolder holder=null;
		if(view==null){
			view= View.inflate(mActivity, R.layout.myspinner_list_item, null);
			holder = new ViewHolder();
			holder.text=(TextView) view.findViewById(R.id.tv_text);
			view.setTag(holder);
		}else {
			holder = (ViewHolder) view.getTag();
		}
		holder.text.setText(mItems.get(arg0));
		
		view.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				mPosition=arg0;	
				mWindow.close();
				mListener.click(mPosition,arg1);
			}
		});
		return view;
	}
	//定义接口和一个为实现的方法
	public interface onItemClickListener{
		public void click(int position, View view);
	}	
}