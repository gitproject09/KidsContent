package com.ticonsys.kidscontent.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubePlayer;
import com.thefinestartist.ytpa.YouTubePlayerActivity;
import com.thefinestartist.ytpa.enums.Orientation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ticonsys.kidscontent.R;
import com.ticonsys.kidscontent.adapter.FeedListAdapter;
import com.ticonsys.kidscontent.app.AppController;
import com.ticonsys.kidscontent.data.FeedItem;

public class KidsContentActivity extends AppCompatActivity {

	//@Bind(R.id.toolbar)
	//Toolbar toolbar;

	YouTubePlayer.PlayerStyle playerStyle;
	Orientation orientation;
	boolean showAudioUi;
	boolean showFadeAnim;
	private static String VIDEO_ID = "iS1g8G_njx8";
	private ProgressDialog pDialog;
	Dialog dialog;

	String youTubeResponse = "";
	String contentTitle = "";

	private static final String TAG = KidsContentActivity.class.getSimpleName();
	private ListView listView;
	private FeedListAdapter listAdapter;
	private List<FeedItem> feedItems;
	private List<String> uniqueNumber;
	private String URL_FEED = "http://api.androidhive.info/feed/feed.json";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_kids_content);
		/*ButterKnife.bind(this);
		setSupportActionBar(toolbar);*/
	//	getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_back);
		TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayShowTitleEnabled(false);


		Intent content = getIntent();
		contentTitle = content.getStringExtra("content_name");

		mTitle.setText(contentTitle);

		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);

		//playerStyle = YouTubePlayer.PlayerStyle.DEFAULT;
		playerStyle = YouTubePlayer.PlayerStyle.MINIMAL;
		orientation = Orientation.ONLY_LANDSCAPE;
		showAudioUi = true;
		showFadeAnim = true;


		listView = (ListView) findViewById(R.id.list);

		feedItems = new ArrayList<FeedItem>();

		uniqueNumber = new ArrayList<String>();

		listAdapter = new FeedListAdapter(this, feedItems);
		listView.setAdapter(listAdapter);

		youTubeResponse = content.getStringExtra("you_tube_response");

		parseYouTubeResponse(youTubeResponse);

		
		// These two lines not needed,
		// just to get the look of facebook (changing background color & hiding the icon)
		/*getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3b5998")));
		getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));*/

		// We first check for cached request
		/*Cache cache = AppController.getInstance().getRequestQueue().getCache();
		Entry entry = cache.get(URL_FEED);
		if (entry != null) {
			// fetch the data from cache
			try {
				String data = new String(entry.data, "UTF-8");
				try {
					parseJsonFeed(new JSONObject(data));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else {
			// making fresh volley request and getting json
			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET, URL_FEED, null, new Response.Listener<JSONObject>() {

						@Override
						public void onResponse(JSONObject response) {
							VolleyLog.d(TAG, "Response: " + response.toString());
							if (response != null) {
								parseJsonFeed(response);
							}
						}
					}, new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							VolleyLog.d(TAG, "Error: " + error.getMessage());
						}
					});

			// Adding request to volley request queue
			AppController.getInstance().addToRequestQueue(jsonReq);
		}*/
		//getDynamicData();

		//getPororoYouTubeData();


		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if(isNetworkConnected()){
					getPororoYouTubeData();
				} else {
					noInternetConnectionDialog();
				}

				FeedItem item = feedItems.get(position);

				Log.d("Pororo", "Name : " + item.getStatus());

				if(item.getStatus().equals("0")){
					Intent intent = new Intent(getApplicationContext(), VideoViewActivity.class);
					intent.putExtra("video_url", item.getUrl());
					startActivity(intent);
				} else {

					Intent intent = new Intent(getApplicationContext(), YouTubePlayerActivity.class);
					intent.putExtra(YouTubePlayerActivity.EXTRA_VIDEO_ID, item.getUrl());
					intent.putExtra(YouTubePlayerActivity.EXTRA_PLAYER_STYLE, playerStyle);
					intent.putExtra(YouTubePlayerActivity.EXTRA_ORIENTATION, orientation);
					intent.putExtra(YouTubePlayerActivity.EXTRA_SHOW_AUDIO_UI, showAudioUi);
					intent.putExtra(YouTubePlayerActivity.EXTRA_HANDLE_ERROR, true);
					if (showFadeAnim) {
						intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.fade_in);
						intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.fade_out);
					} else {
						intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_ENTER, R.anim.modal_close_enter);
						intent.putExtra(YouTubePlayerActivity.EXTRA_ANIM_EXIT, R.anim.modal_close_exit);
					}

					startActivity(intent);

				}




			}
		});

	}

	/*public static void countUnique(List<String> list){
		Collections.sort(list);
		Set<String> uniqueNumbers = new HashSet<>(list);

		System.out.println(uniqueNumbers);
		Log.d("KidsContent", "Array : "+uniqueNumbers.size());
		//ArrayList<String> values = ... //Your values
		//HashSet<String> uniqueValues = new HashSet<>(values);
		for (String list_no : uniqueNumbers) {
			//Do something
			Log.d("KidsContent", "Value : "+list_no);
		}



	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == android.R.id.home) {
			// finish the activity
			onBackPressed();
			return true;
		} else if (id == R.id.action_search) {
			// Toast.makeText(getApplicationContext(), "Logout user!", Toast.LENGTH_LONG).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void parseYouTubeResponse(String response) {

		try {

			JSONObject jObj = new JSONObject(response);

			String status = jObj.getString("status");
			String msg = jObj.getString("msg");

			Log.d("Pororo", "Message : " + msg);

			JSONArray feedArray = jObj.getJSONArray("details");

			Log.d("Pororo", "Length : " + feedArray.length());


			if (msg.equals("OK")) {

				parseJsonArray(feedArray);

			} else {

				Toast.makeText(getApplicationContext(), "No Item Found", Toast.LENGTH_LONG).show();
			}


		} catch (Exception e) {
			// JSON error
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}



	private void getPororoYouTubeData() {

		String tag_string_req = "req_login";

		pDialog.setMessage("Loading");
		showDialog();

		String LOGIN_REQUEST = "https://www.ticonsys.com/pororo/adventure.php";

		StringRequest strReq = new StringRequest(Request.Method.GET, LOGIN_REQUEST, new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				Log.d("Pororo", "responseData : " + response.toString());
				hideDialog();

				try {

					JSONObject jObj = new JSONObject(response.trim());

					String status = jObj.getString("status");
					String msg = jObj.getString("msg");

					Log.d("Pororo", "Message : "+msg);

					JSONArray feedArray = jObj.getJSONArray("details");

					//JSONArray contentNameArray = jObj.getJSONArray("content_name");

					Log.d("Pororo", "Length : "+feedArray.length());


					if (msg.equals("OK")){

						parseJsonArray(feedArray);

					} else {

						Toast.makeText(getApplicationContext(), "No Item Found", Toast.LENGTH_LONG).show();
					}


				} catch (Exception e) {
					// JSON error
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
				}

			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e(TAG, "Login Error: " + error.getMessage());
				Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
				hideDialog();
			}
		}) ;


		int socketTimeout = 60000;
		RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		strReq.setRetryPolicy(policy);

		AppController.getInstance().addToRequestQueue(strReq, " ");

	}

	private void parseJsonArray(JSONArray feedArray) {

		try {

			for (int i = 0; i < feedArray.length(); i++) {
				JSONObject feedObj = (JSONObject) feedArray.get(i);

				FeedItem item = new FeedItem();

				item.setId(feedObj.getInt("id"));
				item.setName(feedObj.getString("title"));

				item.setStatus(feedObj.getString("youtube"));

				if(feedObj.getString("youtube").equals("0")){
					item.setProfilePic(feedObj.getString("icon"));
				} else {
					item.setProfilePic(feedObj.getString("url"));
				}

				item.setUrl(feedObj.getString("url"));
				item.setTimeStamp(feedObj.getString("desc"));

				//uniqueNumber.add(feedObj.getString("category"));


				if(feedObj.getString("category").equals(contentTitle)){
					feedItems.add(item);
				}

			}

			// notify data changes to list adapater
			listAdapter.notifyDataSetChanged();

			//countUnique(uniqueNumber);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void showDialog() {
		if (!pDialog.isShowing())
			pDialog.show();
	}

	private void hideDialog() {
		if (pDialog.isShowing())
			pDialog.dismiss();
	}

	private void noInternetConnectionDialog(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("No Internet Connection");

		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// background.start();
				dialog.dismiss();
			}
		});

		builder.setCancelable(false);
		dialog = builder.show();
	}

	private boolean isNetworkConnected() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		return cm.getActiveNetworkInfo() != null;
	}

}
