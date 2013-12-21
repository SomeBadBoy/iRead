package com.hitiread.view;

import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.ReadNote;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/*
 * �Ķ���¼������棬��ʾ�Ķ���¼��
 * �����ܹ�ͨ���Ѱ�װ�Ŀͻ��˷���SNS��
 */

public class NoteShared extends Activity
{
	private Button sharebtn = null;
	private TextView titletv,notetv,time,pages = null;
	private MyDataBase myDataBase = null;
	private ReadNote note;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.noteview);
		
		titletv = (TextView)findViewById(R.id.noteviewtv1);
		notetv = (TextView)findViewById(R.id.noteviewtv2);
		time = (TextView)findViewById(R.id.noteviewtv3);
		pages = (TextView)findViewById(R.id.noteviewtv4);
		sharebtn = (Button)findViewById(R.id.noteviewbtn1);
		
		Intent intent = getIntent();
		int noteid = intent.getExtras().getInt("noteid");
		myDataBase = new MyDataBase(getApplicationContext());
		note = myDataBase.getReadNote(noteid);
		setNoteView(note);
		sharebtn.setOnClickListener(listener);
	}
	
	private View.OnClickListener listener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, note.getTitle());
			intent.putExtra(Intent.EXTRA_TEXT, note.getContent());
			Intent choose = Intent.createChooser(intent, "�����ҵıʼ�");
			startActivity(choose);
		}
	};
	
	private void setNoteView(ReadNote readnote)
	{
		if(readnote.getTitle().equals(""))
			titletv.setText("���ޱ��⣩");
		else 
			titletv.setText(readnote.getTitle());
		notetv.setText(readnote.getContent());
		time.setText(readnote.getStartTime()+"--"+readnote.getEndTime());
		pages.setText(readnote.getStartPage()+"--"+readnote.getEndPage());
	}

}
