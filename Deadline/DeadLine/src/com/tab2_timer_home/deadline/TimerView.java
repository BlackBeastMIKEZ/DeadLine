package com.tab2_timer_home.deadline;
/*
 * 这个是tab2计时器界面
 */
import java.util.Timer;
import java.util.TimerTask;

import com.jikexueyuan.drawerlayoutusing.R;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TimerView extends LinearLayout {

	private static final int MSG_WHAT_TIME_IS_UP = 1;
	private static final int MSG_WHAT_TIME_TICK = 2;
	
	private int allTimerCount = 0;
	private Timer timer=new Timer();
	private TimerTask timerTask = null;
	private Button btnStart,btnPause,btnResume,btnReset;
	private EditText etHour,etMin,etSec;
	//handler中对时间到或者没到做一个事件处理
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_WHAT_TIME_TICK:
				
				int hour = allTimerCount/60/60;
				int min = (allTimerCount/60)%60;
				int sec = allTimerCount%60;
				
				etHour.setText(hour+"");
				etMin.setText(min+"");
				etSec.setText(sec+"");
				
				break;
			case MSG_WHAT_TIME_IS_UP:
				new AlertDialog.Builder(getContext()).setTitle("吗哈").setMessage("时间到啦！！").setNegativeButton("确定", null).show();
				
				btnReset.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
				
				break;
			default:
				break;
			}
		};
	};
	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimerView(Context context) {
		super(context);
	}


	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
        //为暂停按钮设置监听器和初始化
		btnPause = (Button) findViewById(R.id.btnPause);
		btnPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				stopTimer();
				
				btnPause.setVisibility(View.GONE);
				btnResume.setVisibility(View.VISIBLE);
			}
		});
		//为重置按钮设置监听器和初始化
		btnReset = (Button) findViewById(R.id.btnReset);
		btnReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				stopTimer();
				
				etHour.setText("0");
				etMin.setText("0");
				etSec.setText("0");
				
				btnReset.setVisibility(View.GONE);
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.GONE);
				btnStart.setVisibility(View.VISIBLE);
			}
		});
		//为继续按钮设置监听器和初始化
		btnResume = (Button) findViewById(R.id.btnResume);
		btnResume.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startTimer();
				
				btnResume.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
			}
		});
		//为开始按钮设置监听器和初始化
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTimer();
				
				btnStart.setVisibility(View.GONE);
				btnPause.setVisibility(View.VISIBLE);
				btnReset.setVisibility(View.VISIBLE);
			}
		});
		
        //初始化三个时间框
		etHour = (EditText) findViewById(R.id.etHour);
		etMin = (EditText) findViewById(R.id.etMin);
		etSec = (EditText) findViewById(R.id.etSec);

		etHour.setText("00");
		//为显示小时的时间框添加文本变换事件监听器
		etHour.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());

					if (value>59) {
						etHour.setText("59");
					}else if (value<0) {
						etHour.setText("0");
					}
				}
				checkToEnableBtnStart();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		etMin.setText("00");
		//为显示分钟的时间框添加文本变换事件监听器
		etMin.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());

					if (value>59) {
						etMin.setText("59");
					}else if (value<0) {
						etMin.setText("0");
					}
				}
				checkToEnableBtnStart();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		etSec.setText("00");
		//为显示秒的时间框添加文本变换事件监听器
		etSec.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (!TextUtils.isEmpty(s)) {
					int value = Integer.parseInt(s.toString());

					if (value>59) {
						etSec.setText("59");
					}else if (value<0) {
						etSec.setText("0");
					}
				}
				checkToEnableBtnStart();
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		btnStart.setVisibility(View.VISIBLE);
		btnStart.setEnabled(false);
		btnPause.setVisibility(View.GONE);
		btnResume.setVisibility(View.GONE);
		btnReset.setVisibility(View.GONE);
	}

	//用来判断开始按钮何时才会使能
	private void checkToEnableBtnStart(){
		btnStart.setEnabled((!TextUtils.isEmpty(etHour.getText())&&Integer.parseInt(etHour.getText().toString())>0)||
				(!TextUtils.isEmpty(etMin.getText())&&Integer.parseInt(etMin.getText().toString())>0)||
				(!TextUtils.isEmpty(etSec.getText())&&Integer.parseInt(etSec.getText().toString())>0));
	}
	
	//下面开启计时功能
	private void startTimer(){
		if (timerTask==null) {
			//得到总时间数
			allTimerCount = Integer.parseInt(etHour.getText().toString())*60*60+Integer.parseInt(etMin.getText().toString())*60+Integer.parseInt(etSec.getText().toString());
			timerTask = new TimerTask() {
				
				@Override
				public void run() {
					allTimerCount--;                    //每进入一次计时器，时间秒数减一
					
					handler.sendEmptyMessage(MSG_WHAT_TIME_TICK);//发送时间还在计时的信号给handler
					
					if (allTimerCount<=0) {
						handler.sendEmptyMessage(MSG_WHAT_TIME_IS_UP);//如果计时结束则发送结束的信号给handler
						stopTimer();
					}
				}
			};
			
			timer.schedule(timerTask, 1000, 1000);
		}
	}
	
	//停止计时器
	private void stopTimer(){
		if (timerTask!=null) {
			timerTask.cancel();
			timerTask = null;
		}
	}



}
