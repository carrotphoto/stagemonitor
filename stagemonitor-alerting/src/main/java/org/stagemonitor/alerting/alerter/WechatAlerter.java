package org.stagemonitor.alerting.alerter;

import org.stagemonitor.alerting.AlertingPlugin;
import org.stagemonitor.alerting.alerter.wechat.AccessToken;
import org.stagemonitor.alerting.alerter.wechat.WechatJson;
import org.stagemonitor.alerting.alerter.wechat.WechatMessage;
import org.stagemonitor.alerting.alerter.wechat.WechatMessageBuilder;
import org.stagemonitor.alerting.incident.Incident;
import org.stagemonitor.core.Stagemonitor;
import org.stagemonitor.core.util.HttpClient;
import org.stagemonitor.core.util.Pair;
import org.stagemonitor.core.util.http.HttpRequest;
import org.stagemonitor.core.util.http.HttpRequestBuilder;

import java.text.DateFormat;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WechatAlerter extends Alerter {

	protected final HttpClient httpClient;
	private AlertingPlugin alertingPlugin;
	private final String appId;
	private final String secret;
	private final String templateId;

	public WechatAlerter() {
		this(Stagemonitor.getPlugin(AlertingPlugin.class));
	}

	public WechatAlerter(AlertingPlugin alertingPlugin) {
		this.httpClient = new HttpClient();
		this.alertingPlugin = alertingPlugin;

		this.appId = alertingPlugin.getWechatAppId();
		this.secret = alertingPlugin.getWechatSecret();
		this.templateId = alertingPlugin.getWechatWarnTemplateId();
	}

	@Override
	public void alert(AlertArguments alertArguments) {
		String accessToken = accessToken();
		WechatMessage wechatMessage = warnMessage(templateId, alertArguments);
		String url = variablesExpand(WECHAT_SEND_URL, accessToken);
		httpClient.send(HttpRequestBuilder.<String>jsonRequest("POST", url, wechatMessage.toJson()).build());
	}

	@Override
	public String getAlerterType() {
		return "Wechat";
	}

	@Override
	public String getTargetLabel() {
		return "OPENID";
	}

	private static final String WECHAT_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={ACCESS_TOKEN}";

	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APP_ID}&secret={SECRET}";

	private static final DateFormat df = DateFormat.getDateTimeInstance();

	private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{([^/]+?)\\}");

	public static String variablesExpand(String source, String... vars) {
		int len = vars == null ? 0 : vars.length;

		Matcher matcher = VARIABLE_PATTERN.matcher(source);
		StringBuffer sb = new StringBuffer();
		int count = 0;
		while (matcher.find()) {
			if (count < len)
				matcher.appendReplacement(sb, vars[count]);
			else
				matcher.appendReplacement(sb, "");
			count++;
		}
		return sb.toString();
	}

	private Pair<String, Long> token = Pair.of(null, 0L);

	private synchronized String accessToken() {
		long current = System.currentTimeMillis();

		if (token.getB() < current - TimeUnit.MINUTES.toMillis(1)) {
			String url = variablesExpand(ACCESS_TOKEN_URL, appId, secret);

			HttpRequest<AccessToken> get = HttpRequestBuilder.
					<AccessToken>of("GET", url)
					.successHandler(((httpRequest, is, statusCode, e) -> WechatJson.from(is, AccessToken.class)))
					.build();

			AccessToken at = httpClient.send(get);
			token = Pair.of(at.getAccessToken(), System.currentTimeMillis() + at.getExpiresIn());
		}

		return token.getA();
	}


	private WechatMessage warnMessage(String templateId, AlertArguments arguments) {
		Incident incident = arguments.getIncident();
		return new WechatMessageBuilder()
				.setTemplateId(templateId)
				.setUserOpenid(arguments.getSubscription().getTarget())
				.addData("first", incident.getCheckName())
				.addData("keyword1", String.format("From %s to %s", incident.getOldStatus(), incident.getNewStatus()))
				.addData("keyword2", df.format(incident.getFirstFailureAt()))
				.build();
	}


}
