package com.customer_feedback.deadline;
/*
 * �ý������û��ķ���������棬������벼�ֵķ���ѡ������Ľ���*/
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
	private List<FeedbackMsg> list=new ArrayList<FeedbackMsg>(); //�û���������list����
	private ListView listview;
	private TextView inputtext;
	private Button send;
	private FeedbackMsgAdapter adapter;  //�û��������listview��������
	private ActionBar myactionBar;
	
	private IntentFilter sendmessageintenfilter;  //������Ϣ��Intent��ͼ
	private SendSituationReceiver ssr;            //��Ϣ���͵Ĺ㲥������
	
  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cusotmer__feedback);
        this.setTitle("����");                   //���ô��ڱ���
        myactionBar=this.getActionBar();        
		myactionBar.setDisplayShowHomeEnabled(true);//ActionBar����
		myactionBar.setDisplayHomeAsUpEnabled(true);//ActionBar����
		myactionBar.setHomeButtonEnabled(true);//ActionBar����
        send=(Button) findViewById(R.id.send);
        listview=(ListView) findViewById(R.id.message_list_view);
        inputtext=(TextView) findViewById(R.id.input_text);
        FeedbackMsg msg2=new FeedbackMsg("����!��ʱ��������Ϣֻ���Զ�����ʽ���ͣ�SORRY",FeedbackMsg.TYPE_RECRIVE);
    	list.add(msg2);
        adapter=new FeedbackMsgAdapter(CustomerfeedbackActivity.this,list);
        listview.setAdapter(adapter);
        
        sendmessageintenfilter=new IntentFilter();
        sendmessageintenfilter.addAction("SEND_SMS_ACTION");
        ssr=new SendSituationReceiver();
        registerReceiver(ssr,sendmessageintenfilter);
        
        //���÷��Ͱ�ť���¼�������
        send.setOnClickListener(new OnClickListener(){
    	   public void onClick(View v){
    		   String content=inputtext.getText().toString();
    		   if(!"".equals(content)){                          //����������ݲ�Ϊ���������������ݷ���
    			   
    			   SmsManager smsmanager=SmsManager.getDefault();  //�õ���Ϣ��Ĺ���Manager��
    			   Intent sendintent=new Intent("SEND_SMS_ACTION");
    			   PendingIntent pi=PendingIntent.getBroadcast(CustomerfeedbackActivity.this,
    					   0, sendintent, 0);
    			   
    			   smsmanager.sendTextMessage("18826101638", null, "����û���"+content, pi, null);//������Ϣ��18826101638�û�
    			   FeedbackMsg msg=new FeedbackMsg(content,FeedbackMsg.TYPE_SEND);         //ָ����ϢΪ���ͷ�����Ϣ
    			   list.add(msg);             //����list������
    			   adapter.notifyDataSetChanged();//֪ͨ�����������ı�
    			   listview.setSelection(list.size());//��listviewָ����һ���б�����
    			   inputtext.setText("");          //�����Ϳ�������Ϊ��
    			   
    			   //�������̣߳�������Ϣ��handler�����UI����
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
			//�����ܵ��û������Ķ���֮��ģ�ⷴ����Ϣ
			if(getResultCode()==RESULT_OK){
				for(int i=0;i<10;i++)
					for(int j=0;j<10000000;j++);
				FeedbackMsg msg2=new FeedbackMsg("����!���ı���������յ����ǳ���л��",FeedbackMsg.TYPE_RECRIVE);
		    	list.add(msg2);
		    	adapter.notifyDataSetChanged();
				//Toast.makeText(context, "���ͳɹ������ǽ����촦��", Toast.LENGTH_LONG).show();
			}
			else{
				Toast.makeText(context, "����ʧ�ܣ����ٷ�һ��", Toast.LENGTH_LONG).show();
			}
		}
    	
    }

	public boolean onOptionsItemSelected(MenuItem item){
		if(item.getItemId()==android.R.id.home){            //������Ͻǰ�ť����������
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

