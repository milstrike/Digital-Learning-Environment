package com.dle.book;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class CreateBook extends Activity {
	
	TextView BookTitle, BookDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_book);
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0099CC"));     
        ab.setBackgroundDrawable(colorDrawable);
        
        BookTitle = (TextView) findViewById(R.id.book_title);
        BookDescription = (TextView) findViewById(R.id.book_content);
        
	}
	
	public void createNewBook(View v) {
		
		 SharedPreferences sp = getSharedPreferences("saveData", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("Book_Title", BookTitle.getText().toString());
			editor.putString("Book_Description", BookDescription.getText().toString());
			editor.commit();
			
			 Intent myIntent = new Intent(CreateBook.this, Book_chooseChapter.class);
             CreateBook.this.startActivity(myIntent);
             finish();
		
	}
}
