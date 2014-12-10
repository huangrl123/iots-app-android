package com.dahuangit.iots.app.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dahuangit.iots.app.android.R;

/**
 * 登录Activity
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月9日 下午4:33:21
 */
public class LoginActivity extends Activity {

	private EditText accountView = null;
	private EditText passwordView = null;
	private Button signInBtn = null;
	private LinearLayout loginStatusLinearLayout = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_login);

		accountView = (EditText) findViewById(R.id.email);
		passwordView = (EditText) findViewById(R.id.password);
		signInBtn = (Button) findViewById(R.id.sign_in_button);
		loginStatusLinearLayout = (LinearLayout) findViewById(R.id.loginStatusLinearLayout);

		signInBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				attemptLogin();
			}
		});
	}

	/**
	 * 登录
	 */
	public void attemptLogin() {
		Intent slidingActivityIntent = new Intent(this, SlidingActivity.class);
		startActivity(slidingActivityIntent);
	}

}
