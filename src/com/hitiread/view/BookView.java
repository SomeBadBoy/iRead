package com.hitiread.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hitiread.view.R;
import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.BookInfo;
import com.hitiread.entity.Tag;
import com.hitwwq.scanner.Util;

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
 * 展示图书信息界面，能够添加图书，并选择分类；
 * 并能够保存图书的封面图片；
 */
public class BookView extends Activity
{
	private Intent intent;
	private TextView title, author, publisher, date, isbn, summary;
	private ImageView cover;
	private Button btn;
	private MyDataBase myDataBase;
	private BookInfo book;
// private ArrayList<Classify> classify;
	private String[] tag;
	private String choice;

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
		// InitClassify();
		getTagArray();
		btn.setOnClickListener(listener);
	}

	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			if (tag.length == 0)
			{
				choice = "无分类";
			}
			else {
				choice = tag[0];
			}
			AlertDialog.Builder builder = new AlertDialog.Builder(BookView.this);
			builder.setTitle("选择分类");
			builder.setSingleChoiceItems(tag, 0, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					choice = tag[which];
					Log.v("tag", "which="+which);
					Log.v("tag", "choice="+choice);
					Log.v("tag", "tag="+tag[which]);
					Log.v("num", "tagnum="+myDataBase.toFindTagNameByName("choice"));
				}
			});
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
					Log.v("bookview", choice);
					//need to find if it exits tag named tag[choice]
					//if it exits the number must increase;
					//or it need to create a new column;
					long num = 0;
					num = myDataBase.toFindTagNameByName(choice);
					Log.v("num", "num="+num);
					if (num == 0)
					{
						Tag t = new Tag(choice, num);
						myDataBase.toInsert(t);
						Log.v("tag", choice);
					}
					myDataBase.toUpdateNumberByTagName(choice, num+1);
					book.setTag(choice);
					book.setTimes(0);
					Log.v("book", book.getTitle()+book.getTag()+book.getSummary());
					myDataBase.toInsert(book);
					saveBitmap(book);
					Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_LONG)
							.show();
					Intent intent = new Intent();
					intent.setClass(getApplicationContext(), MainActivity.class);
					startActivity(intent);
					BookView.this.finish();
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
			return;
		}
	};

	public void onBackPressed()
	{
		// TODO Auto-generated method stub
		// super.onBackPressed();
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
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist)
		{
			sdPath = Environment.getExternalStorageDirectory();// 获取跟目录
		}
		return sdPath.toString() + "/";
	}

	public void getTagArray()
	{
		tag = new String[Util.array.length()];
		for (int i = 0; i < Util.array.length(); i++)
		{
			try
			{
				JSONObject object = Util.array.getJSONObject(i);
				tag[i] = object.getString("name");
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
