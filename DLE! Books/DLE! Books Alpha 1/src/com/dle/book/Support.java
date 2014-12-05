package com.dle.book;

import android.os.Bundle;
import android.view.View;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class Support extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.support);
		
		ActionBar ab = getActionBar(); 
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0099CC"));     
        ab.setBackgroundDrawable(colorDrawable);
	}
	
	public void sentEmail(View V){
		Intent email = new Intent(Intent.ACTION_SEND);
		email.putExtra(Intent.EXTRA_EMAIL, new String[]{"dle.dleproject@hotmail.com"});		  
		email.putExtra(Intent.EXTRA_SUBJECT, "subject");
		email.setType("message/rfc822");
		startActivity(email);
	}
	
}
