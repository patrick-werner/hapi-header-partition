package de.gefyra.tenant;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.interceptor.api.IInterceptorBroadcaster;
import ca.uhn.fhir.jpa.model.config.PartitionSettings;
import ca.uhn.fhir.jpa.partition.RequestPartitionHelperSvc;
import java.lang.reflect.Field;
import java.util.Set;

public class CustomPartitionHelperSvc extends RequestPartitionHelperSvc {

	public CustomPartitionHelperSvc(FhirContext fhirContext, IInterceptorBroadcaster interceptorBroadcaster, PartitionSettings partitionSettings){
		super();

		// Setze Felder über Reflection, da keine public Setter vorhanden sind
		setPrivateField("myFhirContext", fhirContext);
		setPrivateField("myPartitionSettings", partitionSettings);
		setPrivateField("myInterceptorBroadcaster", interceptorBroadcaster);

		// Remove "Questionnaire" from the hardcoded non-partitionables
		removeQuestionnaireFromNonPartitionables();
	}

	private void setPrivateField(String fieldName, Object value) {
		try {
			Field field = RequestPartitionHelperSvc.class.getSuperclass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(this, value);
		} catch (Exception e) {
			throw new RuntimeException("Unable to set field " + fieldName, e);
		}
	}

	private void removeQuestionnaireFromNonPartitionables() {
		// Verwende Reflection, um Zugriff auf das private Feld zu erhalten
		try {
			var field = RequestPartitionHelperSvc.class.getSuperclass().getDeclaredField("myNonPartitionableResourceNames");
			field.setAccessible(true);
			Object rawValue = field.get(this);
			if (rawValue instanceof java.util.Set) {
				@SuppressWarnings("unchecked")
				Set<String> set = (Set<String>) rawValue;
				set.remove("Questionnaire");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to modify non-partitionable resource list", e);
		}
	}
}
