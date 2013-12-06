package com.ustc.prlib.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ustc.prlib.vo.SmsVo;
import com.xiang.xunfei.R;

public class SmsTemplateListAdapter extends BaseAdapter {
	static class ListItemHolder {
		TextView mTextView1;
		TextView mTextView2;
		Button btn;
	}

	private LayoutInflater mInflater;
	private List<SmsVo> items;
	SharePreferenceInfo info;

	public SmsTemplateListAdapter(Context context, List<SmsVo> items) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = items;
		info = new SharePreferenceInfo(context);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.smstemplet_item, null);
			holder = new ListItemHolder();
			holder.mTextView1 = (TextView) convertView
					.findViewById(R.id.smstemplet_list_item_tv_title);
			holder.mTextView2 = (TextView) convertView
					.findViewById(R.id.smstemplet_list_item_tv_company);
			holder.btn = (Button) convertView
					.findViewById(R.id.smstemplet_list_item_btn);

			convertView.setTag(holder);
		} else {
			holder = (ListItemHolder) convertView.getTag();
		}

		SmsVo item = items.get(position);
		if (item != null) {
			holder.mTextView1.setText(item.getContent());
			holder.mTextView2.setText(info.getDefaultCompany());
			if (info.getDefaultSmsTemplateId() == item.getId()) {
				holder.btn.setBackgroundResource(R.drawable.btn_sel);
			} else {
				holder.btn.setBackgroundResource(R.drawable.btn_selno);
			}
		}
		return convertView;
	}
}
