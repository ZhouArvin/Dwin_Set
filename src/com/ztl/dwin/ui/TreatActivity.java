package com.ztl.dwin.ui;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ztl.dwin.R;

/**
 * ����������
 * 
 * @author tianliang.zhou
 * 
 */
public class TreatActivity extends BaseActivity implements OnClickListener,
		OnTouchListener{

	private Button btn_decrease;// width����ť
	private Button btn_increase;// width�Ӱ�ť
	private Button btn_repeat_decrease;// repeat����ť
	private Button btn_repeat_increase;// repeat�Ӱ�ť
	private Button btn_plan1;// plan1��ť
	private Button btn_plan2;// plan2��ť
	private Button btn_plan3;// plan3��ť
	private Button btn_fluence_add;// fluence�Ӱ�ť
	private Button btn_fluence_reduce;// fluence����ť
	private Button btn_tem_add;// temperature�Ӱ�ť
	private Button btn_tem_reduce;// temperature����ť
	private Button btn_ready;// ��������׼��
	private int temperature = 0;
	private int widht_step = 1;// width�Ĳ���
	private int fluence_step = 1;// fluence�Ĳ���
	private int fluence = 5;
	private int width = 5;
	private ImageView iv_temp;
	private Button btn_cure_count;
	private TextView tv_count;
	private final int READY = 0;// ����
	private final int STANDBY = 1;// ׼��
	private int state = READY;
	private int repeat_num = 0;// repeat��ֵ��Ĭ��Ϊ0���� OFF״̬
	private TextView tv_repeat_num;// չʾrepeat��ֵ���ı��Ŀؼ�
	private TextView tv_width_num;// չʾwidth��ֵ���ı��ؼ�
	private TextView tv_fluence;// չʾfluence���ı��ؼ�
	
	private final int FLAG_WIDTH_ADD = 1;
	private final int FLAG_WIDTH_REDUCE = 2;
	private final int FLAG_FLUCENCE_ADD = 3;
	private final int FLAG_FLUCENCE_REDUCE = 4;
	private Timer timer;
	private MyTimerTask task;
	
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case FLAG_WIDTH_ADD:
				if (width < 500) {
					width += widht_step;
				}
				tv_width_num.setText(String.valueOf(width));
				break;
			case FLAG_WIDTH_REDUCE:
				if (width > 5) {
					width -= widht_step;
				}
				tv_width_num.setText(String.valueOf(width));
				break;
			case FLAG_FLUCENCE_ADD:
				if (fluence < 32) {
					fluence += fluence_step;
				}
				tv_fluence.setText(String.valueOf(fluence));
				break;
			case FLAG_FLUCENCE_REDUCE:
				if (fluence > 5) {
					fluence -= fluence_step;
				}
				tv_fluence.setText(String.valueOf(fluence));
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
		
	};
	
	/**
	 * ��ʱ����
	 * @author tianliang.zhou
	 *
	 */
	private class MyTimerTask extends TimerTask{
		private int flag;
		
		public MyTimerTask(int flag){
			this.flag = flag;
		}

		@Override
		public void run() {
			mHandler.sendEmptyMessage(flag);			
		}		
	}

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
		btn_ready = (Button) findViewById(R.id.btn_ready);
		iv_temp = (ImageView) findViewById(R.id.iv_temp);
		btn_cure_count = (Button) findViewById(R.id.btn_cure_count);
		tv_count = (TextView) findViewById(R.id.tv_count);
		tv_repeat_num = (TextView) findViewById(R.id.tv_repeat_num);
		tv_width_num = (TextView) findViewById(R.id.tv_width_num);
		tv_fluence = (TextView) findViewById(R.id.tv_fluence);
	}
		

	@Override
	protected void setAttribute() {
		btn_increase.setOnTouchListener(this);
		btn_decrease.setOnTouchListener(this);
		btn_fluence_add.setOnTouchListener(this);
		btn_fluence_reduce.setOnTouchListener(this);
		btn_repeat_decrease.setOnClickListener(this);
		btn_repeat_increase.setOnClickListener(this);
		btn_plan1.setOnClickListener(this);
		btn_plan2.setOnClickListener(this);
		btn_plan3.setOnClickListener(this);		
		btn_tem_add.setOnClickListener(this);
		btn_tem_reduce.setOnClickListener(this);
		btn_ready.setOnClickListener(this);
		btn_cure_count.setOnClickListener(this);

		
		tv_width_num.setText(String.valueOf(width));
		setRepeatText();
		tv_fluence.setText(String.valueOf(fluence));
		tv_count.setText("12");
	}

	@Override
	protected void initData() {
		timer = new Timer();
	}

	@Override
	protected void handleBundle(Bundle bundle) {

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {		
		case R.id.btn_plan1:// ���plan1��ť
			btn_plan1.setSelected(true);
			btn_plan2.setSelected(false);
			btn_plan3.setSelected(false);
			break;
		case R.id.btn_plan2:// ���plan2��ť
			btn_plan1.setSelected(false);
			btn_plan2.setSelected(true);
			btn_plan3.setSelected(false);
			break;
		case R.id.btn_plan3:// ���plan3��ť
			btn_plan1.setSelected(false);
			btn_plan2.setSelected(false);
			btn_plan3.setSelected(true);
			break;
		case R.id.btn_repeat_increase:// ���repeat�Ӱ�ť
			if (repeat_num < 5) {
				repeat_num++;
			}
			setRepeatText();
			break;
		case R.id.btn_repeat_decrease:// ���repeat����ť
			if (repeat_num > 0) {
				repeat_num--;
			}
			setRepeatText();
			break;		
		case R.id.btn_tem_add:// ���temperature�Ӱ�ť
			if (temperature < 10) {// ��ǰ�¶�С��10��
				temperature += 5;
			}
			setTempImgResource();
			break;
		case R.id.btn_tem_reduce:// ���temperature����ť
			if (temperature > 0) {// ��ǰ�¶ȸ���0���϶�
				temperature -= 5;
			}
			setTempImgResource();
			break;
		case R.id.btn_ready:
			if (state == READY) {// ��ǰ��READY״̬������֮��ͱ�ΪSTANDBY״̬
				state = STANDBY;
				btn_ready.setBackgroundResource(R.drawable.standby);
			} else if (state == STANDBY) {// ��ǰ��STANDBY״̬������֮��ͱ�ΪREADY״̬
				state = READY;
				btn_ready.setBackgroundResource(R.drawable.ready);
			}
			break;
		case R.id.btn_cure_count:// ���counterͼ��
			tv_count.setText("0");
			break;
		default:
			break;
		}

	}

	/**
	 * ���õ�ǰ�¶ȼ�ͼƬ
	 */
	private void setTempImgResource() {
		switch (temperature) {
		case 0:// 0���϶�
			iv_temp.setImageResource(R.drawable.temp_zero);
			break;
		case 5:// 5���϶�
			iv_temp.setImageResource(R.drawable.temp_five);
			break;
		case 10:// 10���϶�
			iv_temp.setImageResource(R.drawable.temp_ten);
			break;
		default:
			break;
		}
	}

	/**
	 * ����repeat��ֵ
	 */
	private void setRepeatText() {
		if (repeat_num != 0) {
			tv_repeat_num.setText(String.valueOf(repeat_num));
		} else {
			tv_repeat_num.setText("OFF");
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {	
		switch (view.getId()) {
		case R.id.btn_increase:// ���width�Ӱ�ť:
			handTouch(event, FLAG_WIDTH_ADD);
			break;
		case R.id.btn_decrease:// ���width����ť
			handTouch(event, FLAG_WIDTH_REDUCE);
			break;
		case R.id.btn_fluence_add:// ���fluence�Ӱ�ť
			handTouch(event, FLAG_FLUCENCE_ADD);
			break;
		case R.id.btn_fluence_reduce:// ���fluence����ť
			handTouch(event, FLAG_FLUCENCE_REDUCE);
			break;	
		default:
			break;
		}
		return false;
	}
	
	/**
	 * ��Ӧtouch
	 * @param event
	 * @param flag
	 */
	private void handTouch(MotionEvent event, int flag){
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if(task == null){
				task = new MyTimerTask(flag);
				timer.schedule(task, 0l, 500l);
			}
			break;
		case MotionEvent.ACTION_CANCEL:				
		case MotionEvent.ACTION_UP:
			if(task != null){
				task.cancel();
				task = null;
			}
			break;
		default:
			break;
		}
	}
}
