package org.stagemonitor.alerting.alerter.wechat;

/**
 * Wechat miniprogram, ie: "微信小程序"
 *
 * @author Qian Liping
 * @since 2018/2/9
 */
public class Miniprogram {

	private String appid;
	private String pagepath;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
}
