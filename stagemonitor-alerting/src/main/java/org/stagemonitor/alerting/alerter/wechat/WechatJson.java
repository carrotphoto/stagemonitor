package org.stagemonitor.alerting.alerter.wechat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Qian Liping
 * @since 2018/2/9
 */
public abstract class WechatJson {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);

		SimpleModule module = new SimpleModule();
		module.addSerializer(Color.class, new ColorSerializer());

		MAPPER.registerModule(module);
	}

	private static class ColorSerializer extends StdSerializer<Color> {
		private ColorSerializer() {
			super(Color.class);
		}

		@Override
		public void serialize(Color color,
							  JsonGenerator json,
							  SerializerProvider provider) throws IOException {

			int red = color.getRed();
			int green = color.getGreen();
			int blue = color.getBlue();
			String format = String.format("#%02x%02x%02x", red, green, blue);
			json.writeString(format);
		}

	}

	public String toJson() {
		try {
			return MAPPER.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return null;
	}


	public static <T extends WechatJson> T from(InputStream in, Class<T> clz) {
		try {
			return MAPPER.readValue(in, clz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
