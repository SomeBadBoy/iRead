package com.hitwwq.scanner;

import com.hitiread.view.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ScanAdd extends Activity
{

	public static String isbn="9787121135767";
	public final static int RESULT_CODE=2;
	EditText text;
	Button okButton;
	Button cancelButton;
	private Handler hd;
	private ProgressDialog mpd;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanadd);
		
		text=(EditText)findViewById(R.id.isbn_num);
		isbn=text.getText().toString();

		Log.v("soubook", isbn);
		Log.v("soubook", "here");
		System.out.println(isbn);
		okButton=(Button)findViewById(R.id.ok_button);
		cancelButton=(Button)findViewById(R.id.cancel_button);
		okButton.setOnClickListener(listener);
		
	}
	
	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v)
		{
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			Button btn=(Button)v;
			switch (btn.getId()) {
			case R.id.ok_button:
				Log.v("hitiread", text.getText().toString());
				intent.putExtra("ISBN", text.getText().toString());
				setResult(RESULT_CODE,intent);
				finish();
				break;

			default:
				break;
			}
		}
	};

/*	private OnClickListener listener=new OnClickListener() {
		
		@Override
		public void onClick(View v)
		{
			Intent intent=new Intent();
			Button btn=(Button)v;
			Log.v("scanadd", text.getText().toString());
			GETisbn(text.getText().toString());
			intent.setClass(getApplicationContext(), BookView.class);
			switch (btn.getId()) {
			case R.id.ok_button:
				hd=	new Handler(){
		            @Override
		            public void handleMessage(Message msg) {
		                // TODO Auto-generated method stub
		                super.handleMessage(msg);
		                BookInfo book= (BookInfo)msg.obj;

		                //进度条消失
		                mpd.dismiss();

		                Intent intent=new Intent(ScanAdd.this,BookView.class);
		                //Bundle bd=new Bundle();
		                //bundle.putSerializable(key,object);
		                //bd.putSerializable(BookInfo.class.getName(),book);
		                //intent.putExtras(bd);
		                intent.putExtra(BookInfo.class.getName(),book);
		                startActivity(intent);
		            }
		        };
				break;

			default:
				break;
			}
			// TODO Auto-generated method stub
			
		}
	};
	public void GETisbn(String temp)
    {
        mpd=new ProgressDialog(this);
        mpd.setMessage("请稍候，正在读取信息...");
        mpd.show();

        String urlstr="https://api.douban.com/v2/book/isbn/"+temp;
        Log.i("OUTPUT",urlstr);
        //扫到ISBN后，启动下载线程下载图书信息
        new DownloadThread(urlstr).start();
    }

    private class DownloadThread extends Thread
    {
        String url=null;
        public DownloadThread(String urlstr)
        {
            url=urlstr;
        }
        public void run()
        {
            String result=Util.Download(url);
            Log.i("OUTPUT", "download over");
            BookInfo book=new Util().parseBookInfo(result);
            Log.i("OUTPUT", "parse over");
            Log.i("OUTPUT",book.getSummary()+book.getAuthor());
            //给主线程UI界面发消息，提醒下载信息，解析信息完毕

            Message msg=Message.obtain();
            msg.obj=book;
            hd.sendMessage(msg);
            Log.i("OUTPUT","send over");
        }
    }*/
}
