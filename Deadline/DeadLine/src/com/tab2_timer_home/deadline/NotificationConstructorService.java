package com.tab2_timer_home.deadline;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;

public class NotificationConstructorService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	public int onStartCommand(Intent intent, int flags, int startId){
		
		SharedPreferences pref_position=getSharedPreferences("position",0);
		SharedPreferences data;
		String messageContent ="";
		if(pref_position.getInt("listpositioin", 0)!=0){
		for(int i=0;i<pref_position.getInt("listpositioin", 0);i++){
			if(i==3)
				break;
		   data=getSharedPreferences("DeadlineData"+i,0);
		   if(data.getInt("betweenday", 0)<0){
			   messageContent=messageContent+data.getString("thingname", "")+"��ȥ"+(data.getInt("betweenday", 0)*(-1)+"��   ");
		   }
		   if(data.getInt("betweenday", 0)>0)
		   messageContent=messageContent+data.getString("thingname", "")+"ʣ"+(data.getInt("betweenday", 0)+"��   ");
		   if(data.getInt("betweenday", 0)==0)
			   messageContent=messageContent+data.getString("thingname", "")+"�����ֹ   ";
		}
		
		
		NotificationManager manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent i=new Intent(this,MainActivity.class);
		PendingIntent p=PendingIntent.getActivity(this, 0, i,PendingIntent.FLAG_CANCEL_CURRENT);
		Notification notification=new Notification.Builder(this)
		.setTicker("��ס���õ�����")
		.setSmallIcon(com.jikexueyuan.drawerlayoutusing.R.drawable.ic_launcher)
		.setContentTitle("ÿһ�춼����һ����")
		.setContentText(messageContent)
		.setContentIntent(p)
		.build();
		manager.notify((int)System.currentTimeMillis(), notification);
		for(int i1=0;i1<pref_position.getInt("listpositioin", 0);i1++){
			   data=getSharedPreferences("DeadlineData"+i1,0);
			   MainActivity.deadline_listdata.get(i1).setDeadline_day(data.getInt("betweenday", 0)+"��");
			}		
		MainActivity.deadlineadapter.notifyDataSetChanged();
		}
		return super.onStartCommand(intent, flags, startId);
	}


}
