package com.ustc.prlib.xunfei;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ustc.prlib.util.ButtonColorFilter;
import com.ustc.prlib.util.JsonParserUtil;
import com.ustc.prlib.util.PrivateFileReadSave;
import com.ustc.prlib.util.SharePreferenceInfo;
import com.ustc.prlib.vo.BaseParam;
import com.ustc.prlib.vo.ExpressVo;
import com.ustc.prlib.vo.SmsVo;
import com.xiang.xunfei.R;

/**
 * @description : 模板增删改
 * @package com.xiang.xunfei
 * @title:AddTempletActivity.java
 * @author : email:xiangyanhui@unitepower.net
 * @date :2013-12-3 下午3:46:33
 * @version : v4.0
 */
public class ItemOperationActivity extends Activity implements OnClickListener {
	private Context context = this;
	private Button btn_save, btn_back;
	private EditText et;
	private TextView tv_title;
	private SharePreferenceInfo info;
	private int addType;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_item);
		addType = getIntent().getIntExtra(BaseParam.OPERATION_TYPE, -1);
		info = new SharePreferenceInfo(context);
		initWidget();
	}

	private void initWidget() {
		btn_save = (Button) findViewById(R.id.addtemplet_btn_save);
		btn_back = (Button) findViewById(R.id.addtemplet_btn_back);
		et = (EditText) findViewById(R.id.addtemplet_et);
		tv_title = (TextView) findViewById(R.id.addtemplet_tv_title);
		ButtonColorFilter.setButtonFocusChanged(btn_save);
		ButtonColorFilter.setButtonFocusChanged(btn_back);

		btn_save.setOnClickListener(this);
		btn_back.setOnClickListener(this);

		switch (addType) {
		case BaseParam.OPERATION_ADD_SMS_TEMPLATE:
			tv_title.setText("添加短信模板");
			et.setHint("请输入短信内容");
			break;
		case BaseParam.OPERATION_ADD_COMPANY_TEMPLATE:
			tv_title.setText("添加快递公司");
			et.setHint("请输入快递名称");
			break;
		case BaseParam.OPERATION_EDIT_SMS_TEMPLATE:
			tv_title.setText("修改短信模板");
			et.setText(getIntent().getExtras()
					.get(BaseParam.CLICK_ITEM_CONTENT).toString());
			position = (Integer) getIntent().getExtras().get(
					BaseParam.CLICK_ITEM_POSION);
		case BaseParam.OPERATION_EDIT_COMPANY_TEMPLATE:
			tv_title.setText("修改快递公司");
			et.setText(getIntent().getExtras()
					.get(BaseParam.CLICK_ITEM_CONTENT).toString());
			position = (Integer) getIntent().getExtras().get(
					BaseParam.CLICK_ITEM_POSION);
		}
	}

	@SuppressWarnings("unchecked")
	private void addSmsTemplate(String content) {
		int currentId = 0;
		ArrayList<SmsVo> listVo = null;
		String temp = PrivateFileReadSave.read(BaseParam.SMS_FILENAME, context);
		if (temp != null) {
			Type type = new TypeToken<ArrayList<SmsVo>>() {
			}.getType();
			listVo = (ArrayList<SmsVo>) JsonParserUtil.parseJson2ListNoItem(
					temp, type);

		}
		if (listVo == null) {
			listVo = new ArrayList<SmsVo>();
		} else if (listVo.size() > 0) {
			currentId = listVo.get(listVo.size() - 1).getId() + 1;
		}

		SmsVo vo = new SmsVo();
		vo.setContent(content);
		vo.setId(currentId);
		listVo.add(vo);

		Gson gson = new Gson();
		String result = gson.toJson(listVo);
		PrivateFileReadSave.save(BaseParam.SMS_FILENAME, result, context);
		info.updateDefaultSmsTemplateId(currentId);
		info.updateDefaultSmsTemplate(content);
		Toast.makeText(context, "添加成功", 0).show();
		setResult(1);
		finish();
	}

	@SuppressWarnings("unchecked")
	private void editSmsTemplate(String content) {

		ArrayList<SmsVo> listVo = null;
		String temp = PrivateFileReadSave.read(BaseParam.SMS_FILENAME, context);
		if (temp != null) {
			Type type = new TypeToken<ArrayList<SmsVo>>() {
			}.getType();
			listVo = (ArrayList<SmsVo>) JsonParserUtil.parseJson2ListNoItem(
					temp, type);

		}
		if (listVo == null || listVo.size() < position + 1) {
			return;
		}
		listVo.get(position).setContent(content);

		Gson gson = new Gson();
		String result = gson.toJson(listVo);
		PrivateFileReadSave.save(BaseParam.SMS_FILENAME, result, context);
		info.updateDefaultSmsTemplateId(listVo.get(position).getId());
		info.updateDefaultSmsTemplate(content);
		Toast.makeText(context, "修改成功", 0).show();
		setResult(1);
		finish();
	}

	@SuppressWarnings("unchecked")
	private void addCompany(String content) {
		int currentId = 0;
		ArrayList<ExpressVo> listVo = null;
		String temp = PrivateFileReadSave.read(BaseParam.EXPRESS_FILENAME,
				context);
		if (temp != null) {
			Type type = new TypeToken<ArrayList<ExpressVo>>() {
			}.getType();
			listVo = (ArrayList<ExpressVo>) JsonParserUtil
					.parseJson2ListNoItem(temp, type);
		}

		if (listVo == null) {
			listVo = new ArrayList<ExpressVo>();
		} else if (listVo.size() > 0) {
			currentId = listVo.get(listVo.size() - 1).getId() + 1;
		}

		ExpressVo vo = new ExpressVo();
		vo.setContent(content);
		vo.setId(currentId);
		listVo.add(vo);

		Gson gson = new Gson();
		String result = gson.toJson(listVo);
		PrivateFileReadSave.save(BaseParam.EXPRESS_FILENAME, result, context);
		info.updateDefaultCompanyId(currentId);
		info.updateDefaultCompany(content);
		Toast.makeText(context, "添加成功", 0).show();
		setResult(1);
		finish();
	}

	@SuppressWarnings("unchecked")
	private void editCompany(String content) {
		ArrayList<ExpressVo> listVo = null;
		String temp = PrivateFileReadSave.read(BaseParam.EXPRESS_FILENAME,
				context);
		if (temp != null) {
			Type type = new TypeToken<ArrayList<ExpressVo>>() {
			}.getType();
			listVo = (ArrayList<ExpressVo>) JsonParserUtil
					.parseJson2ListNoItem(temp, type);
		}

		if (listVo == null || listVo.size() < position + 1) {
			return;
		}
		listVo.get(position).setContent(content);

		Gson gson = new Gson();
		String result = gson.toJson(listVo);
		PrivateFileReadSave.save(BaseParam.EXPRESS_FILENAME, result, context);

		info.updateDefaultCompanyId(listVo.get(position).getId());
		info.updateDefaultCompany(content);

		Toast.makeText(context, "修改成功", 0).show();
		setResult(1);
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addtemplet_btn_save:
			switch (addType) {
			case BaseParam.OPERATION_ADD_SMS_TEMPLATE:
				if (!"".equals(et.getText().toString())
						&& et.getText().toString() != null) {
					addSmsTemplate(et.getText().toString());
				} else {
					Toast.makeText(context, "模板内容不能为空", 0).show();
				}
				break;
			case BaseParam.OPERATION_ADD_COMPANY_TEMPLATE:
				if (!"".equals(et.getText().toString())
						&& et.getText().toString() != null) {
					addCompany(et.getText().toString());
				} else {
					Toast.makeText(context, "快递名称不能为空", 0).show();
				}
				break;
			case BaseParam.OPERATION_EDIT_SMS_TEMPLATE:
				if (!"".equals(et.getText().toString())
						&& et.getText().toString() != null) {
					editSmsTemplate(et.getText().toString());
				} else {
					Toast.makeText(context, "模板内容不能为空", 0).show();
				}
				break;

			case BaseParam.OPERATION_EDIT_COMPANY_TEMPLATE:
				if (!"".equals(et.getText().toString())
						&& et.getText().toString() != null) {
					editCompany(et.getText().toString());
				} else {
					Toast.makeText(context, "模板内容不能为空", 0).show();
				}
				break;

			}
			break;
		case R.id.addtemplet_btn_back:
			finish();
			break;
		}
	}
}
