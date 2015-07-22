package com.ztl.dwin.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle bundle) {		
		super.onCreate(bundle);		
		initData();
		initView();
		setAttribute();
		handleBundle(bundle);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	/**
	 * 初始化控件
	 */
	protected abstract void initView();

	/**
	 * 设置监听
	 */
	protected abstract void setAttribute();
	
	/**
	 * 初始化数据
	 */
	protected abstract void initData();
	
	/**
	 * 处理bundle数据
	 * @param bundle
	 */
	protected abstract void handleBundle(Bundle bundle);
	
}
