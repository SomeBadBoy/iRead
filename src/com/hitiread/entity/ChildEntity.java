package com.hitiread.entity;

public class ChildEntity
{
	private int id;
	private int bookid;
	private String title;
	private String endtime;
	
	public ChildEntity(int ids, int bookids, String title, String endtime)
	{
		this.id = ids;
		this.bookid = bookids;
		this.title = title;
		this.endtime = endtime;
	}
	
	public int getID()
	{
		return id;
	}
	public int getBookID()
	{
		return bookid;
	}
	public String getTitle()
	{
		return title;
	}
	public String getEndTime()
	{
		return endtime;
	}
}
