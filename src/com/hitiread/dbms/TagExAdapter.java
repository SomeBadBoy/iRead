package com.hitiread.dbms;

import java.util.ArrayList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.hitiread.entity.ChildEntity;
import com.hitiread.entity.GroupEntity;
import com.hitiread.entity.Tag;

public class TagExAdapter extends BaseExpandableListAdapter
{
	private ArrayList<Tag> tags;
	private ArrayList<ArrayList<String>> books;
	LayoutInflater inflater;
	public TagExAdapter(LayoutInflater inf, ArrayList<Tag> group, ArrayList<ArrayList<String>> child)
	{
		inflater = inf;
		tags = group;
		books = child;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return books.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		// TODO Auto-generated method stub
		return books.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		// TODO Auto-generated method stub
		return tags.get(groupPosition);
	}

	@Override
	public int getGroupCount()
	{
		// TODO Auto-generated method stub
		return tags.size();
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public boolean hasStableIds()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		ChildHolder childHolder = null;
		if(convertView == null)
		{
			childHolder = new ChildHolder();
			convertView = inflater.inflate(com.hitiread.view.R.layout.childexlv, null);
			childHolder.tv1 = (TextView)convertView.findViewById(com.hitiread.view.R.id.childtv1);
			childHolder.tv2 = (TextView)convertView.findViewById(com.hitiread.view.R.id.childtv2);
			convertView.setTag(childHolder);
		}
		childHolder = (ChildHolder)convertView.getTag();
		childHolder.tv1.setText(books.get(groupPosition).get(childPosition));
		childHolder.tv2.setText("");
		return convertView;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, 
			View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		GroupHolder gHolder = null;
		if(convertView == null)
		{
			Log.v("groupview", "null");
			gHolder = new GroupHolder();
			convertView = inflater.inflate(com.hitiread.view.R.layout.groupexlv, null);
			gHolder.tv1 = (TextView)convertView.findViewById(com.hitiread.view.R.id.grouptv1);
			gHolder.tv2 = (TextView)convertView.findViewById(com.hitiread.view.R.id.grouptv2);
			convertView.setTag(gHolder);
		}
		gHolder = (GroupHolder)convertView.getTag();
		gHolder.tv1.setText(tags.get(groupPosition).getTag());
		Log.v("groupview", "2"+gHolder.tv1.getText());
		gHolder.tv2.setText(Integer.toString(getChildrenCount(groupPosition)));
		Log.v("groupview", "3"+gHolder.tv2.getText());
		return convertView;
	}
}