package com.ustc.prlib.util;

import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;

/**
 * @description : 对点击的按钮等进行颜色过滤,实现点击效果 调用方法:public final static void
 *              setButtonFocusChanged(View inView)
 * @package cn.apppark.mcd.util
 * @title:ButtonColorFilter.java
 * @author : email:xiangyanhui@unitepower.net
 * @date :2012-5-4 下午02:23:23
 * @version : v1.0
 */
public class ButtonColorFilter {

	// 按下这个按钮进行的颜色过滤
	public final static float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 50, 0,
			1, 0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };

	// 按钮恢复原状的颜色过滤
	public final static float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0,
			0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

	private static int initAlpha = 255;
	private static int clickAlpha = 200;

	/**
	 * 按钮焦点改变
	 */
	private final static OnFocusChangeListener buttonOnFocusChangeListener = new OnFocusChangeListener() {

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if (hasFocus) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			} else {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			}
		}
	};

	/**
	 * 按钮触碰按下效果(纯图片)
	 */
	private final static OnTouchListener buttonOnTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			} else {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			}
			return false;
		}
	};

	/**
	 * 按钮触碰按下效果(颜色)
	 */
	private final static OnTouchListener buttonColorOnTouchListener = new OnTouchListener() {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_SELECTED));
				v.getBackground().setAlpha(clickAlpha);
				v.setBackgroundDrawable(v.getBackground());
			} else {
				v.getBackground().setAlpha(initAlpha);
				v.getBackground().setColorFilter(
						new ColorMatrixColorFilter(BT_NOT_SELECTED));
				v.setBackgroundDrawable(v.getBackground());
			}
			return false;
		}
	};

	/**
	 * 设置图片按钮获取焦点改变状态
	 */
	public final static void setButtonFocusChanged(View inView) {
		inView.setOnTouchListener(buttonOnTouchListener);
		inView.setOnFocusChangeListener(buttonOnFocusChangeListener);
	}

	/**
	 * 设置图片按钮获取焦点改变状态(当背景是图片时)
	 * 
	 * @param inImageButton
	 * @param initalpha
	 *            初始透明度,用于点击后还原
	 */
	public final static void setButtonFocusChanged(View inView, int initalpha) {
		ButtonColorFilter.initAlpha = initalpha;
		inView.setOnTouchListener(buttonOnTouchListener);
		inView.setOnFocusChangeListener(buttonOnFocusChangeListener);
	}

	/**
	 * 设置图片按钮获取焦点改变状态 (当背景是颜色时)
	 * 
	 * @param inImageButton
	 * @param initalpha
	 *            初始透明度,用于点击后还原
	 */
	public final static void setButtonColorFocusChanged(View inView,
			int initalpha) {
		ButtonColorFilter.initAlpha = initalpha;
		inView.setOnTouchListener(buttonColorOnTouchListener);
		inView.setOnFocusChangeListener(buttonOnFocusChangeListener);
	}

}
