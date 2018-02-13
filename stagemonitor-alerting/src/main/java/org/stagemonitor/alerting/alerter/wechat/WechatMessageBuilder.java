package org.stagemonitor.alerting.alerter.wechat;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Qian Liping
 * @since 2018/2/9
 */
public class WechatMessageBuilder {

	private WechatMessage message;

	public WechatMessageBuilder() {
		this.message = new WechatMessage();
	}

	public WechatMessageBuilder setUserOpenid(String openid) {
		this.message.setTouser(openid);
		return this;
	}

	public WechatMessageBuilder setTemplateId(String templateId) {
		this.message.setTemplateId(templateId);
		return this;
	}

	public WechatMessageBuilder setUrl(String url) {
		this.message.setUrl(url);
		return this;
	}

	public WechatMessageBuilder addData(String key, String value, Color color) {

		Map<String, DataValue> data = this.message.getData();
		if (data == null) {
			data = new HashMap<>();
			this.message.setData(data);
		}

		data.put(key, new DataValue(value, color));

		return this;
	}

	public WechatMessageBuilder addData(String key, String value) {
		return addData(key, value, Color.BLACK);
	}

	public WechatMessage build() {
		return this.message;
	}

}
