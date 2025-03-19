package de.gefyra.interceptor;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.interceptor.model.RequestPartitionId;
import ca.uhn.fhir.rest.server.servlet.ServletRequestDetails;
import org.springframework.stereotype.Component;

@Component
@Interceptor
public class CustomHeaderBasedPartitionInterceptor {

	@Hook(Pointcut.STORAGE_PARTITION_IDENTIFY_CREATE)
	public RequestPartitionId PartitionIdentifyCreate(ServletRequestDetails theRequestDetails) {
		String partitionName = theRequestDetails.getHeader("X-Partition-Name");
		return RequestPartitionId.fromPartitionName(partitionName);
	}

	@Hook(Pointcut.STORAGE_PARTITION_IDENTIFY_READ)
	public RequestPartitionId PartitionIdentifyRead(ServletRequestDetails theRequestDetails) {
		String partitionName = theRequestDetails.getHeader("X-Partition-Name");
		return RequestPartitionId.fromPartitionName(partitionName);
	}
}
