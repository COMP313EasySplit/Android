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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ViewExpense extends Activity {

	ArrayList<ExpenseShareModel> expenseParticipantList;
	ListView lvVExSelectParticipants;
	int expenseId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_expense);
		
		lvVExSelectParticipants = (ListView) findViewById(R.id.lvVExSelectParticipants);
		expenseParticipantList = new ArrayList<ExpenseShareModel>();
		
		Intent intent = getIntent();
		expenseId = Integer.parseInt(intent.getStringExtra("expenseId"));
		Log.v("Type 1", "Loading expense id = " + expenseId);

		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();
		ArrayList<ExpenseModel> expenseList = esGlobal.getExpenseList();
		ArrayList<ParticipantModel> participantList = esGlobal.getParticipantList();
		
	    for ( ExpenseModel expense : expenseList)
	    {
			if (expense.ExpenseID == expenseId)
			{
				TextView txtVExDisplayName = (TextView) findViewById(R.id.txtVExDisplayName);
				txtVExDisplayName.setText(expense.Name);
				
				TextView txtVExDisplayDateCreated = (TextView) findViewById(R.id.txtVExDisplayDateCreated);
				txtVExDisplayDateCreated.setText(expense.DateCreated);
				TextView txtVExDisplayAmount = (TextView) findViewById(R.id.txtVExDisplayAmount);
				txtVExDisplayAmount.setText(Double.toString(expense.Amount));
				TextView txtVExDisplayPlace = (TextView) findViewById(R.id.txtVExDisplayPlace);
				txtVExDisplayPlace.setText(expense.Place);
				
				TextView txtVExDisplayPayer = (TextView) findViewById(R.id.txtVExDisplayPayer);
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
        lvVExSelectParticipants.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*case R.id.action_edit_expense:
			Intent editExIntentVE = new Intent(ViewExpense.this, EditExpense.class);
			editExIntentVE.putExtra("expenseId",Integer.toString(expenseId));
			startActivity(editExIntentVE);
			return true;*/
		//case R.id.action_delete_expense:
			// deleteExpense();
			//return true;
		case R.id.action_home:
			Intent homeIntentVE = new Intent(ViewExpense.this, MainActivity.class);
			startActivity(homeIntentVE);
			return true;
		/*case R.id.action_accountInfo:
			Intent accountInfoIntent = new Intent(ViewExpense.this, AccountInfo.class);
			startActivity(accountInfoIntent);
			return true;*/
		case R.id.action_logout:
			//processLogout(); need to deactivate user credentials
			Intent logoutIntentVEx = new Intent(ViewExpense.this, UserLogin.class);
			startActivity(logoutIntentVEx);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
