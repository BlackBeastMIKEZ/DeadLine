package com.tab2_timer_home.deadline;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class BootCampNotificationBroadcast extends BroadcastReceiver{

	@Override
	//开机发送广播开始进行倒计时服务
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent i=new Intent(context,NotificationDeadlineService.class);
		context.startService(i);
	}

}
