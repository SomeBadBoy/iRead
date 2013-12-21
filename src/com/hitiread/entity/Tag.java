package com.hitiread.entity;

public class Tag
{
	private int id;
	private String tag;
	private long num;
	
	public Tag(int ids, String t, long total)
	{
		this.id = ids;
		this.tag = t;
		this.num = total;
	}
	public Tag(String t, long total)
	{
		this.tag = t;
		this.num = total;
	}
	public int getID()
	{
		return id;
	}
	public void setTag(String t)
	{
		tag = t;
	}
	public void setNum(long total)
	{
		num = total;
	}
	public String getTag()
	{
		return tag;
	}
	public long getNum()
	{
		return num;
	}
}
