package com.example.readbook;

import android.database.sqlite.*;
import android.content.*;
import android.database.*;

public class DBMS extends SQLiteOpenHelper
{	
	
	public DBMS(Context c)
	{
		super(c,DB_NAME,null,1);
	}
	
	public void onCreate(SQLiteDatabase db)
	{
		String Create_Table_Book;
		String Cretae_Table_ReadLog;
		
		this.db=db;
		db.execSQL(Create_Table_Book);
		db.execSQL(Cretae_Table_ReadLog);
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