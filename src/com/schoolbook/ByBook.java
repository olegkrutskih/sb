package com.schoolbook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.schoolbook.common.BookAdapter;
import com.schoolbook.common.UpdateTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class ByBook extends Activity {

	ListView lvBooks;
	ArrayList<HashMap<String, String>> bookArray = new ArrayList<HashMap<String, String>>();
	TextView tvDescrBk;

	String TITLE = "name"; // Верхний текст
	String ID = "id_book"; // идентификатор
	String AUTHOR = "author"; // автор
	String ID_SUBJ = "id_predmet"; // идентификатор предмета
	String SUBJ = "predmet"; // название предмета
	String ICON = "icon"; // картинка

	// Создаём адаптер, чтобы привязать массив к ListView
	// SimpleAdapter bookAdapter;
	BookAdapter bookAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_books);

		lvBooks = (ListView) findViewById(R.id.lvBooks);		

		bookAdapter = new BookAdapter(this, bookArray, R.layout.lv_book_item,
				new String[] { TITLE, ID, AUTHOR, ID_SUBJ, ICON }, new int[] {
						R.id.tvLabel, R.id.tvIdBook, R.id.tvAuthor,
						R.id.tvIdSubj, R.id.ivLogo }, getIntent().getStringExtra("descr"));

		lvBooks.setAdapter(bookAdapter);

		lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try {
					Intent Examples = new Intent(ByBook.this, ByExample.class);
					Examples.putExtra(ID, ((TextView) arg1
							.findViewById(R.id.tvIdBook)).getText().toString());
					Examples.putExtra("cover", ((TextView) arg1
							.findViewById(R.id.tvIcon)).getText().toString());
					Examples.putExtra("descr", getIntent().getStringExtra("descr"));
					Examples.putExtra(TITLE, ((TextView) arg1
							.findViewById(R.id.tvLabel)).getText().toString());
					Examples.putExtra(AUTHOR, ((TextView) arg1
							.findViewById(R.id.tvAuthor)).getText().toString());
					Examples.putExtra(SUBJ, ((TextView) arg1
							.findViewById(R.id.tvSubj)).getText().toString());
					startActivity(Examples);
				} catch (Exception ex) {
					System.err.println(ex.getMessage());
					ex.printStackTrace();

				}

			}
		});

		try {
			getBooks(getIntent().getStringExtra("class"), getIntent()
					.getStringExtra("subj"));		
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			ex.printStackTrace();
		}

		
	}

	private void getBooks(String cls, String id_subj) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();

		params.add(new BasicNameValuePair("type", "list"));
		params.add(new BasicNameValuePair("klass", cls.toString()));
		params.add(new BasicNameValuePair("id_predmet", id_subj.toString()));

		new UpdateTask(this, bookAdapter, bookArray, params, TITLE, false)
				.execute("http://test.poozir.ru/api.php");
	}

}
