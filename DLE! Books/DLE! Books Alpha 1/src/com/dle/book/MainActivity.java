package com.dle.book;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity {

	LinearLayout ll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		if (getIntent().getBooleanExtra("EXIT", false)) {
		    finish();
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0099CC"));     
        ab.setBackgroundDrawable(colorDrawable);
		
        goToClassroom();
        goToBookshelf();
        goToChapter();
        goToBook();
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchItem = (MenuItem) menu.findItem(R.id.action_search).getActionView();
	    //SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item){
		final Context context = this;
		final Intent intent;
	
    	switch(item.getItemId()){
    	case R.id.action_settings:
    		intent = new Intent(context, Settings.class);
    		startActivity(intent);
    		break;
    		
    	case R.id.action_profile:
    		intent = new Intent(context, Profile.class);
    		startActivity(intent);
    		break;
    		
    	}
    	return false;
    }
	
	public void onBackPressed() {
		// build the alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to Exit?")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            // if the user chooses 'yes',
                            public void onClick(DialogInterface dialog, int id) {
                            	finish();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    // if the user chooses 'no'
                    public void onClick(DialogInterface dialog, int id) {

                        // simply dismiss the dialog
                        dialog.cancel();
                    }
                });

        // show the dialog
        builder.create().show();
	}
	
	
	public void goToClassroom(){
    	final Context context = this;
    	ll = (LinearLayout) findViewById(R.id.menu_classroom);
    	ll.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intents = new Intent(context, Classroom.class);
				startActivity(intents); 
			}
    	});
    	
    }
	
	public void goToBookshelf(){
    	final Context context = this;
    	ll = (LinearLayout) findViewById(R.id.menu_bookshelf);
    	ll.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intents = new Intent(context, Bookshelf.class);
				startActivity(intents); 
			}
    	});
    	
    }
	
	public void goToChapter(){
    	final Context context = this;
    	ll = (LinearLayout) findViewById(R.id.menu_chapter);
    	ll.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intents = new Intent(context, Chapter.class);
				startActivity(intents); 
			}
    	});
    	
    }
	
	public void goToBook(){
    	final Context context = this;
    	ll = (LinearLayout) findViewById(R.id.menu_buku);
    	ll.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intents = new Intent(context, Book.class);
				startActivity(intents); 
			}
    	});
    	
    }

}
