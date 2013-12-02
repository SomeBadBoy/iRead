package com.hitiread.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import com.hitwwq.scanner.Util;

public class MainActivity extends Activity
{
	private Button btn = null;
	ListView lv;
	LayoutInflater inflater;
	ArrayList<BookInfo> array;
	MyDataBase mdb;
	private Handler handler;
	private ProgressDialog mpd;
	public final static int REQUEST_CODE = 1;

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

		btn = (Button) findViewById(R.id.scan_add_button);
		btn.setOnClickListener(listener);
		// ����Ķ�
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
				new AlertDialog.Builder(getApplicationContext())
						.setTitle("ɾ��")
						.setMessage("�Ƿ�ɾ���鼮")
						.setNegativeButton("ȡ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1)
									{
										// TODO Auto-generated method stub

									}
								})
						.setPositiveButton("ȷ��",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which)
									{
										// TODO Auto-generated method stub
										mdb.toDelete(array.get(position)
												.getId(), BookInfo.BOOK_INFO);
										array = mdb.getArray();
										MyAdapter adapter = new MyAdapter(
												getLayoutInflater(), array);
										lv.setAdapter(adapter);
									}
								}).create().show();
				return true;
			}
		});
	}

	@SuppressLint("HandlerLeak")
	private android.view.View.OnClickListener listener = new View.OnClickListener() 
	{
		@Override
		public void onClick(View v)
		{
			Button tempbtn = (Button) v;
			switch (tempbtn.getId()) {
			case R.id.classify_button:
				break;
			
			case R.id.scan_add_button:
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ScanAdd.class);
				startActivityForResult(intent, REQUEST_CODE);
				/*
				 * ()) { case R.id.scan_add_butt
				 * intent.setClass(MainActivity.this, ScannerActivity.class);
				 * startActivityForResult(intent, REQUEST_CODE);
				 */
				handler = new Handler()
				{

					@Override
					public void handleMessage(Message msg)
					{
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						BookInfo book = (BookInfo) msg.obj;

						// ��������ʧ
						mpd.dismiss();

						Intent scanIntent = new Intent(MainActivity.this,
								BookView.class);
						// Bundle bd=new Bundle();
						// bundle.putSerializable(key,object);
						// bd.putSerializable(BookInfo.class.getName(),book);
						// intent.putExtras(bd);
						scanIntent.putExtra(BookInfo.class.getName(), book);
						startActivity(scanIntent);
						MainActivity.this.finish();
					}
				};
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
			if (resultCode == ScanAdd.RESULT_CODE)
				;
			{
				Bundle bundle = data.getExtras();
				String str = bundle.getString("ISBN");
				Toast.makeText(MainActivity.this, str, Toast.LENGTH_LONG);
				mpd = new ProgressDialog(this);
				mpd.setMessage("���Ժ����ڶ�ȡ��Ϣ...");
				mpd.show();

				String urlstr = "https://api.douban.com/v2/book/isbn/" + str;
				Log.i("OUTPUT", urlstr);
				// ɨ��ISBN�����������߳�����ͼ����Ϣ
				new DownloadThread(urlstr).start();
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
			String result = Util.Download(url);
			Log.i("OUTPUT", "download over");
			BookInfo book = new Util().parseBookInfo(result);
			Log.i("OUTPUT", "parse over");
			Log.i("OUTPUT", book.getSummary() + book.getAuthor());
			// �����߳�UI���淢��Ϣ������������Ϣ��������Ϣ���

			Message msg = Message.obtain();
			msg.obj = book;
			handler.sendMessage(msg);
			Log.i("OUTPUT", "send over");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
