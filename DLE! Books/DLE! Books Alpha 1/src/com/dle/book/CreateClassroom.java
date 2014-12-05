package com.dle.book;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kii.cloud.storage.KiiBucket;
import com.kii.cloud.storage.KiiObject;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiObjectCallBack;

public class CreateClassroom extends Activity {
	
	// define the UI elements
    private ProgressDialog mProgress;
    
    //Declare Bucket Name
    private static final String BUCKET_NAME = "Classroom";
    
    //Create Index
    private int mObjectCount = 0;
    
    //Create Tag
    private static final String TAG = "MainActivity";
    
    TextView mClassTitleField, mClassDescriptionField;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_classroom);
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0099CC"));     
        ab.setBackgroundDrawable(colorDrawable);
        
        mClassTitleField = (TextView) findViewById(R.id.classroom_title);
        mClassDescriptionField = (TextView) findViewById(R.id.classroom_description);
        
	}
	
	public void createNewClassroom(View v) {

        // show a progress dialog to the user
        mProgress = ProgressDialog.show(CreateClassroom.this, "",
                "Creating Classroom...", true);

        // create an content of Classroom
        String value = " " + (++mObjectCount);
        String ClassValue = mClassTitleField.getText().toString();
        String ClassDescValue = mClassDescriptionField.getText().toString();
        

        // get a reference to a KiiBucket
        KiiBucket bucket = KiiUser.getCurrentUser().bucket(BUCKET_NAME);

        // create a new KiiObject and set a key/value
        KiiObject obj = bucket.object();
        obj.set("ClassID", value);
        obj.set("ClassName", ClassValue);
        obj.set("ClassDesc", ClassDescValue);

        // save the object asynchronously
        obj.save(new KiiObjectCallBack() {

            // catch the callback's "done" request
            public void onSaveCompleted(int token, KiiObject o, Exception e) {

                // hide our progress UI element
                mProgress.dismiss();

                // check for an exception (successful request if e==null)
                if (e == null) {

                    // tell the console and the user it was a success!
                    Toast.makeText(CreateClassroom.this, "Classroom Created!",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Classroom Created:: " + o.toString());
                    
                 // go to the main screen
                    Intent myIntent = new Intent(CreateClassroom.this, Classroom.class);
                    CreateClassroom.this.startActivity(myIntent);
                    finish();

                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Toast.makeText(CreateClassroom.this, "Error creating Classroom",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG,
                            "Error creating Classroom: " + e.getLocalizedMessage());
                }

            }
        });

    }
}
