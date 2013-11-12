package com.example.iread;

import java.util.ArrayList;
import java.util.zip.Inflater;

import android.R.array;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyAdapter extends BaseAdapter
{
	LayoutInflater inflater;
	ArrayList<bookinfo> arrayList;

	public MyAdapter(LayoutInflater inflater,ArrayList<bookinfo> arr)
	{
		this.inflater = inflater;
		this.arrayList = arr;
	}
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return arrayList.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return arrayList.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
