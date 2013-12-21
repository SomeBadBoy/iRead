package com.hitiread.view;

import com.hitiread.dbms.MyDataBase;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;

public class StartReading extends Activity
{

	private Button button, endbtn;
	private EditText editText;
	private ImageView image;
	private MyDataBase myDataBase;
	private int ids;
	private String starttime, endtime, startpage;
	public final static int SECOND = 1;
	private Chronometer chronometer = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reading_start);

		button = (Button) findViewById(R.id.startreading);
		Log.v("start", "start");
		endbtn = (Button) findViewById(R.id.endreading);
		Log.v("start", "start");
		editText = (EditText) findViewById(R.id.start_page);
		Log.v("start", "start");
		image = (ImageView) findViewById(R.id.imageView1);
		Log.v("start", "start");
		chronometer = (Chronometer) findViewById(R.id.clock);
		Log.v("start", "start");
		chronometer.setFormat("%s");
		myDataBase = new MyDataBase(StartReading.this);
		endbtn.setEnabled(false);
		Intent intent = getIntent();
		ids = intent.getIntExtra("ids", 0);
		String mappath = null;
		if (ids != 0)
		{
			// 必须先初始化progress表，否则为空
			Log.v("startreading", Integer.toString(ids));
			startpage = myDataBase.getStartPageByBookId(ids);
			mappath = myDataBase.getBitmap(ids);
		}
		Log.v("startreading", startpage.toString() + " " + mappath);
		Bitmap bm = BitmapFactory.decodeFile(mappath);
		image.setImageBitmap(bm);
		if (startpage == "")
			startpage = "0";
		Log.v("startreading", startpage);
		editText.setText(startpage);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				startpage = editText.getText().toString();
				if (startpage == null || startpage.equals(""))
				{
					editText.setError("请输入开始页码");
					return;
				}
				String total = myDataBase.getTotalPageByBookId(ids);
				int start = Integer.valueOf(startpage);
				int totalpage = Integer.valueOf(total);

				if (start > totalpage)
				{
					editText.setError("您已经把整本书读完啦");
					return;
				}
				chronometer.setBase(SystemClock.elapsedRealtime());
				chronometer.start();
				endbtn.setEnabled(true);
				button.setEnabled(false);
				editText.setEnabled(false);
				long nowtime = System.currentTimeMillis();
				CharSequence nowtimeSequence = DateFormat.format(
						"yyyy-MM-dd HH:mm:ss", nowtime);
				starttime = nowtimeSequence.toString();
				Log.v("time", starttime);

			}
		});
		endbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				chronometer.stop();
				long time = System.currentTimeMillis();
				CharSequence endstr = DateFormat.format("yyyy-MM-dd HH:mm:ss",
						time);
				endtime = endstr.toString();
				// 需要传3个参数：开始时间，结束时间，阅读开始页码
				Intent endIntent = new Intent();
				startpage = editText.getText().toString();
				endIntent.putExtra("starttime", starttime);
				endIntent.putExtra("startpage", startpage);
				endIntent.putExtra("endtime", endtime);
				endIntent.putExtra("bookid", ids);
				Log.v("endreading", "end");
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
		// super.onBackPressed();
		Intent mainIntent = new Intent();
		mainIntent.setClass(getApplicationContext(), MainActivity.class);
		startActivity(mainIntent);
		finish();
	}

}
