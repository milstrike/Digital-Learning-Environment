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

public class CreateBookshelf extends Activity {
	
	TextView BookshelfTitle, BookshelfDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.createbookshelf);
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0099CC"));     
        ab.setBackgroundDrawable(colorDrawable);
        
        BookshelfTitle = (TextView) findViewById(R.id.bookshelf_title);
        BookshelfDescription = (TextView) findViewById(R.id.bookshelf_description);
        
	}
	
	public void createNewBookshelf(View v) {
		
		 SharedPreferences sp = getSharedPreferences("saveData", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("Bookshelf_Title", BookshelfTitle.getText().toString());
			editor.putString("Bookshelf_Description", BookshelfDescription.getText().toString());
			editor.commit();
			
			 Intent myIntent = new Intent(CreateBookshelf.this, Bookshelf_ClassChooser.class);
             CreateBookshelf.this.startActivity(myIntent);
             finish();
		
	}
}
