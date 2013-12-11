package com.hitiread.entity;

public class Classify
{
	private int id;
	private String tag;
	private long num;
	
	public Classify(int ids, String t, long total)
	{
		this.id = ids;
		this.tag = t;
		this.num = total;
	}
	public Classify(String t, long total)
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
