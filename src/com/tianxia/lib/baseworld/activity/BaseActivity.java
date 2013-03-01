package com.tianxia.lib.baseworld.activity;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/**
		 * 此处调用基本统计代码
		 */
		StatService.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		StatService.onPause(this);
	}
}
