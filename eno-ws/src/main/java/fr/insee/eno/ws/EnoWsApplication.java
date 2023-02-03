package fr.insee.eno.ws;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.JdkClientHttpConnector;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.http.HttpClient;

@SpringBootApplication
public class EnoWsApplication {

	public static void main(String[] args) {
		// Remove unauthorized header names for jdk httpclient :
		System.setProperty("jdk.httpclient.allowRestrictedHeaders", "host,connection");
		SpringApplication.run(EnoWsApplication.class, args);
	}

	@Bean
	public WebClient webClientWithProxy(@Value("${test.url}") String baseUrl,
										@Value("${test.proxy.host}") String proxyHost,
										@Value("${test.proxy.port}") String proxyPort,
										WebClient.Builder builder) {
		if (proxyPort.matches("-?\\d+") && !"".equals(proxyHost)) {
			HttpClient httpClient = HttpClient.newBuilder()
					.proxy(ProxySelector.of(new InetSocketAddress(proxyHost,Integer.parseInt(proxyPort))))
					.build();
			builder.clientConnector(new JdkClientHttpConnector(httpClient));
		} else {
			builder.clientConnector(new JdkClientHttpConnector());
		}
		return builder.baseUrl(baseUrl)
				.build();
	}

	@Configuration
	public static class WebConfig implements WebFluxConfigurer{
		@Override
		public void configureViewResolvers(ViewResolverRegistry registry) {
			registry.viewResolver(new UrlBasedViewResolver());
		}
	}

}
