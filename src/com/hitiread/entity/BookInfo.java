package com.hitiread.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wwq on 13-7-10.
 */
public class BookInfo implements Parcelable
{

	private String mTitle;
	private Bitmap mBitmap;
	private String mAuthor;
	private String mPublisher;
	private String mPublishDate;
	private String mISBN;
	private String mSummary;
	private String mPages;
	private int ids;

	public BookInfo()
	{
		// TODO Auto-generated constructor stub
	}

	public BookInfo(String title, String author, String publisher,
			String publishdate, String isbn, String summary, String pages)
	{
		mTitle = title;
		mAuthor = author;
		mPublisher = publisher;
		mPublishDate = publishdate;
		mISBN = isbn;
		mSummary = summary;
		mPages = pages;
	}

	public BookInfo(int id, String title, String author, String publisher,
			String publishdate, String isbn, String summary, String pages)
	{
		ids = id;
		mTitle = title;
		mAuthor = author;
		mPublisher = publisher;
		mPublishDate = publishdate;
		mISBN = isbn;
		mSummary = summary;
		mPages = pages;
	}

	public int getId()
	{
		return ids;
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
