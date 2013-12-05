package com.ustc.prlib.xunfei;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ustc.prlib.util.ButtonColorFilter;
import com.ustc.prlib.util.ComapnyListAdapter;
import com.ustc.prlib.util.JsonParserUtil;
import com.ustc.prlib.util.PrivateFileReadSave;
import com.ustc.prlib.util.SharePreferenceInfo;
import com.ustc.prlib.util.SmsTemplateListAdapter;
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
public class SmsTempletActivity extends Activity implements OnClickListener{
		
	private Context context = this;
	private Button btn_add, btn_back; 
	private ListView listView; 
	private SmsTemplateListAdapter adapter;
	private ArrayList<SmsVo> listVo;
	SharePreferenceInfo info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smstemplet_list);
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
		adapter = new SmsTemplateListAdapter(context, listVo);

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
				info.updateDefaultSmsTemplate( listVo.get(position).getContent() );
				info.updateDefaultSmsTemplateId( listVo.get(position).getId() );
				adapter.notifyDataSetChanged();
				//finish();
			}
		});
		
		registerForContextMenu(listView);
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
		adapter = new SmsTemplateListAdapter(context, listVo);
		listView.setAdapter( adapter);
	}

	 
	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
		case R.id.smstemplet_btn_back:
			finish();
			break;
		case  R.id.smstemplet_btn_add :
			Intent intent = new Intent(context, ItemOperationActivity.class);
			intent.putExtra( BaseParam.OPERATION_TYPE, BaseParam.OPERATION_ADD_SMS_TEMPLATE);
			startActivityForResult(intent, 1);
			break;
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
		if(v.getId() == R.id.smstemplet_listview) {
			AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)info;
			String[] menuItems = getResources().getStringArray(R.array.menu);
			for (int i = 0; i<menuItems.length; i++) {
			      menu.add(Menu.NONE, i, i, menuItems[i]);
			}
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
				.getMenuInfo();
		int menuItemIndex = item.getItemId();
		int position = menuInfo.position;
		
		Toast.makeText(context, position + " clicked", 0).show();	
		
		switch (menuItemIndex) {

		case 0:
			
			Intent intent = new Intent(context, ItemOperationActivity.class);
			intent.putExtra(BaseParam.OPERATION_TYPE,
					BaseParam.OPERATION_EDIT_SMS_TEMPLATE);
			intent.putExtra(BaseParam.CLICK_ITEM_POSION, position);
			intent.putExtra(BaseParam.CLICK_ITEM_CONTENT, listVo.get(position).getContent());
			startActivityForResult(intent, 1);
			break;

		case 1:
			
			boolean needUpdateSetting = false;
			
			if(info.getDefaultSmsTemplateId() == listVo.get(position).getId())
			{
				needUpdateSetting = true;
			}
			listVo.remove(position);
			if (needUpdateSetting && listVo.size() > 0) {
				info.updateDefaultSmsTemplate(listVo.get(0).getContent());
				info.updateDefaultSmsTemplateId(listVo.get(0).getId());
			}else if (listVo.size() == 0) {
				info.updateDefaultSmsTemplate(null);
				info.updateDefaultSmsTemplateId(-1);				
			}
			adapter.notifyDataSetChanged();
			Gson gson = new Gson();
			String result = gson.toJson(listVo);
			PrivateFileReadSave.save(BaseParam.SMS_FILENAME, result, context);
			//finish();
			break;
		}
		
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode == 1 ) {
			initData();
		}
	}
}
