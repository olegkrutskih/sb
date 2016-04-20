package com.schoolbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.schoolbook.common.UpdateTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ByExample extends Activity {

	GridView gvExample;
	Button btExample;
	ImageView ivCover;
	TextView tvDescr;
	TextView tvExAuthor;

	ArrayList<HashMap<String, String>> btnArray = new ArrayList<HashMap<String, String>>();

	String TITLE = "ankor"; // Верхний текст
	// String ID_BOOK = "id_book"; // id
	String EXAMPLE = "img"; // ссылка на картинку примера

	// Создаём адаптер, чтобы привязать массив к ListView
	SimpleAdapter btnAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_by_example);

		gvExample = (GridView) findViewById(R.id.gvExample);
		ivCover = (ImageView) findViewById(R.id.ivCover);
		tvDescr = (TextView) findViewById(R.id.tvDescr);
		tvExAuthor = (TextView) findViewById(R.id.tvExAuthor);

		tvDescr.setText(getIntent().getStringExtra("predmet")
				+ getIntent().getStringExtra("descr"));
		tvExAuthor.setText(getIntent().getStringExtra("author"));

		btnAdapter = new SimpleAdapter(this, btnArray,
				R.layout.gv_example_item, new String[] { TITLE, EXAMPLE },
				new int[] { R.id.btExample, R.id.tvIconEx });

		gvExample.setAdapter(btnAdapter);

		gvExample.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					Intent Example = new Intent(ByExample.this, Example.class);
					Example.putExtra(EXAMPLE, ((TextView) arg1
							.findViewById(R.id.tvIconEx)).getText().toString());
					startActivity(Example);
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
					ex.printStackTrace();

				}

			}
		});

		// скачиваем обложку
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage("http://test.poozir.ru/book/"
				+ getIntent().getStringExtra("cover"), ivCover);

		try {
			getExamples(getIntent().getStringExtra("id_book"));
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}

	}

	private void getExamples(String id_book) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("type", "book"));
		params.add(new BasicNameValuePair("id_book", id_book.toString()));

		new UpdateTask(this, btnAdapter, btnArray, params, TITLE, true)
				.execute("http://test.poozir.ru/api.php");
	}
}
