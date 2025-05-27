package de.gefyra;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;
import ca.uhn.fhir.rest.server.interceptor.consent.ConsentOutcome;
import ca.uhn.fhir.rest.server.interceptor.consent.IConsentContextServices;
import ca.uhn.fhir.rest.server.interceptor.consent.IConsentService;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Observation;
import org.hl7.fhir.r4.model.ResourceType;


public class ConsentTest implements IConsentService {

	/**
	 * Invoked once at the start of every request
	 */
	@Override
	public ConsentOutcome startOperation(
		RequestDetails theRequestDetails,
		IConsentContextServices theContextServices) {

		// getResourceName() is null on transaction/batch or $operations
		String resourceName = theRequestDetails.getResourceName();
		if (resourceName == null) {
			// not a simple resource read/search -> skip consent
			return ConsentOutcome.AUTHORIZED;
		}

		// Only apply consent logic for Questionnaires
		if ("Observation".equals(resourceName)) {
			return ConsentOutcome.PROCEED;
		}

		// All other resource types shortcut out
		return ConsentOutcome.AUTHORIZED;
	}


	/**
	 * Can a given resource be returned to the user?
	 */
	@Override
	public ConsentOutcome canSeeResource(
		RequestDetails theRequestDetails,
		IBaseResource theResource,
		IConsentContextServices theContextServices) {
		// In this basic example, we will filter out lab results so that they
		// are never disclosed to the user. A real interceptor might do something
		// more nuanced.
		if (theResource instanceof Observation) {
			Observation obs = (Observation) theResource;
			if (obs.getCategoryFirstRep()
				.hasCoding("http://hl7.org/fhir/codesystem-observation-category.html", "laboratory")) {
				return ConsentOutcome.REJECT;
			}
		}

		// Otherwise, allow the
		return ConsentOutcome.PROCEED;
	}

}