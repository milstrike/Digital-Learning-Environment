package com.dle.book;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class Settings extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0099CC"));     
        ab.setBackgroundDrawable(colorDrawable);
	}
	
	public void AboutView(View V){
		Intent myIntent = new Intent(Settings.this, About.class);
        Settings.this.startActivity(myIntent);
	}
	
	public void SupportView(View V){
		Intent SupportIntent = new Intent(Settings.this, Support.class);
        Settings.this.startActivity(SupportIntent);
	}
	
	public void LogOut(View V){
		// build the alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to LogOut?")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            // if the user chooses 'yes',
                            public void onClick(DialogInterface dialog, int id) {
                            	
                            	File folder = new File(Environment.getExternalStorageDirectory() + "/DLEBooks");
                            	if (folder.isDirectory()) {
                                    String[] children = folder.list();
                                    for (int i = 0; i < children.length; i++) {
                                        new File(folder, children[i]).delete();
                                    }
                                    folder.delete();
                                }
                            	
                            	
                            	Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            	intent.putExtra("EXIT", true);
                            	startActivity(intent);
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
	
}
