package com.hitiread.view;

import java.util.ArrayList;
import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.ChildEntity;
import com.hitiread.entity.GroupEntity;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

public class NoteActivity extends Activity
{
	private ArrayList<GroupEntity> bookname;
	private ArrayList<ArrayList<ChildEntity>> readnote = new ArrayList<ArrayList<ChildEntity>>();
	private ExpandableListView expandableListView;
	private LayoutInflater inflater;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.noteactivity);
		Log.v("note", "1");
		inflater = getLayoutInflater();
		ContactsInfoAdapter adapter = new ContactsInfoAdapter();
		
		Log.v("note", "2");
		expandableListView = (ExpandableListView)findViewById(R.id.noteexlv);
		Log.v("note", "3");
		Log.v("note", "4");
        initializeData();
		Log.v("note", "5");
        expandableListView.setAdapter(adapter);
        expandableListView.setCacheColorHint(0);  //设置拖动列表的时候防止出现黑色背景 
        expandableListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id)
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("noteid", readnote.get(groupPosition).get(childPosition).getID());
				intent.setClass(getApplicationContext(), NoteShared.class);
				startActivity(intent);
				return false;
			}
		});
	}

	private void initializeData()
	{
		// TODO Auto-generated method stub
		MyDataBase myDataBase = new MyDataBase(getApplicationContext());
		Log.v("note", "6");
		bookname = myDataBase.getBookNote();
		Log.v("note", "7"+bookname.size());
		for (int i = 0; i < bookname.size(); i++)
		{
			Log.v("note", "14  "+bookname.get(i).getID());
			ArrayList<ChildEntity> temp = myDataBase.getNoteList(bookname.get(i).getID());
			Log.v("note", "tempp"+temp.toString());
			readnote.add(temp);
		}
		Log.v("note", "13");
	}

	class ContactsInfoAdapter extends BaseExpandableListAdapter
	{


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
	
}
