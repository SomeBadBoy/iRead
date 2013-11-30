package com.hitiread.dbms;

import java.util.ArrayList;

import com.hitiread.entity.BookInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter
{

	LayoutInflater inflater;
	ArrayList<BookInfo> array;

	public MyAdapter(LayoutInflater inf, ArrayList<BookInfo> arr)
	{
		inflater = inf;
		array = arr;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return array.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return array.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		ViewHolder vh;
		if(convertView==null){
			vh=new ViewHolder();
			convertView=inflater.inflate(com.hitiread.view.R.layout.adapter_listview, null);//注意导包，别导系统包
			vh.tv1=(TextView) convertView.findViewById(com.hitiread.view.R.id.textView1);
			vh.tv2=(TextView) convertView.findViewById(com.hitiread.view.R.id.textView2);
			convertView.setTag(vh);
		}
		vh=(ViewHolder) convertView.getTag();
		vh.tv1.setText(array.get(position).getTitle());
		vh.tv2.setText(array.get(position).getPages());
		return convertView;
	}

	class ViewHolder
	{
		TextView tv1, tv2;
	}

}
