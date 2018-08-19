package com.ticonsys.kidscontent.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thefinestartist.ytpa.enums.Quality;
import com.thefinestartist.ytpa.utils.YouTubeThumbnail;
import com.ticonsys.kidscontent.R;
import com.ticonsys.kidscontent.data.ContentItem;
import com.ticonsys.kidscontent.data.FeedItem;

import java.util.List;

public class ContentListAdapter extends BaseAdapter {



	private Activity activity;
	private LayoutInflater inflater;
	private List<ContentItem> feedItems;

	public ContentListAdapter(Activity activity, List<ContentItem> feedItems) {
		this.activity = activity;
		this.feedItems = feedItems;
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.listview_row, null);


		TextView name = (TextView) convertView.findViewById(R.id.lists_text);


		ContentItem item = feedItems.get(position);

		name.setText(item.getContentName());

		return convertView;
	}

}
