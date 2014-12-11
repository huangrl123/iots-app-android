package com.dahuangit.iots.app.android.common;

import java.util.Properties;

import org.exolab.castor.mapping.Mapping;

import com.dahuangit.iots.app.android.dto.UserInfoDto;

/**
 * 程序起来就加载的配置
 * 
 * @author 黄仁良
 * 
 *         创建时间 2014年12月11日 下午3:50:13
 */
public class IniConfig {
	/**castor mapping*/
	public static Mapping mapping = null;
	
	/** properties config*/
	public static Properties propConfig = null;
	
	/** 当前用户*/
	public static UserInfoDto currentUser = null;
}
