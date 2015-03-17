package com.easysplit.fragments;

import com.example.easysplit.R;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventDetailsFragment extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_detail_fragment, container, false);
		//TextView textview = (TextView) view.findViewById(R.id.detail_tabtext);
		//textview.setText("tab 1");
		
		Log.v("Type 1","Creating Event Details Fragment");

		String eventId = getArguments().getString("eventId");  
		Log.v("Type 1", "Loading event: " + eventId);		
		
		return view;
	}
}
