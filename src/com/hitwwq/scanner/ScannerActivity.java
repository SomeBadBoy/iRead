package com.hitwwq.scanner;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.covics.zxingscanner.OnDecodeCompletionListener;
import com.covics.zxingscanner.ScannerView;
import com.hitiread.view.R;

public class ScannerActivity extends Activity implements OnDecodeCompletionListener
{
	public final static int RESULT_CODE=3;
	private ScannerView scannerView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scanner);
		scannerView=(ScannerView)findViewById(R.id.scanner_view);
		scannerView.setOnDecodeListener(this);
	}

	@Override
	public void onDecodeCompletion(String barcodeFormat,String barcode,Bitmap bitmap)
	{
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		intent.putExtra("ISBN", barcode);
		setResult(RESULT_CODE, intent);
		finish();
	}

	@Override
	protected void onPause()
	{
		// TODO Auto-generated method stub
		super.onPause();
		scannerView.onPause();
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		scannerView.onResume();
	}

}
