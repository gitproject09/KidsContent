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

import java.util.List;

import com.ticonsys.kidscontent.R;
import com.ticonsys.kidscontent.data.FeedItem;

public class FeedListAdapter extends BaseAdapter {

	//@Bind(R.id.thumbnail)
	ImageView thumbnail;

	private static String VIDEO_ID = "iS1g8G_njx8";

	private Activity activity;
	private LayoutInflater inflater;
	private List<FeedItem> feedItems;
	//ImageLoader imageLoader = AppController.getInstance().getImageLoader();

	public FeedListAdapter(Activity activity, List<FeedItem> feedItems) {
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
			convertView = inflater.inflate(R.layout.feed_item, null);


		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);

		thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);

		FeedItem item = feedItems.get(position);

		name.setText(item.getName());
		timestamp.setText(item.getTimeStamp());



		if(item.getStatus().equals("0")){
			Picasso.with(this.activity)
					.load(item.getProfilePic())
					.fit()
					.centerCrop()
					.into(thumbnail);
		} else {
			Picasso.with(this.activity)
					.load(YouTubeThumbnail.getUrlFromVideoId(item.getProfilePic(), Quality.HIGH))
					.fit()
					.centerCrop()
					.into(thumbnail);
		}

		return convertView;
	}

}
