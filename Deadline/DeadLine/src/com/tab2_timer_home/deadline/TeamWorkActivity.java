package com.tab2_timer_home.deadline;

/*
 *�˽����ǵ���ڳ����С����������ѡ��ʱ��ת���Ľ��棬�ý���ֻ�Ǽ򵥵���ʾ����*/
import com.jikexueyuan.drawerlayoutusing.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.MenuItem;
import android.widget.TextView;

public class TeamWorkActivity extends Activity {
	private TextView text;
	private ActionBar myactionBar;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.teamwork_layout);
		this.setTitle("MaH"); 
		myactionBar=getActionBar();
		myactionBar.setDisplayShowHomeEnabled(true);//����ActionBar
		myactionBar.setDisplayHomeAsUpEnabled(true);//����ActionBar
		myactionBar.setHomeButtonEnabled(true);     // ����ActionBar
		//����Ҫ��ʾ�ڽ����ϵ�����
		String s1="���(MaH)����";
		String s2="��ּ���ṩһ���µ�ʱ�����ģʽ���û�ȥ��������Ч�İ����Լ���ʱ��";
		String s3="MaH�Ŷӳ�Ա�����ɵ������ɵ";
		String s4="��ϵ��ʽ��18826101638(Tel/WeChat)";
		
		//���´���������������
		SpannableStringBuilder w1=new SpannableStringBuilder();
		int start=0;
	    int end=s1.length();
	    w1.append(s1+'\n'+'\n');
		w1.setSpan(new AbsoluteSizeSpan(80), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		start=end;
		end+=s2.length();
		w1.append(s2+'\n'+'\n');
	    w1.setSpan(new AbsoluteSizeSpan(50), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		start=end;
		end+=s3.length();
		w1.append(s3+'\n'+'\n');
		w1.setSpan(new AbsoluteSizeSpan(50), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		start=end;
		end+=s4.length();
		w1.append(s4+'\n'+'\n');
		w1.setSpan(new AbsoluteSizeSpan(50), start, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

		text=(TextView) findViewById(R.id.teamwork);
		text.setText( w1);
	}
	public boolean onOptionsItemSelected(MenuItem item){//Ϊ���Ͻǰ�ť����¼�����������ص�������
		if(item.getItemId()==android.R.id.home){ 
			Intent i=new Intent(this,MainActivity.class);
			  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
