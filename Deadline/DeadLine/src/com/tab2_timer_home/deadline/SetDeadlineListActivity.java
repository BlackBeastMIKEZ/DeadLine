package com.tab2_timer_home.deadline;
/*
 * �˽���Ϊ������������tab1�б�����ı༭����*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jikexueyuan.drawerlayoutusing.R;
import com.tab1_deadline_list.deadline.DeadlineListData;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


public class SetDeadlineListActivity extends Activity {
	protected static final int UPDATE_VIEW = 0;   //���̸߳���UI���ź�
	private EditText thing_set;   
	private Button date_set;
	private SharedPreferences pref_position;
	private SharedPreferences data;
	private ActionBar myactionBar;
	private int location;                        //Ҫ���༭��listview�����λ��
	private SharedPreferences.Editor editor;
	private Calendar c;                           
    private int year_deadline;
	private int month_deadline;
	private int month_deadline_1;
	private int day_deadline;
	private DatePickerDialog deadline_dialog;      //����ѡ��Ի���
	public Date currentdate;                       //�ֻ�����ʱ��
	public String currentdate_str;
	public SimpleDateFormat formater;              //���ڸ�ʽ��
	private Handler handler=new Handler(){         //handle�и���UI

		public void handleMessage(Message msg){
			switch (msg.what){
			case UPDATE_VIEW:
				SharedPreferences cposotion=getSharedPreferences("position",0);
				int c_position=cposotion.getInt("listpositioin", 0);             //��position�ļ��еõ���ǰ�ܵ��б�����Ŀ+1
				
				
				SharedPreferences pref=getSharedPreferences("DeadlineData"+location,0);  //�õ����λ�õ��б�����ļ���ϢDeadlineData
				SharedPreferences pref2;

				MainActivity.deadline_listdata.get(location).setTab_deadline_list_item1   //��������tab1���б�����listdata��ֵ���¼����ƣ�
				(pref.getString("thingname", "���"));
				MainActivity.deadline_listdata.get(location).setTab_deadline_list_item2
				("Ŀ���գ�"+data.getInt("year", 0)+"-"+data.getInt("month", 0)+"-"+data.getInt("day", 0));//��������tab1���б�����listdata��ֵ��Ŀ���գ�
				MainActivity.deadline_listdata.get(location).setDeadline_day
				(pref.getInt("betweenday", 0)+"��");                                //��������tab1���б�����listdata��ֵ������������
				
				
				//���潫�༭���listview��������ݷֱ�洢���¹����������У�Ϊ�༭֮���listview����������׼��
				String insertstring=pref.getString("thingname", "���"); //�洢�¼�����         
				int[] insertint=new int[4];      
				insertint[0]=pref.getInt("year", 0);                   //�洢���
				insertint[1]=pref.getInt("month", 0);                 //�洢�·�
				insertint[2]=pref.getInt("day", 0);                   //�洢����
				insertint[3]=pref.getInt("betweenday", 0);            //�洢��������
	
				//�������жϳ��༭���µ�listview����ĵ��������ᴦ��listview����һ��λ�ã��õ���λ��i
				int i=c_position-1;                           
				for(;i>=0;i--){
					pref2=getSharedPreferences("DeadlineData"+i,0);
					if(pref2.getInt("betweenday", 0)<pref.getInt("betweenday", 0)){
						break;
					}					
				}
				
				//��Ϊ������������µ�λ�ô���ԭ����λ�����������Ĵ������listview���ļ�
				if(i>location){
					for(int j=location;j<i;j++){
						SharedPreferences.Editor data1=getSharedPreferences("DeadlineData"+j,0).edit();
						SharedPreferences data2=getSharedPreferences("DeadlineData"+(j+1),0);
						//���Ƚ����ĵ�listview�������������ֵ��ʾ
						MainActivity.deadline_listdata.get(j).setDeadline_day(data2.getInt("betweenday", 0)+"��");
						MainActivity.deadline_listdata.get(j).setTab_deadline_list_item1(data2.getString("thingname", "0"));
						MainActivity.deadline_listdata.get(j).setTab_deadline_list_item2("Ŀ���գ�"+data2.getInt("year", 0)
								+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
						//��λ��Ϊj+1��DeadlineData�ļ���ֵ����λ��Ϊj��DeadlineData�ļ�
						data1.putString("thingname", data2.getString("thingname", "0")) ;
						   data1.putInt("year", data2.getInt("year", 0));
						   data1.putInt("month", data2.getInt("month", 0));
						   data1.putInt("day", data2.getInt("day", 0));
						   data1.putInt("betweenday", data2.getInt("betweenday", 0));
						   data1.commit();
					}
					//��������ĸ�ֵ��ʣ��λ��i�����༭�����listview�����µ�λ�ã�������������顣�������д洢�ű༭�����listview�����ֵ����λ��Ϊi��DeadlineData�ļ�
					editor=getSharedPreferences("DeadlineData"+i, 0).edit();
					editor.putString("thingname",insertstring) ;
					editor.putInt("year", insertint[0]);
					editor.putInt("month", insertint[1]);
					editor.putInt("day", insertint[2]);
					editor.putInt("betweenday", insertint[3]);
					editor.commit();
					//�����listdata�������¸�ֵ������λ��Ϊi������ֵ
					SharedPreferences data2=getSharedPreferences("DeadlineData"+i,0);
					MainActivity.deadline_listdata.get(i).setDeadline_day(data2.getInt("betweenday", 0)+"��");
					MainActivity.deadline_listdata.get(i).setTab_deadline_list_item1(data2.getString("thingname", "0"));
					MainActivity.deadline_listdata.get(i).setTab_deadline_list_item2("Ŀ���գ�"+data2.getInt("year", 0)
							+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
					
					MainActivity.deadlineadapter.notifyDataSetChanged(); //֪ͨ����������listview
			 }
				
			//����µ�λ��С��ԭ����λ�����������Ĵ������listview���ļ�����������ע�ͺ�˼������һ�δ����࣬�����ظ�ע��
				else{
		
					for(int j=location;j>(i+1);j--){
						SharedPreferences.Editor data1=getSharedPreferences("DeadlineData"+j,0).edit();
						SharedPreferences data2=getSharedPreferences("DeadlineData"+(j-1),0);
						
						MainActivity.deadline_listdata.get(j).setDeadline_day(data2.getInt("betweenday", 0)+"��");
						MainActivity.deadline_listdata.get(j).setTab_deadline_list_item1(data2.getString("thingname", "0"));
						MainActivity.deadline_listdata.get(j).setTab_deadline_list_item2("Ŀ���գ�"+data2.getInt("year", 0)
								+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
						
						data1.putString("thingname", data2.getString("thingname", "0")) ;
						   data1.putInt("year", data2.getInt("year", 0));
						   data1.putInt("month", data2.getInt("month", 0));
						   data1.putInt("day", data2.getInt("day", 0));
						   data1.putInt("betweenday", data2.getInt("betweenday", 0));
						   data1.commit();
					}
					editor=getSharedPreferences("DeadlineData"+(i+1), 0).edit();
					editor.putString("thingname",insertstring) ;
					editor.putInt("year", insertint[0]);
					editor.putInt("month", insertint[1]);
					editor.putInt("day", insertint[2]);
					editor.putInt("betweenday", insertint[3]);
					editor.commit();
					SharedPreferences data2=getSharedPreferences("DeadlineData"+(i+1),0);
					MainActivity.deadline_listdata.get(i+1).setDeadline_day(data2.getInt("betweenday", 0)+"��");
					MainActivity.deadline_listdata.get(i+1).setTab_deadline_list_item1(data2.getString("thingname", "0"));
					MainActivity.deadline_listdata.get(i+1).setTab_deadline_list_item2("Ŀ���գ�"+data2.getInt("year", 0)
							+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
					
					MainActivity.deadlineadapter.notifyDataSetChanged(); 
					
				}
				break;
				default:
					break;
			}
		}
       };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deadline_listsetting);
		init();
	
	}
	
	/*
	 * ����ķ����ǶԽ���ͳ�Ա���Եĳ�ʼ��*/
	public void init(){
		thing_set=(EditText) findViewById(R.id.deadline_listsetting_thing_content);
		date_set=(Button) findViewById(R.id.deadline_listsetting_data_content);
		Bundle bundle = getIntent().getExtras();       //��ȡ��IntentЯ����Ҫ�༭�����λ����Ϣ
        location = bundle.getInt("location");
        this.setTitle("�༭");                          //���ý������
        data=getSharedPreferences("DeadlineData"+location, 0);  //�õ���λ�õ�DeadlineData�ļ�
        if(!TextUtils.isEmpty(data.getString("thingname", "DeadLine"))){
        	 thing_set.setText(data.getString("thingname", "DeadLine")); //���ļ�����Ϣ���������ƣ���ʾ�ڱ༭������
        	 thing_set.setSelection(data.getString("thingname", "DeadLine").length());//������ƶ���ĩβ
		}
         
        date_set.setText(data.getInt("year", 0)+"-"+data.getInt("month", 0)+"-"+data.getInt("day", 0));  //���ļ�����Ϣ�����ڣ���ʾ�ڱ༭������
        myactionBar=this.getActionBar();                //����AcitonBar
		myactionBar.setDisplayShowHomeEnabled(true);
		myactionBar.setDisplayHomeAsUpEnabled(true);
		myactionBar.setHomeButtonEnabled(true);
		
		formater=new SimpleDateFormat("yyyy-MM-dd");     //�����ڸ�ʽ����ͳһ���ã�������������ڼӼ�����
		currentdate=new Date(System.currentTimeMillis()); //��ȡ����ǰ�ı���ʱ��
		currentdate_str=formater.format(currentdate);     //����ǰ�ı���ʱ����и�ʽ����
		
	
		//����Ĵ���Ϊ�������ڶԻ������û�ѡ������
		year_deadline=data.getInt("year", 0);
		month_deadline=data.getInt("month", 0);
		month_deadline_1=data.getInt("month", 0)-1;
		day_deadline=data.getInt("day", 0);
		deadline_dialog=new DatePickerDialog(this,
				new OnDateSetListener(){
					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// TODO Auto-generated method stub
						year_deadline=year;
						month_deadline=monthOfYear+1;
						day_deadline=dayOfMonth;
						String deadlintText=year+"-"+month_deadline+"-"+dayOfMonth;
						date_set.setText(deadlintText);
					}
		}, year_deadline, month_deadline_1, day_deadline);		
		date_set.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deadline_dialog.show();
			}
		});
	}
	
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deadlineset, menu);
		return true;
	}
	
	/*
	 * ��������ActionBar�ϰ�ť���¼�����*/
	public boolean onOptionsItemSelected(MenuItem item){
		//���������Ͻǰ�ť�Ļ��򷵻�������
		if(item.getItemId()==android.R.id.home){         
			Intent i=new Intent(this,MainActivity.class);
			  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		//���������Ͻǵ���ɱ༭��ť����������²���
		if(item.getItemId()==R.id.action_add_set){
			//�������úõ��¼�����������д�뵽��Ӧ��DeadlineData�ļ���
			editor=this.getSharedPreferences("DeadlineData"+location, 0).edit();
			editor.putString("thingname", thing_set.getText().toString());
			editor.putInt("year", year_deadline);
			editor.putInt("month", month_deadline);
			editor.putInt("day", day_deadline);
			String destr=year_deadline+"-"+month_deadline+"-"+day_deadline;
			
			//���¼��㵱��ʱ����Ŀ���յ�������������ҽ���Ϣ���뵽��listview�����Ӧ��DeadlineData�ļ���ȥ
			try {
				Date begindate = formater.parse(currentdate_str);
				Date enddate=formater.parse(destr);
				int betweendate=(int) ((enddate.getTime()-begindate.getTime())/(1000*60*60*24));
				editor.putInt("betweenday", betweendate);
				
				}catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			editor.commit();
			
			//�������̣߳������߳��и���UI
			new Thread(new Runnable(){
				public void run(){
					Message msg=new Message();
					msg.what=UPDATE_VIEW;
				     handler.sendMessage(msg);
							
				}
			}).start();
			
			//���ص�������
			Intent i=new Intent(this,MainActivity.class);
			startActivity(i);
			
		}
		return super.onOptionsItemSelected(item);
    }
    


}
