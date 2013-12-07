package com.hitiread.dbms;

import java.util.ArrayList;
import com.hitiread.entity.BookInfo;
import com.hitiread.entity.ChildEntity;
import com.hitiread.entity.GroupEntity;
import com.hitiread.entity.ReadNote;
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
			String lastread = cursor.getString(cursor.getColumnIndex("lastread"));
			String progress = cursor.getString(cursor.getColumnIndex("progress"));
			BookInfo book = new BookInfo(id, title, author, publisher,
					publishdate, summary, isbn, pages, lastread, progress);
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
	
	public ReadNote getReadNote(int ids)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		ReadNote note = null;
		Cursor cursor = mydatabase
				.rawQuery("select * from readnote where _id='"+ids+"'", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String starttime = cursor.getString(cursor.getColumnIndex("starttime"));
			String endtime = cursor.getString(cursor.getColumnIndex("endtime"));
			String startpage = cursor.getString(cursor.getColumnIndex("startpage"));
			String endpage = cursor.getString(cursor.getColumnIndex("endpage"));
			note = new ReadNote(ids, starttime, endtime, startpage, endpage, title, content, 0);
			cursor.moveToNext();
		}
		return note;
	}
	
	public ArrayList<GroupEntity> getBookNote()
	{
		ArrayList<GroupEntity> array = new ArrayList<GroupEntity>();
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase.rawQuery("select _id,title from bookinfo", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String bookname = cursor.getString(cursor.getColumnIndex("title"));
			GroupEntity temp = new GroupEntity(id, bookname);
			array.add(temp);
			cursor.moveToNext();
		}
		mydatabase.close();
		return array;
	}

	public ArrayList<ChildEntity> getNoteList(int id)
	{
		ArrayList<ChildEntity> array = new ArrayList<ChildEntity>();
		ArrayList<ChildEntity> array1 = new ArrayList<ChildEntity>();
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase
				.rawQuery("select _id,title,endtime from readnote where bookid ='"+id+"'",null);
		cursor.moveToFirst();
		Log.v("note", "8");
		while (!cursor.isAfterLast())
		{
			Log.v("note", "9");
			int noteid = cursor.getInt(cursor.getColumnIndex("_id"));
			Log.v("note", "id="+noteid);
			String title = cursor.getString(cursor.getColumnIndex("title"));
			Log.v("note", "title="+title);
			String endtime = cursor.getString(cursor.getColumnIndex("endtime"));
			Log.v("note", "endtime="+endtime);
			ChildEntity object = new ChildEntity(noteid, id, title, endtime);
			array1.add(object);
			cursor.moveToNext();
		}
		Log.v("note", "10");
		mydatabase.close();
		for (int i = array1.size(); i > 0; i--)
		{
			Log.v("note", "11");
			array.add(array1.get(i-1));
		}
		Log.v("note", "12"+array.size()+array.toString());
		return array;
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
						+ ",summary,pages,lastread,progress,isbn)values('"
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
						+"','"
						+ book.getLastRead()
						+"','"
						+book.getProgress()
						+ "','" + book.getISBN() + "')");
		mydatabase.close();
	}
/*
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
*/
	public void toInsert(ReadNote note)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase
				.execSQL("insert into readnote(bookid,starttime,endtime,startpage,"
						+"endpage,title,content,share)values('" 
						+ note.getBookId() 
						+ "','"
						+ note.getStartTime() 
						+ "','" 
						+ note.getEndTime()
						+ "','" 
						+ note.getStartPage()
						+ "','"
						+ note.getEndPage()
						+ "','"
						+ note.getTitle() 
						+ "','" 
						+ note.getContent()
						+ "','" 
						+ note.getShare() + "')");
		mydatabase.close();
	}

	public void toUpdateProgressWihtEndPage(int id, String end, String prog)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase.execSQL("update bookinfo set lastread='" + end
				+ "' where _id='" + id + "'");
		double progress = Double.parseDouble(prog);
		mydatabase.execSQL("update bookinfo set progress='" + Double.toString(progress) 
				+ "' where _id='" + id + "'");
		mydatabase.close();
	}

	public void toDelete(int ids, int type)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		switch (type) {
		case BookInfo.BOOK_INFO:
			mydatabase.execSQL("delete from bookinfo where _id='" + ids + "'");
			mydatabase.execSQL("delete from readnote where bookid='" + ids
					+ "'");
			break;
		
		default:
			break;
		}
		mydatabase.close();
	}

	public String getStartPageByBookId(int ids)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		Log.v("databse", "cursor");
		Cursor cursor = mydatabase.rawQuery("select lastread from bookinfo"
				+ " where _id='" + ids + "'", null);
		Log.v("database", "cursor null");
		String start = "";
		if (cursor.moveToFirst())
			start = cursor.getString(cursor.getColumnIndex("lastread"));
		mydatabase.close();
		return start;
	}
	public String getTotalPageByBookId(int ids)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		Log.v("databse", "cursor");
		Cursor cursor = mydatabase.rawQuery("select pages from bookinfo"
				+ " where _id='" + ids + "'", null);
		Log.v("database", "cursor null");
		String page = "";
		if (cursor.moveToFirst())
			page = cursor.getString(cursor.getColumnIndex("pages"));
		mydatabase.close();
		return page;
	}
}
