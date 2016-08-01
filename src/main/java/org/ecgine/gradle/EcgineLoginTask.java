package org.ecgine.gradle;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.ecgine.gradle.extensions.EcgineExtension;
import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import org.json.JSONObject;

public class EcgineLoginTask extends DefaultTask {

	@TaskAction
	public void login() {
		Object args = getProject().getProperties().get("args");
		if (args == null) {
			throw new GradleException("Please provide ecgine credentials. -Pargs=USERNAME,PASSWORD");
		}
		String[] split = args.toString().split(",");
		if (split.length < 2) {
			throw new GradleException("Invalid credentials formate. -Pargs=USERNAME,PASSWORD");
		}

		String apiKey = getApiKey(split[0], split[1]);
		System.out.println("Successfully got the apikey: " + apiKey);
		System.out.println("Please add below property in gradle.properties file");
		System.out.println("ecgine.apikey=" + apiKey);
	}

	private String getApiKey(String username, String pwd) {

		EcgineExtension ext = (EcgineExtension) getProject().getExtensions().getByName(EcgineExtension.NAME);
		try {
			String url = ext.getLoginUrl();
			System.out.println("Getting API key: " + url);
			List<NameValuePair> params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("email", username));
			params.add(new BasicNameValuePair("password", pwd));
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(url + "?" + URLEncodedUtils.format(params, "utf-8"));
			HttpResponse response = client.execute(request);
			int code = response.getStatusLine().getStatusCode();
			if (code != 200) {
				EntityUtils.consume(response.getEntity());
				throw new GradleException("StatusCode:" + code + " URL:" + url);
			}
			String json = EntityUtils.toString(response.getEntity());
			JSONObject result = new JSONObject(json);
			if (result.getInt("code") == 1) {
				return result.getString("message");
			} else {
				throw new GradleException("Request failed:" + result.getString("message"));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
