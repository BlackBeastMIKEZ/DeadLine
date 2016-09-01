/*
 * 是活动的主界面*/
package com.tab2_timer_home.deadline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import com.customer_feedback.deadline.CustomerfeedbackActivity;
import com.jikexueyuan.drawerlayoutusing.R;
import com.tab1_deadline_list.deadline.AddDeadlineListActivity;
import com.tab1_deadline_list.deadline.DeadlineAdapter;
import com.tab1_deadline_list.deadline.DeadlineListData;

import android.net.Uri;
import android.os.Bundle;
import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener,OnItemLongClickListener{

	protected DrawerLayout mDrawerLayout;    //抽屉布局
	private ListView mDrawerList;            //抽屉列表
	private ListView deadline_list;          //tabhost中第一个tab内容（倒数日）的列表
	private ArrayList<String> menuLists;     //抽屉列表的数据数组
	public static ArrayAdapter<String> adapter;//抽屉列表的listview适配器
	public static DeadlineAdapter  deadlineadapter;//tabhost中第一个tab内容（倒数日）的列表listview的适配器
	public  static ArrayList<DeadlineListData>   deadline_listdata;//tabhost中第一个tab内容（倒数日）的列表数组
	private ActionBarDrawerToggle mDrawerToggle;          //抽屉布局的事件监听器
	private String mTitle;                               //主界面ActionBar的标题
	private TabHost tabhost;
	public SharedPreferences data;                        
	public SharedPreferences pref_position;
	private EditText sticky_notes;
    private Button btn;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("吗哈倒数");
		initDrawer();
		initDeadline_list();

	}
	
    /*
     * 初始化抽屉布局和主界面*/
	public void initDrawer(){                                         
		
		mTitle = (String) getTitle();
		
		sticky_notes=(EditText) findViewById(R.id.tab_sticky_notes);//对便签生成的文件的内容提取（主界面中的tab3）
		String inputtext=loadStickyNotes();
		if(!TextUtils.isEmpty(inputtext)){
			sticky_notes.setText(inputtext);
			sticky_notes.setSelection(inputtext.length());
		}
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//绑定布局
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<String>();                  //抽屉初始化
		menuLists.add("反馈");
		menuLists.add("关于吗哈");
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuLists);//抽屉列表适配器与抽屉列表绑定
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);                                               //添加事件抽屉列表的点击监听器
    
		//下面对抽屉布局和主界面的切换设置动画效果（设置监听器）
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.drawer_open,R.string.drawer_close) {
			@Override
			//当抽屉打开的时候，ActionBar的标题相应的发生改变
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("MaH"); //设置抽屉被打开后的标题
				invalidateOptionsMenu(); // 这里会调用 onPrepareOptionsMenu()
			}

			@Override
			//当抽屉关闭的时候。还原回原来主界面的标题
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();//这里会调用 onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);// 将抽屉布局和事件监听器进行绑定
		
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//开启ActionBar上APP ICON的功能
		getActionBar().setHomeButtonEnabled(true);
				
		tabhost = (TabHost) findViewById(android.R.id.tabhost);//下面是对主界面TabHost内容的初始化
		tabhost.setup();
		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("事务").setContent(R.id.tab_deadline));
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("计时器").setContent(R.id.tabTimer));
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("便签").setContent(R.id.tab_sticky_notes_layout));
	}
	
	/*
	 * 初始化主界面tab1“事务”栏的内容*/
	public void initDeadline_list(){
		deadline_list=(ListView) findViewById(R.id.tab_deadline_list);//对tab1的listview和适配器进行绑定
		deadline_listdata=new ArrayList<DeadlineListData>();
		deadlineadapter=new DeadlineAdapter(this,deadline_listdata);
		
		
		File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs",
				"DeadlineData0.xml");                       //先判断是否存在DeadlineDta文件（此文件存储着listview列表数据的内容）
		if(file.exists()){         
			pref_position=getSharedPreferences("position",0); //如果存在则对positioning文件的内容进行提取（此文件包含着listview列表数据项的个数）
			
			for(int i=0;i<pref_position.getInt("listpositioin", 0);i++){//for循环得到所有DeadlineData数据将其显示在tab1上
				   data=getSharedPreferences("DeadlineData"+i,0);
				   deadline_listdata.add(new DeadlineListData(
						   data.getString("thingname", "你好"),
						   "目标日："+data.getInt("year", 0)+"-"+data.getInt("month", 0)+"-"+data.getInt("day", 0),
						   data.getInt("betweenday", 0)+"天"));
				       }
		}
		
		
		deadline_list.setAdapter(deadlineadapter);                 //对tab1的listview绑定适配器
		deadline_list.setOnItemLongClickListener(this);            //对tab1的listview设置长按事件
		deadline_list.setOnItemClickListener(new OnItemClickListener(){ //对tab1的listview设置点击事件
			@Override
			public void onItemClick(AdapterView<?> parent, View view,    //点击之后能够跳转到编辑界面
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,SetDeadlineListActivity.class);
				Bundle mBundle = new Bundle();                         //设置Bundle让其跳转的时候携带该list子项的位置信息
                mBundle.putInt("location",position );//压入数据    
                i.putExtras(mBundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
			}		
		 });
	   }
	
	/*
	 * 此方法用于保存tab3输入的数据*/
    public void saveStickyNotes(String inputtext){
    	FileOutputStream out=null;
    	BufferedWriter writer=null;
    	try{
    		out=openFileOutput("DailyLife",Context.MODE_PRIVATE);//生成DailyLife文件保存tab3（便签）内的内容
    		writer=new  BufferedWriter(new OutputStreamWriter(out));
    		writer.write(inputtext);
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	finally{
    		try{
    			if(writer!=null){
    				writer.close();
    			}
    		}
    			catch(IOException e){
    				e.printStackTrace();
    			}
    		}
    	}
    
    /*
     * 此方法用于提取DailyLife文件的内容显示在界面上*/
    public String loadStickyNotes(){
    	FileInputStream in=null;
    	BufferedReader reader=null;
    	StringBuilder content=new StringBuilder();
    	try{
    		in =openFileInput("DailyLife");                //主要是运用IO流的方法进行读取
    		reader=new BufferedReader(new InputStreamReader(in));
    		String line="";
    		while((line=reader.readLine())!=null){
    			content.append(line);
    			content.append("\r\n");
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    	finally{
    		try{
    			if(reader!=null){
    				reader.close();
    			}
    		}
    			catch(IOException e){
    				e.printStackTrace();
    			}
    		}
    	return content.toString();
    }

    /*
     * 重写父类的onPrepareOptionsMenu，构建当抽屉拉开是ActionBar上menu项的隐藏效果*/
    @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
		menu.findItem(R.id.action_add2).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);//加载menu布局，将添加的新的menu加载在ActionBar中
		return true;
	}

	@Override
	/*
	 * 下面是构建ActionBar上menu的点击事件*/
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (mDrawerToggle.onOptionsItemSelected(item)){      //将ActionBar上的图标与Drawer结合起来
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_websearch:                          //如果点击到搜索按钮，则开启浏览器进入百度网页
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri uri = Uri.parse("http://www.baidu.com");
			intent.setData(uri);
			startActivity(intent);
			break;
		case R.id.action_add2:                                    //如果点击+按钮，则进入新的界面（新增事务）
			Intent intent1 = new Intent(MainActivity.this,AddDeadlineListActivity.class);
		  intent1.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(intent1);
			break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		//需要将ActionDrawerToggle与DrawerLayout的状态同步
		//将ActionBarDrawerToggle中的drawer图标，设置为ActionBar中的Home-Button的Icon
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	 public void onDestroy(){
		 super.onDestroy();
		 String dailylife=sticky_notes.getText().toString();//在应用程序退出的时候调用saveStickyNotes方法保存tab3便签的内容
		 saveStickyNotes(dailylife);
	 }

	 
	 /*
	  * 下面的方法是抽屉的listview的点击事件*/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
		
		if(position==0){                                                      //如果点击第一项（反馈项）则跳转到用户反馈界面
			Intent i=new Intent(MainActivity.this,CustomerfeedbackActivity.class);
			startActivity(i);
		}
		
		if(position==1){                                                     //如果店家第二项（关于马哈项）则跳转到介绍界面
			Intent i=new Intent(MainActivity.this,TeamWorkActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(i);
		}
		mDrawerLayout.closeDrawer(mDrawerList);                              //点击后关闭抽屉
	}
	
	/*
	 * 下面的方法是tab1（事务）的listview列表项长按删除事件的实现*/
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {
		//长按后弹出对话框
		new AlertDialog.Builder(MainActivity.this)
		.setTitle("删除")
		.setMessage("确定要删除该事件吗？")
		.setPositiveButton("是", new DialogInterface.OnClickListener(){  //在“是”的选项中构造事件监听器

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				deadline_listdata.remove(position);                     //在列表数据中移除
				pref_position=getSharedPreferences("position",0);
				int list_position=pref_position.getInt("listpositioin", 0)-1;//得到position文件中的listposition值（代表列表项数目+1）

				for(int j=position;j<list_position;j++){                      //将DeadlineData文件重新排序赋值 
					SharedPreferences.Editor data1=getSharedPreferences("DeadlineData"+j,0).edit();
					SharedPreferences data2=getSharedPreferences("DeadlineData"+(j+1),0);
					data1.putString("thingname", data2.getString("thingname", "0")) ;    //事件名称
					   data1.putInt("year", data2.getInt("year", 0));                   //年份
					   data1.putInt("month", data2.getInt("month", 0));                 //月份
					   data1.putInt("day", data2.getInt("day", 0));                     //日期
					   data1.putInt("betweenday", data2.getInt("betweenday", 0));       //距离目标日的天数
					   data1.commit();
				}
				
				File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs",
						"DeadlineData"+list_position+".xml");                               //删除文件，将最后一个列表项的文件删除
				if(file.exists()){
				file.delete(); 
				Toast.makeText(MainActivity.this, "删除成功", Toast.LENGTH_LONG).show();
		        }
				
				
				
				deadlineadapter.notifyDataSetChanged();                          //通知适配器数据发生改变
				SharedPreferences.Editor hhhhhh=getSharedPreferences("position",0).edit();//对position文件中的listposition值修改，减一，删除子项成功
				hhhhhh.putInt("listpositioin", list_position); 
				hhhhhh.commit();

	
}
})
.setNegativeButton("否", null).show();//对“否”不做事件监听器的处理
		return true;
}
	

}


