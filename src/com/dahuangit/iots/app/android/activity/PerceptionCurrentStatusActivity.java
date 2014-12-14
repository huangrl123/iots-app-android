package com.dahuangit.iots.app.android.activity;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.VideoView;

import com.dahuangit.iots.app.android.R;
import com.dahuangit.iots.app.android.common.IniConfig;
import com.dahuangit.iots.app.android.dto.response.RemoteQuery2j2PerceptionResponse;
import com.dahuangit.iots.app.android.dto.response.Response;
import com.dahuangit.iots.app.android.util.DialogUtils;
import com.dahuangit.iots.app.android.util.HttpUtils;
import com.dahuangit.iots.app.android.util.NetworkOnMainThreadExceptionKit;
import com.dahuangit.iots.app.android.util.ViewUtils;
import com.dahuangit.iots.app.android.util.XmlUtils;

/**
 * 感知端设备当前状态
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月6日 下午1:35:20
 */
public class PerceptionCurrentStatusActivity extends Activity {
	/** 电机1旋转状态 */
	private Switch machine1RotateStatusSwitch2j2 = null;
	/** 电机2旋转状态 */
	private Switch machine2RotateStatusSwitch2j2 = null;
	/** 电机1开关状态 */
	private Switch machine1SwitchStatusSwitch2j2 = null;
	/** 电机2开关状态 */
	private Switch machine2SwitchStatusSwitch2j2 = null;
	/** i2c状态 */
	private Switch i2cStatusSwitch2j2 = null;
	/** 红外状态 */
	private TextView infraredStatusTextView2j2 = null;
	/** 按键状态 */
	private TextView pressKeyStatusTextView2j2 = null;

	private Button queryBtn2j2 = null;

	/** 实时视频view */
	private VideoView videoView6j6 = null;

	private ProgressDialog progressDialog = null;

	private Context context = null;

	/** 已经加载过 */
	private boolean loaded = false;

