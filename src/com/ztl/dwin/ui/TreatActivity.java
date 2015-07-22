package com.ztl.dwin.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.ztl.dwin.R;
/**
 * ����������
 * @author tianliang.zhou
 *
 */
public class TreatActivity extends BaseActivity implements OnClickListener{

	private Button btn_decrease;//width����ť
	private Button btn_increase;//width�Ӱ�ť
	private Button btn_repeat_decrease;//repeat����ť
	private Button btn_repeat_increase;//repeat�Ӱ�ť
	private Button btn_plan1;//plan1��ť
	private Button btn_plan2;//plan2��ť
	private Button btn_plan3;//plan3��ť
	private Button btn_fluence_add;//fluence�Ӱ�ť
	private Button btn_fluence_reduce;//fluence����ť
	private Button btn_tem_add;//temperature�Ӱ�ť
	private Button btn_tem_reduce;//temperature����ť
	private int temperature = 0;
	private int widht_step = 1;//width�Ĳ���
	private int fluence_step = 1;//fluence�Ĳ���
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
		case R.id.btn_increase://���width�Ӱ�ť
			if(width < 500){
				width += widht_step;
			}
			break;
		case R.id.btn_decrease://���width����ť
			if(width > 5){
				width -= widht_step;
			}
			break;
		case R.id.btn_plan1://���plan1��ť
			btn_plan1.setSelected(true);
			btn_plan2.setSelected(false);
			btn_plan3.setSelected(false);
			break;
		case R.id.btn_plan2://���plan2��ť
			btn_plan1.setSelected(false);
			btn_plan2.setSelected(true);
			btn_plan3.setSelected(false);
			break;
		case R.id.btn_plan3://���plan3��ť
			btn_plan1.setSelected(false);
			btn_plan2.setSelected(false);
			btn_plan3.setSelected(true);
			break;
		case R.id.btn_repeat_increase://���repeat�Ӱ�ť
			break;
		case R.id.btn_repeat_decrease://���repeat����ť
			break;
		case R.id.btn_fluence_add://���fluence�Ӱ�ť	
			if(fluence < 32){
				fluence += fluence_step;
			}
			break;
		case R.id.btn_fluence_reduce://���fluence����ť
			if(fluence > 5){
				fluence -= fluence_step;
			}
			break;
		case R.id.btn_tem_add://���temperature�Ӱ�ť
			if(temperature < 10){//��ǰ�¶�С��10��
				temperature += 5;
			}
			break;
		case R.id.btn_tem_reduce://���temperature����ť
			if(temperature > 0){//��ǰ�¶ȸ���0���϶�
				temperature -= 5;
			}			
			break;
		default:
			break;
		}
		
	}

}
