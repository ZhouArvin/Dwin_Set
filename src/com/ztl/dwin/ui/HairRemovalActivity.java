package com.ztl.dwin.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ztl.dwin.R;

/**
 * HairRemoval工作界面
 * @author tianliang.zhou
 *
 */
public class HairRemovalActivity extends BaseActivity implements OnClickListener{

	private final int INDEX_NONE = 0;//当前未有按钮选中
	private final int INDEX_FUNCTION = 1;//Function按钮被选中
	private final int INDEX_MODE = 2;//Mode按钮被选中
	private final int INDEX_COOLING = 3;//Cooling按钮被选中
	private final int INDEX_ENERGY = 4;//Energy按钮被选中
	private Button btn_function;//fuction按钮
	private Button btn_mode;//mode按钮
	private Button btn_cooling;//cooling按钮
	private Button btn_energy;//energy按钮
	private int curSelectedIndex;//当前选中的是哪个按钮
	private boolean isCoolingOn;//冷却片制冷开关是否打开
	private ImageView iv_snow;
	private TextView tv_counter;
	private int[] functionResIds;
	private int[] modeResIds;	
	private int[] numResIds;
	private int[] fuctionMotionResIds;
	private int functionResIndex = -1;
	private int modeResIndex = -1;
	private ImageView iv_fuction; 
	private ImageView iv_hrpc;//功能示意图片展示
	private ImageView iv_mode;
	private final int READY = 0;// 待命
	private final int STANDBY = 1;// 准备
	private int state = STANDBY;
	private Button btn_ready;
	private float cool_num = 3.0f;
	private int step_cool = 1;
	private int energy_num;
	private int step_energy = 1;
	private TextView tv_cooling;
	private ImageView iv_num1;
	private ImageView iv_num2;
	private TextView tv_current_function;//当前的function
	private TextView tv_current_density;//当前的density
	private TextView tv_current_pulswidth;
	private TextView tv_current_frequency;
	private TextView tv_coolant;
	private ImageView iv_charge;//电池电量展示
	private BatteryReceiver receiver;
	private int maxEnergyNum;//能量密度最大值
	private int minEnergyNum;//能量密度最小值
	/**
	 * 广播接收电池电量变量
	 * @author tianliang.zhou
	 *
	 */
	private class BatteryReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent == null){
				return;
			}
			if(Intent.ACTION_BATTERY_CHANGED.equals(intent.getAction())){
				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);//当前电量
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);//当前总电量
				int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);//当前电池的状态							
				int percent = (int)(level*100/scale);
				if(status == BatteryManager.BATTERY_STATUS_CHARGING){//正在充电
					iv_charge.setImageResource(R.drawable.battery_animation);//显示充电动画
					iv_charge.getDrawable().setLevel(percent);															
				}else{
					iv_charge.setImageResource(R.drawable.battery_drawable);//没有充电，显示当前电量
					iv_charge.getDrawable().setLevel(percent);
				}
			}
			
		}
		
	}

	@Override
	protected void initView() {
		setContentView(R.layout.hairremoval);		
		btn_function = (Button) findViewById(R.id.btn_function);
		btn_mode = (Button) findViewById(R.id.btn_mode);
		btn_cooling = (Button) findViewById(R.id.btn_cooling);
		btn_energy = (Button) findViewById(R.id.btn_energy);
		iv_snow = (ImageView) findViewById(R.id.iv_snow);
		tv_counter = (TextView) findViewById(R.id.tv_counter);
		iv_fuction = (ImageView) findViewById(R.id.iv_function);
		iv_hrpc = (ImageView) findViewById(R.id.iv_hrpc);
		iv_mode = (ImageView) findViewById(R.id.iv_mode);
		btn_ready = (Button) findViewById(R.id.btn_ready);
		tv_cooling= (TextView) findViewById(R.id.tv_cooling);
		iv_num1 = (ImageView) findViewById(R.id.iv_num1);
		iv_num2 = (ImageView) findViewById(R.id.iv_num2);
		tv_current_function = (TextView) findViewById(R.id.tv_current_function);
		tv_current_density = (TextView) findViewById(R.id.tv_current_density);
		tv_current_pulswidth = (TextView) findViewById(R.id.tv_current_pulswidth);
		tv_current_frequency = (TextView) findViewById(R.id.tv_current_frequency);
		tv_coolant = (TextView) findViewById(R.id.tv_coolant);
		iv_charge = (ImageView) findViewById(R.id.iv_charge);
	}

	@Override
	protected void setAttribute() {				
		tv_coolant.setText(Html.fromHtml(String.format(getString(R.string.coolant), "24.7")));
		btn_cooling.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View view) {
				if(curSelectedIndex != INDEX_COOLING){
					curSelectedIndex = INDEX_COOLING;
				}else{
					curSelectedIndex = INDEX_NONE;
				}
				setSelectedState();
				return false;
			}
		});		
		registerReceiver(receiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		
	}

	@Override
	protected void initData() {
		functionResIds = getResources().getIntArray(R.array.functionResArray);
		modeResIds = getResources().getIntArray(R.array.modeResArray);
		numResIds = getResources().getIntArray(R.array.numResArray);
		fuctionMotionResIds = getResources().getIntArray(R.array.functionMotionResArray);
		receiver = new BatteryReceiver();
	}

	@Override
	protected void handleBundle(Bundle bundle) {

	}
		
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_function://功能按键
			if(curSelectedIndex != INDEX_FUNCTION){
				curSelectedIndex = INDEX_FUNCTION;
			}else{
				curSelectedIndex = INDEX_NONE;
			}				
			setSelectedState();
			break;
		case R.id.btn_mode://模式按键
			if(curSelectedIndex != INDEX_MODE){
				curSelectedIndex = INDEX_MODE;
			}else{
				curSelectedIndex = INDEX_NONE;
			}			
			setSelectedState();
			break;
		case R.id.btn_cooling://cool按钮
			isCoolingOn = !isCoolingOn;
			if(isCoolingOn){
				iv_snow.setVisibility(View.VISIBLE);
			}else{
				iv_snow.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.btn_counter://counter按钮,清零
			tv_counter.setText("0");
			break;
		case R.id.btn_exit://返回按钮
			finish();
			break;
		case R.id.btn_add://加按钮
			handAddClick();
			break;
		case R.id.btn_reduce://减按钮
			handReduceClick();
			break;
		case R.id.btn_energy://energy按钮
			if(curSelectedIndex != INDEX_ENERGY){
				curSelectedIndex = INDEX_ENERGY;
			}else{
				curSelectedIndex = INDEX_NONE;
			}			
			setSelectedState();
			break;
		case R.id.btn_tool://tool按钮
			//TODO 跳转到信息话界面
			break;
		case R.id.btn_ready://准备按钮
			if (state == READY) {// 当前是READY状态，按下之后就变为STANDBY状态
				state = STANDBY;
				btn_ready.setBackgroundResource(R.drawable.hair_standby);
			} else if (state == STANDBY) {// 当前是STANDBY状态，按下之后就变为READY状态
				state = READY;
				btn_ready.setBackgroundResource(R.drawable.hair_ready);
			}
			break;
		default:
			break;
		}

	}
	
	/**
	 * 加按钮点击处理
	 */
	private void handAddClick(){
		switch (curSelectedIndex) {
		case INDEX_FUNCTION:
			functionResIndex++;
			setFuction();
			break;
		case INDEX_MODE:
			modeResIndex++;
			setMode();
			break;
		case INDEX_COOLING:	
			cool_num += step_cool;
			tv_cooling.setText(String.format(getString(R.string.temperature_num), String.valueOf(cool_num)));
			break;
		case INDEX_ENERGY:
			if(functionResIndex == -1){//没有选择功能模式，加减能量密度无意义
				return; 
			}
			if((energy_num + step_energy) <= maxEnergyNum){//不能超过最大能量密度
				energy_num += step_energy;
			}				
			setEnergyPic();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 减按钮点击处理
	 */
	private void handReduceClick(){
		switch (curSelectedIndex) {
		case INDEX_FUNCTION:
			if(functionResIndex > 0){
				functionResIndex--;
			}			
			setFuction();
			break;
		case INDEX_MODE:
			if(modeResIndex > 0){
				modeResIndex--;
			}			
			setMode();
			break;
		case INDEX_COOLING:	
			if(cool_num >= step_cool){
				cool_num -= step_cool;
			}
			tv_cooling.setText(String.format(getString(R.string.temperature_num), String.valueOf(cool_num)));
			break;
		case INDEX_ENERGY:
			if(functionResIndex == -1){//没有选择功能模式，加减能量密度无意义
				return; 
			}
			if((energy_num - step_energy) >= minEnergyNum){//不能小于最小能量密度
				energy_num -= step_energy;
			}
			setEnergyPic();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 设置能量密度
	 */
	private void setEnergyPic(){
		String num = String.valueOf(energy_num);
		int length = num.length();
		int[] numArray = new int[length];
		for(int i = 0; i< length; i++){
			numArray[i] = Integer.parseInt(String.valueOf(num.charAt(i)));
		}
		if(length == 1){
			iv_num1.setImageResource(numResIds[numArray[0]]);
			iv_num2.setVisibility(View.GONE);
		}else if(length == 2){
			iv_num1.setImageResource(numResIds[numArray[0]]);
			iv_num2.setVisibility(View.VISIBLE);
			iv_num2.setImageResource(numResIds[numArray[1]]);
		}
	}

	/**
	 * 设置当前选中的状态
	 */
	private void setSelectedState(){
		switch (curSelectedIndex) {
		case INDEX_NONE:
			btn_function.setSelected(false);
			btn_mode.setSelected(false);
			btn_energy.setSelected(false);
			btn_cooling.setSelected(false);
			break;
		case INDEX_FUNCTION:
			btn_function.setSelected(true);
			btn_mode.setSelected(false);
			btn_energy.setSelected(false);
			btn_cooling.setSelected(false);
			break;
		case INDEX_MODE:
			btn_function.setSelected(false);
			btn_mode.setSelected(true);
			btn_energy.setSelected(false);
			btn_cooling.setSelected(false);
			break;
		case INDEX_COOLING:
			btn_function.setSelected(false);
			btn_mode.setSelected(false);
			btn_energy.setSelected(false);
			btn_cooling.setSelected(true);
			break;
		case INDEX_ENERGY:
			btn_function.setSelected(false);
			btn_mode.setSelected(false);
			btn_energy.setSelected(true);
			btn_cooling.setSelected(false);
			break;
		default:
			break;
		}
	}
		
	/**
	 * 设置功能资源
	 */
	private void setFuction(){
		int index = functionResIndex%5;
		iv_fuction.setImageResource(functionResIds[index]);
		iv_hrpc.setImageResource(fuctionMotionResIds[functionResIndex%5]);
		switch (index) {
		case 0:	//AC	
			tv_current_function.setText(String.format(getString(R.string.current_function), "AC"));
			tv_current_density.setText(String.format(getString(R.string.current_density), "15~25"));
			tv_current_pulswidth.setText(String.format(getString(R.string.current_pluswidth), "80"));
			maxEnergyNum = 25;
			minEnergyNum = 15;
			energy_num = 15;
			setEnergyPic();
			break;
		case 1://HR
			tv_current_function.setText(String.format(getString(R.string.current_function), "HR"));
			tv_current_density.setText(String.format(getString(R.string.current_density), "20~40"));
			tv_current_pulswidth.setText(String.format(getString(R.string.current_pluswidth), "100"));
			maxEnergyNum = 40;
			minEnergyNum = 20;
			energy_num = 20;
			setEnergyPic();
			break;
		case 2://HRP
			tv_current_function.setText(String.format(getString(R.string.current_function), "HR+"));
			tv_current_density.setText(String.format(getString(R.string.current_density), "20~60"));
			tv_current_pulswidth.setText(String.format(getString(R.string.current_pluswidth), "150"));
			maxEnergyNum = 60;
			minEnergyNum = 20;
			energy_num = 20;
			setEnergyPic();
			break;
		case 3://SR
			tv_current_function.setText(String.format(getString(R.string.current_function), "SR"));
			tv_current_density.setText(String.format(getString(R.string.current_density), "20~60"));
			tv_current_pulswidth.setText(String.format(getString(R.string.current_pluswidth), "300"));
			maxEnergyNum = 60;
			minEnergyNum = 20;
			energy_num = 20;
			setEnergyPic();
			break;
		case 4://SHR
			tv_current_function.setText(String.format(getString(R.string.current_function), "SHR"));
			tv_current_density.setText(String.format(getString(R.string.current_density), "10~20"));
			tv_current_pulswidth.setText(String.format(getString(R.string.current_pluswidth), "100"));
			maxEnergyNum = 20;
			minEnergyNum = 10;
			energy_num = 10;
			setEnergyPic();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 设置模式
	 */
	private void setMode(){
		int index = modeResIndex%2;
		iv_mode.setImageResource(modeResIds[index]);
		switch (index) {
		case 0:
			tv_current_frequency.setVisibility(View.INVISIBLE);
			break;
		case 1:
			tv_current_frequency.setVisibility(View.VISIBLE);
			tv_current_frequency.setText(String.format(getString(R.string.currrent_frequency), "2"));
			break;
		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}
}
