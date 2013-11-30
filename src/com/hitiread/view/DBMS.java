package com.hitiread.view;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMS extends SQLiteOpenHelper
{	
	
	public DBMS(Context c)
	{
		super(c,DB_NAME,null,1);
	}
	
	public void onCreate(SQLiteDatabase db)
	{
		String Create_Table_Book="create table book(book_id int,book_name varchar(50),isbn10 varchar(10)," +
				"isbn13 varchar(13),title varchar(30),origin_title varchar(30),subtitle varchar(30),"+
				"image varchar(50),author varchar(30),translator varchar(30),publisher varchar(30),"+
				"pubdate varchar(30),price double,pages int,summary varchar(1000),primary key(book_id))";
		String Cretae_Table_ReadLog;
		
		this.db=db;
		db.execSQL(Create_Table_Book);
//		db.execSQL(Cretae_Table_ReadLog);
	}
	
	protected void Exec(String cmd)
	{
		db.execSQL(cmd);
	}
	
	protected Cursor Query(String Query_cmd)
	{
		Cursor c;		
		c=rawQuery(Query_cmd,null);
		
		return c;
	}
	
	private Cursor rawQuery(String query_cmd, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void close()
	{
		if(db!=null)
			db.close();
	}
	
	public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
	{
	}
	
	protected static final String DB_NAME="ReadBook.db"; 
	protected SQLiteDatabase db;
	
}