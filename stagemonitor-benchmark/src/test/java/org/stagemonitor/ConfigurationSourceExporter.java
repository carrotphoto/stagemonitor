package org.stagemonitor;

import org.apache.commons.io.FileUtils;
import org.stagemonitor.configuration.ConfigurationRegistry;
import org.stagemonitor.core.StagemonitorPlugin;
import org.stagemonitor.core.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ServiceLoader;

public class ConfigurationSourceExporter {

	private ConfigurationSourceExporter() {
	}

	public static void main(String[] args) throws IOException {
		final String json = JsonUtils.toJson(ConfigurationRegistry.builder()
				.optionProviders(ServiceLoader.load(StagemonitorPlugin.class, ConfigurationRegistry.class.getClassLoader()))
				.build()
				.getConfigurationOptionsByCategory());
		System.out.println(json);
		File stagemonitorWidgetDevHtml = new File("stagemonitor-web-servlet/src/test/resources/stagemonitorWidgetDev.html");
		String content = FileUtils.readFileToString(stagemonitorWidgetDevHtml);
		content = content.replaceAll("configurationOptions .*", "configurationOptions = " + json.replace("$", "\\$").replace("\\\"", "\\\\\"") + ";");

		FileUtils.writeStringToFile(stagemonitorWidgetDevHtml, content);
	}
}
