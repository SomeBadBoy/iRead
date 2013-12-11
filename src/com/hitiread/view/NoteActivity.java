package com.hitiread.view;

import java.util.ArrayList;
import com.hitiread.dbms.MyDataBase;
import com.hitiread.dbms.MyExAdapter;
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

		expandableListView = (ExpandableListView)findViewById(R.id.noteexlv);

        initializeData();
		inflater = getLayoutInflater();
		MyExAdapter adapter = new MyExAdapter(inflater, bookname, readnote);
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

}
