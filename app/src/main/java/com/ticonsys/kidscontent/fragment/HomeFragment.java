package com.ticonsys.kidscontent.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ticonsys.kidscontent.activity.KidsContentActivity;
import com.ticonsys.kidscontent.R;

import com.ticonsys.kidscontent.adapter.ContentListAdapter;

import com.ticonsys.kidscontent.app.AppController;
import com.ticonsys.kidscontent.data.ContentItem;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Dialog dialog;
    private ProgressDialog pDialog;

    String youTubeResponse = "";

    private ListView listView;
    private ContentListAdapter listAdapter;
    private List<ContentItem> feedItems;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View convertView = inflater.inflate(R.layout.fragment_home, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);


        listView = (ListView) convertView.findViewById(R.id.list);

        feedItems = new ArrayList<ContentItem>();

       // uniqueNumber = new ArrayList<String>();

        listAdapter = new ContentListAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);

        if(isNetworkConnected()){
            getPororoYouTubeData();
        } else {
            noInternetConnectionDialog();
        }



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ContentItem item = feedItems.get(position);

                if(youTubeResponse.equals("")){

                } else {
                    Intent intent = new Intent(getActivity(), KidsContentActivity.class);
                    intent.putExtra("content_name", item.getContentName());
                    intent.putExtra("you_tube_response", youTubeResponse);
                    startActivity(intent);
                }



            }
        });
        /*Button pororo = (Button) convertView.findViewById(R.id.pororo);

        Button robo_car_poli = (Button) convertView.findViewById(R.id.robo_car_poli);

        pororo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isNetworkConnected()){

                    Intent home = new Intent(getActivity(), KidsContentActivity.class);
                    home.putExtra("content_type", "pororo");
                    home.putExtra("content_title", "Pororo");
                    startActivity(home);

                } else {

                    noInternetConnectionDialog();
                }


            }
        });

        robo_car_poli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isNetworkConnected()){

                    Intent home = new Intent(getActivity(), KidsContentActivity.class);
                    home.putExtra("content_type", "robocar_poli");
                    home.putExtra("content_title", "Robocar Poli");
                    startActivity(home);

                } else {

                    noInternetConnectionDialog();

                }


            }
        });*/



        return convertView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    private void noInternetConnectionDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("No Internet Connection");

       /* builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.portsip.smartm.willfon")));
                dialog.dismiss();
            }
        });*/

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


    private void getPororoYouTubeData() {

        String tag_string_req = "req_login";

        pDialog.setMessage("Loading");
        showDialog();

        String LOGIN_REQUEST = "https://www.ticonsys.com/pororo/adventure.php";

        StringRequest strReq = new StringRequest(Request.Method.GET, LOGIN_REQUEST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Pororo", "responseData : " + response.toString());
                youTubeResponse = response.trim();
                hideDialog();

                try {

                    JSONObject jObj = new JSONObject(response.trim());

                    String status = jObj.getString("status");
                    String msg = jObj.getString("msg");

                    Log.d("KidsContent", "Message : "+msg);

                    JSONArray feedArray = jObj.getJSONArray("details");

                   // JSONArray contentNameArray = jObj.getJSONArray("content_name");

                    JSONArray arrJson = jObj.getJSONArray("content_name");


                    Log.d("KidsContent", "Length : "+arrJson.length());


                    if (msg.equals("OK")){

                        parseJsonArray(arrJson);

                    } else {

                        Toast.makeText(getActivity(), "No Item Found", Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("KidsContent", "Login Error: " + error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
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

          //  String[] arr = new String[feedArray.length()];
            for(int i = 0; i < feedArray.length(); i++){


                Log.d("KidsContent", "ItemAgain : "+feedArray.getString(i).toString());

                ContentItem item = new ContentItem();

                item.setContentName(feedArray.getString(i).toString());
                feedItems.add(item);
            }

            // notify data changes to list adapater
            listAdapter.notifyDataSetChanged();


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

}
