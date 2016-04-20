package com.schoolbook.common;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.schoolbook.R;

public class BookAdapter extends SimpleAdapter {

	
	private final Activity activity;
	private final String Descr;
	private final ArrayList<HashMap<String, String>> entries;
	
	public BookAdapter(Context context, ArrayList<HashMap<String, String>> data,
			int resource, String[] from, int[] to, String descr) {
		super(context, data, resource, from, to);
		activity = (Activity) context;
		entries = data;
		Descr = descr;
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            ViewHolder holder;
            if (v == null) {
                    LayoutInflater inflater = (LayoutInflater) activity
                                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = inflater.inflate(R.layout.lv_book_item, parent, false);
                    holder = new ViewHolder();
                    // инициализируем нашу разметку
                    holder.tvLabel = (TextView) v.findViewById(R.id.tvLabel);
                    holder.tvIdBook = (TextView) v.findViewById(R.id.tvIdBook);
                    holder.tvIdSubj = (TextView) v.findViewById(R.id.tvIdSubj);
                    holder.tvSubj = (TextView) v.findViewById(R.id.tvSubj);
                    holder.tvAuthor = (TextView) v.findViewById(R.id.tvAuthor);
                    holder.tvDescrBk = (TextView) v.findViewById(R.id.tvDescrBk);
                    holder.tvIcon = (TextView) v.findViewById(R.id.tvIcon);
                    holder.ivLogo = (ImageView) v.findViewById(R.id.ivLogo);
                    
                    v.setTag(holder);
            } else {
                    holder = (ViewHolder) v.getTag();
            }
            HashMap<String, String> hm = entries.get(position);
            if (hm != null) {
                    // получаем текст из массива
                    holder.tvLabel.setText(hm.get("name").toString());
                    holder.tvIdBook.setText(hm.get("id_book").toString());
                    holder.tvIdSubj.setText(hm.get("id_predmet").toString());
                    holder.tvSubj.setText(hm.get("predmet").toString());
                    holder.tvAuthor.setText(hm.get("author").toString());
                    holder.tvDescrBk.setText(Descr);
                    holder.tvIcon.setText(hm.get("icon").toString());
                    
                    // скачиваем картинки
                    ImageLoader imageLoader = ImageLoader.getInstance();
                    imageLoader.init(ImageLoaderConfiguration.createDefault(activity));
                    imageLoader.displayImage("http://test.poozir.ru/book/" + hm.get("icon").toString(), holder.ivLogo);
            }
            return v;
    }


	private static class ViewHolder {

		public ImageView ivLogo;
		public TextView tvLabel;
		public TextView tvIdBook;
		public TextView tvIdSubj;
		public TextView tvSubj;
		public TextView tvAuthor;
		public TextView tvDescrBk;
		public TextView tvIcon;
	}
}