	private final Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			int what = msg.what;
			msg.getData();
			switch (what) {
			case 1:// 查询成功之后要做的事
				RemoteQuery2j2PerceptionResponse response = (RemoteQuery2j2PerceptionResponse) msg.getData().get(
						"response");

				// 如果放回不成功
				if (!response.getSuccess()) {
					Builder dialog = DialogUtils.createAlertDialog(context, response.getMsg());

					progressDialog.hide();
					dialog.show();
					return;
				}

				machine1RotateStatusSwitch2j2.setChecked(parseBoolean(response.getMachine1RotateStatus()));
				machine2RotateStatusSwitch2j2.setChecked(parseBoolean(response.getMachine2RotateStatus()));
				machine1SwitchStatusSwitch2j2.setChecked(parseBoolean(response.getMachine1SwitchStatus()));
				machine2SwitchStatusSwitch2j2.setChecked(parseBoolean(response.getMachine2SwitchStatus()));
				i2cStatusSwitch2j2.setChecked(parseBoolean(response.getI2cStatus()));

				infraredStatusTextView2j2.setText(response.getInfraredStatus());
				pressKeyStatusTextView2j2.setText(response.getPressKeyStatus());

				loaded = true;
				progressDialog.hide();
				break;

			case 2:// 远程控制成功之后需要做的事
				Response r = (Response) msg.getData().get("response");
				progressDialog.hide();
				break;
			}
		}

	};

	public static int perceptionType = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		NetworkOnMainThreadExceptionKit.kit();

		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		context = this;

		progressDialog = DialogUtils.createProgressDialog(this);

		switch (perceptionType) {
		case 1:// 2+2设备
			setContentView(R.layout.view2j2);
			set2j2viewItem();
			setValue2j2();
			break;

		case 2:// 6+6设备
			setContentView(R.layout.view6j6);
			// 获取界面上VideoView组件
			videoView6j6 = (VideoView) findViewById(R.id.video);
			File video = new File("/storage/extSdCard/电影/2014-11-30-092044.mp4");
			if (video.exists()) {
				videoView6j6.setVideoPath(video.getAbsolutePath());
			}

			// 设置为自动播放
			videoView6j6.setOnPreparedListener(new OnPreparedListener() {

				public void onPrepared(MediaPlayer mp) {
					mp.start();
					mp.setLooping(true);
				}
			});
			break;
		}

	}

	private void set2j2viewItem() {
		View contentView = ViewUtils.getRootView(this);

		queryBtn2j2 = (Button) contentView.findViewById(R.id.queryBtn2j2);
		queryBtn2j2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setValue2j2();
			}
		});

		// 初始化控件
		// 电机1旋转状态
		machine1RotateStatusSwitch2j2 = (Switch) contentView.findViewById(R.id.machine1RotateStatusSwitch2j2);
		setSwitchOnCheckedChangeListener(machine1RotateStatusSwitch2j2, 3, 4);

		// 电机2旋转状态
		machine2RotateStatusSwitch2j2 = (Switch) contentView.findViewById(R.id.machine2RotateStatusSwitch2j2);
		setSwitchOnCheckedChangeListener(machine2RotateStatusSwitch2j2, 9, 10);

		// 电机1开关状态
		machine1SwitchStatusSwitch2j2 = (Switch) contentView.findViewById(R.id.machine1SwitchStatusSwitch2j2);
		setSwitchOnCheckedChangeListener(machine1SwitchStatusSwitch2j2, 5, 6);

		// 电机2开关状态
		machine2SwitchStatusSwitch2j2 = (Switch) contentView.findViewById(R.id.machine2SwitchStatusSwitch2j2);
		setSwitchOnCheckedChangeListener(machine2SwitchStatusSwitch2j2, 11, 12);

		// i2c状态
		i2cStatusSwitch2j2 = (Switch) contentView.findViewById(R.id.i2cStatusSwitch2j2);
		setSwitchOnCheckedChangeListener(i2cStatusSwitch2j2, 7, 8);

		infraredStatusTextView2j2 = (TextView) contentView.findViewById(R.id.infraredStatusTextView2j2);
		pressKeyStatusTextView2j2 = (TextView) contentView.findViewById(R.id.pressKeyStatusTextView2j2);

	}

	/**
	 * 添加Switch的check事件
	 * 
	 * @param switchView
	 * @param onOptValue
	 * @param offOptValue
	 */
	private void setSwitchOnCheckedChangeListener(Switch switchView, final Integer onOptValue, final Integer offOptValue) {
		switchView.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (!loaded) {
					return;
				}

				if (isChecked) {
					remoteCtrl(onOptValue);
				} else {
					remoteCtrl(offOptValue);
				}
			}
		});
	}

	/**
	 * 远程控制
	 * 
	 * @param optValue
	 */
	private void remoteCtrl(final Integer optValue) {

		progressDialog.show();

		new Thread() {
			public void run() {
				try {
					StringBuffer host = new StringBuffer();
					host.append(IniConfig.propConfig.getProperty("server.host"));
					host.append(IniConfig.propConfig.getProperty("remoteCtrl.url"));

					Map<String, String> params = new HashMap<String, String>();
					params.put("perceptionId", getIntent().getStringExtra("perceptionId"));
					params.put("opt", optValue.toString());

					String xml = HttpUtils.getHttpRequestContent(host.toString(), params);

					Response response = XmlUtils.xml2obj(IniConfig.mapping, xml, Response.class);

					Message msg = new Message();
					msg.what = 2;
					Bundle data = new Bundle();
					data.putSerializable("response", response);
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void setValue2j2() {
		progressDialog.show();

		new Thread() {
			public void run() {
				try {
					StringBuffer host = new StringBuffer();
					host.append(IniConfig.propConfig.getProperty("server.host"));
					host.append(IniConfig.propConfig.getProperty("remoteQuery2j2Machine.url"));

					Map<String, String> params = new HashMap<String, String>();
					params.put("perceptionId", getIntent().getStringExtra("perceptionId"));

					String xml = HttpUtils.getHttpRequestContent(host.toString(), params);
					RemoteQuery2j2PerceptionResponse response = XmlUtils.xml2obj(IniConfig.mapping, xml,
							RemoteQuery2j2PerceptionResponse.class);

					Message msg = new Message();
					Bundle data = new Bundle();
					data.putSerializable("response", response);
					msg.what = 1;
					msg.setData(data);
					handler.sendMessage(msg);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	private void setValue6j6() {

	}

	private boolean parseBoolean(String str) {
		if ("1".equals(str.trim())) {
			return true;
		}

		return false;
	}

}
