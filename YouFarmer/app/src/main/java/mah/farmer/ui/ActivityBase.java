package mah.farmer.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;


/** 界面基类，并没有什么用，知识当初全部界面继承了这个类，后期不方便改
 * Created by 黑色野兽迈特祖 on 2016/4/28.
 */
public class ActivityBase extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//自动登陆状态下检测是否在其他设备登陆
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}


	public void hideSoftInputView() {
		InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
}
