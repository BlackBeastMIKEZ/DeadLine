package com.tab2_timer_home.deadline;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.jikexueyuan.drawerlayoutusing.R;
import com.tab1_deadline_list.deadline.DeadlineListData;



import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;


public class NotificationDeadlineService extends Service {
	public static Timer timer=null;
	public SimpleDateFormat formater;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int onStartCommand(Intent intent, int flags, int startId){
		
		int delay_time=0;          //��֪ͨ��ʱ�����ÿ��00��00��
		int period=24*60*60*1000;  //��ʱÿ��ִ��һ�η���֪ͨ
		
		//Ȼ�����������ʱ����ʱ����֪ͨ����㷢��֪ͨ
			Date currentdate=new Date(System.currentTimeMillis());
			formater=new SimpleDateFormat("HHmmss");
			String currentdate_str=formater.format(currentdate);
			try {
				Date begindate = formater.parse(currentdate_str);
				Date enddate=formater.parse("240000");
				delay_time=(int) ((enddate.getTime()-begindate.getTime()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//delay_time=60*1000;Ϊ�˸��쿴��Ч�����ɽ������д���ע��ȥ�����ɿ���Ч����������չʾ��ĵĴ���
		//period=60*1000
		if(timer==null)
			timer=new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//�����еĵ�������ĵ���������һ�����浽���Ե�DeadlineData�ļ���
				 SharedPreferences pref_position;
				 SharedPreferences data;
				 SharedPreferences.Editor editor;
				 pref_position=getSharedPreferences("position", 0);
				 for(int i=0;i<pref_position.getInt("listpositioin", 0);i++){
					 editor=getSharedPreferences("DeadlineData"+i, 0).edit();
					 data=getSharedPreferences("DeadlineData"+i, 0);
					 editor.putInt("betweenday", data.getInt("betweenday", 0)-1);
					 editor.commit();
				 }
				 //���͹㲥���㲥����������UI����
				 sendBroadcast(new Intent( "com.tab2_timer_home.deadline.CHANGE_DATA"));
			}
			
		}, delay_time,period);
		return super.onStartCommand(intent, flags, startId);
	}

}


