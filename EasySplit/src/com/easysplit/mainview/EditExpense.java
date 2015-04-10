package com.easysplit.mainview;

import java.util.ArrayList;
import java.util.HashMap;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.ExpenseModel;
import com.easysplit.base.ExpenseShareModel;
import com.easysplit.base.ParticipantModel;
import com.example.easysplit.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class EditExpense extends Activity {

	int expenseId;
	ArrayList<ExpenseShareModel> expenseParticipantList;
	ListView lvEExSelectParticipants;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_expense);

		
		lvEExSelectParticipants = (ListView) findViewById(R.id.lvEExSelectParticipants);
		expenseParticipantList = new ArrayList<ExpenseShareModel>();
		
		Intent intent = getIntent();
		expenseId = Integer.parseInt(intent.getStringExtra("expenseId"));
		
		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();
		ArrayList<ExpenseModel> expenseList = esGlobal.getExpenseList();
		ArrayList<ParticipantModel> participantList = esGlobal.getParticipantList();
		
	    for ( ExpenseModel expense : expenseList)
	    {
			if (expense.ExpenseID == expenseId)
			{
				TextView txtVExDisplayName = (TextView) findViewById(R.id.txtEExDisplayName);
				txtVExDisplayName.setText(expense.Name);
				
				TextView txtVExDisplayDateCreated = (TextView) findViewById(R.id.txtEExDisplayDateCreated);
				txtVExDisplayDateCreated.setText(expense.DateCreated);
				TextView txtVExDisplayAmount = (TextView) findViewById(R.id.etEExAmount);
				txtVExDisplayAmount.setText(Double.toString(expense.Amount));
				TextView txtVExDisplayPlace = (TextView) findViewById(R.id.etEExPlace);
				txtVExDisplayPlace.setText(expense.Place);
				
				TextView txtVExDisplayPayer = (TextView) findViewById(R.id.txtEExDisplayPayer);
				txtVExDisplayPayer.setText(expense.OriginalPayer.Firstname + " " + expense.OriginalPayer.Lastname);
				
				for (ExpenseModel.Share share : expense.Shares)
				{
					for (ParticipantModel participant : participantList)
					{
						if (participant.Userid == share.UserId)
						{
							ExpenseShareModel expenseParticipant = new ExpenseShareModel();
							expenseParticipant.ExpenseID = expenseId;
							expenseParticipant.Firstname = participant.Firstname;
							expenseParticipant.Lastname = participant.Lastname;
							expenseParticipant.Email = participant.Email;
							expenseParticipant.UserId = share.UserId;
							expenseParticipant.SharedAmount = share.Amount;	//  to add 
							
							expenseParticipantList.add(expenseParticipant);	// add participant to list
						}
					}
				}
				refreshParticipantListView();
			}		
	    }
	}

		private void refreshParticipantListView()
		{
			ArrayList<HashMap<String, String>> plist = new ArrayList<HashMap<String, String>>();
			for (ExpenseShareModel expenseParticipant : expenseParticipantList)
	    	{
	    		HashMap<String, String> map = new HashMap<String, String>();
	    		map.put("ExpenseID", Integer.toString(expenseParticipant.ExpenseID));
	    		map.put("UserId", Integer.toString(expenseParticipant.UserId));
	            map.put("txtEPLVDFullname", expenseParticipant.Firstname + " " + expenseParticipant.Lastname);
	            map.put("txtEPLVDEmail", expenseParticipant.Email);
	            map.put("txtEPLVDAmount", Double.toString(expenseParticipant.SharedAmount));
	            plist.add(map);
	    	}
			
			// bind to list view
	        ListAdapter adapter = new SimpleAdapter(getApplicationContext(),
	        		plist,
	        		R.layout.event_expense_detail_participant_listview_detail,
	        		new String[]{"txtEPLVDFullname","txtEPLVDEmail","txtEPLVDAmount"},
	        		new int[]{R.id.txtEPLVDFullname, R.id.txtEPLVDEmail,R.id.txtEPLVDAmount});
	        lvEExSelectParticipants.setAdapter(adapter);
		}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case R.id.action_cancel:
			Intent homeIntentEE = new Intent(EditExpense.this, MainActivity.class);
			startActivity(homeIntentEE);
			return true;
		case R.id.action_delete:
			// delete();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
