package com.viewpager.deadline;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
/*
 * 这个是放图片的listview，作为用户第一次使用的引导页的适配器
 */
public class ViewpagerDeadlineAdapter extends PagerAdapter {
	private List<View> list;
	private Context context;
	
	public ViewpagerDeadlineAdapter(List<View> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	public void destroyItem(View view,int position,Object object){
		((ViewPager) view).removeView(list.get(position));
		object=null;
	}
	public Object instantiateItem(View view,int position){
		((ViewPager) view).addView(list.get(position));
		return list.get(position);
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0==arg1);
	}

}
