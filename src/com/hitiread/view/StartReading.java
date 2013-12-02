package com.hitiread.view;

import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.BookInfo;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class StartReading extends Activity
{

	private Button button,endbtn;
	private EditText editText;
	private TextView readtime;
	private ImageView image;
	private MyDataBase myDataBase;
	private int ids;
	private BookInfo book;
	private boolean reading;
	private String starttime,endtime;
	public final static int SECOND = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reading_start);

		button = (Button) findViewById(R.id.start_reading);
		endbtn = (Button) findViewById(R.id.endreading);
		editText = (EditText) findViewById(R.id.start_page);
		image = (ImageView) findViewById(R.id.imageView1);
		readtime=(TextView)findViewById(R.id.readingtime);
		myDataBase = new MyDataBase(this);
		Intent intent = getIntent();
		ids = intent.getIntExtra("ids", 0);
		String startpage=null;
		String mappath=null;
		Log.v("startreading", "first");
		if(ids != 0 )
		{
			Log.v("startreading", "second");
			//必须先初始化progress表，否则为空
			Log.v("startreading", Integer.toString(ids));
			startpage=myDataBase.getStartPageByBookId(ids);
			Log.v("startreading", "after startpage"+startpage);
			mappath=myDataBase.getBitmap(ids);
		Log.v("startreading", startpage.toString()+" "+mappath);
		}
		Log.v("startreading", startpage.toString()+" "+mappath);
		Bitmap bm=BitmapFactory.decodeFile(mappath);
		image.setImageBitmap(bm);
		if(startpage == "")
			startpage="0";
		Log.v("startreading", startpage);
		editText.setText(startpage);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}

	public class TimeThread extends Thread
	{

		@Override
		public void run()
		{
			do{
				try
				{
					Thread.sleep(1000);
					Message msg=new Message();
					msg.what=SECOND;
					handler.sendMessage(msg);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			} while (reading);
		}	
	}
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler()
	{

		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			switch (msg.what) {
			case SECOND:
				long sysTime = System.currentTimeMillis();
				CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);
				readtime.setText(sysTimeStr);
				break;
			
			default:
				break;
			}
		}

	};
}
