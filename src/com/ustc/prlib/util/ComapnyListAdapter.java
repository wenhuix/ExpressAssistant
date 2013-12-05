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

import com.ustc.prlib.vo.ExpressVo;
import com.xiang.xunfei.R;


public class ComapnyListAdapter extends BaseAdapter {
	static class ListItemHolder {
		TextView mTextView;
		Button btn;
	}

	private LayoutInflater mInflater;
	private List<ExpressVo> items;
	SharePreferenceInfo info;

	public ComapnyListAdapter(Context context, List<ExpressVo> items) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.items = items;
		info = new SharePreferenceInfo(context);
	}

	@Override
	public int getCount() {
		return  items.size();
	}

	@Override
	public Object getItem(int position) {
		return  items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.company_item, null);
			holder = new ListItemHolder();
			holder.mTextView = (TextView) convertView.findViewById(R.id.compresstemplet_list_item_tv_title);
			holder.btn = (Button) convertView.findViewById(R.id.compresstemplet_list_item_btn);

			convertView.setTag(holder);
		} else {
			holder = (ListItemHolder) convertView.getTag();
		}

		ExpressVo item = items.get(position);
		if (item != null) {
			 holder.mTextView.setText( item.getContent() );
			 if (  info.getDefaultCompressTempletId() == item.getId() ) {
				 holder.btn.setBackgroundResource( R.drawable.btn_sel );
			 } else {
				 holder.btn.setBackgroundResource( R.drawable.btn_selno );
			 }
		}
		return convertView;
	}
}
