package com.schoolbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.schoolbook.common.UpdateTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ByClass extends Activity {

	ListView lvClass;
	ArrayList<HashMap<String, String>> classArray = new ArrayList<HashMap<String, String>>();

	String TITLE = "predmet"; // Верхний текст
	String ID = "id_predmet"; // Верхний текст

	// Создаём адаптер, чтобы привязать массив к ListView
	SimpleAdapter classAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_by_class);

		lvClass = (ListView) findViewById(R.id.lvClass);

		classAdapter = new SimpleAdapter(this, classArray,
				R.layout.lv_class_item, new String[] { TITLE, ID }, new int[] {
						R.id.tvLvMainClass, R.id.tvLvMainId });

		lvClass.setAdapter(classAdapter);

		lvClass.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					Intent books = new Intent(ByClass.this, ByBook.class);
					books.putExtra("class", getIntent().getStringExtra("class"));
					books.putExtra("subj", ((TextView) arg1
							.findViewById(R.id.tvLvMainId)).getText()
							.toString());
					books.putExtra("descr", getIntent().getStringExtra("descr"));
					startActivity(books);
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
					ex.printStackTrace();

				}

			}
		});

		getBooksInClass(getIntent().getStringExtra("class"));
	}

	private void getBooksInClass(String i) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("type", "predmet"));
		params.add(new BasicNameValuePair("klass", i.toString()));

		new UpdateTask(this, classAdapter, classArray, params, TITLE, false)
				.execute("http://test.poozir.ru/api.php");

	}

}
