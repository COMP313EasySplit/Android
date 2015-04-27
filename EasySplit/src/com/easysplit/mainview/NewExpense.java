package com.easysplit.mainview;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.ExpenseShareModel;
import com.easysplit.base.ParticipantModel;
import com.easysplit.base.UserModel;
import com.easysplit.net.EasySplitRequest;
import com.example.easysplit.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class NewExpense extends Activity {
	
	private Context thiscontext;
	private Spinner spNExPayer;
	private HashMap<String, Integer> spinnerMap;
	ArrayList<ParticipantModel> participantList;
	ArrayList<ExpenseShareModel> participantExpenseShareList;
	UserModel currentUser;
	
	String eventId;	
	EditText etNExEventName;
	EditText etNExAmount;
	EditText etNExPlace;
	ListView lvNExSelectParticipants;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_expense);
		
		//if(getResources().getBoolean(R.bool.portrait_only)){
	    //    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	   // }
		
		thiscontext = getApplicationContext();
		
		eventId = getIntent().getStringExtra("eventId"); 
		
		etNExEventName = (EditText) findViewById(R.id.etNExEventName);
		etNExAmount = (EditText) findViewById(R.id.etNExAmount);
		etNExPlace = (EditText) findViewById(R.id.etNExPlace);
		lvNExSelectParticipants = (ListView) findViewById(R.id.lvNExSelectParticipants);
		
		// bind participants to spinner
		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();
		currentUser = esGlobal.getCurrentUser();
		participantList = esGlobal.getParticipantList();
		spinnerMap = new HashMap<String,Integer>();
		String[] spinnerArray = new String[participantList.size()];
		for (int i=0; i<participantList.size(); i++)
    	{
			spinnerMap.put(participantList.get(i).Firstname + " " + participantList.get(i).Lastname + " " + participantList.get(i).Email, participantList.get(i).Userid);
			spinnerArray[i] = participantList.get(i).Firstname + " " + participantList.get(i).Lastname + " " + participantList.get(i).Email;
    	}
		// select payer
		ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplication(),R.layout.spinner_item, spinnerArray);
		adapter.setDropDownViewResource(R.layout.spinner_item);

		spNExPayer = (Spinner) findViewById(R.id.spNExPayer);
		spNExPayer.setAdapter(adapter);
		
		// bind participant to listview
		participantExpenseShareList = new ArrayList<ExpenseShareModel>();
		for(int i=0;i<participantList.size();i++)
		{
			ExpenseShareModel share = new ExpenseShareModel();
			share.Email = participantList.get(i).Email;
			share.Firstname = participantList.get(i).Firstname;
			share.Lastname = participantList.get(i).Lastname;
			share.SharedAmount = 0.0;
			share.PaidAmount = 0.0;
			share.OweAmount = 0.0;
			share.ExpenseID = 0;
			share.isSelected = false;
			share.UserId = participantList.get(i).Userid;
			participantExpenseShareList.add(share);
		}
		refreshParticipantListView();

		lvNExSelectParticipants.setOnItemClickListener(new OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               
              // ListView Clicked item index
              int itemPosition     = position;
		    	String name = spNExPayer.getSelectedItem().toString();
		    	int payerId = spinnerMap.get(name);
		    	
              // ListView Clicked item value
              int userId = Integer.parseInt(plist.get(position).get("UserId"));
                 for(ExpenseShareModel share : participantExpenseShareList)
                 {
                	 if(share.UserId == userId)
                	 {
                		 share.isSelected = ! share.isSelected;
                		 if (share.UserId == payerId) share.isSelected=true;
                		 break;
                	 }
                 }
                 refreshParticipantListView();
             }
        }); 
		
		spNExPayer.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
		    	String name = spNExPayer.getSelectedItem().toString();
		    	int payerId = spinnerMap.get(name);
		    	//Toast.makeText(getApplicationContext(), "user id selected : " + Integer.toString(payerId), Toast.LENGTH_SHORT ).show();

                for(ExpenseShareModel share : participantExpenseShareList)
                {
               	 if(share.UserId == payerId)
               	 {
               		 share.isSelected =true; // force select as share member
               		 break;
               	 }
                }
                refreshParticipantListView();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		etNExAmount.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus)
				{
					refreshParticipantListView();
				}
			}
			
		});
		
	}
	ArrayList<HashMap<String, String>> plist;
	private void refreshParticipantListView()
	{
		// re-calculate share amount
		String txt_amount = etNExAmount.getText().toString();
		if (TextUtils.isEmpty(txt_amount))
		{
			txt_amount="0.0";
		}
		double total_amount = Double.parseDouble(txt_amount);
		int selected_count = 0;
		for (int i=0;i<participantExpenseShareList.size();i++)
		{
			ExpenseShareModel share = participantExpenseShareList.get(i);
			if (share.isSelected)
			{
				selected_count ++;
			}
		}
		double share_amount = Math.floor(total_amount/selected_count * 100.0) / 100.0;
		double host_share_amount = total_amount - share_amount*(selected_count-1);
		host_share_amount = Math.floor(host_share_amount * 100.0) / 100.0;
		for (int i=0;i<participantExpenseShareList.size();i++)
		{
			ExpenseShareModel share = participantExpenseShareList.get(i);
			share.SharedAmount = 0.0;
			share.PaidAmount = 0.0;
			share.OweAmount = 0.0;
			if (share.isSelected)
			{
				share.PaidAmount = 0.0;
				share.SharedAmount = share_amount;
				share.OweAmount = share_amount - 0;
			}
			
	    	String name = spNExPayer.getSelectedItem().toString();
	    	int payerId = spinnerMap.get(name);
			if(share.UserId == payerId)	// payer id
			{
				share.PaidAmount = total_amount;
				share.SharedAmount = host_share_amount;
				share.OweAmount = host_share_amount - total_amount ;
			}
		}
		
		//refresh listview
		plist = new ArrayList<HashMap<String, String>>();
		for (ExpenseShareModel expenseParticipant : participantExpenseShareList)
    	{
    		HashMap<String, String> map = new HashMap<String, String>();
    		map.put("ExpenseID", Integer.toString(expenseParticipant.ExpenseID));
    		map.put("UserId", Integer.toString(expenseParticipant.UserId));
            map.put("txtEPLVDFullname", expenseParticipant.Firstname + " " + expenseParticipant.Lastname);
            map.put("txtEPLVDEmail", expenseParticipant.Email);
            //map.put("txtEPLVDAmount", Double.toString(expenseParticipant.SharedAmount));

            map.put("txtEPLVDAmount", String.format("Paid: %-6sShare: %-6sOwe: %-6s", 
            		expenseParticipant.PaidAmount,
            		expenseParticipant.SharedAmount,
            		expenseParticipant.OweAmount));
            plist.add(map);
    	}
		
		// bind to list view
        ListAdapter adapter = new SimpleAdapter(getApplicationContext(),
        		plist,
        		R.layout.event_expense_detail_participant_listview_detail,
        		new String[]{"txtEPLVDFullname","txtEPLVDEmail","txtEPLVDAmount"},
        		new int[]{R.id.txtEPLVDFullname, R.id.txtEPLVDEmail,R.id.txtEPLVDAmount});
        lvNExSelectParticipants.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_cancel:
			Intent homeIntentNE = new Intent(NewExpense.this, MainActivity.class);
			startActivity(homeIntentNE);
			return true;
		case R.id.action_accept:
			String name = spNExPayer.getSelectedItem().toString();
	    	int payerrId = spinnerMap.get(name);
	    	addExpense = new AddExpense(NewExpense.this);
	    	addExpense.execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	private AddExpenseParticipants addExpenseParticipant;
	public void btnNExSplitSave(View v)
	{
    	String name = spNExPayer.getSelectedItem().toString();
    	int payerrId = spinnerMap.get(name);
    	//addExpenseParticipant = new AddExpenseParticipants();
    	//addExpenseParticipant.execute();
    	addExpense = new AddExpense(NewExpense.this);
    	addExpense.execute();
	}
	
	private static final int TAKE_PICTURE = 1;    
	private Uri imageUri;
	public void btnNexPhoto(View v)
	{
	    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
	    File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
	    intent.putExtra(MediaStore.EXTRA_OUTPUT,
	            Uri.fromFile(photo));
	    imageUri = Uri.fromFile(photo);
	    startActivityForResult(intent, TAKE_PICTURE);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    switch (requestCode) {
	    case TAKE_PICTURE:
	        if (resultCode == Activity.RESULT_OK) {
	            Uri selectedImage = imageUri;
	            getContentResolver().notifyChange(selectedImage, null);
	            ImageView imageView = (ImageView) findViewById(R.id.imageView1);
	            ContentResolver cr = getContentResolver();
	            Bitmap bitmap;
	            try {
	                 bitmap = android.provider.MediaStore.Images.Media
	                 .getBitmap(cr, selectedImage);

	                imageView.setImageBitmap(bitmap);
	                Toast.makeText(this, selectedImage.toString(),
	                        Toast.LENGTH_LONG).show();
	            } catch (Exception e) {
	                Toast.makeText(this, "Failed to load", Toast.LENGTH_LONG)
	                        .show();
	                Log.e("Camera", e.toString());
	            }
	        }
	    }
	}
	
	AddExpense addExpense;
	private class AddExpense extends AsyncTask<Integer, Void, Integer>{
    	private Activity mActivity;
    	public AddExpense(Activity activity) {
    		mActivity = activity;
    	} 
		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			String result = null;
			EasySplitRequest request = new EasySplitRequest(thiscontext);
	    	String name = spNExPayer.getSelectedItem().toString();
	    	int payerId = spinnerMap.get(name);
			
			try {
				result = request.addExpense(Integer.parseInt(eventId), etNExEventName.getText().toString(), 
						Double.parseDouble(etNExAmount.getText().toString()), etNExPlace.getText().toString(), 
						payerId);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Integer.parseInt(result);    
		}
        @Override
        protected void onPostExecute(Integer expenseId) {
        	Log.v("Type 1","add expense result:" + expenseId);
        	if(expenseId > 0)
        	{
        		addExpenseParticipants = new AddExpenseParticipants(mActivity);
        		addExpenseParticipants.execute(expenseId);
        	}
        }		
	}
	AddExpenseParticipants addExpenseParticipants;
    private class AddExpenseParticipants extends AsyncTask<Integer, Void, String> {
    	private Activity mActivity;
    	public AddExpenseParticipants(Activity activity) {
    		mActivity = activity;
    	} 
		@Override
		protected String doInBackground(Integer... params) {
			String result = null;
			EasySplitRequest request = new EasySplitRequest(thiscontext);
        	try {
	            for(ExpenseShareModel share : participantExpenseShareList)
	            {
						result = request.addExpenseParticipants(params[0], share.UserId, share.OweAmount);
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TimeoutException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
        @Override
        protected void onPostExecute(String result) {
        	if (result.equals("true"))
        	{
        		Toast.makeText(getBaseContext(), "Expense saved", Toast.LENGTH_SHORT).show();
        		mActivity.finish();
        	}
        	else
        	{
        		Toast.makeText(getBaseContext(), "Error: cannot save expense", Toast.LENGTH_SHORT).show();
        	}
        }
    	
    }
}
