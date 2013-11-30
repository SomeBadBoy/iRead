package com.hitiread.entity;

import com.hitiread.view.R;
import com.hitiread.dbms.MyDataBase;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ListView;
//显示书库界面，可以添加书籍到分类
public class BookStore extends Activity
{

	public static String DATABASE_NAME = "bookstore";
	public static int VERSION = 1;
	
	LayoutInflater inflater;
	private ListView lv;
	MyDataBase myDataBase;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.book_store);
		lv = (ListView)findViewById(R.id.booklist);
		
		inflater = getLayoutInflater();
		myDataBase = new MyDataBase(this);
	}

}
