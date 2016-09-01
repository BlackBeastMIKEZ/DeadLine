package com.customer_feedback.deadline;
/*
 * 该界面是用户的反馈聊天界面，点击抽屉布局的反馈选项弹出来的界面*/
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.jikexueyuan.drawerlayoutusing.R;
import com.tab2_timer_home.deadline.MainActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerfeedbackActivity extends Activity {

	protected static final int UPDATE_VIEW = 0;
	private List<FeedbackMsg> list=new ArrayList<FeedbackMsg>(); //用户聊天界面的list数组
	private ListView listview;
	private TextView inputtext;
	private Button send;
	private FeedbackMsgAdapter adapter;  //用户聊天界面listview的适配器
	private ActionBar myactionBar;
	
	private IntentFilter sendmessageintenfilter;  //发送信息的Intent意图
	private SendSituationReceiver ssr;            //信息发送的广播接收器
	
  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusotmer__feedback);
        this.setTitle("反馈");                   //设置窗口标题
        myactionBar=this.getActionBar();        
		myactionBar.setDisplayShowHomeEnabled(true);//ActionBar激活
		myactionBar.setDisplayHomeAsUpEnabled(true);//ActionBar激活
		myactionBar.setHomeButtonEnabled(true);//ActionBar激活
        send=(Button) findViewById(R.id.send);
        listview=(ListView) findViewById(R.id.message_list_view);
        inputtext=(TextView) findViewById(R.id.input_text);
        FeedbackMsg msg2=new FeedbackMsg("您好!暂时您发的信息只能以短信形式发送，SORRY",FeedbackMsg.TYPE_RECRIVE);
    	list.add(msg2);
        adapter=new FeedbackMsgAdapter(CustomerfeedbackActivity.this,list);
        listview.setAdapter(adapter);
        
        sendmessageintenfilter=new IntentFilter();
        sendmessageintenfilter.addAction("SEND_SMS_ACTION");
        ssr=new SendSituationReceiver();
        registerReceiver(ssr,sendmessageintenfilter);
        
        //设置发送按钮的事件监听器
        send.setOnClickListener(new OnClickListener(){
    	   public void onClick(View v){
    		   String content=inputtext.getText().toString();
    		   if(!"".equals(content)){                          //如果发送内容不为空则进行下面的内容发送
    			   
    			   SmsManager smsmanager=SmsManager.getDefault();  //得到信息类的管理Manager类
    			   Intent sendintent=new Intent("SEND_SMS_ACTION");
    			   PendingIntent pi=PendingIntent.getBroadcast(CustomerfeedbackActivity.this,
    					   0, sendintent, 0);
    			   
    			   smsmanager.sendTextMessage("18826101638", null, "吗哈用户："+content, pi, null);//发送信息到18826101638用户
    			   FeedbackMsg msg=new FeedbackMsg(content,FeedbackMsg.TYPE_SEND);         //指定信息为发送方的信息
    			   list.add(msg);             //增加list的数据
    			   adapter.notifyDataSetChanged();//通知适配器做出改变
    			   listview.setSelection(list.size());//将listview指向到下一个列表数据
    			   inputtext.setText("");          //将发送框内容置为空
    			   
    			   //构建子线程，发送信息到handler类进行UI更新
    			   new Thread(new Runnable(){
		   					private int UPDATE_VIEW;
							public void run(){
		   						Message msg=new Message();
		   						msg.what=UPDATE_VIEW;
		   					    handler.sendMessage(msg);
   								
   					}
   				}).start();
    			}
    		   }
    	   
       });
    }
    public void onDestroy(){
    	super.onDestroy();
    	unregisterReceiver(ssr);
    }
    class SendSituationReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			//当接受到用户发出的短信之后，模拟反馈信息
			if(getResultCode()==RESULT_OK){
				for(int i=0;i<10;i++)
					for(int j=0;j<10000000;j++);
				FeedbackMsg msg2=new FeedbackMsg("您好!您的宝贵意见已收到，非常感谢！",FeedbackMsg.TYPE_RECRIVE);
		    	list.add(msg2);
		    	adapter.notifyDataSetChanged();
				//Toast.makeText(context, "发送成功，我们将尽快处理", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(context, "发送失败，请再发一次", Toast.LENGTH_LONG).show();
			}
		}
    	
    }

	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId()==android.R.id.home){            //点击左上角按钮返回主界面
			Intent i=new Intent(this,MainActivity.class);
			  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}

    private Handler handler=new Handler(){

		public void handleMessage(Message msg){
			switch (msg.what){
			case UPDATE_VIEW:
				
			}
		}
    };

}

