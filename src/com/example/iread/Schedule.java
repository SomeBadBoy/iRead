package com.example.readbook;

import android.database.*;
import android.content.*;

public class Schedule extends DBMS
{
	public Schedule(Context c)
	{
		super(c);
	}
	
	public void insert(int schedule_id,int belong_to_id,int start_page,int end_page,
			int read_page,String start_time,String end_time)
	{
		String Insert_cmd="insert into "+table_name+" values("+schedule_id+","+belong_to_id+","+
				start_page+","+end_page+","+read_page+",'"+start_time+"','"+end_time+"')";
		
		Exec(Insert_cmd);
	}
	
	public Schedule query(int id)
	{
		String Query_cmd="select * from "+table_name+" where schedule_id="+id;
		Cursor c=Query(Query_cmd);
		
		this.schedule_id=c.getInt(c.getColumnIndex("schedule_id"));
		this.belong_to_id=c.getInt(c.getColumnIndex("belong_to_id"));
		this.start_page=c.getInt(c.getColumnIndex("start_page"));
		this.end_page=c.getInt(c.getColumnIndex("end_page"));
		this.read_page=c.getInt(c.getColumnIndex("read_page"));
		this.start_time=c.getString(c.getColumnIndex("start_time"));
		this.end_time=c.getString(c.getColumnIndex("end_time"));
		
		return this;
	}
	
	public void delete(int id)
	{
		String Delete_cmd="delete from "+table_name+" where schedule_id="+id;
		
		Exec(Delete_cmd);
	}
	
	public int get_available_id()
	{
		int id=-1;
		final int id_column=0;
		String get_id_cmd="select schedule_id from "+table_name+" where schedule_id >=all"+
						"(select schedule_id from "+table_name+")";
		Cursor c=Query(get_id_cmd);
		id=c.getInt(id_column)+1;
		return id;
	}
	
	public void updata_belong_to_id(int id,int book_id)
	{
		String Update_cmd="update "+table_name+" set "+"belong_to_id="+book_id+" where schedule_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_start_page(int id,int page)
	{
		String Update_cmd="update "+table_name+" set "+"start_page="+page+" where schedule_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_end_page(int id,int page)
	{
		String Update_cmd="update "+table_name+" set "+"end_page="+page+" where schedule_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_read_page(int id,int page)
	{
		String Update_cmd="update "+table_name+" set "+"read_page="+page+" where schedule_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_start_time(int id,String time)
	{
		String Update_cmd="update "+table_name+" set "+"start_time="+time+" where schedule_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_end_time(int id,String time)
	{
		String Update_cmd="update "+table_name+" set "+"end_time="+time+" where schedule_id="+id;
		Exec(Update_cmd);
	}

	private final String table_name="schedule";
	private String table_attr="schedule_id,belong_to_id,start_page,end_page,read_page,"+
			"start_time,end_time";
	
	private int schedule_id;
	private int belong_to_id;
	private int start_page;
	private int end_page;
	private int read_page;
	private String start_time;
	private String end_time;
}
