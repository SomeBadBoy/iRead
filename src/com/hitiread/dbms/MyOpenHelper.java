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
		//table book info
		db.execSQL("create table bookinfo("
				+"_id INTEGER PRIMARY KEY autoincrement,"
				+"title text,"
				+"author text,"
				+"publisher text,"
				+"publishdate text,"
				+"summary text,"
				+"pages text,"
				+"isbn text)");
		//table read note
		db.execSQL("create table readnote("
				+"_id INTEGER PRIMARY KEY autoincrement,"
				+"bookid integer,"
				+"title text,"
				+"content text,"
				+"time text,"
				+"share integer)");
		//table read progress
		db.execSQL("create table readprogress("
				+"_id integer primary key autoincrement,"
				+"bookid integer,"
				+"startpage text,"
				+"endpage text,"
				+"totalpage text,"
				+"starttime text,"
				+"endtime text)");
		
	}

}
