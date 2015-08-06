package com.ztl.dwin.ui;

import com.ztl.dwin.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 类型选择界面
 * @author tianliang.zhou
 *
 */
public class ChooseTypeActivity extends BaseActivity implements OnClickListener{
	
	private Button btn_forever;//forver按钮
	private Button btn_hair;//hair按钮
	
	@Override
	protected void initView() {
		setContentView(R.layout.choose_type);
		btn_forever = (Button) findViewById(R.id.btn_forever);
		btn_hair = (Button) findViewById(R.id.btn_hair);
		
	}

	@Override
	protected void setAttribute() {
		btn_forever.setOnClickListener(this);
		btn_hair.setOnClickListener(this);
		
	}

	@Override
	protected void initData() {	
	}

	@Override
	protected void handleBundle(Bundle bundle) {		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_forever://点击forever按钮
			startActivity(new Intent(ChooseTypeActivity.this, TreatActivity.class));
			break;
		case R.id.btn_hair://点击hair按钮
			
			break;
		default:
			break;
		}
		
	}

}
