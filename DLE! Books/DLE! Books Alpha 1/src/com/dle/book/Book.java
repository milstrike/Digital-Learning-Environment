package com.dle.book;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kii.cloud.storage.KiiBucket;
import com.kii.cloud.storage.KiiObject;
import com.kii.cloud.storage.KiiUser;
import com.kii.cloud.storage.callback.KiiObjectCallBack;
import com.kii.cloud.storage.callback.KiiQueryCallBack;
import com.kii.cloud.storage.query.KiiQuery;
import com.kii.cloud.storage.query.KiiQueryResult;

public class Book extends Activity implements OnItemClickListener{

	LinearLayout ll;
	private static final String TAG = "MainActivity";
    private static final String BUCKET_NAME = "Book";
    

    // define the UI elements
    private ProgressDialog mProgress;

    // define the list
    private ListView mListView;

    // define the list manager
    private ObjectAdapter mListAdapter;

    // define the object count
    // used to easily see object names incrementing
    private int mObjectCount = 0;
    
    //Array for Store Data
    private String [][] ethDataBook = new String [1000][1000];
    
    //Looping for Array
    private int loopEthDataBook = 0;
    
    
    // define a custom list adapter to handle KiiObjects
    public class ObjectAdapter extends ArrayAdapter<KiiObject> {

        // define some vars
        int resource;
        String response;
        Context context;

        // initialize the adapter
        public ObjectAdapter(Context context, int resource,
                List<KiiObject> items) {
            super(context, resource, items);

            // save the resource for later
            this.resource = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            // create the view
            LinearLayout rowView;

            // get a reference to the object
            KiiObject obj = getItem(position);

            // if it's not already created
            if (convertView == null) {

                // create the view by inflating the xml resource
                // (res/layout/row.xml)
                rowView = new LinearLayout(getContext());
                String inflater = Context.LAYOUT_INFLATER_SERVICE;
                LayoutInflater vi;
                vi = (LayoutInflater) getContext().getSystemService(inflater);
                vi.inflate(resource, rowView, true);

            }

            // it's already created, reuse it
            else {
                rowView = (LinearLayout) convertView;
            }

            // get the text fields for the row
            TextView titleText = (TextView) rowView
                    .findViewById(R.id.rowTextTitle);
            TextView subtitleText = (TextView) rowView
                    .findViewById(R.id.rowTextSubtitle);

            // set the content of the row texts
            titleText.setText(obj.getString("BookName"));
            subtitleText.setText("Chapter: "+ obj.getString("BookChapter"));
            ethDataBook[loopEthDataBook][0] = obj.getString("BookName");
            ethDataBook[loopEthDataBook][1] = obj.getString("BookChapter");
            ethDataBook[loopEthDataBook][2] = obj.getString("BookDesc");
            loopEthDataBook = loopEthDataBook + 1; 
            

            // return the row view
            return rowView;
        }

    }
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // set our view to the xml in res/layout/main.xml
        setContentView(R.layout.book);

        // create an empty object adapter
        mListAdapter = new ObjectAdapter(this, R.layout.row,
                new ArrayList<KiiObject>());

        mListView = (ListView) this.findViewById(R.id.listeration);
        mListView.setOnItemClickListener(this);
        // set it to our view's list
        mListView.setAdapter(mListAdapter);

        // query for any previously-created objects
        this.loadObjects();
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0099CC"));     
        ab.setBackgroundDrawable(colorDrawable);
        
        goToCreateBook();
	}
	
	
    

    // load any existing objects associated with this user from the server.
    // this is done on view creation
    private void loadObjects() {

        // default to an empty adapter
        mListAdapter.clear();

        // show a progress dialog to the user
        mProgress = ProgressDialog.show(Book.this, "", "Loading Book...",
                true);

        // create an empty KiiQuery (will retrieve all results, sorted by
        // creation date)
        KiiQuery query = new KiiQuery(null);
        query.sortByAsc("_created");

        // define the bucket to query
        KiiBucket bucket = KiiUser.getCurrentUser().bucket(BUCKET_NAME);

        // perform the query
        bucket.query(new KiiQueryCallBack<KiiObject>() {

            // catch the callback's "done" request
            public void onQueryCompleted(int token,
                    KiiQueryResult<KiiObject> result, Exception e) {

                // hide our progress UI element
                mProgress.dismiss();

                // check for an exception (successful request if e==null)
                if (e == null) {

                    // add the objects to the adapter (adding to the listview)
                    List<KiiObject> objLists = result.getResult();
                    for (KiiObject obj : objLists) {
                        mListAdapter.add(obj);
                    }

                    // tell the console and the user it was a success!
                    Log.v(TAG, "Book loaded: "
                            + result.getResult().toString());
                    Toast.makeText(Book.this, "Book loaded",
                            Toast.LENGTH_SHORT).show();

                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Log.v(TAG,
                            "Error loading Book: " + e.getLocalizedMessage());
                    Toast.makeText(
                            Book.this,
                            "Error loading Book: " + e.getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }, query);

    }

    // the user has chosen to delete an object
    // perform that action here...
    void performDelete(int position) {

        // show a progress dialog to the user
        mProgress = ProgressDialog.show(Book.this, "",
                "Deleting Book...", true);

        // get the object to delete based on the index of the row that was
        // tapped
        final KiiObject o = Book.this.mListAdapter.getItem(position);

        // delete the object asynchronously
        o.delete(new KiiObjectCallBack() {

            // catch the callback's "done" request
            public void onDeleteCompleted(int token, Exception e) {

                // hide our progress UI element
                mProgress.dismiss();

                // check for an exception (successful request if e==null)
                if (e == null) {

                    // tell the console and the user it was a success!
                    Toast.makeText(Book.this, "Book Deleted!",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Deleted object: " + o.toString());

                    // remove the object from the list adapter
                    Book.this.mListAdapter.remove(o);
                    
                    finish();
                    Intent myIntent = new Intent(Book.this, Book.class);
                    Book.this.startActivity(myIntent);

                }

                // otherwise, something bad happened in the request
                else {

                    // tell the console and the user there was a failure
                    Toast.makeText(Book.this, "Error deleting Bookshelf",
                            Toast.LENGTH_SHORT).show();
                    Log.d(TAG,
                            "Error deleting Bookshelf: " + e.getLocalizedMessage());
                }

            }
        });
    }
	
	public void goToCreateBook(){
    	final Context context = this;
    	ll = (LinearLayout) findViewById(R.id.menu_createBook);
    	ll.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intents = new Intent(context, CreateBook.class);
				startActivity(intents); 
				finish();
			}
    	});
    	
    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {
        // build the alert
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to remove: " + ethDataBook[arg2][0] + " Book?")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            // if the user chooses 'yes',
                            public void onClick(DialogInterface dialog, int id) {

                                // perform the delete action on the tapped
                                // object
                                Book.this.performDelete(arg2);
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
