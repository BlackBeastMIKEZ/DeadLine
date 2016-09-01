package com.customer_feedback.deadline;

import java.util.List;

import com.jikexueyuan.drawerlayoutusing.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedbackMsgAdapter extends BaseAdapter {
	private List<FeedbackMsg> feedback_listdata;
	private Context context;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return feedback_listdata.size();
	}

	public FeedbackMsgAdapter(Context context,List<FeedbackMsg> feedback_listdata) {
		super();
		this.feedback_listdata = feedback_listdata;
		this.context = context;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return feedback_listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		FeedbackMsg data=(FeedbackMsg) getItem(position);
		ViewHolder viewholder=new ViewHolder();
		if(convertView==null){
			LayoutInflater layoutinflater=LayoutInflater.from(context);
			convertView=layoutinflater.inflate(R.layout.customer_feedback_listitem,null);
			viewholder.left_layout=(LinearLayout) convertView.findViewById(R.id.left_layout);
			viewholder.right_layout=(LinearLayout) convertView.findViewById(R.id.right_layout);
			viewholder.left_msg=(TextView) convertView.findViewById(R.id.left_msg);
			viewholder.right_msg=(TextView) convertView.findViewById(R.id.right_msg);
			convertView.setTag(viewholder);
		}
		else{
			viewholder=(ViewHolder) convertView.getTag();
		}
		if(data.getType()==FeedbackMsg.TYPE_RECRIVE){    //如果用户发送信息，则左边的聊天布局显示，右边的聊天布局隐藏
			viewholder.left_layout.setVisibility(View.VISIBLE);
			viewholder.right_layout.setVisibility(View.GONE);
			viewholder.left_msg.setText( data.getContent());
		}
		else{                                            //如果我们发送信息，则右边的连天布局显示，左边的聊天布局隐藏
			viewholder.left_layout.setVisibility(View.GONE);
			viewholder.right_layout.setVisibility(View.VISIBLE);
			viewholder.right_msg.setText(data.getContent());
		}
		return convertView;
	}
	class ViewHolder {
		private LinearLayout left_layout;
		private LinearLayout right_layout;
		private TextView left_msg;
		private TextView right_msg;
	}

}
