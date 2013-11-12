package com.example.iread;

import java.util.ArrayList;

import android.os.Bundle;
import android.R.string;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.ListView;
//显示书库界面，可以添加书籍到分类
public class BookStore extends Activity
{

	public static String DATABASE_NAME = "bookstore";
	public static int VERSION = 1;
	
	LayoutInflater inflater;
	ArrayList<bookinfo> array;
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
		myDataBase = new MyDataBase(this, DATABASE_NAME, null, VERSION);
	}

}
