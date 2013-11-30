package com.hitiread.dbms;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyOpenHelper extends SQLiteOpenHelper
{
	public final static int BOOK_INFO=1;
	public final static int READ_NOTE=2;
	public final static int SCHEDULE=3;
	public final static int CLASSIFY=4;
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
		db.execSQL("create table bookinfo("
				+"_id INTEGER PRIMARY KEY autoincrement,"
				+"title text,"
				+"author text,"
				+"publisher text,"
				+"publishdate text,"
				+"summary text,"
				+"pages text,"
				+"isbn text)");
		db.execSQL("create table readnote("
				+"_id INTEGER PRIMARY KEY autoincrement,"
				+"title text,"
				+"content text,"
				+"time text,"
				+")");
		
	}
	
	

}
