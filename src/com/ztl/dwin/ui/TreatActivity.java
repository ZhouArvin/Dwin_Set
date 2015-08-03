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
 * 治疗屏界面
 * 
 * @author tianliang.zhou
 * 
 */
public class TreatActivity extends BaseActivity implements OnClickListener,
		OnTouchListener{

	private Button btn_decrease;// width减按钮
	private Button btn_increase;// width加按钮
	private Button btn_repeat_decrease;// repeat减按钮
	private Button btn_repeat_increase;// repeat加按钮
	private Button btn_plan1;// plan1按钮
	private Button btn_plan2;// plan2按钮
	private Button btn_plan3;// plan3按钮
	private Button btn_fluence_add;// fluence加按钮
	private Button btn_fluence_reduce;// fluence减按钮
	private Button btn_tem_add;// temperature加按钮
	private Button btn_tem_reduce;// temperature减按钮
	private Button btn_ready;// 待命或者准备
	private int temperature = 0;
	private int widht_step = 1;// width的步长
	private int fluence_step = 1;// fluence的步长
	private int fluence = 5;
	private int width = 5;
	private ImageView iv_temp;
	private Button btn_cure_count;
	private TextView tv_count;
	private final int READY = 0;// 待命
	private final int STANDBY = 1;// 准备
	private int state = READY;
	private int repeat_num = 0;// repeat数值，默认为0，即 OFF状态
	private TextView tv_repeat_num;// 展示repeat数值的文本的控件
	private TextView tv_width_num;// 展示width数值的文本控件
	private TextView tv_fluence;// 展示fluence的文本控件
	
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
	 * 计时任务
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
		case R.id.btn_plan1:// 点击plan1按钮
			btn_plan1.setSelected(true);
			btn_plan2.setSelected(false);
			btn_plan3.setSelected(false);
			break;
		case R.id.btn_plan2:// 点击plan2按钮
			btn_plan1.setSelected(false);
			btn_plan2.setSelected(true);
			btn_plan3.setSelected(false);
			break;
		case R.id.btn_plan3:// 点击plan3按钮
			btn_plan1.setSelected(false);
			btn_plan2.setSelected(false);
			btn_plan3.setSelected(true);
			break;
		case R.id.btn_repeat_increase:// 点击repeat加按钮
			if (repeat_num < 5) {
				repeat_num++;
			}
			setRepeatText();
			break;
		case R.id.btn_repeat_decrease:// 点击repeat减按钮
			if (repeat_num > 0) {
				repeat_num--;
			}
			setRepeatText();
			break;		
		case R.id.btn_tem_add:// 点击temperature加按钮
			if (temperature < 10) {// 当前温度小于10℃
				temperature += 5;
			}
			setTempImgResource();
			break;
		case R.id.btn_tem_reduce:// 点击temperature减按钮
			if (temperature > 0) {// 当前温度高于0摄氏度
				temperature -= 5;
			}
			setTempImgResource();
			break;
		case R.id.btn_ready:
			if (state == READY) {// 当前是READY状态，按下之后就变为STANDBY状态
				state = STANDBY;
				btn_ready.setBackgroundResource(R.drawable.standby);
			} else if (state == STANDBY) {// 当前是STANDBY状态，按下之后就变为READY状态
				state = READY;
				btn_ready.setBackgroundResource(R.drawable.ready);
			}
			break;
		case R.id.btn_cure_count:// 点击counter图标
			tv_count.setText("0");
			break;
		default:
			break;
		}

	}

	/**
	 * 设置当前温度计图片
	 */
	private void setTempImgResource() {
		switch (temperature) {
		case 0:// 0摄氏度
			iv_temp.setImageResource(R.drawable.temp_zero);
			break;
		case 5:// 5摄氏度
			iv_temp.setImageResource(R.drawable.temp_five);
			break;
		case 10:// 10摄氏度
			iv_temp.setImageResource(R.drawable.temp_ten);
			break;
		default:
			break;
		}
	}

	/**
	 * 设置repeat数值
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
		case R.id.btn_increase:// 点击width加按钮:
			handTouch(event, FLAG_WIDTH_ADD);
			break;
		case R.id.btn_decrease:// 点击width减按钮
			handTouch(event, FLAG_WIDTH_REDUCE);
			break;
		case R.id.btn_fluence_add:// 点击fluence加按钮
			handTouch(event, FLAG_FLUCENCE_ADD);
			break;
		case R.id.btn_fluence_reduce:// 点击fluence减按钮
			handTouch(event, FLAG_FLUCENCE_REDUCE);
			break;	
		default:
			break;
		}
		return false;
	}
	
	/**
	 * 响应touch
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
