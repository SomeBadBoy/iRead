package com.example.readbook;

import android.database.*;
import android.content.*;

public class Note extends DBMS
{
	public Note(Context c)
	{
		super(c);
	}
	
	public void insert(int note_id,int belong_to_id,String title,String comment,
			int score,int shared)
	{
		String Insert_cmd="insert into "+table_name+" values("+note_id+","+belong_to_id+",'"+
				title+"','"+comment+"',"+score+","+shared+")";
		
		Exec(Insert_cmd);
	}
	
	public void query(int id)
	{
		String Query_cmd="select * from "+table_name+" where note_id="+id;
		Cursor c=Query(Query_cmd);
		
		this.note_id=c.getInt(c.getColumnIndex("note_id"));
		this.belong_to_id=c.getInt(c.getColumnIndex("belong_to_id"));
		this.title=c.getString(c.getColumnIndex("title"));
		this.comment=c.getString(c.getColumnIndex("comment"));
		this.score=c.getInt(c.getColumnIndex("score"));
		this.shared=c.getInt(c.getColumnIndex("shared"));
	}
	
	public void delete(int id)
	{
		String Delete_cmd="delete from "+table_name+" where note_id="+id;
		
		Exec(Delete_cmd);
	}
	
	public int get_available_id()
	{
		int id=-1;
		final int id_column=0;
		String get_id_cmd="select schedule_id from "+table_name+" where note_id >=all"+
						"(select schedule_id from "+table_name+")";
		Cursor c=Query(get_id_cmd);
		id=c.getInt(id_column)+1;
		return id;
	}
	
	public void updata_belong_to_id(int id,int schedule_id)
	{
		String Update_cmd="update "+table_name+" set "+"belong_to_id="+schedule_id+" where note_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_title(int id,String title)
	{
		String Update_cmd="update "+table_name+" set "+"title="+title+" where note_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_comment(int id,String comment)
	{
		String Update_cmd="update "+table_name+" set "+"comment="+comment+" where note_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_score(int id,int score)
	{
		String Update_cmd="update "+table_name+" set "+"score="+score+" where note_id="+id;
		Exec(Update_cmd);
	}
	
	public void updata_shared(int id,int shared)
	{
		String Update_cmd="update "+table_name+" set "+"shared="+shared+" where note_id="+id;
		Exec(Update_cmd);
	}
	
	private final String table_name="note";
	private String table_attr="note_id,belong_to_id,title,comment,score,shared";
	
	private int note_id;
	private int belong_to_id;
	private String title;
	private String comment;
	private int score;
	private int shared;
}
