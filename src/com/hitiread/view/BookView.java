package com.hitiread.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.hitiread.view.R;
import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.BookInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wwq on 13-7-10.
 */
public class BookView extends Activity
{
	private Intent intent;
	private TextView title, author, publisher, date, isbn, summary;
	private ImageView cover;
	private Button btn;
	private MyDataBase myDataBase;
	private BookInfo book;
//	private ArrayList<Classify> classify;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.bookresult);

		title = (TextView) findViewById(R.id.bookview_title);
		author = (TextView) findViewById(R.id.bookview_author);
		publisher = (TextView) findViewById(R.id.bookview_publisher);
		date = (TextView) findViewById(R.id.bookview_publisherdate);
		isbn = (TextView) findViewById(R.id.bookview_isbn);
		summary = (TextView) findViewById(R.id.bookview_summary);
		cover = (ImageView) findViewById(R.id.bookview_cover);

		myDataBase = new MyDataBase(this);
		intent = getIntent();
		// BookInfo book=(BookInfo)
		// getIntent().getSerializableExtra(BookInfo.class.getName());
		book = (BookInfo) intent.getParcelableExtra(BookInfo.class.getName());

		title.setText(book.getTitle());
		author.setText(book.getAuthor());
		publisher.setText(book.getPublisher());
		date.setText(book.getPublishDate());
		isbn.setText(book.getISBN());
		summary.setText(book.getSummary());
		cover.setImageBitmap(book.getBitmap());
		btn = (Button) findViewById(R.id.bookview);

		btn.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			myDataBase.toInsert(book);
/*			new AlertDialog.Builder(BookView.this)
			.setTitle("��ӵ�����")
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					
				}
			})
			.setNeutralButton("�½�����", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					
				}
			})
			.setItems(1,new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					
				}
			})
			.show();
*/
			Log.v("bookview", "Onclick");
			saveBitmap(book);
			Toast.makeText(getApplicationContext(), "��ӳɹ�", Toast.LENGTH_LONG)
					.show();
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			BookView.this.finish();
		}
	};
	
	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Intent anotherIntent = new Intent();
		anotherIntent.setClass(getApplicationContext(), MainActivity.class);
		startActivity(anotherIntent);
		BookView.this.finish();
	}

	private void saveBitmap(BookInfo book)
	{
		String filename = getSDPath();
		File file = new File(filename + book.getISBN() + ".png");
		try
		{
			file.createNewFile();
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		FileOutputStream fileout = null;
		try
		{
			fileout = new FileOutputStream(file);
		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
		}
		Bitmap bitmap = book.getBitmap();
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileout);
		try
		{
			fileout.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			fileout.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static String getSDPath()
	{
		File sdPath = null;
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist)
		{
			sdPath = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼
		}
		return sdPath.toString() + "/";
	}
}
