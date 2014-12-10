package com.dahuangit.iots.app.android.activity;

import java.io.IOException;
import java.io.InputStream;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.xml.sax.InputSource;

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
	public Mapping mapping = null;

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
				try {
					attemptLogin();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 登录
	 * 
	 * @throws MappingException
	 * @throws IOException
	 */
	public void attemptLogin() throws IOException, MappingException {
		mapping = new Mapping();
		InputStream in = getResources().openRawResource(R.raw.castor_mapping);
		InputSource is = new InputSource(in);
		mapping.loadMapping(is);

		Intent slidingActivityIntent = new Intent(this, SlidingActivity.class);
		startActivity(slidingActivityIntent);
	}

}
