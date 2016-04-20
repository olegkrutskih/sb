package com.schoolbook.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;

import com.google.gson.Gson;

import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class UpdateTask extends
		AsyncTask<String, Void, HashMap<Integer, Map<String, String>>> {
	Context context;
	List<NameValuePair> reqParams = null;
	SimpleAdapter artAdapter;
	ArrayList<HashMap<String, String>> artArray;
	String sortField;
	Boolean sortInt;

	public UpdateTask(Context context, SimpleAdapter adapter,
			ArrayList<HashMap<String, String>> art, List<NameValuePair> params, String _sortField, Boolean _sortInt) {
		super();
		this.context = context;
		this.reqParams = params;
		this.artAdapter = adapter;
		this.artArray = art;
		this.sortField = _sortField;
		this.sortInt = _sortInt;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected HashMap<Integer, Map<String, String>> doInBackground(String... urls) {

		return loadJSON(urls[0]);
	}

	@SuppressWarnings("unchecked")
	public HashMap<Integer, Map<String, String>> loadJSON(String url) {

		// посылаем запрос методом GET
		String json = JSONLoader.makeHttpRequest(url,
				"GET", reqParams); 
		
		// разбираем результат запроса
		HashMap<Integer, Map<String, String>> gson = new Gson().fromJson(json,
				HashMap.class);

		return gson;
	}

	@Override
	protected void onPostExecute(HashMap<Integer, Map<String, String>> jsonData) {
		super.onPostExecute(jsonData);
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>> ();
		if (jsonData != null) {
			try {

				Integer cnt = 0;
				
				for (Entry<Integer, Map<String, String>> e : jsonData.entrySet()) {
					try {
						Map<String, String> hm1 = e.getValue();
						
						HashMap<String, String> hm;
						hm = new HashMap<String, String>();						
						hm.putAll(hm1);
						result.add(cnt, hm);
						cnt++;
					} catch (Exception ex) {
						System.err.printf(ex.getMessage());
						ex.printStackTrace();
					}

				}
				 artArray.addAll(result);
				 artAdapter.notifyDataSetChanged();
				 
				 Sort(sortField);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Toast toast = Toast.makeText(context,
					"Проблема с обновлением адаптера!", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
	}
	
	private void Sort(String s) {
		class cExSort implements Comparator<Object> {
			
			private String sField;
			
			public cExSort(String s) {
				sField = s;				
			}
			@SuppressWarnings("unchecked")
			public int compare(Object el1, Object el2) {
				if (!sortInt) {
				return ((HashMap<String, String>) el1).get(sField).compareTo(
						((HashMap<String, String>) el2).get(sField));
				} else {
				return Integer.valueOf(((HashMap<String, String>) el1).get(sField)).compareTo(
						Integer.valueOf(((HashMap<String, String>) el2).get(sField)));
				}
			}
		}
		Collections.sort(artArray, new cExSort(s));
	}

}
