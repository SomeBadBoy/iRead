package com.hitiread.entity;

public class ReadProgress
{
	public final static int READ_PROGRESS = 2;
	String startpage;
	String endpage;
	String totalpage;
	int ids;
	int bookids;
	String starttime;
	String endtime;
	public ReadProgress(int id, int bookid, String start, String end, String total, String now)
	{
		ids=id;
		bookids=bookid;
		startpage=start;
		endpage=end;
		totalpage=total;
		starttime=now;
		endtime=now;
	}
	public ReadProgress()
	{}
	public ReadProgress(int bookid,String total)
	{
		bookids=bookid;
		startpage="0";
		endpage="0";
		starttime="0";
		endtime="0";
		totalpage=total;
	}

	
 	public void setStartPage(String start)
	{
		this.startpage=start;
	}
	public void setEndPage(String end)
	{
		this.endpage=end;
	}
	public void setStartTime(String time)
	{
		this.starttime=time;
	}
	public void setEndTime(String time)
	{
		this.endtime=time;
	}
	public void setBookIds(int ids)
	{
		this.bookids=ids;
	}
	public void setTotalPage(String total)
	{
		this.totalpage=total;
	}
	public String getStartPage()
	{
		return startpage;
	}
	public String getEndPage()
	{
		return endpage;
	}
	public String getTotalPage()
	{
		return totalpage;
	}
	public String getStartTime()
	{
		return starttime;
	}
	public String getEndTime()
	{
		return endtime;
	}
	public int getIds()
	{
		return ids;
	}
	public int getBookIds()
	{
		return bookids;
	}
}
