package com.easysplit.fragments;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.EventModel;
import com.easysplit.base.ParticipantModel;
import com.easysplit.fragments.EventHostTabFragment;
import com.easysplit.mainview.SettleParticipants;
import com.easysplit.net.EasySplitRequest;
import com.easysplit.net.Parse;
import com.example.easysplit.R;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class EventDetailsFragment extends Fragment{

	private Context thiscontext;
	private View fragment_v;
	private ArrayList<HashMap<String, String>> plist;
	private int eventId;
	private String source;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_detail_fragment, container, false);
		//TextView textview = (TextView) view.findViewById(R.id.detail_tabtext);
		//textview.setText("tab 1");
		plist = new ArrayList<HashMap<String, String>>();
		fragment_v=view;
		thiscontext = getActivity().getApplicationContext();
		
		Log.v("Type 1","Creating Event Details Fragment");

		this.eventId = Integer.parseInt(getArguments().getString("eventId"));
		this.source = getArguments().getString("source");	// host or participant
		Log.v("Type 1", "Loading event: " + eventId + " Source: " + source); 		
		
	    Button btnSettle = (Button) view.findViewById(R.id.btnEDSettleParticipants);
	    btnSettle.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	            Intent intent = new Intent(getActivity(), SettleParticipants.class);
	            startActivity(intent);
	        }
	    });
	    
	    // display Name, Date, Budget
		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
		ArrayList<EventModel> eventList = esGlobal.getEventList(source);
	    for ( EventModel event : eventList)
	    {
	    	if (eventId == event.EventId)
	    	{
	    		TextView txtEDDisplayHostName = (TextView) view.findViewById(R.id.txtEDDisplayHostName);
	    		txtEDDisplayHostName.setText(event.Name);
	    		TextView txtEDDisplayDateCreated = (TextView) view.findViewById(R.id.txtEDDisplayDateCreated);
	    		txtEDDisplayDateCreated.setText(event.DateCreated);
	    		TextView txtEDDisplayBudget = (TextView) view.findViewById(R.id.txtEDDisplayBudget);
	    		txtEDDisplayBudget.setText(Double.toString(event.Budget));
	    		TextView txtEDDisplayTotal = (TextView) view.findViewById(R.id.txtEDDisplayTotal);
	    		txtEDDisplayTotal.setText(Double.toString(event.TotalSpend));
	    		break;
	    	}
	    }
	    

		loadParticipants = new LoadParticipants();
		loadParticipants.execute(eventId);

		return view;
	}
	
	
	   private LoadParticipants loadParticipants;
	    private class LoadParticipants extends AsyncTask<Integer, Void, String> {
	        @Override
			protected String doInBackground(Integer... params) {
				String result = null;
				EasySplitRequest request = new EasySplitRequest(thiscontext);
				int eventId = params[0];

			
				try {
					result = request.getEventParticipants(eventId);	// retrieve event list by host id, json string of eventModel is returned
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return result;       
		}
	        
	        // onPostExecute displays the results of the AsyncTask.
	        @Override
	        protected void onPostExecute(String result) {
	        	
	        	ArrayList<ParticipantModel> participantList = Parse.getEventParticipantsList(result);
	        	// save to global variable, for re-use
	    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
	    		esGlobal.setParticipantList(participantList);
	        	//Log.v("Type 1"," Number of Events found: " + eventList.size());

	    		for (ParticipantModel participant : participantList)
	        	{
	        		HashMap<String, String> map = new HashMap<String, String>();
	        		map.put("UserId", Integer.toString(participant.Userid));
	                map.put("txtEPLVDFullname", participant.Firstname + " " + participant.Lastname);
	                map.put("txtEPLVDEmail", participant.Email);
	                plist.add(map);
	        	}
	    		
	    		// bind to list view
	            ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
	            		plist,
	            		R.layout.event_detail_participant_listview_detail,
	            		new String[]{"txtEPLVDFullname","txtEPLVDEmail"},
	            		new int[]{R.id.txtEPLVDFullname, R.id.txtEPLVDEmail});
	            ListView hostEventList = (ListView) fragment_v.findViewById(R.id.lvEDPartDetails);
	            hostEventList.setAdapter(adapter);
	            
	            
	            /*
	            hostEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	                @Override
	                public void onItemClick(AdapterView<?> parent, View view,
	                                        int position, long id) {
	                	//Log.v("Type 1", "Clicked");
	                   int eventId = Integer.parseInt(plist.get(position).get("EventId"));
	                   
	                   Intent viewEventIntent = new Intent(getActivity().getApplicationContext(), com.easysplit.mainview.ViewEvent.class);
	                   viewEventIntent.putExtra("eventId", Integer.toString(eventId)); //Optional parameters
	                   startActivity(viewEventIntent);
	                   
	                }
	            });
	            */
	       }
	    }
}
