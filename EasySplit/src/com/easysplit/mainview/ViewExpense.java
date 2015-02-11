package com.easysplit.mainview;

import com.example.easysplit.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ViewExpense extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_expense);
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
			// deleteExpense();
			return true;
		case R.id.action_delete_expense:
			// deleteExpense();
			return true;
		case R.id.action_home:
			// openHome();
			return true;
		case R.id.action_accountInfo:
			//openAccountInfo();
			return true;
		case R.id.action_logout:
			//processLogout();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
