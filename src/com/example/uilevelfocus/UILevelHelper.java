package com.example.uilevelfocus;

import com.example.uilevelfocus.Interface.OnDeviceEventListener;
import com.example.uilevelfocus.Interface.UILevel;

/**
 * UILevel帮助类
 * <br>Copyright (c) 2015 双华科技
 * @author ZZQ
 * @version 2015-9-29 上午9:53:14
 */
public class UILevelHelper implements OnDeviceEventListener.OnDeviceKeyEventListener, OnDeviceEventListener.OnDeviceTouchEventListener {
	
	/** 当前所在焦点项 */
	UILevel uiLevel;
	
	/** 单例句柄 */
	private static UILevelHelper instance;
	
	private UILevelHelper () {
		
	}
	
	/**
	 * 返回单例操作句柄
	 * @return
	 */
	public static UILevelHelper getInstance () {
		if (instance == null) {
			instance = new UILevelHelper ();
		}
		
		return instance;
	}
	
	/**
	 * 设置需要操作的UILevel
	 * @param uiLevel
	 */
	public void setUiLevel (UILevel uiLevel) {
		this.uiLevel = uiLevel;
	}
	
	/** 
	 * 获取当前的UILevel
	 * @return
	 */
	public UILevel getUiLevel () {
		return this.uiLevel;
	}

	/**
	 * 初始化焦点项
	 */
	public void init () {
		if (this.uiLevel == null) {
			return ;
		}
		this.uiLevel.onFocus();
	}
	
	@Override
	public boolean onReturn() {
		// TODO Auto-generated method stub
		/** 获取父节点,若为null则返回false */
		if (this.uiLevel == null || this.uiLevel.getParentUiLevel() == null) {
			return false;
		}
		this.uiLevel.onFocusCancle();
		this.uiLevel = this.uiLevel.getParentUiLevel();
		this.uiLevel.onFocus();
		return true;
	}

	@Override
	public boolean onNext() {
		// TODO Auto-generated method stub
		/** 获取兄弟下一节点 */
		if (this.uiLevel == null || this.uiLevel.getNextUiLevel() == null) {
			return false;
		}
		this.uiLevel.onFocusCancle();
		this.uiLevel = this.uiLevel.getNextUiLevel();
		this.uiLevel.onFocus();
		return true;
	}

	@Override
	public boolean onPre() {
		// TODO Auto-generated method stub
		/** 获取兄弟上一节点 */
		if (this.uiLevel == null || this.uiLevel.getPreUiLevel() == null) {
			return false;
		}
		this.uiLevel.onFocusCancle();
		this.uiLevel = this.uiLevel.getPreUiLevel();
		this.uiLevel.onFocus();
		return true;
	}

	@Override
	public boolean onOk() {
		// TODO Auto-generated method stub
		/** 获取子节点 */
		if (this.uiLevel == null || this.uiLevel.getChildUiLevel() == null) {
			return false;
		}
		this.uiLevel.onFocusCancle();
		this.uiLevel = this.uiLevel.getChildUiLevel();
		this.uiLevel.onFocus();
		return true;
	}

	@Override
	public boolean onSelect(String name) {
		// TODO Auto-generated method stub
		/** 获取指定节点 */
		if (this.uiLevel == null || this.uiLevel.getUILevelWithName(name) == null) {
			return false;
		}
		this.uiLevel.onFocusCancle();
		this.uiLevel = this.uiLevel.getUILevelWithName(name);
		this.uiLevel.onFocus();
		return true;
	}

}









