package com.hitiread.view;

import java.util.ArrayList;

import com.hitiread.dbms.MyDataBase;
import com.hitiread.dbms.TagExAdapter;
import com.hitiread.entity.Tag;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class TagView extends Activity
{
	private ExpandableListView exlv;
	private ArrayList<Tag> tags;
	private ArrayList<ArrayList<String>> books;
	private MyDataBase myDataBase;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tagview);
		Log.v("tagview", "tagview");
		exlv = (ExpandableListView)findViewById(R.id.tagexlv);
		
		myDataBase = new MyDataBase(getApplicationContext());
		tags = myDataBase.getTag();
		Log.v("tagnum", "size="+tags.size());
		books = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < tags.size(); i++)
		{
			books.add(myDataBase.getBooksByTagName(tags.get(i).getTag()));
		}
		TagExAdapter adapter = new TagExAdapter(getLayoutInflater(), tags, books);
		
		exlv.setAdapter(adapter);
        exlv.setCacheColorHint(0);  //设置拖动列表的时候防止出现黑色背景 
//		exlv.setOnChildClickListener(new OnChildClickListener() {
//			
//			@Override
//			public boolean onChildClick(ExpandableListView parent, View v,
//					int groupPosition, int childPosition, long id)
//			{
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.putExtra("noteid", readnote.get(groupPosition).get(childPosition).getID());
//				intent.setClass(getApplicationContext(), NoteShared.class);
//				startActivity(intent);
//				return false;
//			}
//		});
	}

}
