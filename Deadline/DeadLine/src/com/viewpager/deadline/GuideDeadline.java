package com.viewpager.deadline;

import java.util.ArrayList;
import java.util.List;

import com.jikexueyuan.drawerlayoutusing.R;
import com.tab2_timer_home.deadline.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
/*
 * 这个是用户第一次使用的引导页界面
 */
public class GuideDeadline extends Activity{
	private List<View> list;
	private ViewPager viewpager;
	private ViewpagerDeadlineAdapter vgadapter;
	private ImageView[] imageview;
	private Button start;
	protected void onCreate(Bundle save){
		super.onCreate(save);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.guide_layout);
		initviews();
	
		start.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(GuideDeadline.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
			
		});
	}
	
	public void initviews(){
		list=new ArrayList<View>();
		LayoutInflater inflater=LayoutInflater.from(GuideDeadline.this);
		list.add(inflater.inflate(R.layout.viewthird,null));
		vgadapter=new ViewpagerDeadlineAdapter(list,GuideDeadline.this);
		viewpager=(ViewPager) findViewById(R.id.guide);
		viewpager.setAdapter(vgadapter);
		start=(Button) list.get(0).findViewById(R.id.button_1);
		
	}

	}

