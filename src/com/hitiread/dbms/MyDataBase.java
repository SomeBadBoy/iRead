package com.hitiread.dbms;

import java.util.ArrayList;

import com.hitiread.entity.BookInfo;
import com.hitiread.entity.ReadProgress;
import com.hitiread.view.BookView;

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
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
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

	public String getBitmap(int id)
	{
		String path = BookView.getSDPath();
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase.rawQuery(
				"select isbn from bookinfo where _id='" + id + "'", null);
		cursor.moveToFirst();
		String isbn = cursor.getString(cursor.getColumnIndex("isbn"));
		String filename = path + isbn + ".png";
		mydatabase.close();
		return filename;
	}

	public void toInsert(BookInfo book)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase
				.execSQL("insert into bookinfo(title,author,publisher,publishdate"
						+ ",summary,pages,isbn)values('"
						+ book.getTitle()
						+ "','"
						+ book.getAuthor()
						+ "','"
						+ book.getPublisher()
						+ "','"
						+ book.getPublishDate()
						+ "','"
						+ book.getSummary()
						+ "','"
						+ book.getPages()
						+ "','" + book.getISBN() + "')");
		mydatabase.close();
	}

	public void toInsert(ReadProgress progress)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase
				.execSQL("insert into readprogress(bookid,startpage,endpage,totalpage,"
						+ "starttime,endtime)values('"
						+ progress.getBookIds()
						+ "','"
						+ progress.getStartPage()
						+ "','"
						+ progress.getEndPage()
						+ "','"
						+ progress.getTotalPage()
						+ "','"
						+ progress.getStartTime()
						+ "','"
						+ progress.getEndTime() + "')");
		mydatabase.close();
	}

	public void toUpdate(ReadProgress progress)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase.execSQL("");
		mydatabase.close();
	}

	public void toDelete(int ids, int type)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		switch (type) {
		case BookInfo.BOOK_INFO:
			mydatabase.execSQL("delete from bookinfo where _id=" + ids + "");
			mydatabase.execSQL("delete from readprogress where bookid=" + ids
					+ "");
			break;
		case ReadProgress.READ_PROGRESS:
			mydatabase
					.execSQL("delete from readprogress where _id=" + ids + "");
		default:
			break;
		}
		mydatabase.close();
	}

	public String getStartPageByBookId(int ids)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		Log.v("databse", "cursor");
		Cursor cursor = mydatabase.rawQuery("select * from readprogress"
				+ " where bookid='" + ids + "'", null);
		Log.v("database", "cursor null");
		String start="";
		if(cursor.moveToFirst())
			start = cursor.getString(cursor.getColumnIndex("endpage"));
		mydatabase.close();
		return start;
	}
}
