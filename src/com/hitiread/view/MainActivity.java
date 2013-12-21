package com.hitiread.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hitiread.dbms.MyAdapter;
import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.BookInfo;
import com.hitwwq.scanner.ScanAdd;
import com.hitwwq.scanner.ScannerActivity;
import com.hitwwq.scanner.Util;

public class MainActivity extends Activity
{
	private Button scanbtn = null;
	private Button progressbtn = null;
	private Button classifybtn = null;
	private Button intbtn = null;
	ListView lv;
	LayoutInflater inflater;
	ArrayList<BookInfo> array;
	MyDataBase mdb;
	private Handler handler;
	private ProgressDialog mpd;
	public final static int REQUEST_CODE = 1;
	public final static int LISTALERTDIALOG = 2;
	DownloadThread thread = null;
	private String str = null;
	private SharedPreferences mSharedPreferences;
	private final String[] scantype = { "扫描添加", "手动输入" };

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.listView1);
		inflater = getLayoutInflater();
		mdb = new MyDataBase(this);
		array = mdb.getArray();
		MyAdapter adapter = new MyAdapter(inflater, array);
		Log.v("main", "listview");
		lv.setAdapter(adapter);
		mSharedPreferences = getSharedPreferences("ReadRemind",
				Context.MODE_PRIVATE);
		boolean flag = mSharedPreferences.getBoolean("remind", false);
		if (flag)
		{
			Log.v("remind", "null");
			if (array != null)
			{
				Log.v("remind", "remind");
				setReadRemind();
				SharedPreferences.Editor editor = mSharedPreferences.edit();
				editor.putBoolean("remind", false);
				editor.commit();
			}
		}

		scanbtn = (Button) findViewById(R.id.scan_add_button);
		scanbtn.setOnClickListener(listener);
		progressbtn = (Button) findViewById(R.id.progress_button);
		progressbtn.setOnClickListener(listener);
		classifybtn = (Button)findViewById(R.id.classify_button);
		classifybtn.setOnClickListener(listener);
		intbtn = (Button) findViewById(R.id.interest_button);
		intbtn.setOnClickListener(listener);
		// 点击阅读
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						StartReading.class);
				intent.putExtra("ids", array.get(position).getId());
				startActivity(intent);
				MainActivity.this.finish();
			}
		});

		lv.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id)
			{
				// TODO Auto-generated method stub
				Log.v("main", "long");
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("删除")
						.setMessage("是否删除书籍")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1)
									{
										// TODO Auto-generated method stub
										mdb.toDelete(array.get(position)
												.getId(), BookInfo.BOOK_INFO);
										array = mdb.getArray();
										MyAdapter myAdapter = new MyAdapter(
												inflater, array);
										lv.setAdapter(myAdapter);
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1)
									{
										// TODO Auto-generated method stub

									}
								}).create().show();
				return true;
			}
		});
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg)
			{
				// TODO Auto-generated method stub
				//super.handleMessage(msg);
				BookInfo book = (BookInfo) msg.obj;

				// 进度条消失
				mpd.dismiss();

				Intent scanIntent = new Intent(MainActivity.this,
						BookView.class);
				scanIntent.putExtra(BookInfo.class.getName(), book);
				startActivity(scanIntent);
				MainActivity.this.finish();
			}
		};
	}

	@SuppressLint("HandlerLeak")
	private android.view.View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v)
		{
			Button tempbtn = (Button) v;
			switch (tempbtn.getId()) {
			case R.id.progress_button:
				Intent progintent = new Intent();
				progintent.setClass(MainActivity.this, NoteActivity.class);
				startActivity(progintent);
				break;
			case R.id.classify_button:
				Intent classifyintent = new Intent();
				Log.v("classify", "intent");
				classifyintent.setClass(MainActivity.this, TagView.class);
				startActivity(classifyintent);
				break;
			case R.id.interest_button:
				Intent interint = new Intent();
				interint.setClass(getApplicationContext(), InterestActivity.class);
				startActivity(interint);
				MainActivity.this.finish();
				break;
			case R.id.scan_add_button:

				new AlertDialog.Builder(MainActivity.this)
						.setTitle("添加方式")
						.setItems(scantype,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which)
									{
										// TODO Auto-generated method stub
										Log.v("dialog", "which" + which);
										switch (which) {
										case 0:
											Intent newintent = new Intent();
											newintent.setClass(
													getApplicationContext(),
													ScannerActivity.class);
											startActivityForResult(newintent,
													REQUEST_CODE);
											break;
										case 1:
											Intent intent = new Intent();
											intent.setClass(
													getApplicationContext(),
													ScanAdd.class);
											startActivityForResult(intent,
													REQUEST_CODE);
											break;

										default:
											break;
										}
									}
								}).show();

				
				break;

			default:
				break;
			}
		}
	};

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (requestCode == REQUEST_CODE)
		{
			if (resultCode == ScannerActivity.RESULT_CODE
					|| resultCode == ScanAdd.RESULT_CODE)
			{
				Bundle bundle = data.getExtras();
				str = bundle.getString("ISBN");
				mpd = new ProgressDialog(this);
				mpd.setMessage("请稍候，正在读取信息...");
				mpd.show();

				String urlstr = "https://api.douban.com/v2/book/isbn/" + str;
				Log.i("OUTPUT", urlstr);
				// 扫到ISBN后，启动下载线程下载图书信息
				thread = new DownloadThread(urlstr);
				thread.start();
			}
		}

	}

	private class DownloadThread extends Thread
	{
		String url = null;

		public DownloadThread(String urlstr)
		{
			url = urlstr;
		}

		public void run()
		{
			Looper.prepare();
			String result = Util.Download(url);
			BookInfo book = new Util().parseBookInfo(result);
			Log.i("OUTPUT", book.getSummary() + book.getAuthor());
			// 给主线程UI界面发消息，提醒下载信息，解析信息完毕

			Message msg = Message.obtain();
			msg.obj = book;
			handler.sendMessage(msg);
			Log.i("OUTPUT", "send over");
			Looper.loop();
		}
	}

	private void setReadRemind()
	{
		final int id = mSharedPreferences.getInt("id", 1);
		final String name = mSharedPreferences.getString("name", "");
		final double progress = mSharedPreferences.getFloat("progress", 0);
		Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("阅读提醒");
		builder.setIcon(R.drawable.remind);
		builder.setMessage("应该看看\""+name+"\"这本书了哦！");
		builder.setPositiveButton("去看看", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1)
			{
				// TODO Auto-generated method stub
				Intent newintent = new Intent(getApplicationContext(),
						StartReading.class);
				Log.v("remind", "id="+id);
				Log.v("remind", "bookname="+name);
				newintent.putExtra("ids", id);
				startActivity(newintent);
				MainActivity.this.finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				// TODO Auto-generated method stub
				
			}
		});
		builder.create().show();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		if (thread != null)
			if (thread.isAlive())
			{
				Log.v("thread", "onPause");
				thread.interrupt();
			}
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		if (thread != null)
		{
			if (thread.isInterrupted())
			{
				Log.v("thread", "onResume");
				thread = null;
				thread = new DownloadThread(
						"https://api.douban.com/v2/book/isbn/" + str);
				thread.start();
			}
		}
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
		if (thread != null)
			if (thread.isAlive())
			{
				Log.v("thread", "onStop");
				thread.interrupt();
			}
	}
	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.putBoolean("remind", true);
		editor.commit();
	}

}
