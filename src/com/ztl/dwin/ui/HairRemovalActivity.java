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
 * HairRemoval��������
 * @author tianliang.zhou
 *
 */
public class HairRemovalActivity extends BaseActivity implements OnClickListener{

	private final int INDEX_NONE = 0;//��ǰδ�а�ťѡ��
	private final int INDEX_FUNCTION = 1;//Function��ť��ѡ��
	private final int INDEX_MODE = 2;//Mode��ť��ѡ��
	private final int INDEX_COOLING = 3;//Cooling��ť��ѡ��
	private final int INDEX_ENERGY = 4;//Energy��ť��ѡ��
	private Button btn_function;//fuction��ť
	private Button btn_mode;//mode��ť
	private Button btn_cooling;//cooling��ť
	private Button btn_energy;//energy��ť
	private int curSelectedIndex;//��ǰѡ�е����ĸ���ť
	private boolean isCoolingOn;//��ȴƬ���俪���Ƿ��
	private ImageView iv_snow;
	private TextView tv_counter;
	private int[] functionResIds;
	private int[] modeResIds;	
	private int[] numResIds;
	private int[] fuctionMotionResIds;
	private int functionResIndex = -1;
	private int modeResIndex = -1;
	private ImageView iv_fuction; 
	private ImageView iv_hrpc;//����ʾ��ͼƬչʾ
	private ImageView iv_mode;
	private final int READY = 0;// ����
	private final int STANDBY = 1;// ׼��
	private int state = STANDBY;
	private Button btn_ready;
	private float cool_num = 3.0f;
	private int step_cool = 1;
	private int energy_num;
	private int step_energy = 1;
	private TextView tv_cooling;
	private ImageView iv_num1;
	private ImageView iv_num2;
	private TextView tv_current_function;//��ǰ��function
	private TextView tv_current_density;//��ǰ��density
	private TextView tv_current_pulswidth;
	private TextView tv_current_frequency;
	private TextView tv_coolant;
	private ImageView iv_charge;//��ص���չʾ
	private BatteryReceiver receiver;
	private int maxEnergyNum;//�����ܶ����ֵ
	private int minEnergyNum;//�����ܶ���Сֵ
	/**
	 * �㲥���յ�ص�������
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
				int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);//��ǰ����
				int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);//��ǰ�ܵ���
				int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);//��ǰ��ص�״̬							
				int percent = (int)(level*100/scale);
				if(status == BatteryManager.BATTERY_STATUS_CHARGING){//���ڳ��
					iv_charge.setImageResource(R.drawable.battery_animation);//��ʾ��綯��
					iv_charge.getDrawable().setLevel(percent);															
				}else{
					iv_charge.setImageResource(R.drawable.battery_drawable);//û�г�磬��ʾ��ǰ����
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
		case R.id.btn_function://���ܰ���
			if(curSelectedIndex != INDEX_FUNCTION){
				curSelectedIndex = INDEX_FUNCTION;
			}else{
				curSelectedIndex = INDEX_NONE;
			}				
			setSelectedState();
			break;
		case R.id.btn_mode://ģʽ����
			if(curSelectedIndex != INDEX_MODE){
				curSelectedIndex = INDEX_MODE;
			}else{
				curSelectedIndex = INDEX_NONE;
			}			
			setSelectedState();
			break;
		case R.id.btn_cooling://cool��ť
			isCoolingOn = !isCoolingOn;
			if(isCoolingOn){
				iv_snow.setVisibility(View.VISIBLE);
			}else{
				iv_snow.setVisibility(View.INVISIBLE);
			}
			break;
		case R.id.btn_counter://counter��ť,����
			tv_counter.setText("0");
			break;
		case R.id.btn_exit://���ذ�ť
			finish();
			break;
		case R.id.btn_add://�Ӱ�ť
			handAddClick();
			break;
		case R.id.btn_reduce://����ť
			handReduceClick();
			break;
		case R.id.btn_energy://energy��ť
			if(curSelectedIndex != INDEX_ENERGY){
				curSelectedIndex = INDEX_ENERGY;
			}else{
				curSelectedIndex = INDEX_NONE;
			}			
			setSelectedState();
			break;
		case R.id.btn_tool://tool��ť
			//TODO ��ת����Ϣ������
			break;
		case R.id.btn_ready://׼����ť
			if (state == READY) {// ��ǰ��READY״̬������֮��ͱ�ΪSTANDBY״̬
				state = STANDBY;
				btn_ready.setBackgroundResource(R.drawable.hair_standby);
			} else if (state == STANDBY) {// ��ǰ��STANDBY״̬������֮��ͱ�ΪREADY״̬
				state = READY;
				btn_ready.setBackgroundResource(R.drawable.hair_ready);
			}
			break;
		default:
			break;
		}

	}
	
	/**
	 * �Ӱ�ť�������
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
			if(functionResIndex == -1){//û��ѡ����ģʽ���Ӽ������ܶ�������
				return; 
			}
			if((energy_num + step_energy) <= maxEnergyNum){//���ܳ�����������ܶ�
				energy_num += step_energy;
			}				
			setEnergyPic();
			break;
		default:
			break;
		}
	}
	
	/**
	 * ����ť�������
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
			if(functionResIndex == -1){//û��ѡ����ģʽ���Ӽ������ܶ�������
				return; 
			}
			if((energy_num - step_energy) >= minEnergyNum){//����С����С�����ܶ�
				energy_num -= step_energy;
			}
			setEnergyPic();
			break;
		default:
			break;
		}
	}
	
	/**
	 * ���������ܶ�
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
	 * ���õ�ǰѡ�е�״̬
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
	 * ���ù�����Դ
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
	 * ����ģʽ
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
