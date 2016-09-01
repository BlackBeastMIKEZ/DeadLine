package com.tab2_timer_home.deadline;

/*
 *此界面是点击在抽屉中“关于吗哈”选项时跳转到的界面，该界面只是简单的显示文字*/
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
		myactionBar.setDisplayShowHomeEnabled(true);//激活ActionBar
		myactionBar.setDisplayHomeAsUpEnabled(true);//激活ActionBar
		myactionBar.setHomeButtonEnabled(true);     // 激活ActionBar
		//这是要显示在界面上的文字
		String s1="吗哈(MaH)倒数";
		String s2="宗旨：提供一个新的时间管理模式让用户去更方便有效的安排自己的时间";
		String s3="MaH团队成员：朱大傻，刘大傻";
		String s4="联系方式：18826101638(Tel/WeChat)";
		
		//以下代码对字体进行设置
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
	public boolean onOptionsItemSelected(MenuItem item){//为左上角按钮添加事件处理，点击返回到主界面
		if(item.getItemId()==android.R.id.home){ 
			Intent i=new Intent(this,MainActivity.class);
			  i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
		}
		return super.onOptionsItemSelected(item);
	}
}
