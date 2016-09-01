package com.tab1_deadline_list.deadline;

import java.util.List;

import com.jikexueyuan.drawerlayoutusing.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class DeadlineAdapter extends BaseAdapter {
	private Context context;
	private List<DeadlineListData> deadline_list;
	
	public DeadlineAdapter(Context context, List<DeadlineListData> deadline_list){
		this.context = context;
		this.deadline_list = deadline_list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return deadline_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return deadline_list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, 
			ViewGroup parent) {
	
		DeadlineListData data = deadline_list.get(position);
		//下面的代码设置字体的格式，w4为显示未到截止日期的事件的字体，w5为显示已经过了截止日期的事件的字体，w9为显示截止日期在今天的事件字体
		String w1="距离";
		String w2="剩";
		String w6="已过";
		String w11="截止在";
		String w22="今天";
		String w3=data.getTab_deadline_list_item1();
		SpannableStringBuilder w4=new SpannableStringBuilder();
		SpannableStringBuilder w5=new SpannableStringBuilder();
		SpannableStringBuilder w9=new SpannableStringBuilder();
		String w8=(-1)*Integer.valueOf(data.getDeadline_day()
				.substring(0, data.getDeadline_day().length()-1)).intValue()+"天";//将负数的倒数天数变为正数
		Spannable w7=new SpannableString(w8);
		w7.setSpan(new ForegroundColorSpan(0xFFFF0000), 0, data.getDeadline_day().length()-1,  //将已经过了截止日期的字体设置为红色
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		Spannable w33=new SpannableString(w22);      
		w33.setSpan(new ForegroundColorSpan(0xFF669933), 0, w22.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		w33.setSpan(new StyleSpan(Typeface.BOLD), 0, w22.length(),
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
	    int start=0;
	    int end=w1.length();
	    w4.append(w1);
		w4.setSpan(new AbsoluteSizeSpan(40), start, end, 
				  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   
		   start=end;
		   
		   end+=w3.length();
		   
		   
		   w4.append(w3);
		   w5.append(w3);
		   w9.append(w3);
		   w4.setSpan(new StyleSpan(Typeface.BOLD), start, end, 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w4.setSpan(new AbsoluteSizeSpan(80), start, end, 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w5.setSpan(new StyleSpan(Typeface.BOLD), 0, w3.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w5.setSpan(new AbsoluteSizeSpan(80), 0, w3.length(), 
				  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w5.setSpan(new ForegroundColorSpan(0xFFFF0000), 0, w3.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w5.setSpan(new StrikethroughSpan(), 0, w3.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   
		   w9.setSpan(new StyleSpan(Typeface.BOLD), 0, w3.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w9.setSpan(new AbsoluteSizeSpan(80), 0, w3.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w9.setSpan(new ForegroundColorSpan(0xFF669933), 0, w3.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w9.append(w11);
		   w9.setSpan(new AbsoluteSizeSpan(40), w3.length(), w3.length()+w11.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w9.setSpan(new ForegroundColorSpan(0xFF669933), w3.length(), w3.length()+w11.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   
		  start=end;
		  end+=w2.length();

		  w4.append(w2);
		  w5.append(w6);
		   w4.setSpan(new AbsoluteSizeSpan(40), start, end, 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w5.setSpan(new AbsoluteSizeSpan(40), w3.length(), w3.length()+w6.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   w5.setSpan(new ForegroundColorSpan(0xFFFF0000), w3.length(), w3.length()+w6.length(), 
				   Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		   //分为三种情况，第一种情况：还没过截止日期
		   if(Integer.valueOf(data.getDeadline_day().substring(0, data.getDeadline_day().length()-1))>0){
			   if(convertView == null){
					LayoutInflater inflate = LayoutInflater.from(context);
					convertView = inflate.inflate(R.layout.deadline_list_item, null);
					ViewHolder viewHolder = new ViewHolder();
					viewHolder.tvTitle = 
							(TextView) convertView.findViewById(R.id.tab_deadline_list_item1);
					viewHolder.tvContent = 
							(TextView) convertView.findViewById(R.id.tab_deadline_list_item2);
					viewHolder.tvday=(Button) convertView.findViewById(R.id.tab_deadline_list_setting);
					viewHolder.tvTitle.setText(w4);
					viewHolder.tvContent.setText(data.getTab_deadline_list_item2());
					viewHolder.tvday.setText(data.getDeadline_day());
					
				
						
					convertView.setTag(viewHolder);
					
				}else{
					ViewHolder viewHolder= 
							(ViewHolder) convertView.getTag();
					viewHolder.tvContent.setText(data.getTab_deadline_list_item2());
					viewHolder.tvTitle.setText(w4);
					viewHolder.tvday.setText(data.getDeadline_day());
				
				
				}
		   }
		   //第二种情况：已经过了截止日期
		   if(Integer.valueOf(data.getDeadline_day().substring(0, data.getDeadline_day().length()-1))<0){
			   if(convertView == null){
					LayoutInflater inflate = LayoutInflater.from(context);
					convertView = inflate.inflate(R.layout.deadline_list_item, null);
					ViewHolder viewHolder = new ViewHolder();
					viewHolder.tvTitle = 
							(TextView) convertView.findViewById(R.id.tab_deadline_list_item1);
					viewHolder.tvContent = 
							(TextView) convertView.findViewById(R.id.tab_deadline_list_item2);
					viewHolder.tvday=(Button) convertView.findViewById(R.id.tab_deadline_list_setting);
					viewHolder.tvTitle.setText(w5);
					viewHolder.tvContent.setText(data.getTab_deadline_list_item2());
					viewHolder.tvday.setText(w7);
								
					convertView.setTag(viewHolder);
					
				}else{
					ViewHolder viewHolder= 
							(ViewHolder) convertView.getTag();
					viewHolder.tvContent.setText(data.getTab_deadline_list_item2());
					viewHolder.tvTitle.setText(w5);
					
					viewHolder.tvday.setText(w7);
				
				
				}
		   }
		   //截止日期在今天
		   if(Integer.valueOf(data.getDeadline_day().substring(0, data.getDeadline_day().length()-1))==0){
			   if(convertView == null){
					LayoutInflater inflate = LayoutInflater.from(context);
					convertView = inflate.inflate(R.layout.deadline_list_item, null);
					ViewHolder viewHolder = new ViewHolder();
					viewHolder.tvTitle = 
							(TextView) convertView.findViewById(R.id.tab_deadline_list_item1);
					viewHolder.tvContent = 
							(TextView) convertView.findViewById(R.id.tab_deadline_list_item2);
					viewHolder.tvday=(Button) convertView.findViewById(R.id.tab_deadline_list_setting);
					viewHolder.tvTitle.setText(w9);
					viewHolder.tvContent.setText(data.getTab_deadline_list_item2());
					viewHolder.tvday.setText(w33);
								
					convertView.setTag(viewHolder);
					
				}else{
					ViewHolder viewHolder= 
							(ViewHolder) convertView.getTag();
					viewHolder.tvContent.setText(data.getTab_deadline_list_item2());
					viewHolder.tvTitle.setText(w9);
					
					viewHolder.tvday.setText(w33);
				
				
				}
		   }
		
		return convertView;
	}
	class ViewHolder {
		TextView tvTitle;
		TextView tvContent;
		Button tvday;


		
	}

}
