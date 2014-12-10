package com.dahuangit.iots.app.android.activity;

import java.io.File;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.widget.VideoView;

import com.dahuangit.iots.app.android.R;

/**
 * 感知端设备当前状态
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月6日 下午1:35:20
 */
public class PerceptionCurrentStatusActivity extends Activity {
	VideoView videoView;

	public static int perceptionType = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);

		switch (perceptionType) {
		case 1:// 2+2设备
			setContentView(R.layout.view2j2);
			break;
		case 2:// 6+6设备
			setContentView(R.layout.view6j6);
			// 获取界面上VideoView组件
			videoView = (VideoView) findViewById(R.id.video);
			File video = new File("/storage/extSdCard/电影/2014-11-30-092044.mp4");
			if (video.exists()) {
				videoView.setVideoPath(video.getAbsolutePath());
			}

			// 设置为自动播放
			videoView.setOnPreparedListener(new OnPreparedListener() {

				public void onPrepared(MediaPlayer mp) {
					mp.start();
					mp.setLooping(true);
				}
			});
			break;
		}
	}

}
