package com.easysplit.mainview;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.easysplit.base.EasySplitGlobal;
import com.easysplit.base.ParticipantModel;
import com.example.easysplit.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class NewExpense extends Activity {

	private Spinner spNExPayer;
	private HashMap<String, Integer> spinnerMap;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_expense);
		
		// bind participants to spinner
		final EasySplitGlobal esGlobal = (EasySplitGlobal) getApplicationContext();
		ArrayList<ParticipantModel> participantList = esGlobal.getParticipantList();
		spinnerMap = new HashMap<String,Integer>();
		String[] spinnerArray = new String[participantList.size()];
		for (int i=0; i<participantList.size(); i++)
    	{
			spinnerMap.put(participantList.get(i).Firstname + " " + participantList.get(i).Lastname + " " + participantList.get(i).Email, participantList.get(i).Userid);
			spinnerArray[i] = participantList.get(i).Firstname + " " + participantList.get(i).Lastname + " " + participantList.get(i).Email;
    	}

		ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplication(),R.layout.spinner_item, spinnerArray);
		adapter.setDropDownViewResource(R.layout.spinner_item);

		spNExPayer = (Spinner) findViewById(R.id.spNExPayer);
		spNExPayer.setAdapter(adapter);		
		
		
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void btnNExSplitSave(View v)
	{
		
    	String name = spNExPayer.getSelectedItem().toString();
    	int payerrId = spinnerMap.get(name);
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
}
