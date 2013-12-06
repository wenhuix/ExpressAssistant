package com.ustc.prlib.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.ustc.prlib.vo.BaseParam;
import com.ustc.prlib.vo.PhoneVo;
import com.xiang.xunfei.R;

public class MainListAdapter extends BaseAdapter {
	static class ListItemHolder {
		RelativeLayout rel_root;
		TextView mTextView;
	}

	private LayoutInflater mInflater;
	private List<PhoneVo> items;

	public MainListAdapter(Context context, List<PhoneVo> items) {
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = items;
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
			convertView = mInflater.inflate(R.layout.main_list_item, null);
			holder = new ListItemHolder();
			holder.rel_root = (RelativeLayout) convertView
					.findViewById(R.id.main_list_item_rel_root);
			holder.mTextView = (TextView) convertView
					.findViewById(R.id.main_list_item_tv_title);
			convertView.setTag(holder);
		} else {
			holder = (ListItemHolder) convertView.getTag();
		}

		PhoneVo item = items.get(position);
		if (item != null) {
			holder.mTextView.setText(item.getContent());
			if (item.getPositon() == BaseParam.ITEM_POSITON_LEFT) {
				holder.mTextView.setBackgroundResource(R.drawable.leftbg);
				RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				holder.mTextView.setLayoutParams(param);
			} else {
				holder.mTextView.setBackgroundResource(R.drawable.rightbg);
				RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				param.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				holder.mTextView.setLayoutParams(param);
			}
		}
		return convertView;
	}
}
