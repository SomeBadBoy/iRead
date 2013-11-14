package com.example.readbook;

import android.database.*;
import android.content.*;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.*;

public class Book extends DBMS
{
	public Book(Context c)
	{
		super(c);
	}
	
	public void insert(int book_id,String book_name,String isbn10,String isbn13,String title,String origin_title,
			String subtitle,String images_small,String images_middle,String images_large,String author,
			String translator,String publisher,String pubdata,double price,int pages,String summary)
	{
		String Insert_cmd="insert into "+table_name+"("+book_id+book_name+isbn10+isbn13+title+
				origin_title+subtitle+images_small+images_middle+images_large+author+translator+
				publisher+pubdata+price+pages+summary+")";
		
		Exec(Insert_cmd);
	}
	
	public Cursor query(int id)
	{	
		String Query_cmd="select * from "+table_name+" where book_id="+id;
		Cursor c=Query(Query_cmd);
		
		return c;
	}

	public void delete(int id)
	{
		String Delete_cmd="delete from "+table_name+" where book_id="+id;
		
		Exec(Delete_cmd);
	}
	
	public int sum_pages(int id)
	{
		int page;
		final int page_column=15;
		String page_cmd="select pages from "+table_name+" where book_id="+id;
		Cursor c=Query(page_cmd);
		page=c.getInt(page_column);
		return page;
	}
	
	public void updata_bookname(int id,String name)
	{
		String Update_cmd="update "+table_name+" set "+"book_name="+name+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_isbn10(int id,String isbn10)
	{
		String Update_cmd="update "+table_name+" set "+"isbn10="+isbn10+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_isbn13(int id,String isbn13)
	{
		String Update_cmd="update "+table_name+" set "+"isbn13="+isbn13+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_title(int id,String title)
	{
		String Update_cmd="update "+table_name+" set "+"title="+title+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_subtitle(int id,String subtitle)
	{
		String Update_cmd="update "+table_name+" set "+"subtitle="+subtitle+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_images_small(int id,String image_s)
	{
		String Update_cmd="update "+table_name+" set "+"images_small="+image_s+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_images_middle(int id,String image_m)
	{
		String Update_cmd="update "+table_name+" set "+"images_middle="+image_m+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_images_large(int id,String image_l)
	{
		String Update_cmd="update "+table_name+" set "+"image_large="+image_l+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_author(int id,String author)
	{
		String Update_cmd="update "+table_name+" set "+"author="+author+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_translator(int id,String translator)
	{
		String Update_cmd="update "+table_name+" set "+"translator="+translator+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_publisher(int id,String publisher)
	{
		String Update_cmd="update "+table_name+" set "+"publisher="+publisher+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_pubdate(int id,String date)
	{
		String Update_cmd="update "+table_name+" set "+"pubdata="+date+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_price(int id,double price)
	{
		String Update_cmd="update "+table_name+" set "+"price="+price+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_pages(int id,int pages)
	{
		String Update_cmd="update "+table_name+" set "+"pages="+pages+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_summary(int id,String summary)
	{
		String Update_cmd="update "+table_name+" set "+"summary="+summary+" where book_id="+id;
		Exec(Update_cmd);
	}
	
	private static final String DB_NAME="ReadBook.db"; 
	private String table_name="book";
	private String table_attr="book_id,book_name,isbn10,isbn13,title,"+
				"origin_title,subtitle,images_small,images_middle,images_large,"+
				"author,translator,publisher,pubdata,price,pages,summary";

	private int book_id;
	private String book_name;
	private String isbn10;
	private String isbn13;
	private String title;
	private String origin_title;
	private String subtitle;
	private String images_small;
	private String images_middle;
	private String images_large;
	private String author;
	private String translator;
	private String publisher;
	private String pubdata;
	private double price;
	private int pages;
	private String summary;
}
