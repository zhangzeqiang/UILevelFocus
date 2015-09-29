package com.example.uilevelfocus.Interface;

import android.util.Log;

/**
 * 可分级焦点的UI抽象类
 * Copyright (c) 2015 双华科技
 * @author ZZQ
 * @version 2015-9-29 上午9:47:16
 */
public abstract class UILevel {
	
	private static final String TAG = "UILevel";
	
	/** 焦点UI实体 */
	Object uiObject;
	
	/** 下一级UI */
	UILevel childUiLevel = null;
	
	/** 上一级UI */
	UILevel parentUiLevel = null;
	
	/** 同级UI链表 */
	UILevel nextUiLevel;
	UILevel preUiLevel;
	
	/** 信息块 */
	int iLevel;		// 当前所处级数
	boolean ifHeader;	// 标记是否为本级队列中的头部
	String name;	// 此UILevel的名字(唯一标识符)
	
	public static final int IEOF = -1;
	public static final boolean HEADER = true;
	public static final boolean NOHEADER = false;
	
	public UILevel () {
		this.iLevel = IEOF;
		this.ifHeader = NOHEADER;
		this.nextUiLevel = this;
		this.preUiLevel = this;
	}
	
	public UILevel (int iLevel) {
		this.iLevel = iLevel;
		this.ifHeader = NOHEADER;
		this.nextUiLevel = this;
		this.preUiLevel = this;
	}
	
	/**************************************
	 *              静态函数
	 *************************************/
	/**
	 * 清除UILevel的内存
	 * @param uiLevel
	 */
	public static void clearUILevel (UILevel uiLevel) {
		uiLevel.nextUiLevel = null;
		uiLevel.preUiLevel = null;
		uiLevel.iLevel = IEOF;
		uiLevel = null;
	}
	
	/**
	 * 重新设置UILevel的级别
	 * @param uiLevel
	 */
	public static void resetILevel (UILevel uiLevel, int iLevel) {
		if (uiLevel == null) {
			return ;
		}
		UILevel uiLevel_header = uiLevel.getHeader();
		UILevel uiLevel_tmp = uiLevel_header;
		
		if (uiLevel_header == null) {	return ;	}
		for (uiLevel_tmp = uiLevel_header;uiLevel_tmp.getNextUiLevel() != uiLevel_header;uiLevel_tmp = uiLevel_tmp.getNextUiLevel()) {
			uiLevel_tmp.setILevel(iLevel);
			resetILevel(uiLevel_tmp.getChildUiLevel(), iLevel+1);
		}
		uiLevel_tmp.setILevel(iLevel);
		resetILevel(uiLevel_tmp.getChildUiLevel(), iLevel+1);
	}
	
	/**
	 * 获取Ui实体
	 * @return
	 */
	public Object getUiObject () 		{	return this.uiObject;	}
	public UILevel getChildUiLevel () 	{	return this.childUiLevel;	}
	public UILevel getParentUiLevel () 	{	return this.parentUiLevel;	}
	public UILevel getNextUiLevel ()	{	return this.nextUiLevel;	}
	public UILevel getPreUiLevel () 	{	return this.preUiLevel;	}
	
	/**
	 * 设置Ui实体
	 * @param uiObj
	 */
	public void setUiObject (Object uiObj) 				{	this.uiObject = uiObj;	}
	
	public void setChildUiLevel (UILevel childUiLevel) 	{
		this.childUiLevel = childUiLevel;
		
		if (childUiLevel == null) {
			return ;
		}
		childUiLevel.parentUiLevel = this;
		/** 下一级别 */
		resetILevel (this.childUiLevel, iLevel+1);
	}
	
	public void setParentUiLevel (UILevel parentUiLevel) {
		this.parentUiLevel = parentUiLevel;
		if (parentUiLevel == null) {
			return ;
		}
		
		parentUiLevel.childUiLevel = this;
		/** 上一级别 */
		resetILevel (this.parentUiLevel, iLevel-1);
	}
	
