package com.dle.book;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiUserCallBack;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;

public class Pre extends Activity {
	
	private HelloKii Auths = new HelloKii();
	
	private static final String TAG = "LoginActivity";
	private ProgressDialog mProgress;
	
	TextView State;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Auths.KiiAuth();
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pre);
		
		checkFile();
	}

public void checkFile(){
	//Check and Create Directory
    File folder = new File(Environment.getExternalStorageDirectory() + "/DLEBooks");
    boolean success = true;
    if (!folder.exists()) {
        success = folder.mkdir();
        createFileUserPassword();
    }
    else{
    	checkUser();
    }
    
    
}

public void createFileUserPassword(){
	//Create User Storage if doesn't exist
    File folder = new File(Environment.getExternalStorageDirectory() + "/DLEBooks");
    	try {
            File fileUser = new File(folder, "username" + ".txt");
            fileUser.createNewFile();
            File filePassword = new File(folder, "password"+ ".txt");
            filePassword.createNewFile();
        } catch (Exception ex) {
            
        }
    	
    	Intent myIntent = new Intent(Pre.this, Splash.class);
        Pre.this.startActivity(myIntent);
        finish();
    
}

public void checkUser(){
	String username = "";
	String password = "";
	
	State = (TextView) findViewById(R.id.status);
    State.setText("Checking Credentials....");
	
	 //Read File
    //Find the directory for the SD Card using the API
  //*Don't* hardcode "/sdcard"
  File sdcard = Environment.getExternalStorageDirectory();
	
	//Get the text file
    File fileUser = new File(sdcard,"DLEBooks/username.txt");
    File filePassword = new File(sdcard,"DLEBooks/password.txt");

    //Read text from file
    StringBuilder textUser = new StringBuilder();
    StringBuilder textPassword = new StringBuilder();

    try {
        BufferedReader brUser = new BufferedReader(new FileReader(fileUser));
        String lineUser;
        BufferedReader brPassword = new BufferedReader(new FileReader(filePassword));
        String linePassword;

        while ((lineUser = brUser.readLine()) != null) {
            textUser.append(lineUser);
        }
        brUser.close();
        
        username = textUser.toString();
        
        while ((linePassword = brPassword.readLine()) != null) {
            textPassword.append(linePassword);
        }
        brPassword.close();
        
        password = textPassword.toString();
    }
    catch (IOException e) {
        //You'll need to add proper error handling here
    }
    
    
 // authenticate the user asynchronously
    KiiUser.logIn(new KiiUserCallBack() {

        // catch the callback's "done" request
        public void onLoginCompleted(int token, KiiUser user, Exception e) {

            // hide our progress UI element
            State = (TextView) findViewById(R.id.status);
            State.setText("Welcome!....");

            // check for an exception (successful request if e==null)
            if (e == null) {

                // tell the console and the user it was a success!
                Log.v(TAG, "Logged in: " + user.toString());
                Toast.makeText(Pre.this, "User authenticated!", Toast.LENGTH_SHORT).show();
               

                // go to the main screen
                Intent myIntent = new Intent(Pre.this, MainActivity.class);
                Pre.this.startActivity(myIntent);
                finish();

            }

            // otherwise, something bad happened in the request
            else {

                // tell the console and the user there was a failure
                Log.v(TAG, "Error registering: " + e.getLocalizedMessage());
                Toast.makeText(Pre.this,
                        "Error registering: " + e.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();
            }

        }

    }, username, password);  
}



	
}
