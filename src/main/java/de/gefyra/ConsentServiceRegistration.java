package de.gefyra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.consent.ConsentInterceptor;

@Configuration
public class ConsentServiceRegistration {

	/**
	 * Dieser Method Call wird nach dem Erzeugen aller Beans aufgerufen
	 * und hängt den ConsentInterceptor ans FHIR-Servlet.
	 */
	@Autowired
	public void registerConsentInterceptor(
		RestfulServer restfulServer,
		ConsentInterceptor consentInterceptor) {
		// Convenience-Methode ruft intern getInterceptorService().registerInterceptor()
		restfulServer.registerInterceptor(consentInterceptor);
	}
}
