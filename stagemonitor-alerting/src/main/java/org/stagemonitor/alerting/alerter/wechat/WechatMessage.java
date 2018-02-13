package org.stagemonitor.alerting.alerter.wechat;

import java.util.Map;

/**
 * @author Qian Liping
 * @since 2018/2/9
 */
public class WechatMessage extends WechatJson {

	private String touser;
	private String templateId;
	private String url;
	private Miniprogram miniprogram;

	private Map<String, DataValue> data;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Miniprogram getMiniprogram() {
		return miniprogram;
	}

	public void setMiniprogram(Miniprogram miniprogram) {
		this.miniprogram = miniprogram;
	}

	public Map<String, DataValue> getData() {
		return data;
	}

	public void setData(Map<String, DataValue> data) {
		this.data = data;
	}

}
