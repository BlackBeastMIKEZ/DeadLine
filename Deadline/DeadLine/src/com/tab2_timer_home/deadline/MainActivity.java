/*
 * �ǻ��������*/
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

	protected DrawerLayout mDrawerLayout;    //���벼��
	private ListView mDrawerList;            //�����б�
	private ListView deadline_list;          //tabhost�е�һ��tab���ݣ������գ����б�
	private ArrayList<String> menuLists;     //�����б����������
	public static ArrayAdapter<String> adapter;//�����б��listview������
	public static DeadlineAdapter  deadlineadapter;//tabhost�е�һ��tab���ݣ������գ����б�listview��������
	public  static ArrayList<DeadlineListData>   deadline_listdata;//tabhost�е�һ��tab���ݣ������գ����б�����
	private ActionBarDrawerToggle mDrawerToggle;          //���벼�ֵ��¼�������
	private String mTitle;                               //������ActionBar�ı���
	private TabHost tabhost;
	public SharedPreferences data;                        
	public SharedPreferences pref_position;
	private EditText sticky_notes;
    private Button btn;
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("�������");
		initDrawer();
		initDeadline_list();

	}
	
    /*
     * ��ʼ�����벼�ֺ�������*/
	public void initDrawer(){                                         
		
		mTitle = (String) getTitle();
		
		sticky_notes=(EditText) findViewById(R.id.tab_sticky_notes);//�Ա�ǩ���ɵ��ļ���������ȡ���������е�tab3��
		String inputtext=loadStickyNotes();
		if(!TextUtils.isEmpty(inputtext)){
			sticky_notes.setText(inputtext);
			sticky_notes.setSelection(inputtext.length());
		}
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);//�󶨲���
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		menuLists = new ArrayList<String>();                  //�����ʼ��
		menuLists.add("����");
		menuLists.add("�������");
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menuLists);//�����б�������������б��
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(this);                                               //����¼������б�ĵ��������
    
		//����Գ��벼�ֺ���������л����ö���Ч�������ü�������
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer, R.string.drawer_open,R.string.drawer_close) {
			@Override
			//������򿪵�ʱ��ActionBar�ı�����Ӧ�ķ����ı�
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle("MaH"); //���ó��뱻�򿪺�ı���
				invalidateOptionsMenu(); // �������� onPrepareOptionsMenu()
			}

			@Override
			//������رյ�ʱ�򡣻�ԭ��ԭ��������ı���
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();//�������� onPrepareOptionsMenu()
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);// �����벼�ֺ��¼����������а�
		
		
		getActionBar().setDisplayHomeAsUpEnabled(true);//����ActionBar��APP ICON�Ĺ���
		getActionBar().setHomeButtonEnabled(true);
				
		tabhost = (TabHost) findViewById(android.R.id.tabhost);//�����Ƕ�������TabHost���ݵĳ�ʼ��
		tabhost.setup();
		tabhost.addTab(tabhost.newTabSpec("tab1").setIndicator("����").setContent(R.id.tab_deadline));
		tabhost.addTab(tabhost.newTabSpec("tab2").setIndicator("��ʱ��").setContent(R.id.tabTimer));
		tabhost.addTab(tabhost.newTabSpec("tab3").setIndicator("��ǩ").setContent(R.id.tab_sticky_notes_layout));
	}
	
	/*
	 * ��ʼ��������tab1��������������*/
	public void initDeadline_list(){
		deadline_list=(ListView) findViewById(R.id.tab_deadline_list);//��tab1��listview�����������а�
		deadline_listdata=new ArrayList<DeadlineListData>();
		deadlineadapter=new DeadlineAdapter(this,deadline_listdata);
		
		
		File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs",
				"DeadlineData0.xml");                       //���ж��Ƿ����DeadlineDta�ļ������ļ��洢��listview�б����ݵ����ݣ�
		if(file.exists()){         
			pref_position=getSharedPreferences("position",0); //����������positioning�ļ������ݽ�����ȡ�����ļ�������listview�б�������ĸ�����
			
			for(int i=0;i<pref_position.getInt("listpositioin", 0);i++){//forѭ���õ�����DeadlineData���ݽ�����ʾ��tab1��
				   data=getSharedPreferences("DeadlineData"+i,0);
				   deadline_listdata.add(new DeadlineListData(
						   data.getString("thingname", "���"),
						   "Ŀ���գ�"+data.getInt("year", 0)+"-"+data.getInt("month", 0)+"-"+data.getInt("day", 0),
						   data.getInt("betweenday", 0)+"��"));
				       }
		}
		
		
		deadline_list.setAdapter(deadlineadapter);                 //��tab1��listview��������
		deadline_list.setOnItemLongClickListener(this);            //��tab1��listview���ó����¼�
		deadline_list.setOnItemClickListener(new OnItemClickListener(){ //��tab1��listview���õ���¼�
			@Override
			public void onItemClick(AdapterView<?> parent, View view,    //���֮���ܹ���ת���༭����
					int position, long id) {
				// TODO Auto-generated method stub
				Intent i=new Intent(MainActivity.this,SetDeadlineListActivity.class);
				Bundle mBundle = new Bundle();                         //����Bundle������ת��ʱ��Я����list�����λ����Ϣ
                mBundle.putInt("location",position );//ѹ������    
                i.putExtras(mBundle);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				startActivity(i);
			}		
		 });
	   }
	
	/*
	 * �˷������ڱ���tab3���������*/
    public void saveStickyNotes(String inputtext){
    	FileOutputStream out=null;
    	BufferedWriter writer=null;
    	try{
    		out=openFileOutput("DailyLife",Context.MODE_PRIVATE);//����DailyLife�ļ�����tab3����ǩ���ڵ�����
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
     * �˷���������ȡDailyLife�ļ���������ʾ�ڽ�����*/
    public String loadStickyNotes(){
    	FileInputStream in=null;
    	BufferedReader reader=null;
    	StringBuilder content=new StringBuilder();
    	try{
    		in =openFileInput("DailyLife");                //��Ҫ������IO���ķ������ж�ȡ
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
     * ��д�����onPrepareOptionsMenu������������������ActionBar��menu�������Ч��*/
    @Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		boolean isDrawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_websearch).setVisible(!isDrawerOpen);
		menu.findItem(R.id.action_add2).setVisible(!isDrawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);//����menu���֣�����ӵ��µ�menu������ActionBar��
		return true;
	}

	@Override
	/*
	 * �����ǹ���ActionBar��menu�ĵ���¼�*/
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (mDrawerToggle.onOptionsItemSelected(item)){      //��ActionBar�ϵ�ͼ����Drawer�������
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_websearch:                          //��������������ť���������������ٶ���ҳ
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			Uri uri = Uri.parse("http://www.baidu.com");
			intent.setData(uri);
			startActivity(intent);
			break;
		case R.id.action_add2:                                    //������+��ť��������µĽ��棨��������
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
		//��Ҫ��ActionDrawerToggle��DrawerLayout��״̬ͬ��
		//��ActionBarDrawerToggle�е�drawerͼ�꣬����ΪActionBar�е�Home-Button��Icon
		mDrawerToggle.syncState();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	 public void onDestroy(){
		 super.onDestroy();
		 String dailylife=sticky_notes.getText().toString();//��Ӧ�ó����˳���ʱ�����saveStickyNotes��������tab3��ǩ������
		 saveStickyNotes(dailylife);
	 }

	 
	 /*
	  * ����ķ����ǳ����listview�ĵ���¼�*/
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
		
		if(position==0){                                                      //��������һ����������ת���û���������
			Intent i=new Intent(MainActivity.this,CustomerfeedbackActivity.class);
			startActivity(i);
		}
		
		if(position==1){                                                     //�����ҵڶ��������������ת�����ܽ���
			Intent i=new Intent(MainActivity.this,TeamWorkActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			startActivity(i);
		}
		mDrawerLayout.closeDrawer(mDrawerList);                              //�����رճ���
	}
	
	/*
	 * ����ķ�����tab1�����񣩵�listview�б����ɾ���¼���ʵ��*/
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			final int position, long id) {
		//�����󵯳��Ի���
		new AlertDialog.Builder(MainActivity.this)
		.setTitle("ɾ��")
		.setMessage("ȷ��Ҫɾ�����¼���")
		.setPositiveButton("��", new DialogInterface.OnClickListener(){  //�ڡ��ǡ���ѡ���й����¼�������

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				deadline_listdata.remove(position);                     //���б��������Ƴ�
				pref_position=getSharedPreferences("position",0);
				int list_position=pref_position.getInt("listpositioin", 0)-1;//�õ�position�ļ��е�listpositionֵ�������б�����Ŀ+1��

				for(int j=position;j<list_position;j++){                      //��DeadlineData�ļ���������ֵ 
					SharedPreferences.Editor data1=getSharedPreferences("DeadlineData"+j,0).edit();
					SharedPreferences data2=getSharedPreferences("DeadlineData"+(j+1),0);
					data1.putString("thingname", data2.getString("thingname", "0")) ;    //�¼�����
					   data1.putInt("year", data2.getInt("year", 0));                   //���
					   data1.putInt("month", data2.getInt("month", 0));                 //�·�
					   data1.putInt("day", data2.getInt("day", 0));                     //����
					   data1.putInt("betweenday", data2.getInt("betweenday", 0));       //����Ŀ���յ�����
					   data1.commit();
				}
				
				File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs",
						"DeadlineData"+list_position+".xml");                               //ɾ���ļ��������һ���б�����ļ�ɾ��
				if(file.exists()){
				file.delete(); 
				Toast.makeText(MainActivity.this, "ɾ���ɹ�", Toast.LENGTH_LONG).show();
		        }
				
				
				
				deadlineadapter.notifyDataSetChanged();                          //֪ͨ���������ݷ����ı�
				SharedPreferences.Editor hhhhhh=getSharedPreferences("position",0).edit();//��position�ļ��е�listpositionֵ�޸ģ���һ��ɾ������ɹ�
				hhhhhh.putInt("listpositioin", list_position); 
				hhhhhh.commit();

	
}
})
.setNegativeButton("��", null).show();//�ԡ��񡱲����¼��������Ĵ���
		return true;
}
	

}