	public void setNextUiLevel (UILevel nextUiLevel)	{	
		this.nextUiLevel = nextUiLevel;
		if (nextUiLevel == null) {	return ; }
		
		nextUiLevel.preUiLevel = this;
		if (nextUiLevel.getIFHeader() == HEADER) {
			/** 若nextUiLevel为头部和列表头部对接 */
			return ;
		}
		/** 同级别下个节点 */
		resetILevel (this.nextUiLevel, iLevel);
	}
	
	public void setPreUiLevel (UILevel preUiLevel) 		{	
		this.preUiLevel = preUiLevel;
		if (preUiLevel == null) {	return ; }
		
		preUiLevel.nextUiLevel = this;
		if (preUiLevel.getIFHeader() == HEADER) {
			/** 若nextUiLevel为头部和列表头部对接 */
			return ;
		}
		/** 同级别上个节点 */
		resetILevel (this.preUiLevel, iLevel);
	}
	
	/**
	 * 如果孩子节点不存在则直接设置,孩子节点已经存在则直接往孩子节点链表添加
	 */
	public void addChildUILevel (UILevel uiLevel) {
		if (this.childUiLevel == null) {
			this.setChildUiLevel(uiLevel);
		} else {
			this.getChildUiLevel().addBrother(uiLevel);
		}
	}
	
	/**
	 * 在A和A.preUiLevel之间插入新的UILevel(其中A为本级链表的Header节点)
	 * @param uiLevel 需要加入的同级节点
	 */
	public void addBrother (UILevel uiLevel) {
		
		/** 获取头部 */
		UILevel uiLevel_tmp = this.getHeader();
		
		uiLevel.setILevel(iLevel);		// 同一级别的节点
		UILevel p = uiLevel_tmp.preUiLevel;
		uiLevel_tmp.setPreUiLevel(uiLevel);
		uiLevel.setNextUiLevel(uiLevel_tmp);
		p.setNextUiLevel(uiLevel);
		uiLevel.setPreUiLevel(p);
	}
	
	/**
	 * 从链表中移除uiLevel
	 * 若uiLevel恰好为Header节点则将uiLevel.nextUiLevel设为Header节点
	 * @param uiLevel
	 */
	public void removeBrother (UILevel uiLevel) {
		
		/** 获取头部 */
		UILevel uiLevel_header = this.getHeader();
		UILevel uiLevel_tmp = uiLevel_header;
		
		if (uiLevel_tmp == uiLevel) {
			/** 要移除的节点恰好为Header节点 */
			UILevel p = uiLevel.getNextUiLevel();
			uiLevel_tmp = uiLevel.getPreUiLevel();
			p.setPreUiLevel(uiLevel_tmp);
			uiLevel_tmp.setNextUiLevel(p);
			p.setIFHeader(HEADER);	// 重新定位Header
			clearUILevel(uiLevel);		// 释放资源
		} else {
			while (uiLevel_tmp.getNextUiLevel() != uiLevel_header) {
				uiLevel_tmp = uiLevel_tmp.getNextUiLevel();
				if (uiLevel_tmp == uiLevel) {
					UILevel p = uiLevel.getNextUiLevel();
					uiLevel_tmp = uiLevel.getPreUiLevel();
					p.setPreUiLevel(uiLevel_tmp);
					uiLevel_tmp.setNextUiLevel(p);
					clearUILevel(uiLevel);
					break;
				}
			}
		}
	}
	
	/**
	 * 从Header节点开始,遍历本级别的所有UILevel节点
	 */
	public UILevel getHeader () {
		
		UILevel uiLevel_start = this;
		if (uiLevel_start.getIFHeader() == HEADER) {
			return uiLevel_start;
		}
		
		UILevel uiLevel_tmp = uiLevel_start.getNextUiLevel();
		UILevel uiLevel_header = null;
		
		/** 搜索Header节点 */
		while (uiLevel_tmp != uiLevel_start) {
			if (uiLevel_tmp.getIFHeader() == HEADER) {
				uiLevel_header = uiLevel_tmp;
				break;
			}
			uiLevel_tmp = uiLevel_tmp.getNextUiLevel();
		}
		return uiLevel_header;
	}
	
