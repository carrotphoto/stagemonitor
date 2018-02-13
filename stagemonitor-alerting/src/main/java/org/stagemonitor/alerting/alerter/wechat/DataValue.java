package org.stagemonitor.alerting.alerter.wechat;

import java.awt.*;

/**
 * @author Qian Liping
 * @since 2018/2/9
 */
public class DataValue {

	private String value;
	private Color color;

	public DataValue() {

	}

	public DataValue(String value) {
		this(value, Color.BLACK);
	}

	public DataValue(String value, Color color) {
		this.value = value;
		this.color = color;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
