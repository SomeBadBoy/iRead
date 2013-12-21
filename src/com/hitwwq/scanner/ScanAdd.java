package com.hitwwq.scanner;

import com.hitiread.view.MainActivity;
import com.hitiread.view.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ScanAdd extends Activity
{

	public static String isbn="";
	public final static int RESULT_CODE=2;
	EditText text;
	Button okButton;
	Button cancelButton;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanadd);
		
		text=(EditText)findViewById(R.id.isbn_num);
		isbn=text.getText().toString();

		Log.v("soubook", isbn);
		Log.v("soubook", "here");
		System.out.println(isbn);
		okButton=(Button)findViewById(R.id.ok_button);
		cancelButton=(Button)findViewById(R.id.cancel_button);
		okButton.setOnClickListener(listener);
		cancelButton.setOnClickListener(listener);
	}
	
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			Button btn=(Button)v;
			switch (btn.getId()) {
			case R.id.ok_button:
				Log.v("hitiread", text.getText().toString());
				intent.putExtra("ISBN", text.getText().toString());
				setResult(RESULT_CODE,intent);
				finish();
				break;
			case R.id.cancel_button:
				Intent cancelintent = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(cancelintent);
				finish();
				break;
			default:
				break;
			}
		}
	};
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
	}

}
