package com.ustc.prlib.xunfei;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ustc.prlib.util.ButtonColorFilter;
import com.ustc.prlib.util.ComapnyListAdapter;
import com.ustc.prlib.util.JsonParserUtil;
import com.ustc.prlib.util.PrivateFileReadSave;
import com.ustc.prlib.util.SharePreferenceInfo;
import com.ustc.prlib.vo.BaseParam;
import com.ustc.prlib.vo.ExpressVo;
import com.xiang.xunfei.R;


/**
 * @description : TODO
 * @package com.xiang.xunfei
 * @title:CompressTempletAct.java
 * @author : email:xiangyanhui@unitepower.net
 * @date :2013-12-3 ÏÂÎç4:48:43 
 * @version : v4.0
 */
public class CompanyListActivity extends Activity implements OnClickListener{
	
	public static final int EDIT_COMPANY_ITEM = 0;
	public static final int DELETE_COMPANY_ITEM = 1;
	
	private Context context = this;
	private Button btn_add, btn_back; 
	private ListView listView; 
	private ComapnyListAdapter adapter;
	private ArrayList<ExpressVo> listVo;
	SharePreferenceInfo info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.company_list);
		info = new SharePreferenceInfo(context);
		initWidget();
		initData();
	}


	private void initWidget() {

		listView = (ListView)findViewById( R.id.company_listview );
		listView.setDividerHeight(0);

		btn_back = (Button)findViewById( R.id.company_btn_back );
		btn_back.setOnClickListener( this );
		btn_add = (Button)findViewById( R.id.company_btn_add );
		btn_add.setOnClickListener( this );
		ButtonColorFilter.setButtonFocusChanged( btn_back);
		ButtonColorFilter.setButtonFocusChanged( btn_add);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//listVo.get(position).setDefault( true );
				info.updateDefaultCompressContent( listVo.get(position).getContent() );
				info.updateDefaultCompressTempletId( listVo.get(position).getId() );
				adapter.notifyDataSetChanged();
				finish();
			}
		});
		
/*		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				//Toast.makeText(context, "Item in position " + position + " clicked", 0).show();
				return true;
			}
			
		}
		);*/
		
		registerForContextMenu(listView);
		
	}

	@SuppressWarnings("unchecked")
	private void initData() {
		String jsonStr = PrivateFileReadSave.read(BaseParam.COMPRESS_FILENAME, context);
		if ( jsonStr != null ) {
			Type type = new TypeToken<ArrayList<ExpressVo>>(){}.getType();
			listVo = (ArrayList<ExpressVo>) JsonParserUtil.parseJson2ListNoItem(jsonStr, type);
		}
		if ( listVo == null ) {
			listVo = new ArrayList<ExpressVo>();
		}
		adapter = new ComapnyListAdapter(context, listVo);
		listView.setAdapter( adapter);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
		case R.id.company_btn_back:
			finish();
			break;
		case  R.id.company_btn_add :
			Intent intent = new Intent(context, ItemOperationActivity.class);
			intent.putExtra( BaseParam.OPERATION_TYPE, BaseParam.OPERATION_ADD_COMPANY_TEMPLATE);
			startActivityForResult(intent, 1);
			break;
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo info) {
		if(v.getId() == R.id.company_listview) {
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
		//String[] menuItems = getResources().getStringArray(R.array.menu);
		//String menuItemName = menuItems[menuItemIndex];

		//Toast.makeText(context, info.position + " clicked", 0).show();
		int position = menuInfo.position;
		switch (menuItemIndex) {

		case EDIT_COMPANY_ITEM:
			
			Intent intent = new Intent(context, ItemOperationActivity.class);
			intent.putExtra(BaseParam.OPERATION_TYPE,
					BaseParam.OPERATION_EDIT_COMPANY_TEMPLATE);
			intent.putExtra(BaseParam.CLICK_ITEM_POSION, position);
			intent.putExtra(BaseParam.CLICK_ITEM_CONTENT, listVo.get(position).getContent());
			startActivityForResult(intent, 1);
			break;

		case DELETE_COMPANY_ITEM:

			listVo.remove(position);
			if(this.info.getDefaultCompressTempletId() == position && listVo.size()>0)
			{
				info.updateDefaultCompressContent(listVo.get(0).getContent());
				info.updateDefaultCompressTempletId(0);
			}
			adapter.notifyDataSetChanged();
			Gson gson = new Gson();
			String result = gson.toJson( listVo );
			PrivateFileReadSave.save(BaseParam.SMS_FILENAME, result, context);
			finish();
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
