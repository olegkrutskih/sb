package com.schoolbook;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class Example extends Activity {

	ImageView ivExample;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_example);

		ivExample = (ImageView) findViewById(R.id.ivExample);

		// скачиваем обложку
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.init(ImageLoaderConfiguration.createDefault(this));
		imageLoader.displayImage("http://test.poozir.ru/book/"
				+ getIntent().getStringExtra("img"), ivExample);
	}
}
