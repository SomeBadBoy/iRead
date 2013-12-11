package com.hitiread.view;

import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.ReadNote;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EndReading extends Activity
{

	private String starttime, endtime, startpage;
	private EditText title, endpage, note;
	private Button okbtn;
	private int bookid;
	private MyDataBase myDataBase;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reading_end);

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
