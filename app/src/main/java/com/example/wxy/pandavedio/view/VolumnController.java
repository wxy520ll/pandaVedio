package com.example.wxy.pandavedio.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.wxy.pandavedio.R;


public class VolumnController {
	private Toast t;
	private SeekBar seekBar;
	private Context context;

	public VolumnController(Context context) {
		this.context = context;
	}

	public void show(float progress) {
		if (t == null) {
			t = new Toast(context);
			View layout = LayoutInflater.from(context).inflate(R.layout.vv, null);
			layout.setLayoutParams(new ViewGroup.LayoutParams(100,100));
			seekBar = (SeekBar) layout.findViewById(R.id.seekbar);
			t.setView(layout);
			t.setGravity(Gravity.CENTER, 0, 0);
			t.setDuration(Toast.LENGTH_SHORT);
		}
		seekBar.setProgress((int)progress);
		t.show();
	}
}
