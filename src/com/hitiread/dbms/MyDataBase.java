package com.hitiread.dbms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.hitiread.entity.BookInfo;
import com.hitiread.entity.ChildEntity;
import com.hitiread.entity.Tag;
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
		Log.v("database", "afterquery");
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
			String lastread = cursor.getString(cursor
					.getColumnIndex("lastread"));
			String progress = cursor.getString(cursor
					.getColumnIndex("progress"));
			long times = cursor.getLong(cursor.getColumnIndex("times"));
			String recent = cursor.getString(cursor.getColumnIndex("recent"));
			String tag = cursor.getString(cursor.getColumnIndex("tag"));
			BookInfo book = new BookInfo(id, title, author, publisher,
					publishdate, summary, isbn, pages, lastread, progress,
					recent, times, tag);
			array.add(book);
			cursor.moveToNext();
		}
		mydatabase.close();
		Collections.sort(array, new SortByRecent());
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
		Cursor cursor = mydatabase.rawQuery(
				"select * from readnote where _id='" + ids + "'", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String content = cursor.getString(cursor.getColumnIndex("content"));
			String starttime = cursor.getString(cursor
					.getColumnIndex("starttime"));
			String endtime = cursor.getString(cursor.getColumnIndex("endtime"));
			String startpage = cursor.getString(cursor
					.getColumnIndex("startpage"));
			String endpage = cursor.getString(cursor.getColumnIndex("endpage"));
			note = new ReadNote(ids, starttime, endtime, startpage, endpage,
					title, content, 0);
			cursor.moveToNext();
		}
		mydatabase.close();
		return note;
	}

	public ArrayList<Tag> getTag()
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase.rawQuery("select tag,num from classify",
				null);
		ArrayList<Tag> array = new ArrayList<Tag>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			String tag = cursor.getString(cursor.getColumnIndex("tag"));
			long num = cursor.getLong(cursor.getColumnIndex("num"));
			Tag temp = new Tag(tag, num);
			array.add(temp);
			cursor.moveToNext();
		}
		mydatabase.close();
		return array;
	}

	public ArrayList<String> getBooksByTagName(String names)
	{
		ArrayList<String> arrayList = new ArrayList<String>();
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase.rawQuery("select title from bookinfo where tag='"+names+"'", null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			arrayList.add(cursor.getString(cursor.getColumnIndex("title")));
			cursor.moveToNext();
		}
		mydatabase.close();
		return arrayList;
	}
	public ArrayList<GroupEntity> getBookNote()
	{
		ArrayList<GroupEntity> array = new ArrayList<GroupEntity>();
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase.rawQuery("select _id,title from bookinfo",
				null);
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
		Cursor cursor = mydatabase.rawQuery(
				"select _id,title,endtime from readnote where bookid ='" + id
						+ "'", null);
		cursor.moveToFirst();
		Log.v("note", "8");
		while (!cursor.isAfterLast())
		{
			Log.v("note", "9");
			int noteid = cursor.getInt(cursor.getColumnIndex("_id"));
			Log.v("note", "id=" + noteid);
			String title = cursor.getString(cursor.getColumnIndex("title"));
			Log.v("note", "title=" + title);
			String endtime = cursor.getString(cursor.getColumnIndex("endtime"));
			Log.v("note", "endtime=" + endtime);
			ChildEntity object = new ChildEntity(noteid, id, title, endtime);
			array1.add(object);
			cursor.moveToNext();
		}
		Log.v("note", "10");
		mydatabase.close();
		for (int i = array1.size(); i > 0; i--)
		{
			Log.v("note", "11");
			array.add(array1.get(i - 1));
		}
		Log.v("note", "12" + array.size() + array.toString());
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

	public  Cursor getAllNote()
	{
		mydatabase =myOpenHelper.getWritableDatabase();
		Cursor cursor =mydatabase.rawQuery(
				"select bookinfo.tag,readnote.endtime from bookinfo inner join readnote on bookinfo._id=readnote.bookid",null);
		return cursor;
	}
	public BookInfo getReadingRemind()
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		ArrayList<BookInfo> arrayList = getArray();
		Collections.sort(arrayList, new SortByProgress());
		mydatabase.close();
		BookInfo bookInfo = arrayList.get(0);
		Log.v("remind", bookInfo.getTitle()+bookInfo.getProgress());
		return bookInfo;
	}
	public void toInsert(BookInfo book)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase
				.execSQL("insert into bookinfo(title,author,publisher,publishdate"
						+ ",summary,pages,lastread,progress,isbn,recent,times,tag)values('"
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
						+ "','"
						+ book.getLastRead()
						+ "','"
						+ book.getProgress()
						+ "','"
						+ book.getISBN()
						+ "','"
						+ book.getRecent()
						+ "','"
						+ book.getTimes()
						+ "','"
						+ book.getTag() + "')");

		mydatabase.close();
	}

	public void toInsert(ReadNote note)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase
				.execSQL("insert into readnote(bookid,starttime,endtime,startpage,"
						+ "endpage,title,content,share)values('"
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
						+ "','" + note.getShare() + "')");
		mydatabase.close();
	}

	public void toInsert(Tag tag)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase.execSQL("insert into classify(tag,num)values('"
				+ tag.getTag() + "','" + tag.getNum() + "')");
		mydatabase.close();
	}

	public void toUpdateProgressWihtEndPage(int id, String end, String prog)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase.execSQL("update bookinfo set lastread='" + end
				+ "' where _id='" + id + "'");
		double progress = Double.parseDouble(prog);
		mydatabase.execSQL("update bookinfo set progress='"
				+ Double.toString(progress) + "' where _id='" + id + "'");
		mydatabase.close();
	}

	public void toUpdateReadTimesByBookId(int id)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase.rawQuery(
				"select times from bookinfo where _id='" + id + "'", null);
		long times;
		if (cursor.moveToFirst())
		{
			times = cursor.getLong(cursor.getColumnIndex("times"));
			times++;
			mydatabase.execSQL("update bookinfo set times='" + times
					+ "' where +id='" + id + "'");
		}
		mydatabase.close();
	}

	public void toUpdateRecentByBookId(int id, String recent)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		mydatabase.execSQL("update bookinfo set recent='" + recent
				+ "' where _id='" + id + "'");
		mydatabase.close();
	}

	public void toUpdateNumberByTagName(String name, long num)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		Log.v("num", "classifynum=" + num);
		mydatabase.execSQL("update classify set num='" + num + "' where tag='"
				+ name + "'");
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

	public long toFindTagNameByName(String name)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase.rawQuery(
				"select * from classify where tag like '%" + name + "%'", null);
		long num = 0;
		cursor.moveToFirst();
		if (!cursor.isAfterLast())
		{
			Log.v("move", "last");
			num = cursor.getLong(cursor.getColumnIndex("num"));
			Log.v("num", "num=" + num);
		}

		Log.v("num", "num=" + num);
		mydatabase.close();
		return num;
	}

	public String toFindBookNameByID(int id)
	{
		mydatabase = myOpenHelper.getWritableDatabase();
		Cursor cursor = mydatabase.rawQuery("select title from bookinfo where _id='"+id+"'", null);
		String name = null;
		cursor.moveToFirst();
		if (!cursor.isAfterLast())
		{
			name = cursor.getString(cursor.getColumnIndex("title"));
		}
		mydatabase.close();
		Log.v("name", "name="+name);
		return name;
	}
	class SortByRecent implements Comparator<BookInfo>
	{

		@Override
		public int compare(BookInfo lhs, BookInfo rhs)
		{
			// TODO Auto-generated method stub

			return lhs.getRecent().compareTo(rhs.getRecent());
		}

	}
	
	class SortByProgress implements Comparator<BookInfo>
	{

		@Override
		public int compare(BookInfo lhs, BookInfo rhs)
		{
			// TODO Auto-generated method stub
			double mprog,nprog;
			mprog = Double.valueOf(lhs.getProgress());
			nprog = Double.valueOf(rhs.getProgress());
			if (mprog > nprog)
			{
				return 1;
			} else if(mprog < nprog)
			{
				return -1;
			}
			else {
				if (lhs.getTimes() == rhs.getTimes())
				{
					return lhs.getLastRead().compareTo(rhs.getLastRead());
				}
				else {
					return (int) (lhs.getTimes() - rhs.getTimes());
				}
			}
		}
		
	}

}
