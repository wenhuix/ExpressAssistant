package com.ustc.prlib.xunfei;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.reflect.TypeToken;
import com.ustc.prlib.util.ButtonColorFilter;
import com.ustc.prlib.util.ExpressTempletListAdapter;
import com.ustc.prlib.util.JsonParserUtil;
import com.ustc.prlib.util.PrivateFileReadSave;
import com.ustc.prlib.util.SharePreferenceInfo;
import com.ustc.prlib.util.SmsTempletListAdapter;
import com.ustc.prlib.vo.BaseParam;
import com.ustc.prlib.vo.SmsVo;
import com.xiang.xunfei.R;


/**
 * @description : 短信模板选择
 * @package com.xiang.xunfei
 * @title:SmsTempletAct.java
 * @author : email:xiangyanhui@unitepower.net
 * @date :2013-12-3 下午2:59:18 
 * @version : v4.0
 */
public class SmsTempletAct extends Activity implements OnClickListener{
	private Context context = this;
	private Button btn_add, btn_back; 
	private ListView listView; 
	private SmsTempletListAdapter adapter;
	private ArrayList<SmsVo> listVo;
	SharePreferenceInfo info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smstemplet);
		info = new SharePreferenceInfo(context);
		initWidget();
		initData();
	}

	@SuppressWarnings("unchecked")
	private void initWidget() {
		String jsonStr = PrivateFileReadSave.read(BaseParam.SMS_FILENAME, context);
		if ( jsonStr != null ) {
			Type type = new TypeToken<ArrayList<SmsVo>>(){}.getType();
			listVo = (ArrayList<SmsVo>) JsonParserUtil.parseJson2ListNoItem(jsonStr, type);
		}
		if ( listVo == null ) {
			listVo = new ArrayList<SmsVo>();
		}
		adapter = new SmsTempletListAdapter(context, listVo);

		listView = (ListView)findViewById( R.id.smstemplet_listview );
		listView.setDividerHeight(0);
		listView.setAdapter( adapter);

		btn_back = (Button)findViewById( R.id.smstemplet_btn_back );
		btn_back.setOnClickListener( this );
		btn_add = (Button)findViewById( R.id.smstemplet_btn_add );
		btn_add.setOnClickListener( this );
		ButtonColorFilter.setButtonFocusChanged( btn_add);
		ButtonColorFilter.setButtonFocusChanged( btn_back);
		
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//listVo.get(position).setDefault( true );
				info.updateDefaultSmsContent( listVo.get(position).getContent() );
				info.updateDefaultSmsTempletId( listVo.get(position).getId() );
				adapter.notifyDataSetChanged();
				finish();
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		String jsonStr = PrivateFileReadSave.read(BaseParam.SMS_FILENAME, context);
		if ( jsonStr != null ) {
			Type type = new TypeToken<ArrayList<SmsVo>>(){}.getType();
			listVo = (ArrayList<SmsVo>) JsonParserUtil.parseJson2ListNoItem(jsonStr, type);
		}
		if ( listVo == null ) {
			listVo = new ArrayList<SmsVo>();
		}
		adapter = new SmsTempletListAdapter(context, listVo);
		listView.setAdapter( adapter);
	}

	 
	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
		case R.id.smstemplet_btn_back:
			finish();
			break;
		case  R.id.smstemplet_btn_add :
			Intent intent = new Intent(context, AddTempletActivity.class);
			intent.putExtra( BaseParam.ADDTYPE, BaseParam.ADDTYPE_SMS_TEMPLET);
			startActivityForResult(intent, 1);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode == 1 ) {
			initData();
		}
	}
}
