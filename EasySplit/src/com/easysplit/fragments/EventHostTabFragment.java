package com.easysplit.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.EventModel;
import com.easysplit.fragments.EventHostTabFragment;
import com.easysplit.mainview.NewEvent;
import com.easysplit.net.EasySplitRequest;
import com.easysplit.net.Parse;
import com.example.easysplit.R;

public class EventHostTabFragment extends Fragment {
	private Context thiscontext;
	private View fragment_v;
	private ArrayList<HashMap<String, String>> elist;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.host_tab_fragment, container, false);
		TextView textview = (TextView) view.findViewById(R.id.tabtextview);
		textview.setText("My Events");
		
		elist = new ArrayList<HashMap<String, String>>();
		
		thiscontext = getActivity().getApplicationContext();
		
		fragment_v=view;
        
		loadHostEvent = new LoadHostEvent();
		loadHostEvent.execute();
		
		return view;
	}
    private LoadHostEvent loadHostEvent;
    private class LoadHostEvent extends AsyncTask<String, Void, String> {
        @Override
		protected String doInBackground(String... params) {
			String result = null;
			EasySplitRequest request = new EasySplitRequest(thiscontext);

    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
        	int hostID = esGlobal.getHostID();			
			
			try {
				result = request.getEvent(hostID);	// retrieve event list by host id, json string of eventModel is returned
			} catch (IOException e) {
				e.printStackTrace();
			} catch (HttpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return result;       }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
        	
        	ArrayList<EventModel> eventList = Parse.getEventList(result);
    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
    		esGlobal.setEventList(eventList);        	
        	//Log.v("Type 1"," Number of Events found: " + eventList.size());
        	
        	for (EventModel event : eventList)
        	{
        		HashMap<String, String> map = new HashMap<String, String>();
        		map.put("EventId", Integer.toString(event.EventId));
                map.put("txtELVDEventName", event.Name);
                map.put("txtELVDStatus", event.Status);
                map.put("txtELVDAmount", Double.toString( event.Budget) );
                elist.add(map);
        	}
        	
            ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
            		elist,
            		R.layout.eventhost_listview_details,
            		new String[]{"txtELVDEventName","txtELVDStatus","txtELVDAmount"},
            		new int[]{R.id.txtELVDEventName, R.id.txtELVDStatus, R.id.txtELVDAmount});
            ListView hostEventList = (ListView) fragment_v.findViewById(R.id.hostEventList);
            hostEventList.setAdapter(adapter);
            
            
            hostEventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                	//Log.v("Type 1", "Clicked");
                   int eventId = Integer.parseInt(elist.get(position).get("EventId"));
                   
                   Intent viewEventIntent = new Intent(getActivity().getApplicationContext(), com.easysplit.mainview.ViewEvent.class);
                   viewEventIntent.putExtra("eventId", Integer.toString(eventId)); //Optional parameters
                   startActivity(viewEventIntent);
                   
                }
            });
       }
    }
}
