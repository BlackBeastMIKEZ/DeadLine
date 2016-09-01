package com.tab1_deadline_list.deadline;
/*
 * �ý���Ϊ�����������+��ť�󵯳��Ľ��棬���������tab1��listview�������µĵ����¼�*/

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jikexueyuan.drawerlayoutusing.R;
import com.tab2_timer_home.deadline.MainActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class AddDeadlineListActivity extends Activity {
	protected static final int UPDATE_VIEW = 1;
	private EditText add_thing;
	private Button add_data;
	private DatePickerDialog deadline_dialog;   //����ѡ���
	private Calendar c;                  
	private int year_deadline;                 //Ŀ�������
	private int month_deadline;                //Ŀ���շ�
	private int day_deadline;                  //Ŀ������
	private int year_current;                 //�������
	private int month_current;                //�����·�
	private int day_current;                  //��������
	private ActionBar myactionBar;
	public SharedPreferences.Editor editor;
	public  SharedPreferences.Editor editor_posotion;
	public  SharedPreferences cposotion;
	public Date currentdate;                   //��ȡ��ǰ����ʱ���Date��
	public String currentdate_str;
	public SimpleDateFormat formater;          //ʱ���ʽ��
	private MenuItem item1;
	//public int deadline_list_position;
	private Handler handler=new Handler(){     //����handler�������߳��и���UI

		public void handleMessage(Message msg){
			switch (msg.what){
			case UPDATE_VIEW:
				
				cposotion=getSharedPreferences("position",0);
				int c_position=cposotion.getInt("listpositioin", 0);  //�õ�position�ļ���listposition��ֵ�õ���ǰ�б�ĸ���
				
				//sendBroadcast(new Intent("com.tab2_timer_home.deadline.MY_BROADCAST"));
				
				SharedPreferences pref=getSharedPreferences("DeadlineData"+c_position,0);//�õ�listpositionλ�õ�DeadlineData�ļ�
				SharedPreferences pref2;

				//�����ǽ�listpositionλ�ö�Ӧ��DeadlineData�ļ���������ӵ�listview��������ȥ
				MainActivity.deadline_listdata.add(new DeadlineListData(
				pref.getString("thingname", "���"),
				pref.getInt("year", 0)+"-"+pref.getInt("month", 0)+"-"+pref.getInt("day", 0),
				pref.getInt("betweenday", 0)+"��"));
				
				//�����ǽ�listpositionλ�ö�Ӧ��DeadlineData�ļ������ݴ洢�������У�Ϊ�����listview������׼��
				String insertstring=new String();
				insertstring=pref.getString("thingname", "���");//�洢�洢����
				int[] insertint=new int[4];
				insertint[0]=pref.getInt("year", 0);          //�洢���
				insertint[1]=pref.getInt("month", 0);         //�洢�·�
				insertint[2]=pref.getInt("day", 0);           //�洢����
				insertint[3]=pref.getInt("betweenday", 0);    //�洢��������

				//����ͨ��һ��forѭ���жϳ������ɵ�listdata������listview���ĸ�λ�ã�����������������
				int i=c_position-1;
				for(;i>=0;i--){
					pref2=getSharedPreferences("DeadlineData"+i,0);
					if(pref2.getInt("betweenday", 0)<=pref.getInt("betweenday", 0)){
						break;
					}					
				}
				
				//�õ�����λ��i֮��ͨ��һ��forѭ����listview�������򣬲��ҽ���Ӧλ�õ�DeadlineData�ļ����и���
				for(int j=c_position;j>(i+1);j--){
					SharedPreferences.Editor data1=getSharedPreferences("DeadlineData"+j,0).edit();//�õ�DeadlineData j�ļ��ı༭��
					SharedPreferences data2=getSharedPreferences("DeadlineData"+(j-1),0);          //�õ�DeadlineData j-1�ļ�
					
					//���ȸ���listdata��jλ�õ�ֵ����j-1λ�õ�ֵ����jλ����
					MainActivity.deadline_listdata.get(j).setDeadline_day(data2.getInt("betweenday", 0)+"��");
					MainActivity.deadline_listdata.get(j).setTab_deadline_list_item1(data2.getString("thingname", "0"));
					MainActivity.deadline_listdata.get(j).setTab_deadline_list_item2("Ŀ���գ�"+data2.getInt("year", 0)
							+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
					
					//�ٸ�����Ӧ��DeadlineData�ļ�����j-1λ�õ�DeadlineData�ļ������ݸ�ֵ��jλ�õ�DeadlineData�ļ�
					data1.putString("thingname", data2.getString("thingname", "0")) ;
					   data1.putInt("year", data2.getInt("year", 0));
					   data1.putInt("month", data2.getInt("month", 0));
					   data1.putInt("day", data2.getInt("day", 0));
					   data1.putInt("betweenday", data2.getInt("betweenday", 0));
					   data1.commit();
				}
				//������λ��i+1�ϣ��������ӵ�λ�ã���DeadlineData�ļ������ҽ������洢���½������¼��Լ����ڵ������ֵ��ֵ��DeadlineData�ļ�
				editor=getSharedPreferences("DeadlineData"+(i+1), 0).edit();
				editor.putString("thingname",insertstring) ;
				editor.putInt("year", insertint[0]);
				editor.putInt("month", insertint[1]);
				editor.putInt("day", insertint[2]);
				editor.putInt("betweenday", insertint[3]);
				editor.commit();
				
				//����λ��i+1λ���ϵ�listdata������listview��������
				SharedPreferences data2=getSharedPreferences("DeadlineData"+(i+1),0);
				MainActivity.deadline_listdata.get(i+1).setDeadline_day(data2.getInt("betweenday", 0)+"��");
				MainActivity.deadline_listdata.get(i+1).setTab_deadline_list_item1(data2.getString("thingname", "0"));
				MainActivity.deadline_listdata.get(i+1).setTab_deadline_list_item2("Ŀ���գ�"+data2.getInt("year", 0)
						+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
				MainActivity.deadlineadapter.notifyDataSetChanged(); //֪ͨ����������listview�ĸ���
				
			    add_thing.setText("");//��������������
				add_data.setText("");//��������������
				editor_posotion.putInt("listpositioin", c_position+1);//��position�ļ��е�listpositionֵ����1
				editor_posotion.commit();
				break;
				default:
					break;
			}
		}
        };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deadline_list_add_activity);
		init();
	}
	
	/*
	 * �����ǶԸý����Լ���س�Ա���Ժ��й����õĳ�ʼ��*/
	public void init(){
		formater=new SimpleDateFormat("yyyy-MM-dd");                 //�������ڸ�ʽ
		currentdate=new Date(System.currentTimeMillis());            //��ȡ����ʱ��
		currentdate_str=formater.format(currentdate);                //������ʱ��ת��Ϊ��ʽ����ַ���
		add_thing=(EditText) findViewById(R.id.deadline_list_add_thing_content);
		add_data=(Button) findViewById(R.id.deadline_list_add_data_content);
		myactionBar=this.getActionBar(); 
		myactionBar.setDisplayShowHomeEnabled(true);            //��������ActioinBar
		myactionBar.setDisplayHomeAsUpEnabled(true);           //��������ActioinBar
		myactionBar.setHomeButtonEnabled(true);                //��������ActioinBar
	 
		//�������ж��Ƿ����position�ļ�������������򹹽�һ��position�ļ�����¼listdata��λ�ø���������ʼ��ֵlistpositioinΪ0
		File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs",
				"position.xml");
		if(!file.exists()){
		editor_posotion=this.getSharedPreferences("position", 0).edit();
		editor_posotion.putInt("listpositioin", 0);
		editor_posotion.commit();
		}
	
		
		c=Calendar.getInstance();
		year_current=c.get(Calendar.YEAR);//��ȡ����ǰ�ı������
		month_current=c.get(Calendar.MONTH);//��ȡ����ǰ���·�
		day_current=c.get(Calendar.DAY_OF_MONTH);//��ȡ����ǰ������
		this.setTitle("���");                  //���õ�ǰ����ı���
		
		//�����ǹ�������Ի���
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
						add_data.setText(deadlintText);
					}
		}, year_current, month_current, day_current);	
		//�ڰ�ť�����õ���¼���������������ܹ��������ڶԻ���
		add_data.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deadline_dialog.show();
			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deadlineadd, menu);//����AcitonBar�ϵİ�ť
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		//Ϊ���Ͻǵİ�ť���÷��ع��ܣ����ص�������
		if(item.getItemId()==android.R.id.home){           
			Intent i=new Intent(this,MainActivity.class);
			  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		
		//Ϊ���Ͻǵİ�ť����һϵ���¼�����
		if(item.getItemId()==R.id.action_add_list){
			if(add_thing.getText().toString().length()>1&&year_deadline!=0){  //���ж��û��Ƿ��������¼��ͽ�ֹ����
			    cposotion=getSharedPreferences("position",0);
			    int change_position=cposotion.getInt("listpositioin", 0);     //�õ�position�ļ���listpositionֵ
			    
			    editor=this.getSharedPreferences("DeadlineData"+change_position, 0).edit();//�����µĶ�Ӧ��λ��Ϊlistposition��DeadlineData�ļ�
				editor.putString("thingname", add_thing.getText().toString());          //���¼��������뵽�ļ���
				editor.putInt("year", year_deadline);                         //�������Ϣ���뵽�ļ���
				editor.putInt("month", month_deadline);                       //���·���Ϣ���뵽�ļ���
				editor.putInt("day", day_deadline);                           //��������Ϣ���뵽�ļ���
				
				//��������μ��㱾��ʱ������ֹ���ڻ��ж����죨������������
				String destr=year_deadline+"-"+month_deadline+"-"+day_deadline;//�õ���ֹ���ڵ��ַ���
					Date begindate;
					try {
						begindate = formater.parse(currentdate_str);
						Date enddate=formater.parse(destr);
						int betweendate=(int) ((enddate.getTime()-begindate.getTime())/(1000*60*60*24));//�õ���������
						editor.putInt("betweenday", betweendate);         //��������������Ϣ���뵽�ļ���
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				editor.commit();//�ύ��������ļ���Ч
				editor_posotion=this.getSharedPreferences("position", 0).edit(); //�õ�position�ļ��ı༭��
			  
				//�������߳̽���UI����
				new Thread(new Runnable(){
					public void run(){
						Message msg=new Message();
						msg.what=UPDATE_VIEW;
					     handler.sendMessage(msg);
								
					}
				}).start();
				
		    //��ɲ���֮����ת��������
			Intent i=new Intent(this,MainActivity.class);
			startActivity(i);	
		}
			else{
				Toast.makeText(this, "�������¼����ƺͽ�ֹ����", Toast.LENGTH_SHORT).show();//����û�û�������¼��ͽ�ֹ�����򵯳���ʾ
				
		}
		
		}
		return super.onOptionsItemSelected(item);
	}
}
