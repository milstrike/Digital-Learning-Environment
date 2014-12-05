package com.dle.book;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import com.kii.cloud.storage.Kii;
import com.kii.cloud.storage.KiiBucket;
import com.kii.cloud.storage.KiiObject;
import com.kii.cloud.storage.Kii.Site;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiObjectCallBack;
import com.kii.cloud.storage.callback.KiiUserCallBack;

public class Register extends Activity {
	private HelloKii Auths = new HelloKii();
	private static final String TAG = "LoginActivity";
	private ProgressDialog mProgress;
	private static final String BUCKET_NAME = "Profile";
	TextView mUsernameField, mPasswordField, mNIMField, mFullNameField, mEmailField;
	String username, password, nim, fullname, email;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Auths.KiiAuth();
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.registrasi);
		mUsernameField = (TextView) findViewById(R.id.dleuser);
        mPasswordField = (TextView) findViewById(R.id.dlepassword);
        mNIMField = (TextView) findViewById(R.id.NIM);
        mFullNameField = (TextView) findViewById(R.id.fullname);
        mEmailField = (TextView) findViewById(R.id.email);
	}
	
	public void handleSignUp(View v) {

        // show a loading progress dialog
        mProgress = ProgressDialog.show(Register.this, "", "Signing up...", true);

        // get the username/password combination from the UI
        username = mUsernameField.getText().toString();
        password = mPasswordField.getText().toString();
        nim = mNIMField.getText().toString();
        fullname = mFullNameField.getText().toString();
        email = mEmailField.getText().toString();
        Log.v(TAG, "Registering: " + username + ":" + password);

        // create a KiiUser object
        try {
            KiiUser user = KiiUser.createWithUsername(username);
            // register the user asynchronously
            user.register(new KiiUserCallBack() {

                // catch the callback's "done" request
                public void onRegisterCompleted(int token, KiiUser user,
                        Exception e) {

                    // hide our progress UI element
                    mProgress.cancel();

                    // check for an exception (successful request if e==null)
                    if (e == null) {

                        // tell the console and the user it was a success!
                        Log.v(TAG, "Registered: " + user.toString());
                        Toast.makeText(Register.this, "User registered!", Toast.LENGTH_SHORT).show();

                        // go to the next screen
                        Intent myIntent = new Intent(Register.this, Splash.class);
                        Register.this.startActivity(myIntent);
                        finish();

                    }

                    // otherwise, something bad happened in the request
                    else {

                        // tell the console and the user there was a failure
                        Log.v(TAG,
                                "Error registering: " + e.getLocalizedMessage());
                        Toast.makeText(
                                Register.this,
                                "Error Registering: " + e.getLocalizedMessage(),
                                Toast.LENGTH_SHORT).show();
                    }

                }

            }, password);

        } catch (Exception e) {
            mProgress.cancel();
            Toast.makeText(this,
                    "Error signing up: " + e.getLocalizedMessage(),
                    Toast.LENGTH_SHORT).show();
        }

    }
	
	public void createProfile(){
		loginFirst();
        // get a reference to a KiiBucket
        KiiBucket bucket = KiiUser.getCurrentUser().bucket(BUCKET_NAME);
        // create a new KiiObject and set a key/value
        KiiObject obj = bucket.object();
        obj.set("ProfileID", nim);
        obj.set("ProfileName", fullname);
        obj.set("ProfileEmail", email);
        // save the object asynchronously
        obj.save(new KiiObjectCallBack() {
            // catch the callback's "done" request
            public void onSaveCompleted(int token, KiiObject o, Exception e) {

                // hide our progress UI element
                mProgress.dismiss();

                // check for an exception (successful request if e==null)
                if (e == null) {

                    // tell the console and the user it was a success!
                    Toast.makeText(Register.this, "New Account Created!",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Classroom Created:: " + o.toString());
                    
                    goToSplash();
                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Toast.makeText(Register.this, "Error creating Account",
                            Toast.LENGTH_SHORT).show();                    
                }

            }
        });

    }
		
	
	public void goToSplash(){
		// go to the main screen
        Intent myIntent = new Intent(Register.this, Splash.class);
        Register.this.startActivity(myIntent);
        finish();
	}
	
	public void loginFirst(){
		
		mProgress = ProgressDialog.show(Register.this, "", "Registering...", true);
		
        KiiUser.logIn(new KiiUserCallBack() {

            // catch the callback's "done" request
            public void onLoginCompleted(int token, KiiUser user, Exception e) {
            	
            	// hide our progress UI element
                mProgress.cancel();

                // check for an exception (successful request if e==null)
                if (e == null) {
                    goToSplash();
                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Log.v(TAG, "Error registering: " + e.getLocalizedMessage());
                    Toast.makeText(Register.this,
                            "Error registering: " + e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }

            }

        }, username, password);
	}
	
}
