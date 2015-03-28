package com.easysplit.mainview;

import java.util.ArrayList;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.EventModel;
import com.easysplit.base.ExpenseModel;
import com.example.easysplit.R;
import com.example.easysplit.R.id;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewExpense extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_expense);
		
		Intent intent = getIntent();
		int expenseId = Integer.parseInt(intent.getStringExtra("expenseId"));
		Log.v("Type 1", "Loading expense id = " + expenseId);

		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();
		ArrayList<ExpenseModel> expenseList = esGlobal.getExpenseList();
	    for ( ExpenseModel expense : expenseList)
	    {
		if (expense.ExpenseID == expenseId)
		{
		TextView txtVExDisplayDateCreated = (TextView) this.findViewById(R.id.txtVExDisplayDateCreated);
		txtVExDisplayDateCreated.setText(expense.DateCreated);
		TextView txtVExDisplayAmount = (TextView) findViewById(R.id.txtVExDisplayAmount);
		txtVExDisplayAmount.setText(Double.toString(expense.Amount));
		TextView txtVExDisplayPlace = (TextView) findViewById(R.id.txtVExDisplayPlace);
		txtVExDisplayPlace.setText(expense.Place);
		
		TextView txtVExDisplayPayer = (TextView) findViewById(R.id.txtVExDisplayPayer);
		txtVExDisplayPayer.setText(expense.OriginalPayer.Firstname + " " + expense.OriginalPayer.Lastname);
		
		}
	    }
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
		case R.id.action_edit_expense:
			Intent editExIntentVE = new Intent(ViewExpense.this, EditExpense.class);
			startActivity(editExIntentVE);
			return true;
		case R.id.action_delete_expense:
			// deleteExpense();
			return true;
		case R.id.action_home:
			Intent homeIntentVE = new Intent(ViewExpense.this, MainActivity.class);
			startActivity(homeIntentVE);
			return true;
		case R.id.action_accountInfo:
			Intent accountInfoIntent = new Intent(ViewExpense.this, AccountInfo.class);
			startActivity(accountInfoIntent);
			return true;
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
