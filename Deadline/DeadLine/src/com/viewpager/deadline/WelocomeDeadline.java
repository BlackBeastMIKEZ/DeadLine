package com.viewpager.deadline;

/*
 * ������û�ÿ��ʹ�ö��ῴ���Ļ�ӭ����
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
	private static final int TIME=1500;     //��ʱ�˳���ӭ�����ʱ��
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
		//����ź���gohome����ʾ��ӭ����
		Intent i=new Intent(WelocomeDeadline.this,MainActivity.class);
		startActivity(i);
		finish();
	}
	public void goguide(){
		//����ź���goguide����ʾ��������
		Intent ii=new Intent(WelocomeDeadline.this,GuideDeadline.class);
		startActivity(ii);
		finish();
	}
	public void init(){
		SharedPreferences perPreferences=getSharedPreferences("deadline",0);//�����ļ�deadline�洢�û��Ƿ��ǵ�һ�ε�¼
		isfirstin=perPreferences.getBoolean("isfirstin", true);
		if(!isfirstin){
			mhandler.sendEmptyMessageDelayed(GO_HOME, TIME);//������ǵ�һ�ε�½����gohome�źŵ�handler��
			
		}else{
			mhandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);//����ǵ�һ�ε�½����goguide�ź�
			Editor eidtor=perPreferences.edit();
			eidtor.putBoolean("isfirstin", false);
			eidtor.commit();
		}
	}
}
