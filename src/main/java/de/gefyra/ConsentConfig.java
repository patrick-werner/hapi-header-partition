package de.gefyra;

import ca.uhn.fhir.rest.server.interceptor.consent.ConsentInterceptor;
import ca.uhn.fhir.rest.server.interceptor.consent.IConsentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentConfig {

	/**
	 * Deine Consent-Service-Implementierung als Bean.
	 */
	@Bean
	public IConsentService myConsentService() {
		return new ConsentTest();
	}

	/**
	 * Der ConsentInterceptor, der die Hooks (@Hook-Methoden) liefert
	 * und deine Service-Logik über registerConsentService() anstößt.
	 *
	 * HAPI FHIR Spring Boot registriert automatisch alle Beans
	 * vom Typ IServerInterceptor.
	 */
	@Bean
	public ConsentInterceptor consentInterceptor(IConsentService myConsentService) {
		ConsentInterceptor interceptor = new ConsentInterceptor();
		interceptor.registerConsentService(myConsentService);
		return interceptor;
	}
}