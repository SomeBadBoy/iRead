package com.hitiread.view;

import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.ReadNote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EndReading extends Activity
{

	private String starttime, endtime, startpage;
	private EditText title, endpage, note;
	private Button okbtn;
	private int bookid;
	private MyDataBase myDataBase;
	private SharedPreferences mSharedPreferences;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reading_end);

		myDataBase = new MyDataBase(getApplicationContext());
		Intent intent = getIntent();
		starttime = intent.getExtras().getString("starttime");
		endtime = intent.getExtras().getString("endtime");
		Log.v("endreading", endtime);
		startpage = intent.getExtras().getString("startpage");
		bookid = intent.getExtras().getInt("bookid");
		title = (EditText) findViewById(R.id.content_title);
		endpage = (EditText) findViewById(R.id.end_page);
		note = (EditText) findViewById(R.id.note_content);
		okbtn = (Button) findViewById(R.id.content_ok_btn);
		tv = (TextView) findViewById(R.id.endreading_bookname);
		tv.setText(myDataBase.toFindBookNameByID(bookid));
		okbtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				myDataBase = new MyDataBase(getApplicationContext());
				String end = endpage.getText().toString();
				String totalpage = myDataBase.getTotalPageByBookId(bookid);
				Log.v("endreading", "total"+totalpage);
				if(end == null || end.equals(""))
				{
					endpage.setError("结束页码不能为空");
					return ;
				}
				if (Integer.valueOf(end) > Integer.valueOf(totalpage))
				{
					endpage.setError("本书没有那么多页");
					return ;
				}
				
				Log.v("endreading", "endpage"+endpage.getText().toString());
				Log.v("endtime", endtime);
				ReadNote readnote = new ReadNote(bookid, starttime, endtime,startpage,
						endpage.getText().toString(),title.getText().toString(), note.getText().toString(), 0);
				myDataBase.toInsert(readnote);
				String pages = myDataBase.getTotalPageByBookId(bookid);
				Log.v("endreading", "pages"+pages);
				double endpg = Double.parseDouble(endpage.getText().toString());
				double total = Double.parseDouble(pages);
				double prog = (endpg / total)*100;
				prog = (double)Math.round(prog*100)/100;
				Log.v("endreading", String.valueOf(prog));
				myDataBase.toUpdateProgressWihtEndPage(bookid, endpage.getText().toString(), String.valueOf(prog));
				myDataBase.toUpdateRecentByBookId(bookid, endtime);
				
				mSharedPreferences = getSharedPreferences("ReadRemind",Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = mSharedPreferences.edit();
				editor.putBoolean("flag", true);
				editor.putInt("id", bookid);
				editor.putString("name", myDataBase.toFindBookNameByID(bookid));
				editor.putFloat("progress", (float)(prog));
				editor.commit();
				
				Intent aIntent = new Intent();
				aIntent.setClass(getApplicationContext(), MainActivity.class);
				startActivity(aIntent);
				EndReading.this.finish();
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		//super.onBackPressed();
		new AlertDialog.Builder(EndReading.this)
		.setTitle("退出将丢失未保存数据，确认退出？")
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(EndReading.this, MainActivity.class);
				startActivity(intent);
				EndReading.this.finish();
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				// TODO Auto-generated method stub
				
			}
		})
		.create().show();
	}

}
