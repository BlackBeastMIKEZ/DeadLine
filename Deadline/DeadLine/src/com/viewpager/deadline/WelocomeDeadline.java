package com.viewpager.deadline;

/*
 * 这个是用户每次使用都会看到的欢迎界面
 */
import com.jikexueyuan.drawerlayoutusing.R;
import com.tab2_timer_home.deadline.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class WelocomeDeadline extends Activity {
	private boolean isfirstin=false;
	private static final int TIME=1500;     //延时退出欢迎界面的时间
	private static final int GO_HOME=1000;  
	private static final int GO_GUIDE=1001;
	private Handler mhandler=new Handler(){
		public void handleMessage(android.os.Message msg){
			switch (msg.what){
			case GO_HOME:
				gohome();
				break;
			case GO_GUIDE:
				goguide();
			break;
			}
		};
	};
	
	protected void onCreate(Bundle save){
		super.onCreate(save);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);
	

		init();
		
	}
	public void gohome(){
		//如果信号是gohome则显示欢迎界面
		Intent i=new Intent(WelocomeDeadline.this,MainActivity.class);
		startActivity(i);
		finish();
	}
	public void goguide(){
		//如果信号是goguide则显示引导界面
		Intent ii=new Intent(WelocomeDeadline.this,GuideDeadline.class);
		startActivity(ii);
		finish();
	}
	public void init(){
		SharedPreferences perPreferences=getSharedPreferences("deadline",0);//构建文件deadline存储用户是否是第一次登录
		isfirstin=perPreferences.getBoolean("isfirstin", true);
		if(!isfirstin){
			mhandler.sendEmptyMessageDelayed(GO_HOME, TIME);//如果不是第一次登陆则发送gohome信号到handler中
			
		}else{
			mhandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);//如果是第一次登陆则发送goguide信号
			Editor eidtor=perPreferences.edit();
			eidtor.putBoolean("isfirstin", false);
			eidtor.commit();
		}
	}
}
