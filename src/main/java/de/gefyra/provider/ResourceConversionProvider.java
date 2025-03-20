package de.gefyra.provider;

import ca.uhn.fhir.rest.annotation.Operation;
import ca.uhn.fhir.rest.annotation.ResourceParam;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import org.hl7.fhir.r4.model.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceConversionProvider {

	@Operation(name = "$convert")
	public Resource convertResource(@ResourceParam Resource inputResource, RequestDetails requestDetails) {
		// Simply return the resource to let HAPI FHIR serialize it based on Accept header
		return inputResource;
	}
}
