package de.gefyra.tenant;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.interceptor.api.IInterceptorBroadcaster;
import ca.uhn.fhir.jpa.model.config.PartitionSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TenantPartitioningConfiguration {

	@Bean
	@Primary
	public CustomPartitionHelperSvc customPartitionHelperSvc(
		FhirContext fhirContext,
		IInterceptorBroadcaster interceptorBroadcaster,
		PartitionSettings partitionSettings) {

		return new CustomPartitionHelperSvc(fhirContext, interceptorBroadcaster, partitionSettings);
	}
}
