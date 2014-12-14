package com.dahuangit.iots.app.android.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

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
import com.dahuangit.iots.app.android.common.IniConfig;
import com.dahuangit.iots.app.android.dto.UserInfoDto;
import com.dahuangit.iots.app.android.dto.request.UserLoginRequest;
import com.dahuangit.iots.app.android.dto.response.UserLoginResponse;
import com.dahuangit.iots.app.android.util.BeanUtils;
import com.dahuangit.iots.app.android.util.HttpUtils;
import com.dahuangit.iots.app.android.util.NetworkOnMainThreadExceptionKit;
import com.dahuangit.iots.app.android.util.XmlUtils;

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

		NetworkOnMainThreadExceptionKit.kit();

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
		Mapping mapping = new Mapping();
		InputStream in = getResources().openRawResource(R.raw.castor_mapping);
		InputSource is = new InputSource(in);
		mapping.loadMapping(is);

		IniConfig.mapping = mapping;

		Properties config = new Properties();
		try {
			config.load(getAssets().open("config.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		IniConfig.propConfig = config;

		UserInfoDto user = new UserInfoDto();
		user.setUserName(accountView.getText().toString().trim());
		user.setPassword(passwordView.getText().toString().trim());

		IniConfig.currentUser = user;

		Intent slidingActivityIntent = new Intent(this, SlidingActivity.class);

		try {
			StringBuffer host = new StringBuffer();
			host.append(IniConfig.propConfig.getProperty("server.host"));
			host.append(IniConfig.propConfig.getProperty("userLogin.url"));
			UserLoginRequest request = new UserLoginRequest(this);
			request.setUserName(user.getUserName());
			request.setPassword(user.getPassword());

			Map<String, String> params = BeanUtils.bean2Map(request);
			String xml = HttpUtils.getHttpRequestContent(host.toString(), params);

			UserLoginResponse response = XmlUtils.xml2obj(mapping, xml, UserLoginResponse.class);
			IniConfig.currentUser.setUserId(response.getUserId());
			slidingActivityIntent.putExtra("userLoginResponse", response);
		} catch (Exception e) {
			e.printStackTrace();
		}

		startActivity(slidingActivityIntent);
	}
}
