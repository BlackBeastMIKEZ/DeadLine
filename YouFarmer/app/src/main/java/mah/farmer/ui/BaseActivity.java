package mah.farmer.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import mah.farmer.CustomApplcation;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/** 界面基类，得到手机屏幕宽度和高度，实现侧滑退出
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class BaseActivity extends SwipeBackActivity {

	BmobUserManager userManager;
	BmobChatManager manager;
	
	CustomApplcation mApplication;   //手机唯一的application存储

	
	protected int mScreenWidth;//屏幕宽度
	protected int mScreenHeight;//屏幕高度
    protected SwipeBackLayout swipeBackLayout;//控制界面从哪个方向退出的布局
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userManager = BmobUserManager.getInstance(this);
		manager = BmobChatManager.getInstance(this);
		mApplication = CustomApplcation.getInstance();
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;//得到屏幕宽度
		mScreenHeight = metric.heightPixels;//得到屏幕高度
        swipeBackLayout=getSwipeBackLayout();//实例化侧滑布局
        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);//设定界面侧滑退出
	}

	Toast mToast;

    /**
     * 打印Toast
     * @param text
     */
	public void ShowToast(final String text) {
		if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (mToast == null) {
						mToast = Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_LONG);
					} else {
						mToast.setText(text);
					}
					mToast.show();
				}
			});
			
		}
	}

	public void ShowToast(final int resId) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast == null) {
					mToast = Toast.makeText(BaseActivity.this.getApplicationContext(), resId,
							Toast.LENGTH_LONG);
				} else {
					mToast.setText(resId);
				}
				mToast.show();
			}
		});
	}

	/** 打Log
	  * ShowLog
	  * @return void
	  * @throws
	  */

    public void showOfflineDialog(Context context){

    }
	public void ShowLog(String msg){
		Log.i("life",msg);
	}

    /**
     * 界面跳转方法
     * * @param cla
     */
	public void startAnimActivity(Class<?> cla) {
		this.startActivity(new Intent(this, cla));
	}
	
	public void startAnimActivity(Intent intent) {
		this.startActivity(intent);
	}



}

