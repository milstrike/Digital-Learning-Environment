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

public class CreateChapter extends Activity {
	
	TextView ChapterTitle, ChapterDescription;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_chapter);
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0099CC"));     
        ab.setBackgroundDrawable(colorDrawable);
        
        ChapterTitle = (TextView) findViewById(R.id.chapter_title);
        ChapterDescription = (TextView) findViewById(R.id.chapter_description);
        
	}
	
	public void createNewChapter(View v) {
		
		 SharedPreferences sp = getSharedPreferences("saveData", Activity.MODE_PRIVATE);
			SharedPreferences.Editor editor = sp.edit();
			editor.putString("Chapter_Title", ChapterTitle.getText().toString());
			editor.putString("Chapter_Description", ChapterDescription.getText().toString());
			editor.commit();
			
			 Intent myIntent = new Intent(CreateChapter.this, Chapter_BookshelfChooser.class);
             CreateChapter.this.startActivity(myIntent);
             finish();
		
	}
}
