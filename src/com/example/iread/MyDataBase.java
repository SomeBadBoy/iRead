package com.example.iread;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MyDataBase
{
	Context context;
	MyOpenHelper myOpenHelper;
	SQLiteDatabase mydatabase;
	
	public MyDataBase(Context context,String name,CursorFactory factory,
			int version)
	{
		this.context = context;
		//name is the dbname,factory often null,version often 1
		myOpenHelper = new MyOpenHelper(context,name,factory,version);
	}
	
}
