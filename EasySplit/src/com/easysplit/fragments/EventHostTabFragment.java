package com.easysplit.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.easysplit.fragments.EventHostTabFragment;
import com.example.easysplit.R;

public class EventHostTabFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.host_tab_fragment, container, false);
		TextView textview = (TextView) view.findViewById(R.id.tabtextview);
		textview.setText("tab 1");
		
		return view;
	}

}
