package com.ustc.prlib.xunfei;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.iflytek.speech.RecognizerResult;
import com.iflytek.speech.SpeechConfig.RATE;
import com.iflytek.speech.SpeechError;
import com.iflytek.ui.RecognizerDialog;
import com.iflytek.ui.RecognizerDialogListener;
import com.ustc.prlib.util.ButtonColorFilter;
import com.ustc.prlib.util.MainListAdapter;
import com.ustc.prlib.util.PublicUtil;
import com.ustc.prlib.util.SharePreferenceInfo;
import com.ustc.prlib.vo.BaseParam;
import com.ustc.prlib.vo.PhoneVo;
import com.xiang.xunfei.R;


/**
 * @description : 启动后的主界面
 * @package com.xiang.xunfei
 * @title:MainActivity.java
 * @author :  
 * @date :2013-12-3 上午11:15:50 
 * @version : v4.0
 */
public class MainActivity extends Activity implements OnClickListener, RecognizerDialogListener{
	private static String POSITION_LEFT = "1";
	private static String POSTIION_RIGHT = "2";
	private StringBuilder builder;

	private ListView listView; 
	private MainListAdapter adapter;
	private ArrayList<PhoneVo> listVo;

	private Context context = this;
	private Button btn_start, btn_more; 
	private SharedPreferences mSharedPreferences;//缓存，保存当前的引擎参数到下一次启动应用程序使用.

	private RecognizerDialog iatDialog;//识别Dialog
	private String mInitParams;//初始化参数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initWidget();

		mInitParams = "appid=" + getString( R.string.app_id);

		//初始化转写Dialog, appid需要在http://open.voicecloud.cn获取.
		iatDialog = new RecognizerDialog(this, mInitParams);
		iatDialog.setListener(this);
		//初始化缓存对象.
		mSharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
	}


	private void initWidget() {
		listVo = new ArrayList<PhoneVo>();
		PhoneVo vo = new PhoneVo();
		vo.setContent("您好，很高兴为您服务，请说手机号！");
		vo.setPositon( POSITION_LEFT );
		listVo.add(vo);
		adapter = new MainListAdapter(context, listVo);

		listView = (ListView)findViewById( R.id.main_listview );
		listView.setDividerHeight(0);
		listView.setAdapter( adapter);

		btn_more = (Button)findViewById( R.id.main_btn_set );
		btn_more.setOnClickListener( this );
		btn_start = (Button)findViewById( R.id.main_btn_getword );
		btn_start.setOnClickListener( this );
		ButtonColorFilter.setButtonFocusChanged(btn_more);
	}

	/**
	 * 显示转写对话框.
	 * @param
	 */
	public void showIatDialog() {
		//获取引擎参数
		String engine = mSharedPreferences.getString(
				getString(R.string.preference_key_iat_engine),
				getString(R.string.preference_default_iat_engine));

		//获取area参数，POI搜索时需要传入.
		String area = "";
		//设置转写Dialog的引擎和poi参数.
		iatDialog.setEngine(engine, area, null);
		//设置采样率参数，由于绝大部分手机只支持8K和16K，所以设置11K和22K采样率将无法启动录音. 
		String rate = mSharedPreferences.getString(
				getString(R.string.preference_key_iat_rate),
				getString(R.string.preference_default_iat_rate));
		if(rate.equals("rate8k"))
			iatDialog.setSampleRate(RATE.rate8k);
		else if(rate.equals("rate11k"))
			iatDialog.setSampleRate(RATE.rate11k);
		else if(rate.equals("rate16k"))
			iatDialog.setSampleRate(RATE.rate16k);
		else if(rate.equals("rate22k"))
			iatDialog.setSampleRate(RATE.rate22k);

		builder = new StringBuilder(); 
		iatDialog.show(); 
	}

	//语音识别结束， 处理数据
	@Override
	public void onEnd(SpeechError arg0) {
		if ( !"".equals( builder.toString() )) {
			String phone = PublicUtil.getPhone( builder.toString() );
			if ( phone != null ) {
				PhoneVo vo = new PhoneVo();
				vo.setContent( phone );
				vo.setPositon( POSTIION_RIGHT );
				listVo.add( vo );
				adapter.notifyDataSetChanged();
				Toast.makeText(context, "识别号码"+ phone, Toast.LENGTH_SHORT).show();
				startOperate( phone);
			} else {
				Toast.makeText(context, "无有效号码:" + builder.toString(), Toast.LENGTH_LONG).show();
			}
		}
	}

	SharePreferenceInfo info = new SharePreferenceInfo(context);
	EditText et;
	String phone;
	private void startOperate(String phoneStr) {
		this.phone = phoneStr;
		switch ( info.getDefaultOperate() ) {
		case BaseParam.OPERATE_CALLPHONE :
			PublicUtil.callPhone(context, phone);
			break;

		case BaseParam.OPERATE_SENDSMS :
			String content = info.getDefaultSmsContent() + 
			"【" + info.getDefaultCompressContent() + "】";
			PublicUtil.editSendSms(context, phone, content);
			break;

		case BaseParam.OPERATE_ADDCONNECT :
			et = new EditText(this);
			new AlertDialog.Builder(this)
			.setTitle("请输入联系人")
			.setIcon( android.R.drawable.ic_dialog_info)
			.setView( et )
			.setPositiveButton("确定",  
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if ( !"".equals( et.getText().toString().trim() ) ) {
						PublicUtil.insertContact(context, et.getText().toString(), phone);
					}
				}
			}  
					)
					.setNegativeButton("取消", null)
					.show();
			break;
		}


	}

	@Override
	public void onResults(ArrayList<RecognizerResult> results, boolean arg1) {
		for (RecognizerResult recognizerResult : results) {
			builder.append(recognizerResult.text);
		}
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
		case R.id.main_btn_getword:
			showIatDialog();
			break;

		case  R.id.main_btn_set :
			startActivity(new Intent(MainActivity.this, SetActivity.class));
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
