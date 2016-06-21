package com.example.fingerprintdbmaker;


import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class wifilist extends MainActivity {

	static SQLiteDatabase mydb;
	Handler handler = new Handler();
	final int INTERVAL_PERIOD = 1000;  //1秒間に1回実行
	Timer timer = new Timer();
	int i;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list);

		WifiScan();
		DataBaseButton();
	}

	public void WifiScan(){


		WifiManager manager =(WifiManager)getSystemService(WIFI_SERVICE);

		manager.startScan();
		List<ScanResult> results = manager.getScanResults();
		ArrayList<String> funresults = new ArrayList<String>();

		int n=0;	
		String[] ssid = new String[results.size()];		//fun-wlan
		String[] bssid = new String[results.size()];
		int[] level = new int[results.size()];


		for(int i=0;i<results.size();i++){
			if(results.get(i).SSID.equals("fun-wlan")==true){
				ssid[n]=results.get(i).SSID;
				bssid[n]=results.get(i).BSSID;
				level[n]=results.get(i).level;
				funresults.add(ssid[n]);
				funresults.add(bssid[n]);
				funresults.add(String.valueOf(level[n]));
				n++;
			}
		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, funresults);
		ListView list = (ListView)findViewById(R.id.listView1);
		list.setAdapter(adapter);


	}

	
	
	
	
	
	
	public void DataBaseButton(){
		MySQLiteOpenHelper SQLite = new MySQLiteOpenHelper(getApplicationContext());
		mydb = SQLite.getWritableDatabase();
		final Button btn =(Button)findViewById(R.id.DBbutton);
		btn.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v) {
                i = 0;
				Toast.makeText(getApplicationContext(), "計測中", Toast.LENGTH_LONG).show();
				timer.scheduleAtFixedRate(new TimerTask(){
					@Override
					public void run() {

						handler.post(new Runnable() {
							@Override
							public void run() {

								WifiInsertDB();
								i++;
								System.out.println(i);

								if (i == 60) {  //i==x回分データベースに記録したら終了
									timer.cancel();
									Intent intent = new Intent(wifilist.this,MainActivity.class);
									startActivity(intent);
								}

							}
						});
						
					}
				}, 0, INTERVAL_PERIOD);

				//				for(int j=0; j<1800; j++){
				//				WifiInsertDB();
				//				System.out.println(j);
				//				Sleep(1000);
				//				}
			}
		});
	}

	public void WifiInsertDB(){
		WifiManager manager =(WifiManager)getSystemService(WIFI_SERVICE);
		manager.startScan();
		//GregorianCalendar cal1 = new GregorianCalendar();

		List<ScanResult> results = manager.getScanResults();
		
		int n=0;	
		String[] ssid = new String[results.size()];		//fun-wlan
		String[] bssid = new String[results.size()];
		int[] level = new int[results.size()];

		ContentValues values = new ContentValues();

		
		for(int i=0;i<results.size();i++){
			if(results.get(i).SSID.equals("fun-wlan")==true){
				ssid[n]=results.get(i).SSID;
				bssid[n]=results.get(i).BSSID;
				level[n]=results.get(i).level;
				values.put("bssid", bssid[n]);
				values.put("level", level[n]);
	//			values.put("pointX", x);
	//			values.put("pointY", y);
	//			values.put("time", cal1.getTime().toString());
				mydb.insert("MyTable", null, values);
				n++;
			}
		}
		Cursor cursor = mydb.query("mytable", new String[] {"_id", "bssid", "level"}, null, null, null, null, null);
		cursor.moveToFirst();
		System.out.println("cursor : " + cursor.getString(0));
	}

//	public void Sleep(int time){
//		try{
//			Thread.sleep(time);
//		}catch (InterruptedException e){}
//	}
}

