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
		
		int delay_time=0;          //将通知的时间调到每天00：00分
		int period=24*60*60*1000;  //定时每天执行一次发送通知
		
		//然后计算与零点的时间差，定时发送通知，零点发送通知
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
		//delay_time=60*1000;为了更快看到效果，可将这两行代码注释去掉即可看到效果，这是我展示会改的代码
		//period=60*1000
		if(timer==null)
			timer=new Timer();
		timer.schedule(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				//将所有的倒数事项的倒数天数减一，保存到各自的DeadlineData文件中
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
				 //发送广播给广播接收器进行UI更新
				 sendBroadcast(new Intent( "com.tab2_timer_home.deadline.CHANGE_DATA"));
			}
			
		}, delay_time,period);
		return super.onStartCommand(intent, flags, startId);
	}

}


