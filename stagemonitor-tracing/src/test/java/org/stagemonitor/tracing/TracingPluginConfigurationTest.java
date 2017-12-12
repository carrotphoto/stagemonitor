package org.stagemonitor.tracing;

import com.codahale.metrics.SharedMetricRegistries;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.stagemonitor.configuration.ConfigurationRegistry;
import org.stagemonitor.core.Stagemonitor;

import static org.junit.Assert.assertEquals;

public class TracingPluginConfigurationTest {

	private TracingPlugin config;

	@Before
	public void before() throws Exception {
		Stagemonitor.reset();
		SharedMetricRegistries.clear();
		ConfigurationRegistry configuration = ConfigurationRegistry.builder()
				.addOptionProvider(new TracingPlugin())
				.build();
		config = configuration.getConfig(TracingPlugin.class);
	}

	@After
	public void cleanUp() {
		Stagemonitor.reset();
		SharedMetricRegistries.clear();
	}

	@Test
	public void testDefaultValues() {
		assertEquals(1000000d, config.getProfilerRateLimitPerMinute(), 0);
		assertEquals(false, config.isLogSpans());
	}
}
