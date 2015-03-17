package com.easysplit.mainview;

import com.example.easysplit.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ViewExpense extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_expense);
		
		Intent intent = getIntent();
		String expenseId = intent.getStringExtra("expenseId");
		Log.v("Type 1", "Loading expense id = " + expenseId);	
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
