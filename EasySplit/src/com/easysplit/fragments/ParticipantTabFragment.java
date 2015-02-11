package com.easysplit.fragments;

import com.example.easysplit.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ParticipantTabFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.participant_tab_fragment, container, false);
		//TextView textview = (TextView) view.findViewById(R.id.tabtextview2);
		//textview.setText("tab 2");
		return view;
	}

}
