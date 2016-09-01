package com.tab1_deadline_list.deadline;
/*
 * 该界面为点击主界面上+按钮后弹出的界面，作用是添加tab1中listview，生成新的倒数事件*/

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
	private DatePickerDialog deadline_dialog;   //日期选择框
	private Calendar c;                  
	private int year_deadline;                 //目标日年份
	private int month_deadline;                //目标日份
	private int day_deadline;                  //目标日期
	private int year_current;                 //本地年份
	private int month_current;                //本地月份
	private int day_current;                  //本地日期
	private ActionBar myactionBar;
	public SharedPreferences.Editor editor;
	public  SharedPreferences.Editor editor_posotion;
	public  SharedPreferences cposotion;
	public Date currentdate;                   //获取当前本机时间的Date类
	public String currentdate_str;
	public SimpleDateFormat formater;          //时间格式器
	private MenuItem item1;
	//public int deadline_list_position;
	private Handler handler=new Handler(){     //利用handler类在子线程中更新UI

		public void handleMessage(Message msg){
			switch (msg.what){
			case UPDATE_VIEW:
				
				cposotion=getSharedPreferences("position",0);
				int c_position=cposotion.getInt("listpositioin", 0);  //得到position文件中listposition的值得到当前列表的个数
				
				//sendBroadcast(new Intent("com.tab2_timer_home.deadline.MY_BROADCAST"));
				
				SharedPreferences pref=getSharedPreferences("DeadlineData"+c_position,0);//得到listposition位置的DeadlineData文件
				SharedPreferences pref2;

				//下面是将listposition位置对应的DeadlineData文件中内容添加到listview的数据中去
				MainActivity.deadline_listdata.add(new DeadlineListData(
				pref.getString("thingname", "你好"),
				pref.getInt("year", 0)+"-"+pref.getInt("month", 0)+"-"+pref.getInt("day", 0),
				pref.getInt("betweenday", 0)+"天"));
				
				//下面是将listposition位置对应的DeadlineData文件中内容存储在数组中，为下面的listview排序做准备
				String insertstring=new String();
				insertstring=pref.getString("thingname", "你好");//存储存储名称
				int[] insertint=new int[4];
				insertint[0]=pref.getInt("year", 0);          //存储年份
				insertint[1]=pref.getInt("month", 0);         //存储月份
				insertint[2]=pref.getInt("day", 0);           //存储日期
				insertint[3]=pref.getInt("betweenday", 0);    //存储倒数天数

				//下面通过一个for循环判断出新生成的listdata插入在listview的哪个位置（倒数天数递增排序）
				int i=c_position-1;
				for(;i>=0;i--){
					pref2=getSharedPreferences("DeadlineData"+i,0);
					if(pref2.getInt("betweenday", 0)<=pref.getInt("betweenday", 0)){
						break;
					}					
				}
				
				//得到插入位置i之后，通过一个for循环对listview进行排序，并且将对应位置的DeadlineData文件进行更改
				for(int j=c_position;j>(i+1);j--){
					SharedPreferences.Editor data1=getSharedPreferences("DeadlineData"+j,0).edit();//得到DeadlineData j文件的编辑器
					SharedPreferences data2=getSharedPreferences("DeadlineData"+(j-1),0);          //得到DeadlineData j-1文件
					
					//首先更新listdata中j位置的值，将j-1位置的值给到j位置上
					MainActivity.deadline_listdata.get(j).setDeadline_day(data2.getInt("betweenday", 0)+"天");
					MainActivity.deadline_listdata.get(j).setTab_deadline_list_item1(data2.getString("thingname", "0"));
					MainActivity.deadline_listdata.get(j).setTab_deadline_list_item2("目标日："+data2.getInt("year", 0)
							+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
					
					//再更新相应的DeadlineData文件，将j-1位置的DeadlineData文件的内容赋值给j位置的DeadlineData文件
					data1.putString("thingname", data2.getString("thingname", "0")) ;
					   data1.putInt("year", data2.getInt("year", 0));
					   data1.putInt("month", data2.getInt("month", 0));
					   data1.putInt("day", data2.getInt("day", 0));
					   data1.putInt("betweenday", data2.getInt("betweenday", 0));
					   data1.commit();
				}
				//构建在位置i+1上（即新增加的位置）的DeadlineData文件，并且将上述存储着新建立的事件以及日期的数组的值赋值给DeadlineData文件
				editor=getSharedPreferences("DeadlineData"+(i+1), 0).edit();
				editor.putString("thingname",insertstring) ;
				editor.putInt("year", insertint[0]);
				editor.putInt("month", insertint[1]);
				editor.putInt("day", insertint[2]);
				editor.putInt("betweenday", insertint[3]);
				editor.commit();
				
				//更新位置i+1位置上的listdata，传到listview适配器中
				SharedPreferences data2=getSharedPreferences("DeadlineData"+(i+1),0);
				MainActivity.deadline_listdata.get(i+1).setDeadline_day(data2.getInt("betweenday", 0)+"天");
				MainActivity.deadline_listdata.get(i+1).setTab_deadline_list_item1(data2.getString("thingname", "0"));
				MainActivity.deadline_listdata.get(i+1).setTab_deadline_list_item2("目标日："+data2.getInt("year", 0)
						+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
				MainActivity.deadlineadapter.notifyDataSetChanged(); //通知适配器进行listview的更新
				
			    add_thing.setText("");//将界面的内容清空
				add_data.setText("");//将界面的内容清空
				editor_posotion.putInt("listpositioin", c_position+1);//将position文件中的listposition值增加1
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
	 * 下面是对该界面以及相关成员属性和有关设置的初始化*/
	public void init(){
		formater=new SimpleDateFormat("yyyy-MM-dd");                 //设置日期格式
		currentdate=new Date(System.currentTimeMillis());            //获取本地时间
		currentdate_str=formater.format(currentdate);                //将本地时间转换为格式后的字符串
		add_thing=(EditText) findViewById(R.id.deadline_list_add_thing_content);
		add_data=(Button) findViewById(R.id.deadline_list_add_data_content);
		myactionBar=this.getActionBar(); 
		myactionBar.setDisplayShowHomeEnabled(true);            //激活界面的ActioinBar
		myactionBar.setDisplayHomeAsUpEnabled(true);           //激活界面的ActioinBar
		myactionBar.setHomeButtonEnabled(true);                //激活界面的ActioinBar
	 
		//下面是判断是否存在position文件，如果不存在则构建一个position文件（记录listdata的位置个数），初始化值listpositioin为0
		File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs",
				"position.xml");
		if(!file.exists()){
		editor_posotion=this.getSharedPreferences("position", 0).edit();
		editor_posotion.putInt("listpositioin", 0);
		editor_posotion.commit();
		}
	
		
		c=Calendar.getInstance();
		year_current=c.get(Calendar.YEAR);//获取到当前的本机年份
		month_current=c.get(Calendar.MONTH);//获取到当前的月份
		day_current=c.get(Calendar.DAY_OF_MONTH);//获取到当前的日期
		this.setTitle("添加");                  //设置当前界面的标题
		
		//下面是构建提起对话框
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
		//在按钮上设置点击事件监听器，点击就能够弹出日期对话框
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
		getMenuInflater().inflate(R.menu.deadlineadd, menu);//加载AcitonBar上的按钮
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item){
		//为左上角的按钮设置返回功能，返回到主界面
		if(item.getItemId()==android.R.id.home){           
			Intent i=new Intent(this,MainActivity.class);
			  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		
		//为右上角的按钮设置一系列事件处理
		if(item.getItemId()==R.id.action_add_list){
			if(add_thing.getText().toString().length()>1&&year_deadline!=0){  //先判段用户是否输入了事件和截止日期
			    cposotion=getSharedPreferences("position",0);
			    int change_position=cposotion.getInt("listpositioin", 0);     //得到position文件的listposition值
			    
			    editor=this.getSharedPreferences("DeadlineData"+change_position, 0).edit();//构建新的对应于位置为listposition的DeadlineData文件
				editor.putString("thingname", add_thing.getText().toString());          //将事件名称输入到文件中
				editor.putInt("year", year_deadline);                         //将年份信息输入到文件中
				editor.putInt("month", month_deadline);                       //将月份信息输入到文件中
				editor.putInt("day", day_deadline);                           //将日期信息输入到文件中
				
				//下面是如何计算本地时间距离截止日期还有多少天（即倒数天数）
				String destr=year_deadline+"-"+month_deadline+"-"+day_deadline;//得到截止日期的字符串
					Date begindate;
					try {
						begindate = formater.parse(currentdate_str);
						Date enddate=formater.parse(destr);
						int betweendate=(int) ((enddate.getTime()-begindate.getTime())/(1000*60*60*24));//得到倒数天数
						editor.putInt("betweenday", betweendate);         //将倒数天数的信息输入到文件中
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				editor.commit();//提交结果，让文件生效
				editor_posotion=this.getSharedPreferences("position", 0).edit(); //得到position文件的编辑器
			  
				//构建子线程进行UI更新
				new Thread(new Runnable(){
					public void run(){
						Message msg=new Message();
						msg.what=UPDATE_VIEW;
					     handler.sendMessage(msg);
								
					}
				}).start();
				
		    //完成步骤之后跳转到主界面
			Intent i=new Intent(this,MainActivity.class);
			startActivity(i);	
		}
			else{
				Toast.makeText(this, "请输入事件名称和截止日期", Toast.LENGTH_SHORT).show();//如果用户没有输入事件和截止日期则弹出提示
				
		}
		
		}
		return super.onOptionsItemSelected(item);
	}
}
