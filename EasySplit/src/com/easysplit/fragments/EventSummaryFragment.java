package com.easysplit.fragments;

import com.example.easysplit.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventSummaryFragment extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_summary_fragment, container, false);
		//TextView textview = (TextView) view.findViewById(R.id.summary_tabtext);
		//textview.setText("tab 3");
		
		return view;
	}
}
