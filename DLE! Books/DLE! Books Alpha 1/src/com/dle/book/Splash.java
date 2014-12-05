package com.dle.book;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.Kii.Site;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;


public class Splash extends Activity {
	
	private static final String TAG = "LoginActivity";
	private ProgressDialog mProgress;
	
	private HelloKii Auths = new HelloKii();
	
	TextView mUsernameField, mPasswordField;
	CheckBox cb;
	Button bt;
	String uss, pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Auths.KiiAuth();
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		mUsernameField = (TextView) findViewById(R.id.dleid);
        mPasswordField = (TextView) findViewById(R.id.dlepass);
		
		goToRegistrasi();
		
	}
	
	public void handleLogin(View v) {
		
        // show a loading progress dialog
        mProgress = ProgressDialog.show(Splash.this, "", "Signing in...", true);

        // get the username/password combination from the UI
        uss = mUsernameField.getText().toString();
        pass = mPasswordField.getText().toString();
        Log.v(TAG, "Logging in: " + uss + ":" + pass);

        // authenticate the user asynchronously
        KiiUser.logIn(new KiiUserCallBack() {

            // catch the callback's "done" request
            public void onLoginCompleted(int token, KiiUser user, Exception e) {

                // hide our progress UI element
                mProgress.cancel();

                // check for an exception (successful request if e==null)
                if (e == null) {

                    // tell the console and the user it was a success!
                    Log.v(TAG, "Logged in: " + user.toString());
                    Toast.makeText(Splash.this, "User authenticated!", Toast.LENGTH_SHORT).show();
                    
                    SharedPreferences sp = getSharedPreferences("saveData", Activity.MODE_PRIVATE);
        			SharedPreferences.Editor editor = sp.edit();
        			editor.putString("akun", uss);
        			editor.commit();
        			
        			File folder = new File(Environment.getExternalStorageDirectory() + "/DLEBooks");
        			File fileUsername = new File(folder, "username.txt");
        			File filePassword = new File(folder, "password.txt");
        			try {
        				FileOutputStream fU = new FileOutputStream(fileUsername);
        		        PrintWriter pwU = new PrintWriter(fU);
        		        pwU.print(uss);
        		        pwU.flush();
        		        pwU.close();
        		        fU.close();
        		    }
        		    catch (IOException e1) {
        		        Log.e("Exception", "File write failed: " + e1.toString());
        		    } 
        			
        			try {
        				FileOutputStream fP = new FileOutputStream(filePassword);
        		        PrintWriter pwP = new PrintWriter(fP);
        		        pwP.print(pass);
        		        pwP.flush();
        		        pwP.close();
        		        fP.close();
        		    }
        		    catch (IOException e1) {
        		        Log.e("Exception", "File write failed: " + e1.toString());
        		    } 

                    // go to the main screen
                    Intent myIntent = new Intent(Splash.this, MainActivity.class);
                    Splash.this.startActivity(myIntent);
                    finish();

                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Log.v(TAG, "Error registering: " + e.getLocalizedMessage());
                    Toast.makeText(Splash.this,
                            "Error registering: " + e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }

        }, uss, pass);
    }


	
    public void goToRegistrasi(){
    	final Context context = this;
    	bt = (Button) findViewById(R.id.createid);
    	bt.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intents = new Intent(context, Register.class);
				startActivity(intents); 
			}
    	});
    	
    }
    
    public void onBackPressed() {
    	File folder = new File(Environment.getExternalStorageDirectory() + "/DLEBooks");
    	if (folder.isDirectory()) {
            String[] children = folder.list();
            for (int i = 0; i < children.length; i++) {
                new File(folder, children[i]).delete();
            }
            folder.delete();
        }
    	finish();
	}
	
}
