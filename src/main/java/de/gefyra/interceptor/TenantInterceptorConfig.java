package de.gefyra.interceptor;

import ca.uhn.fhir.rest.server.RestfulServer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantInterceptorConfig {

	@Autowired
	private RestfulServer server;

	@Autowired
	private CustomHeaderBasedPartitionInterceptor customHeaderBasedPartitionInterceptor;

	@PostConstruct
	public void registerPartitionInterceptor() {
		server.registerInterceptor(customHeaderBasedPartitionInterceptor);
	}
}
