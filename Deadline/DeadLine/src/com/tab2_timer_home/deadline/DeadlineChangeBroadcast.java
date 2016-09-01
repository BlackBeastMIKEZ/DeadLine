package com.tab2_timer_home.deadline;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class DeadlineChangeBroadcast extends BroadcastReceiver {

	@Override
	
	//广播接收到更新UI服务的指示后发送广播给构建通知的服务
	public void onReceive(Context context, Intent intent) {
		Intent i= new Intent(context,NotificationConstructorService.class);
		context.startService(i);
		//Toast.makeText(context, "发送通知啦", Toast.LENGTH_LONG).show();

	}


}
