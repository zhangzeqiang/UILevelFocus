package com.example.uilevelfocus.Interface;

public interface OnDeviceEventListener {
	
	public static interface OnDeviceKeyEventListener {
		/**
		 * 返回信号
		 * @return
		 */
		boolean onReturn ();
		
		/**
		 * 下一步信号
		 * @return
		 */
		boolean onNext ();
		
		/**
		 * 上一步信号
		 * @return
		 */
		boolean onPre ();
		
		/**
		 * 确定按键
		 * @return
		 */
		boolean onOk ();
	}
	
	public static interface OnDeviceTouchEventListener {
		/**
		 * 选择指定的UILevel
		 * @param name 唯一标识
		 * @return
		 */
		boolean onSelect (String name);
	}
}
