package com.ztl.dwin.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.ztl.dwin.R;
/**
 * 治疗屏界面
 * @author tianliang.zhou
 *
 */
public class TreatActivity extends BaseActivity implements OnClickListener{

	private Button btn_decrease;//width减按钮
	private Button btn_increase;//width加按钮
	private Button btn_repeat_decrease;//repeat减按钮
	private Button btn_repeat_increase;//repeat加按钮
	private Button btn_plan1;//plan1按钮
	private Button btn_plan2;//plan2按钮
	private Button btn_plan3;//plan3按钮
	private Button btn_fluence_add;//fluence加按钮
	private Button btn_fluence_reduce;//fluence减按钮
	private Button btn_tem_add;//temperature加按钮
	private Button btn_tem_reduce;//temperature减按钮
	private int temperature = 0;
	private int widht_step = 1;//width的步长
	private int fluence_step = 1;//fluence的步长
	private int fluence = 5;
	private int width = 5;
	
	@Override
	protected void initView() {
		setContentView(R.layout.treat);
		btn_decrease = (Button) findViewById(R.id.btn_decrease);
		btn_increase = (Button) findViewById(R.id.btn_increase);
		btn_repeat_decrease = (Button) findViewById(R.id.btn_repeat_decrease);
		btn_repeat_increase = (Button) findViewById(R.id.btn_repeat_increase);
		btn_plan1 = (Button) findViewById(R.id.btn_plan1);
		btn_plan2 = (Button) findViewById(R.id.btn_plan2);
		btn_plan3 = (Button) findViewById(R.id.btn_plan3);
		btn_fluence_add = (Button) findViewById(R.id.btn_fluence_add);
		btn_fluence_reduce = (Button) findViewById(R.id.btn_fluence_reduce);
		btn_tem_add = (Button) findViewById(R.id.btn_tem_add);
		btn_tem_reduce = (Button) findViewById(R.id.btn_tem_reduce);
	}

	@Override
	protected void setAttribute() {
		btn_increase.setOnClickListener(this);
		btn_decrease.setOnClickListener(this);
		btn_repeat_decrease.setOnClickListener(this);
		btn_repeat_increase.setOnClickListener(this);
		btn_plan1.setOnClickListener(this);
		btn_plan2.setOnClickListener(this);
		btn_plan3.setOnClickListener(this);
		btn_fluence_add.setOnClickListener(this);
		btn_fluence_reduce.setOnClickListener(this);
		btn_tem_add.setOnClickListener(this);
		btn_tem_reduce.setOnClickListener(this);
		
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
		case R.id.btn_increase://点击width加按钮
			if(width < 500){
				width += widht_step;
			}
			break;
		case R.id.btn_decrease://点击width减按钮
			if(width > 5){
				width -= widht_step;
			}
			break;
		case R.id.btn_plan1://点击plan1按钮
			btn_plan1.setSelected(true);
			btn_plan2.setSelected(false);
			btn_plan3.setSelected(false);
			break;
		case R.id.btn_plan2://点击plan2按钮
			btn_plan1.setSelected(false);
			btn_plan2.setSelected(true);
			btn_plan3.setSelected(false);
			break;
		case R.id.btn_plan3://点击plan3按钮
			btn_plan1.setSelected(false);
			btn_plan2.setSelected(false);
			btn_plan3.setSelected(true);
			break;
		case R.id.btn_repeat_increase://点击repeat加按钮
			break;
		case R.id.btn_repeat_decrease://点击repeat减按钮
			break;
		case R.id.btn_fluence_add://点击fluence加按钮	
			if(fluence < 32){
				fluence += fluence_step;
			}
			break;
		case R.id.btn_fluence_reduce://点击fluence减按钮
			if(fluence > 5){
				fluence -= fluence_step;
			}
			break;
		case R.id.btn_tem_add://点击temperature加按钮
			if(temperature < 10){//当前温度小于10℃
				temperature += 5;
			}
			break;
		case R.id.btn_tem_reduce://点击temperature减按钮
			if(temperature > 0){//当前温度高于0摄氏度
				temperature -= 5;
			}			
			break;
		default:
			break;
		}
		
	}

}
