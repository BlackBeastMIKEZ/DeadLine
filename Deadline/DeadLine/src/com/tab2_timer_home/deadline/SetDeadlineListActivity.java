package com.tab2_timer_home.deadline;
/*
 * 此界面为单击主界面中tab1列表项弹出的编辑界面*/
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
	protected static final int UPDATE_VIEW = 0;   //子线程更新UI的信号
	private EditText thing_set;   
	private Button date_set;
	private SharedPreferences pref_position;
	private SharedPreferences data;
	private ActionBar myactionBar;
	private int location;                        //要被编辑的listview子项的位置
	private SharedPreferences.Editor editor;
	private Calendar c;                           
    private int year_deadline;
	private int month_deadline;
	private int month_deadline_1;
	private int day_deadline;
	private DatePickerDialog deadline_dialog;      //日期选择对话框
	public Date currentdate;                       //手机本地时间
	public String currentdate_str;
	public SimpleDateFormat formater;              //日期格式器
	private Handler handler=new Handler(){         //handle中更新UI

		public void handleMessage(Message msg){
			switch (msg.what){
			case UPDATE_VIEW:
				SharedPreferences cposotion=getSharedPreferences("position",0);
				int c_position=cposotion.getInt("listpositioin", 0);             //从position文件中得到当前总的列表项数目+1
				
				
				SharedPreferences pref=getSharedPreferences("DeadlineData"+location,0);  //得到点击位置的列表项的文件信息DeadlineData
				SharedPreferences pref2;

				MainActivity.deadline_listdata.get(location).setTab_deadline_list_item1   //重新设置tab1中列表数据listdata的值（事件名称）
				(pref.getString("thingname", "你好"));
				MainActivity.deadline_listdata.get(location).setTab_deadline_list_item2
				("目标日："+data.getInt("year", 0)+"-"+data.getInt("month", 0)+"-"+data.getInt("day", 0));//重新设置tab1中列表数据listdata的值（目标日）
				MainActivity.deadline_listdata.get(location).setDeadline_day
				(pref.getInt("betweenday", 0)+"天");                                //重新设置tab1中列表数据listdata的值（倒数天数）
				
				
				//下面将编辑后的listview子项的数据分别存储到新构建的数组中，为编辑之后的listview重新排序做准备
				String insertstring=pref.getString("thingname", "你好"); //存储事件名称         
				int[] insertint=new int[4];      
				insertint[0]=pref.getInt("year", 0);                   //存储年份
				insertint[1]=pref.getInt("month", 0);                 //存储月份
				insertint[2]=pref.getInt("day", 0);                   //存储日期
				insertint[3]=pref.getInt("betweenday", 0);            //存储倒数天数
	
				//下面是判断出编辑后新的listview子项的倒数天数会处在listview的哪一个位置，得到新位置i
				int i=c_position-1;                           
				for(;i>=0;i--){
					pref2=getSharedPreferences("DeadlineData"+i,0);
					if(pref2.getInt("betweenday", 0)<pref.getInt("betweenday", 0)){
						break;
					}					
				}
				
				//分为两个方向，如果新的位置大于原来的位置则进行下面的代码更新listview和文件
				if(i>location){
					for(int j=location;j<i;j++){
						SharedPreferences.Editor data1=getSharedPreferences("DeadlineData"+j,0).edit();
						SharedPreferences data2=getSharedPreferences("DeadlineData"+(j+1),0);
						//首先将更改的listview子项进行重新设值显示
						MainActivity.deadline_listdata.get(j).setDeadline_day(data2.getInt("betweenday", 0)+"天");
						MainActivity.deadline_listdata.get(j).setTab_deadline_list_item1(data2.getString("thingname", "0"));
						MainActivity.deadline_listdata.get(j).setTab_deadline_list_item2("目标日："+data2.getInt("year", 0)
								+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
						//将位置为j+1的DeadlineData文件的值赋给位置为j的DeadlineData文件
						data1.putString("thingname", data2.getString("thingname", "0")) ;
						   data1.putInt("year", data2.getInt("year", 0));
						   data1.putInt("month", data2.getInt("month", 0));
						   data1.putInt("day", data2.getInt("day", 0));
						   data1.putInt("betweenday", data2.getInt("betweenday", 0));
						   data1.commit();
					}
					//经过上面的赋值后剩下位置i，即编辑过后的listview子项新的位置，调用上面的数组。将数组中存储着编辑过后的listview子项的值赋给位置为i的DeadlineData文件
					editor=getSharedPreferences("DeadlineData"+i, 0).edit();
					editor.putString("thingname",insertstring) ;
					editor.putInt("year", insertint[0]);
					editor.putInt("month", insertint[1]);
					editor.putInt("day", insertint[2]);
					editor.putInt("betweenday", insertint[3]);
					editor.commit();
					//下面对listdata进行重新赋值，更改位置为i的数据值
					SharedPreferences data2=getSharedPreferences("DeadlineData"+i,0);
					MainActivity.deadline_listdata.get(i).setDeadline_day(data2.getInt("betweenday", 0)+"天");
					MainActivity.deadline_listdata.get(i).setTab_deadline_list_item1(data2.getString("thingname", "0"));
					MainActivity.deadline_listdata.get(i).setTab_deadline_list_item2("目标日："+data2.getInt("year", 0)
							+"-"+data2.getInt("month", 0)+"-"+data2.getInt("day", 0));
					
					MainActivity.deadlineadapter.notifyDataSetChanged(); //通知适配器更新listview
			 }
				
			//如果新的位置小于原来的位置则进行下面的代码更新listview和文件，下面代码的注释和思想与上一段代码差不多，不在重复注释
				else{
		
					for(int j=location;j>(i+1);j--){
						SharedPreferences.Editor data1=getSharedPreferences("DeadlineData"+j,0).edit();
						SharedPreferences data2=getSharedPreferences("DeadlineData"+(j-1),0);
						
						MainActivity.deadline_listdata.get(j).setDeadline_day(data2.getInt("betweenday", 0)+"天");
						MainActivity.deadline_listdata.get(j).setTab_deadline_list_item1(data2.getString("thingname", "0"));
						MainActivity.deadline_listdata.get(j).setTab_deadline_list_item2("目标日："+data2.getInt("year", 0)
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
					MainActivity.deadline_listdata.get(i+1).setDeadline_day(data2.getInt("betweenday", 0)+"天");
					MainActivity.deadline_listdata.get(i+1).setTab_deadline_list_item1(data2.getString("thingname", "0"));
					MainActivity.deadline_listdata.get(i+1).setTab_deadline_list_item2("目标日："+data2.getInt("year", 0)
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
	 * 下面的方法是对界面和成员属性的初始化*/
	public void init(){
		thing_set=(EditText) findViewById(R.id.deadline_listsetting_thing_content);
		date_set=(Button) findViewById(R.id.deadline_listsetting_data_content);
		Bundle bundle = getIntent().getExtras();       //获取到Intent携带的要编辑子项的位置信息
        location = bundle.getInt("location");
        this.setTitle("编辑");                          //设置界面标题
        data=getSharedPreferences("DeadlineData"+location, 0);  //得到该位置的DeadlineData文件
        if(!TextUtils.isEmpty(data.getString("thingname", "DeadLine"))){
        	 thing_set.setText(data.getString("thingname", "DeadLine")); //将文件的信息（事务名称）显示在编辑界面上
        	 thing_set.setSelection(data.getString("thingname", "DeadLine").length());//将光标移动到末尾
		}
         
        date_set.setText(data.getInt("year", 0)+"-"+data.getInt("month", 0)+"-"+data.getInt("day", 0));  //将文件的信息（日期）显示在编辑界面上
        myactionBar=this.getActionBar();                //激活AcitonBar
		myactionBar.setDisplayShowHomeEnabled(true);
		myactionBar.setDisplayHomeAsUpEnabled(true);
		myactionBar.setHomeButtonEnabled(true);
		
		formater=new SimpleDateFormat("yyyy-MM-dd");     //对日期格式进行统一设置，方便下面的日期加减计算
		currentdate=new Date(System.currentTimeMillis()); //获取到当前的本地时间
		currentdate_str=formater.format(currentdate);     //将当前的本地时间进行格式设置
		
	
		//下面的代码为构造日期对话框，让用户选择日期
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
	 * 下面设置ActionBar上按钮的事件处理*/
	public boolean onOptionsItemSelected(MenuItem item){
		//如果点击左上角按钮的话则返回主界面
		if(item.getItemId()==android.R.id.home){         
			Intent i=new Intent(this,MainActivity.class);
			  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		//如果点击右上角的完成编辑按钮，则进行如下步骤
		if(item.getItemId()==R.id.action_add_set){
			//将新设置好的事件和日期重新写入到对应的DeadlineData文件中
			editor=this.getSharedPreferences("DeadlineData"+location, 0).edit();
			editor.putString("thingname", thing_set.getText().toString());
			editor.putInt("year", year_deadline);
			editor.putInt("month", month_deadline);
			editor.putInt("day", day_deadline);
			String destr=year_deadline+"-"+month_deadline+"-"+day_deadline;
			
			//重新计算当今时间与目标日的相隔天数，并且将信息存入到该listview子项对应的DeadlineData文件中去
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
			
			//构建子线程，在子线程中更新UI
			new Thread(new Runnable(){
				public void run(){
					Message msg=new Message();
					msg.what=UPDATE_VIEW;
				     handler.sendMessage(msg);
							
				}
			}).start();
			
			//返回到主界面
			Intent i=new Intent(this,MainActivity.class);
			startActivity(i);
			
		}
		return super.onOptionsItemSelected(item);
    }
    


}
