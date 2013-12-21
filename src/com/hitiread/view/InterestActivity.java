package com.hitiread.view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.hitiread.dbms.MyDataBase;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

public class InterestActivity extends Activity
{
	private ArrayList<String> tagname=new ArrayList<String>();
	private int tagnum[];
	MyDataBase db;
	private TextView view;   
    private ArrayAdapter array; 
    private String temptagname;
		
	private double transform(String time)
	{
		double temp=0;
		int i;
		char num[]=time.toCharArray();
		for(i=9;i>=0;i--)
		{
			if(i!=4 && i!=7)
			{
				temp=temp*10+(double)num[i]-'0';
			}
		}
		return temp;
	}
		
	private void GetTagNum(int kind,Cursor cur)
	{
		String temptime;
		double tempdate;
		int i,datai;
		boolean added=false;
		tagname.clear();
			
		SimpleDateFormat sDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");     
		String date=sDateFormat.format(new java.util.Date());  
		double timelimit=transform(date);	
		switch(kind)
		{
			case 0:
				if(timelimit%10000/100<=6)
				{
					timelimit-=10000;
					timelimit=timelimit/10000+timelimit%100+(12+timelimit%10000/100-6)*100;
				}
				else
					timelimit-=600;	
				break;
			case 1:
				if(timelimit%10000/100<=6)
				{
					timelimit-=10000;
					timelimit=timelimit/10000+timelimit%100+(12+timelimit%10000/100-6)*100;
				}
				else
					timelimit-=600;	
				break;
			case 2:
				if(timelimit%10000/100<=6)
				{
					timelimit-=10000;
					timelimit=timelimit/10000+timelimit%100+(12+timelimit%10000/100-6)*100;
				}
				else
					timelimit-=600;	
				break;
			case 3:
				timelimit-=10000;					
				break;
			case 4:
				timelimit-=30000;
				break;
		}
		cur.moveToFirst();
		if(cur.isNull(0))
			Log.v("inte","datanull");
		else
			Log.v("inte", "datafull");
		Log.v("inte","datanum:"+cur.getCount());
		datai=0;
		tagnum = new int[(cur.getCount())];
		for(i=0;i<cur.getCount();i++)
			tagnum[i]=0;
		while(datai<cur.getCount())
		{
			Log.v("inte","while");
			Log.v("inte", "em"+tagnum.length);
			Log.v("tagname", "size="+tagname.size());
			temptime=cur.getString(cur.getColumnIndex("endtime"));
			temptagname=cur.getString(cur.getColumnIndex("tag"));
			tempdate=transform(temptime);
			Log.v("inte","datai:"+datai);
			if(tempdate>timelimit)
			{
				Log.v("inte", "em"+tagnum.length);
				Log.v("inte", "empty"+tagname.size());
				added=false;
				if(tagname.isEmpty())
				{
					tagname.add(temptagname);
					Log.v("inte", "em"+tagnum.length);
					Log.v("inte", "empty"+tagname.size());
					tagnum[0]=1;
					Log.v("inte","addnum");
					added=true;
					Log.v("inte", "empty");
				}
				else
				{
					Log.v("inte", "ready for for");
					for(i=0;i<tagname.size();i++)
					{
						if(temptagname.equals(tagname.get(i)))
						{
							tagnum[i]++;
							added=true;
							Log.v("inte","addadd");
							break;
						}
					}
					Log.v("inte","added:"+added);
					if(added==false)
					{
						tagname.add(temptagname);
						tagnum[i]=1;
						Log.v("inte","addafter");
						added=true;
					}
				}
			}
			cur.moveToNext();
			Log.v("inte","datai:"+datai);
			datai+=1;
		}
	}
	
	private String Analyse(int kind)
	{
		String result="\n";
		int sum,i;
		String temp=null;
		double tempnum;
		String stringnum;
		DecimalFormat df=new DecimalFormat(".##");
		
		db=new MyDataBase(getApplicationContext());
		Cursor cur=db.getAllNote();
		
		GetTagNum(kind,cur);
		Log.v("inte", "null");
		
		for(sum=0,i=0;i<tagname.size();i++)
		{
			if(tagnum[i]==0)
				break;
			sum+=tagnum[i];
		}
		Log.v("inte",sum+"ddsum");
		for(i=0;i<tagname.size();i++)
		{	
			temp=tagname.get(i);
			tempnum=(double)tagnum[i]/(double)sum*100;
			stringnum=df.format(tempnum);
			
			Log.v("inte",temp+":"+tagnum[i]*100+'%');
			result=result+temp+":"+stringnum+"%\n";
		}
		cur.close();
			
		return result;
	}
    
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.interest);
		
		Button bn=(Button)findViewById(R.id.button_interest);
		bn.setOnClickListener(new ButtonListener());
		
		Spinner spinner=(Spinner)findViewById(R.id.spinner_interest);
		array= ArrayAdapter.createFromResource(this, R.array.Time, android.R.layout.simple_spinner_item);
		array.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(array); 
		spinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
		spinner.setVisibility(View.VISIBLE);
		
		view=(TextView)findViewById(R.id.result_interest);
	}
	
	class ButtonListener implements View.OnClickListener
	{
		@Override
		public void onClick(View arg0)
		{
			Intent intent =new Intent(InterestActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	     //使用XML形式操作  
	class SpinnerXMLSelectedListener implements OnItemSelectedListener
	{  
	    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3)
	    {  
	    	String result="result:"+ Analyse(arg2);
	    	view.setText(result);
	    }
	    public void onNothingSelected(AdapterView<?> arg0) 
	    {             
	    }  
	}
}
	          
