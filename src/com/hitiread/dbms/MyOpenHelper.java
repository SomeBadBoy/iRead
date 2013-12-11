package com.hitiread.dbms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper
{
	public MyOpenHelper(Context context)
	{
		super(context, "myibook", null, 1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// table book info
		db.execSQL("create table bookinfo("
				+ "_id INTEGER PRIMARY KEY autoincrement," + "title text,"
				+ "author text," + "publisher text," + "publishdate text,"
				+ "summary text," + "pages text," + "lastread text,"
				+ "progress text," + "isbn text," + "recent text)");
		// table read note
		db.execSQL("create table readnote("
				+ "_id INTEGER PRIMARY KEY autoincrement," + "bookid integer,"
				+ "title text," + "content text," + "starttime text,"
				+ "endtime text," + "startpage text," + "endpage text,"
				+ "share integer)");
		// table classify
		// db.execSQL("create table classify("
		// + "_id INTEGER PRIMARY KEY autoincrement,"
		// + "classifytag text," + "num long)");

	}

}
