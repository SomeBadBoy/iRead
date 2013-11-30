package com.hitiread.view;

import com.hitiread.view.R;
import com.hitiread.dbms.MyDataBase;
import com.hitiread.entity.BookInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by wwq on 13-7-10.
 */
public class BookView extends Activity {
    private Intent intent;
    private TextView title,author,publisher,date,isbn,summary;
    private ImageView cover;
    private Button btn;
    private MyDataBase myDataBase;
    private BookInfo book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.bookresult);

        title=(TextView)findViewById(R.id.bookview_title);
        author=(TextView)findViewById(R.id.bookview_author);
        publisher=(TextView)findViewById(R.id.bookview_publisher);
        date=(TextView)findViewById(R.id.bookview_publisherdate);
        isbn=(TextView)findViewById(R.id.bookview_isbn);
        summary=(TextView)findViewById(R.id.bookview_summary);
        cover=(ImageView)findViewById(R.id.bookview_cover);
        
        myDataBase = new MyDataBase(this);
        intent=getIntent();
        //BookInfo book=(BookInfo) getIntent().getSerializableExtra(BookInfo.class.getName());
        book=(BookInfo)intent.getParcelableExtra(BookInfo.class.getName());

        title.setText(book.getTitle());
        author.setText(book.getAuthor());
        publisher.setText(book.getPublisher());
        date.setText(book.getPublishDate());
        isbn.setText(book.getISBN());
        summary.setText(book.getSummary());
        cover.setImageBitmap(book.getBitmap());
        btn = (Button)findViewById(R.id.bookview);
        
        btn.setOnClickListener(listener);
    }
    private View.OnClickListener listener=new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0)
		{
			// TODO Auto-generated method stub
			myDataBase.toInsert(book);
			Log.v("bookview", "Onclick");
			Toast.makeText(getApplicationContext(), "Ìí¼Ó³É¹¦", Toast.LENGTH_LONG).show();
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			BookView.this.finish();
		}
	};
    
}
