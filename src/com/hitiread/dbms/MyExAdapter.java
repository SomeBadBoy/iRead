package com.hitiread.dbms;

import java.util.ArrayList;

import com.hitiread.entity.ChildEntity;
import com.hitiread.entity.GroupEntity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MyExAdapter extends BaseExpandableListAdapter
{
	private ArrayList<GroupEntity> bookname;
	private ArrayList<ArrayList<ChildEntity>> readnote = new ArrayList<ArrayList<ChildEntity>>();
	LayoutInflater inflater;
	public MyExAdapter(LayoutInflater inf, ArrayList<GroupEntity> group, ArrayList<ArrayList<ChildEntity>> child)
	{
		inflater = inf;
		bookname = group;
		readnote = child;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		// TODO Auto-generated method stub
		return readnote.get(groupPosition).get(childPosition);
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
		return readnote.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		// TODO Auto-generated method stub
		return bookname.get(groupPosition);
	}

	@Override
	public int getGroupCount()
	{
		// TODO Auto-generated method stub
		return bookname.size();
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
		Log.v("groupview", "1");
		Log.v("groupview", "4"+gHolder.tv1.getText());
		Log.v("groupview", bookname.get(groupPosition).getTitle());
		gHolder.tv1.setText(bookname.get(groupPosition).getTitle());
		Log.v("groupview", "2"+gHolder.tv1.getText());
		gHolder.tv2.setText(Integer.toString(getChildrenCount(groupPosition)));
		Log.v("groupview", "3"+gHolder.tv2.getText());
		return convertView;
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
		if(readnote.get(groupPosition).get(childPosition).getTitle().equals(""))
			childHolder.tv1.setText("£®Œﬁ±ÍÃ‚£©");
		else
			childHolder.tv1.setText(readnote.get(groupPosition).get(childPosition).getTitle());
		childHolder.tv2.setText(readnote.get(groupPosition).get(childPosition).getEndTime());
		return convertView;
	}
	
}
class GroupHolder
{
	TextView tv1,tv2;
}

class ChildHolder
{
	TextView tv1,tv2;
}

