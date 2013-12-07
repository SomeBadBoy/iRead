package com.hitiread.view;

import java.text.SimpleDateFormat;

import com.hitiread.dbms.MyDataBase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
	private boolean reading;
	private String starttime,endtime,startpage;
	public final static int SECOND = 1;
	private long timer = 0;
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
		endbtn.setEnabled(false);
		Intent intent = getIntent();
		ids = intent.getIntExtra("ids", 0);
		String mappath=null;
		if(ids != 0 )
		{
			//必须先初始化progress表，否则为空
			Log.v("startreading", Integer.toString(ids));
			startpage=myDataBase.getStartPageByBookId(ids);
			mappath=myDataBase.getBitmap(ids);
		}
		Log.v("startreading", startpage.toString()+" "+mappath);
		Bitmap bm=BitmapFactory.decodeFile(mappath);
		image.setImageBitmap(bm);
		if(startpage == "")
			startpage="0";
		Log.v("startreading", startpage);
		editText.setText(startpage);
//		Typeface face=Typeface.createFromAsset(getAssets(), "fonts/ni7seg.ttf");
		Log.v("startreading", "ziti");
//		readtime.setTypeface(face);
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				reading=true;
				endbtn.setEnabled(true);				long nowtime = System.currentTimeMillis();
				CharSequence nowtimeSequence = DateFormat.format("hh:mm:ss", nowtime);
				starttime = nowtimeSequence.toString();
				new TimeThread().start();				
			}
		});
		endbtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				reading=false;
				button.setEnabled(false);
				long time = System.currentTimeMillis();
				CharSequence endstr = DateFormat.format("hh:mm:ss", time);
				endtime = endstr.toString();
				//需要传3个参数：开始时间，结束时间，阅读开始页码
				Intent endIntent = new Intent();
				endIntent.putExtra("starttime", starttime);
				endIntent.putExtra("startpage", startpage);
				endIntent.putExtra("endtime", endtime);
				endIntent.putExtra("bookid", ids);
				endIntent.setClass(getApplicationContext(), EndReading.class);
				startActivity(endIntent);
				StartReading.this.finish();
			}
		});
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent mainIntent = new Intent();
		mainIntent.setClass(getApplicationContext(), MainActivity.class);
		startActivity(mainIntent);
		finish();
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
					timer+=1000;
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
				readtime.setText(new SimpleDateFormat("HH:mm:ss").format(timer));
				break;
			
			default:
				break;
			}
		}

	};
}
