package mah.farmer.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.baidu.locTest.R;

import java.util.List;

import mah.farmer.adapter.SpinnerAdapter;
import mah.farmer.ui.PublishProductActivity;

public class MyPopupWindow extends PopupWindow implements OnItemClickListener {
	
	private List<String> mItems;
	private MyPopupWindow mWindow;
	private SpinnerAdapter.onItemClickListener mListener;
	
	public MyPopupWindow(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyPopupWindow(PublishProductActivity activity, int width, List<String> items){
		LayoutInflater inflater=activity.getLayoutInflater();
		View contentView=inflater.inflate(R.layout.activity_pop, null);
		// 设置PopupWindow的View 
		this.setContentView(contentView);
		 // 设置PopupWindow弹出窗体的宽  
		this.setWidth(width);
        // 设置PopupWindow弹出窗体的高  
		this.setHeight(android.view.WindowManager.LayoutParams.WRAP_CONTENT);  
		this.setFocusable(true);  
        this.setOutsideTouchable(true);  
        // 刷新状态  
        this.update();   
     // 实例化一个ColorDrawable颜色为半透明  
        ColorDrawable dw = new ColorDrawable(0000000000);
        this.setBackgroundDrawable(dw); 
        
        this.mItems=items;
        ListView listView=(ListView) contentView.findViewById(R.id.lv_list);
        mWindow=this;
        SpinnerAdapter adapter=new SpinnerAdapter(mWindow,activity, mItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MyPopupWindow.this.dismiss();
		
	}

	public void showAtLocation(View parent, int gravity, int x, int y) {
		super.showAtLocation(parent, gravity, x, y);
	}
	
	public void close(){
		this.dismiss();
	}
	
	public int position(){
		
		return 0;	
	}
	
	public void setOnItemClickListener(SpinnerAdapter.onItemClickListener listener){
		this.mListener=listener;
	}
	
	public SpinnerAdapter.onItemClickListener getListener(){
		//可以通过this的实例来获取设置好的listener
		return mListener;		
	}
}
