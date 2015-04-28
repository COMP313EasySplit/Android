package com.easysplit.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.EventModel;
import com.easysplit.base.ExpenseModel;
import com.easysplit.net.EasySplitRequest;
import com.easysplit.net.Parse;
import com.example.easysplit.R;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class EventExpensesFragment extends Fragment{

	private Context thiscontext;
	private View fragment_v;
	private ArrayList<HashMap<String, String>> exlist;
	private int eventId;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.event_expense_fragment, container, false);
		//TextView textview = (TextView) view.findViewById(R.id.expense_tabtext);
		//textview.setText("tab 2");
		
		if(getResources().getBoolean(R.bool.portrait_only)){
	        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    }
		else 
		{
			getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);			
		} 
		Log.v("Type 1","Creating Event Expenses Fragment");
		
		String eId = getArguments().getString("eventId");
		Log.v("Type 1", "Loading event expenses: " + eId);
		this.eventId = Integer.parseInt(eId);

		
		exlist = new ArrayList<HashMap<String, String>>();
		thiscontext = getActivity().getApplicationContext();
		fragment_v=view;
        
		loadEventExpense = new LoadEventExpense();
		loadEventExpense.execute(eventId);
		
		return view;
	}
	
    private LoadEventExpense loadEventExpense;
    private class LoadEventExpense extends AsyncTask<Integer, Void, String> {
        @Override
		protected String doInBackground(Integer... params) {
			String result = null;
			EasySplitRequest request = new EasySplitRequest(thiscontext);

    		//final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
        	int eventId = params[0];
			
			try {
				result = request.getEventExpenses(eventId);	// retrieve event list by host id, json string of eventModel is returned
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
        	// parse JSON object
        	ArrayList<ExpenseModel> expenseList = Parse.getEventExpensesList(result);
        	// save to global variable, for re-use
    		final EasySplitGlobal esGlobal = (EasySplitGlobal) getActivity().getApplicationContext();
    		esGlobal.setExpenseList(expenseList);
    		
        	// generate adapter for list view
        	for (ExpenseModel expense : expenseList)
        	{
        		HashMap<String, String> map = new HashMap<String, String>();
        		map.put("ExpenseId", Integer.toString(expense.ExpenseID));
                map.put("txtEELVDEventName", expense.Name);
                map.put("txtEELVDStatus", expense.Place);
                map.put("txtEELVDamount", "$" + Double.toString(expense.Amount) );
                exlist.add(map);
        	}
    		
    		// bind to listview
            ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(),
            		exlist,
            		R.layout.eventexpense_listview_details,
            		new String[]{"txtEELVDEventName","txtEELVDStatus","txtEELVDamount"},
            		new int[]{R.id.txtEELVDEventName, R.id.txtEELVDStatus, R.id.txtEELVDamount});
            ListView eventExpenseList = (ListView) fragment_v.findViewById(R.id.lvEEFDisplayExpenses);
            eventExpenseList.setAdapter(adapter);
            
            // click to show expense sharing detail
            eventExpenseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                	//Log.v("Type 1", "Clicked");
                   int expenseId = Integer.parseInt(exlist.get(position).get("ExpenseId"));
                   
                   Intent viewExpensetIntent = new Intent(getActivity().getApplicationContext(), com.easysplit.mainview.ViewExpense.class);
                   viewExpensetIntent.putExtra("expenseId", Integer.toString(expenseId)); //Optional parameters
                   startActivity(viewExpensetIntent);
                   
                }
            });
       }
    }
}
