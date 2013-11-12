package com.example.iread;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.Menu;
import android.widget.Button;

public class MainActivity extends Activity
{
	
	private Button btn=null;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener((android.view.View.OnClickListener) listener);
	}
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			// TODO Auto-generated method stub
			Intent tobookstoreIntent = new Intent(getApplicationContext(), BookStore.class);
			startActivity(tobookstoreIntent);
		}
	};
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
