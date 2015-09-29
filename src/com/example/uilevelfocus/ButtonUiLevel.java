package com.example.uilevelfocus;

import android.util.Log;

import com.example.uilevelfocus.Interface.UILevel;
/**
 * 存放着按钮的UILevel
 * <br>Copyright (c) 2015 双华科技
 * @author ZZQ
 * @version 2015-9-29 上午10:02:14
 */
public class ButtonUiLevel extends UILevel {

	private static final String TAG = "Button";

	@Override
	public void onFocus() {
		// TODO Auto-generated method stub
		Log.v (TAG, "onFocus:("+this.getILevel()+","+this.getName()+")");
	}

	@Override
	public void onFocusCancle() {
		// TODO Auto-generated method stub
		Log.v (TAG, "onFocusCancle:("+this.getILevel()+","+this.getName()+")");
	}

}
