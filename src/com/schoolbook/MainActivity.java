package com.schoolbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.schoolbook.common.UpdateTask;

public class MainActivity extends Activity {

	class cBookSort implements Comparator<Object> {
		@SuppressWarnings("unchecked")
		public int compare(Object el1, Object el2) {
			return ((HashMap<String, String>) el1).get(TITLE).compareTo(
					((HashMap<String, String>) el2).get(TITLE));
		}
	}

	ListView lvMain;
	ArrayList<HashMap<String, String>> bookArray = new ArrayList<HashMap<String, String>>();

	String TITLE = "klass"; // Верхний текст

	// Создаём адаптер, чтобы привязать массив к ListView
	SimpleAdapter bookAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvMain = (ListView) findViewById(R.id.lvMain);
		//tstArrayFill();
		// Создаём пустой массив для хранения данных
		bookAdapter = new SimpleAdapter(this, bookArray, R.layout.lv_main_item,
				new String[] { TITLE }, new int[] { R.id.tvLvMain });

		Collections.sort(bookArray, new cBookSort());
		// Привяжем массив через адаптер к ListView
		lvMain.setAdapter(bookAdapter);

		lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(MainActivity.this, ByClass.class);
				intent.putExtra("class", ((TextView) arg1
						.findViewById(R.id.tvLvMain)).getText().toString());
				intent.putExtra("descr", ", " + ((TextView) arg1
						.findViewById(R.id.tvLvMain)).getText().toString() + " " + ((TextView) arg1
								.findViewById(R.id.tvLvMainClass)).getText().toString());
				startActivity(intent);
			}
		});
		
		try {
			getClasses();
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}
	}	
	
	private void getClasses() {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("type", "klass"));		

		new UpdateTask(this, bookAdapter, bookArray, params, TITLE, true)
				.execute("http://test.poozir.ru/api.php");
	}

}
