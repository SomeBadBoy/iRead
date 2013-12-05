package com.hitiread.entity;

public class ReadNote
{
	int ids;
	int bookid;
	String starttime;
	String endtime;
	String startpage;
	String endpage;
	String title;
	String content;
	int share;
	
	public ReadNote()
	{}
	
	public ReadNote(int id,String start, String end, String stpg,
			String endpg, String ti, String cont, int sh)
	{
		bookid = id;
		starttime = start;
		endtime = end;
		startpage=stpg;
		endpage = endpg;
		title = ti;
		content = cont;
		share = sh;
	}
	
	public String getStartPage()
	{
		return startpage;
	}
	public String getEndPage()
	{
		return endpage;
	}
	public void setStartPage(String start)
	{
		this.startpage = start;
	}
	public void setEndPage(String end)
	{
		this.endpage = end;
	}
	public int getId()
	{
		return ids;
	}
	public int getBookId()
	{
		return bookid;
	}
	public String getStartTime()
	{
		return starttime;
	}
	public String getEndTime()
	{
		return endtime;
	}
	public String getTitle()
	{
		return title;
	}
	public String getContent()
	{
		return content;
	}
	public int getShare()
	{
		return share;
	}
	public void setId(int id)
	{
		this.ids = id;
	}
	public void setBookId(int id)
	{
		this.bookid = id;
	}
	public void setTitle(String titleString)
	{
		this.title=titleString;
	}
	public void setContent(String cont)
	{
		this.content=cont;
	}
	public void setStartTime(String time)
	{
		this.starttime = time;
	}
	public void setEndTime(String time)
	{
		this.endtime=time;
	}
	public void setShare(int shared)
	{
		this.share = shared;
	}
}
