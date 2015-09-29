package com.example.uilevelfocus;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.uilevelfocus.Interface.UILevel;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	
	UILevel headerFragment, fragment1, fragment2;
	
	UILevelHelper uiHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		uiHelper = UILevelHelper.getInstance();
		
		// 初始化Fragment
		initFragment ();

		// 初始化每个Fragment的分支
		initItem (headerFragment, 2);
		initItem (fragment1, 1);
		initItem (fragment2, 3);
		
		uiHelper.setUiLevel(headerFragment);
		uiHelper.init();
		uiHelper.onNext();
		uiHelper.onOk();
		uiHelper.onReturn();
		uiHelper.onPre();
		uiHelper.onPre();
		uiHelper.onSelect("fragment2");
		uiHelper.onOk();
		uiHelper.onPre();
		uiHelper.onPre();
		
		UILevel uiLevel_tmp = headerFragment.getUILevelWithName("fragment1_button1");
		Log.v (TAG, uiLevel_tmp.getName()+"(getWithName):"+uiLevel_tmp.getBrotherNum());
	}
	
	public void initItem (UILevel fragment, int num) {

		UILevel headerButton = null;
		for (int i=0;i<num;i++) {
			
			headerButton = new ButtonUiLevel();
			headerButton.setName(fragment.getName()+"_button"+(i+1));
			
			if (i == 0) {
				headerButton.setIFHeader(ButtonUiLevel.HEADER);
				headerButton.setILevel(0);		// 0表示根级别
			}
			fragment.addChildUILevel(headerButton);
		}
		visit (headerButton);
	}
	
	/**
	 * 初始化
	 */
	public void initFragment () {
		headerFragment = new FragmentUiLevel();
		headerFragment.setIFHeader(FragmentUiLevel.HEADER);
		headerFragment.setName("headerFragment");
		headerFragment.setILevel(0);		// 0表示根级别
		
		fragment1 = new FragmentUiLevel();
		fragment1.setIFHeader(FragmentUiLevel.NOHEADER);
		fragment1.setName("fragment1");
		fragment1.setILevel(1);		// 0表示根级别
		
		fragment2 = new FragmentUiLevel();
		fragment2.setIFHeader(FragmentUiLevel.NOHEADER);
		fragment2.setName("fragment2");
		
		headerFragment.addBrother(fragment1);
		headerFragment.addBrother(fragment2);
		
		visit (headerFragment);
	}
	
	public void logName (UILevel uiLevel) {
		Log.v (TAG, uiLevel.getName());
	}
	
	public void visit (UILevel uiLevel) {
		UILevel headerUiLevel = uiLevel.getHeader();
		
		/** 从Header节点开始遍历所有节点 */
		UILevel uiLevel_tmp = headerUiLevel;
		
		for (;uiLevel_tmp.getNextUiLevel().getIFHeader() != UILevel.HEADER;uiLevel_tmp = uiLevel_tmp.getNextUiLevel()) {
			Log.v (TAG, uiLevel_tmp.getName()+":"+uiLevel_tmp.getILevel());
		}
		Log.v (TAG, uiLevel_tmp.getName()+":"+uiLevel_tmp.getILevel());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
