package com.easysplit.fragments;

import com.example.easysplit.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class EventSummaryFragment extends Fragment{

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_summary_fragment, container, false);
		
		TableLayout t1 = (TableLayout) view.findViewById(R.id.table);
		
		TableRow tr = new TableRow(getActivity());
		
		 TextView tv1 = new TextView(getActivity());
	        tv1.setText("Paid");
	        tr.addView(tv1);

		    //for (int i = 0; i < 30; i++) {
	        
		        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		        t1.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

	
		
		
	//}
		    return view;
	}}
