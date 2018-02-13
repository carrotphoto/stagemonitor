package org.stagemonitor.alerting.alerter.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Qian Liping
 * @since 2018/2/9
 */
public class AccessToken extends WechatJson {
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("expires_in")
	private long expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

}
