package com.easysplit.mainview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import com.easysplit.fragments.EventHostTabFragment;
import com.easysplit.fragments.ParticipantTabFragment;
import com.example.easysplit.R;

public class MainActivity extends Activity {

	// Create action bar tabs and fragments
	ActionBar.Tab tab1, tab2;
	Fragment fragmentTab1 = new EventHostTabFragment();
	Fragment fragmentTab2 = new ParticipantTabFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		
		// set global variable // for testing initializing
		//final EasySplitGlobal esGlobal = (EasySplitGlobal) this.getApplicationContext();
		//esGlobal.setHostID(1);	for test only

		// Action bar tab code
		ActionBar actionBar = getActionBar();		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tab1 = actionBar.newTab().setText("Event Host");
		tab2 = actionBar.newTab().setText("Participant");
		tab1.setTabListener(new MyTabListener(fragmentTab1));
		tab2.setTabListener(new MyTabListener(fragmentTab2));
		actionBar.addTab(tab1);
		actionBar.addTab(tab2);

	}

	  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.

		switch (item.getItemId()) {
		case R.id.action_home:
			Intent homeIntentMA = new Intent(MainActivity.this, MainActivity.class);
			startActivity(homeIntentMA);
			return true;
		case R.id.action_addNewEvent:
			Intent newEventIntentMA = new Intent(MainActivity.this, NewEvent.class);
			startActivity(newEventIntentMA);
			return true;
		/*case R.id.action_accountInfo:
			Intent accountInfoIntentMA = new Intent(MainActivity.this, AccountInfo.class);
			startActivity(accountInfoIntentMA);
			return true;*/
		case R.id.action_logout:
			//processLogout();
			Intent logoutIntentMA = new Intent(MainActivity.this, UserLogin.class);
			startActivity(logoutIntentMA);
			return true;			
		case R.id.action_refresh:
			if(getFragmentRefreshListener()!=null){
                getFragmentRefreshListener().onRefresh();
            }
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// listener for tabs
	public class MyTabListener implements ActionBar.TabListener {
		Fragment fragment;

		public MyTabListener(Fragment fragment) {
			this.fragment = fragment;
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.replace(R.id.fragment_container, fragment);
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(fragment);
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}
	}
	
	public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;

    public interface FragmentRefreshListener{
        void onRefresh();
    }
    
 // Back Button
 	@Override
 	public void onBackPressed() {
 		super.onBackPressed();
 	}

 	@Override
 	public boolean onKeyDown(int keyCode, KeyEvent event) {
 		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
 			AlertDialog.Builder builder = new AlertDialog.Builder(
 					MainActivity.this);
 			builder.setCancelable(false);
 			builder.setTitle("Exit?");
 			builder.setMessage("Logout of EasySplit?");
 			builder.setPositiveButton("Yes",
 					new DialogInterface.OnClickListener() {
 						public void onClick(DialogInterface dialog, int id) {
 							MainActivity.this.finish();
 							
 						}
 					});
 			builder.setNegativeButton(R.string.cancel, null);
 			builder.show();
 		}
 		return super.onKeyDown(keyCode, event);
 	}


}
