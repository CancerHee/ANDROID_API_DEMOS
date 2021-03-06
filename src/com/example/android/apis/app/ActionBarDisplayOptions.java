/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.apis.app;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.example.android.apis.R;

/**
 * This demo shows how various action bar display option flags can be combined
 * and their effects.
 */

/**
 * 设置ActionBar的展现模式对应效果
 * 
 * @description：
 * @author ldm
 * @date 2016-5-9 上午11:05:49
 */

public class ActionBarDisplayOptions extends Activity implements
		View.OnClickListener, ActionBar.TabListener {
	private View mCustomView;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_bar_display_options);
		// 设置监听事件
		findViewById(R.id.toggle_home_as_up).setOnClickListener(this);
		findViewById(R.id.toggle_show_home).setOnClickListener(this);
		findViewById(R.id.toggle_use_logo).setOnClickListener(this);
		findViewById(R.id.toggle_show_title).setOnClickListener(this);
		findViewById(R.id.toggle_show_custom).setOnClickListener(this);
		findViewById(R.id.toggle_navigation).setOnClickListener(this);
		findViewById(R.id.cycle_custom_gravity).setOnClickListener(this);
		findViewById(R.id.toggle_visibility).setOnClickListener(this);
		findViewById(R.id.toggle_system_ui).setOnClickListener(this);

		mCustomView = getLayoutInflater().inflate(
				R.layout.action_bar_display_options_custom, null);
		// 获取ActionBar
		final ActionBar bar = getActionBar();
		bar.setCustomView(mCustomView, new ActionBar.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		bar.addTab(bar.newTab().setText("Tab 1").setTabListener(this));
		bar.addTab(bar.newTab().setText("Tab 2").setTabListener(this));
		bar.addTab(bar.newTab().setText("Tab 3").setTabListener(this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.display_options_actions, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("InlinedApi")
	public void onClick(View v) {
		final ActionBar bar = getActionBar();
		int flags = 0;
		switch (v.getId()) {
		case R.id.toggle_home_as_up:
			// 给左上角图标的左边加上一个返回的图标 。
			flags = ActionBar.DISPLAY_HOME_AS_UP;
			break;
		case R.id.toggle_show_home:
			// 使左上角图标可点击，对应id为android.R.id.home
			flags = ActionBar.DISPLAY_SHOW_HOME;
			break;
		case R.id.toggle_use_logo:
			flags = ActionBar.DISPLAY_USE_LOGO;
			break;
		case R.id.toggle_show_title:
			// 去掉标题栏,显示ActionBar的Title
			flags = ActionBar.DISPLAY_SHOW_TITLE;
			break;
		case R.id.toggle_show_custom:
			// 使自定义的普通View能在title栏显示
			flags = ActionBar.DISPLAY_SHOW_CUSTOM;
			break;

		case R.id.toggle_navigation:
			// 获取当前的导航模式
			bar.setNavigationMode(bar.getNavigationMode() == ActionBar.NAVIGATION_MODE_STANDARD ? ActionBar.NAVIGATION_MODE_TABS
					: ActionBar.NAVIGATION_MODE_STANDARD);
			return;
		case R.id.cycle_custom_gravity:
			ActionBar.LayoutParams lp = (ActionBar.LayoutParams) mCustomView
					.getLayoutParams();
			int newGravity = 0;
			// 根据父视图中的gravity属性，决定子视图的起始位置。
			switch (lp.gravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) {
			case Gravity.START:
				newGravity = Gravity.CENTER_HORIZONTAL;
				break;
			case Gravity.CENTER_HORIZONTAL:
				newGravity = Gravity.END;
				break;
			case Gravity.END:
				newGravity = Gravity.START;
				break;
			}
			lp.gravity = lp.gravity & ~Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK
					| newGravity;
			bar.setCustomView(mCustomView, lp);
			return;
		case R.id.toggle_visibility:
			if (bar.isShowing()) {
				bar.hide();
			} else {
				bar.show();
			}
			return;
		case R.id.toggle_system_ui:
			if ((getWindow().getDecorView().getSystemUiVisibility() & View.SYSTEM_UI_FLAG_FULLSCREEN) != 0) {
				getWindow().getDecorView().setSystemUiVisibility(0);
			} else {
				getWindow().getDecorView().setSystemUiVisibility(
						View.SYSTEM_UI_FLAG_FULLSCREEN);
			}
			return;
		}

		int change = bar.getDisplayOptions() ^ flags;
		bar.setDisplayOptions(change, flags);
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}
}