	/**
	 * 获取指定位置的UILevel
	 * @param pos 同一级别UILevel的位置,0表示Header
	 * @return 获取的UILevel
	 */
	public UILevel getUILevelInPos (int pos) {
		UILevel uiLevel_header = this.getHeader();
		UILevel uiLevel_tmp = uiLevel_header;

		if (pos == 0) {
			return uiLevel_tmp;
		}
		for (int index=0;uiLevel_tmp.getNextUiLevel() != uiLevel_header && index<pos;uiLevel_tmp = uiLevel_tmp.getNextUiLevel(),index++) {
		}
		return uiLevel_tmp;
	}
	
	/**
	 * 获取指定名字的UILevel
	 * @param name 指定的UILevel名
	 * @return 得到的UILevel
	 */
	private UILevel getUILevelWithName (UILevel uiLevel, String name) {
		if (uiLevel == null) {	return null;	}
		UILevel uiLevel_header = uiLevel.getHeader();
		UILevel uiLevel_tmp;
		
		for (uiLevel_tmp=uiLevel_header;uiLevel_tmp.getNextUiLevel() != uiLevel_header;uiLevel_tmp = uiLevel_tmp.getNextUiLevel()) {
			Log.v (TAG, "getUILevelWithName search:"+uiLevel_tmp.getName());
			
			if (uiLevel_tmp != null && uiLevel_tmp.getName().equals(name)) {
				/** 若节点已经满足 */
				return uiLevel_tmp;
			} else {
				/** 查看直系节点是否满足 */
				UILevel uiLevel_child = getUILevelWithName (uiLevel_tmp.getChildUiLevel(), name);
				if (uiLevel_child != null) {
					return uiLevel_child;
				}
			}
		}
		Log.v (TAG, "getUILevelWithName search:"+uiLevel_tmp.getName());
		
		if (uiLevel_tmp != null && uiLevel_tmp.getName().equals(name)) {
			/** 若节点已经满足 */
			return uiLevel_tmp;
		} else {
			/** 查看直系节点是否满足 */
			UILevel uiLevel_child = getUILevelWithName (uiLevel_tmp.getChildUiLevel(), name);
			if (uiLevel_child != null) {
				return uiLevel_child;
			}
		}
		return null;
	}
	
	/**
	 * 遍历整个家族树获取指定UILevel节点
	 * @param name 指定的UILevel名
	 * @return 得到的UILevel
	 */
	public UILevel getUILevelWithName (String name) {
		UILevel uiLevel_root;
		for (uiLevel_root=this;uiLevel_root.getParentUiLevel() != null;) {
			uiLevel_root = uiLevel_root.getParentUiLevel();
		}
		return getUILevelWithName (uiLevel_root, name);
	}
	
	/**********************************
	 *            配置信息块
	 **********************************/
	public int getILevel () 	{	return this.iLevel;	}
	
	/**
	 * 获取当前级的UILevel总数
	 * @return
	 */
	public int getBrotherNum () 		{	
		UILevel uiLevel_header = this.getHeader();
		UILevel uiLevel_tmp = uiLevel_header;

		int index = 0;
		int num = 0;
		
		if (uiLevel_header == null) {
			return num++;
		}
		for (index=0;uiLevel_tmp.getNextUiLevel() != uiLevel_header;uiLevel_tmp = uiLevel_tmp.getNextUiLevel(),index++) {
		}
		num = index+1;
		return num;
	}
	
	public boolean getIFHeader () 	{	return this.ifHeader;	}
	public String getName () 	{	return this.name;	}
	
	/**
	 * 递归更新
	 * @param iLevel
	 */
	public void setILevel (int iLevel) 	{	
		this.iLevel = iLevel;
	}
	
	public void setIFHeader (boolean ifHeader) 			{	this.ifHeader = ifHeader;	}
	public void setName (String name)	{	this.name = name;	}
	/**********************************
	 *            UILevel动作
	 **********************************/
	/**
	 * 执行获取焦点高亮动作
	 */
	abstract public void onFocus ();
	
	/**
	 * 执行取消焦点高亮动作
	 */
	abstract public void onFocusCancle ();
}
