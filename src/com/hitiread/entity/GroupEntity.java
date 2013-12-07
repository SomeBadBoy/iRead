package com.hitiread.entity;

public class GroupEntity
{
	private int id;
	private String title;
	
	public GroupEntity(int id, String title)
	{
		this.id = id;
		this.title = title;
	}
	
	public int getID()
	{
		return id;
	}
	public String getTitle()
	{
		return title;
	}
}