package com.hitiread.dbms;

import java.util.ArrayList;

import com.hitiread.entity.BookInfo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDataBase
{
	Context context;
	MyOpenHelper myOpenHelper;
	SQLiteDatabase mydatabase;

	public MyDataBase(Context context)
	{
		this.context = context;
		// name is the dbname,factory often null,version often 1
		myOpenHelper = new MyOpenHelper(context);
	}

	public ArrayList<BookInfo> getArray()
	{
		ArrayList<BookInfo> array = new ArrayList<BookInfo>();
		ArrayList<BookInfo> array1 = new ArrayList<BookInfo>();
		mydatabase = myOpenHelper.getWritableDatabase();
		Log.v("database", "beforequery");
		Cursor cursor = mydatabase.rawQuery("select * from bookinfo", null);
		Log.v("database", "afgerquery");
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String author = cursor.getString(cursor.getColumnIndex("author"));
			String publisher = cursor.getString(cursor
					.getColumnIndex("publisher"));
			String publishdate = cursor.getString(cursor
					.getColumnIndex("publishdate"));
			String pages = cursor.getString(cursor.getColumnIndex("pages"));
			String isbn = cursor.getString(cursor.getColumnIndex("isbn"));
			String summary = cursor.getString(cursor.getColumnIndex("summary"));
			BookInfo book = new BookInfo(id, title, author, publisher,
					publishdate, summary, isbn, pages);
			array.add(book);
			cursor.moveToNext();
		}
		mydatabase.close();
		for (int i = array.size(); i > 0; i--)
		{
			array1.add(array.get(i - 1));
		}
		return array1;
	}

	public void toInsert(BookInfo book)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase
				.execSQL("insert into bookinfo(title,author,publisher,publishdate"
						+ ",summary,pages,isbn)values('"
						+ book.getTitle() + "','"
						+ book.getAuthor() + "','"
						+ book.getPublisher() + "','"
						+ book.getPublishDate() + "','"
						+ book.getSummary() + "','"
						+ book.getPages() + "','"
						+ book.getISBN() + "')");
		mydatabase.close();
	}

	public void toDelete(int ids)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase.execSQL("delete from bookinfo where ids=" + ids + "");
		mydatabase.close();
	}
}
