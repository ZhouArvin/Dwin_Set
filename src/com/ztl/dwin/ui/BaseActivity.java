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
	 * ��ʼ���ؼ�
	 */
	protected abstract void initView();

	/**
	 * ���ü���
	 */
	protected abstract void setAttribute();
	
	/**
	 * ��ʼ������
	 */
	protected abstract void initData();
	
	/**
	 * ����bundle����
	 * @param bundle
	 */
	protected abstract void handleBundle(Bundle bundle);
	
}
