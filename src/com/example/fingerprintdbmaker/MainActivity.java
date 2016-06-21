package com.example.fingerprintdbmaker;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
//	static int x;
//	static int y;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Resources res = getResources();
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.siraishilab);
		
		ImageView img = new ImageView(this);
		img.setImageBitmap(bmp);
		
	}

	public boolean onTouchEvent(MotionEvent event){
		switch (event.getAction()) {
	    case MotionEvent.ACTION_DOWN:
//	    	x = (int)event.getX();
//			y = (int)event.getY();
			Intent intent = new Intent(MainActivity.this, wifilist.class);
			startActivity(intent);

	        break;
	    case MotionEvent.ACTION_UP:
//	        Log.d("TouchEvent", "getAction()" + "ACTION_UP");
	        break;
	    case MotionEvent.ACTION_MOVE:
//	        Log.d("TouchEvent", "getAction()" + "ACTION_MOVE");
	        break;
	    case MotionEvent.ACTION_CANCEL:
//	        Log.d("TouchEvent", "getAction()" + "ACTION_CANCEL");
	        break;
	    }
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
