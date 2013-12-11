package com.hitiread.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;

/**
 * Created by wwq on 13-7-10.
 */
public class BookInfo implements Parcelable
{

	public final static int BOOK_INFO = 1;
	private String mTitle;
	private Bitmap mBitmap;
	private String mAuthor;
	private String mPublisher;
	private String mPublishDate;
	private String mISBN;
	private String mSummary;
	private String mPages;
	private String lastread;
	private String progress;
	private int ids;
	private String tag;
	private String recent;


	public BookInfo(String title, String author, String publisher,
			String publishdate, String isbn, String summary, 
			String pages, String last, String prog, String rec)
	{
		mTitle = title;
		mAuthor = author;
		mPublisher = publisher;
		mPublishDate = publishdate;
		mISBN = isbn;
		mSummary = summary;
		mPages = pages;
		lastread = last;
		progress = prog;
		recent = rec;
	}

	public BookInfo(int id, String title, String author, String publisher,
			String publishdate, String isbn, String summary, String pages,
			String last, String prog, String rec)
	{
		ids = id;
		mTitle = title;
		mAuthor = author;
		mPublisher = publisher;
		mPublishDate = publishdate;
		mISBN = isbn;
		mSummary = summary;
		mPages = pages;
		lastread = last;
		progress = prog;
		recent = rec;
	}

	public BookInfo()
	{
		// TODO Auto-generated constructor stub
	}

	public String getTag()
	{
		return tag;
	}
	public void setTag(String t)
	{
		this.tag = t;
	}
	public String getRecent()
	{
		return recent;
	}
	public void setRecent(String rec)
	{
		this.recent = rec;
	}
	public int getId()
	{
		return ids;
	}
	public void setLastRead(String last)
	{
		lastread = last;
	}
	public String getLastRead()
	{
		return lastread;
	}
	public void setProgress(String prog)
	{
		progress = prog;
	}
	public String getProgress()
	{
		return progress;
	}

	public void setPages(String Pages)
	{
		mPages = Pages;
	}

	public String getPages()
	{
		return mPages;
	}

	public void setTitle(String Title)
	{
		mTitle = Title;
	}

	public void setBitmap(Bitmap bitmap)
	{
		mBitmap = bitmap;
	}

	public void setAuthor(String Author)
	{
		mAuthor = Author;
	}

	public void setISBN(String ISBN)
	{
		mISBN = ISBN;
	}

	public void setPublishDate(String PublishDate)
	{
		mPublishDate = PublishDate;
	}

	public void setPublisher(String Publisher)
	{
		mPublisher = Publisher;
	}

	public void setSummary(String Summary)
	{
		mSummary = Summary;
	}

	public String getTitle()
	{
		return mTitle;
	}

	public Bitmap getBitmap()
	{
		return mBitmap;
	}

	public String getAuthor()
	{
		return mAuthor;
	}

	public String getISBN()
	{
		return mISBN;
	}

	public String getPublishDate()
	{
		return mPublishDate;
	}

	public String getPublisher()
	{
		return mPublisher;
	}

	public String getSummary()
	{
		return mSummary;
	}

	public static final Parcelable.Creator<BookInfo> CREATOR = new Creator<BookInfo>() {
		public BookInfo createFromParcel(Parcel source)
		{
			BookInfo bookInfo = new BookInfo();
			bookInfo.mTitle = source.readString();
			bookInfo.mBitmap = source.readParcelable(Bitmap.class
					.getClassLoader());
			bookInfo.mAuthor = source.readString();
			bookInfo.mPublisher = source.readString();
			bookInfo.mPublishDate = source.readString();
			bookInfo.mISBN = source.readString();
			bookInfo.mSummary = source.readString();
			bookInfo.mPages = source.readString();
			bookInfo.lastread = "0";
			bookInfo.progress = "0";
			SimpleDateFormat date = new SimpleDateFormat("HH:mm:ss");
			bookInfo.recent = date.format(new Date(System.currentTimeMillis()));
			Log.v("bookinfo", bookInfo.recent);
			return bookInfo;
		}

		public BookInfo[] newArray(int size)
		{
			return new BookInfo[size];
		}
	};

	public int describeContents()
	{
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(mTitle);
		dest.writeParcelable(mBitmap, flags);
		dest.writeString(mAuthor);
		dest.writeString(mPublisher);
		dest.writeString(mPublishDate);
		dest.writeString(mISBN);
		dest.writeString(mSummary);
		dest.writeString(mPages);
	}

}
