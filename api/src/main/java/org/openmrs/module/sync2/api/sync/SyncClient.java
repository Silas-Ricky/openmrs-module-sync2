package org.openmrs.module.sync2.api.sync;

import org.openmrs.api.context.Context;
import org.openmrs.module.sync2.client.rest.RestClient;

import java.util.Map;

public class SyncClient {

    private static final String REST_CLIENT_KEY = "rest";
    private static final String FHIR_CLIENT_KEY = "fhir";

    public PulledObject pullDataFromParent(String category, Map<String, String> resourceLinks, String address) {
        // get preferred link
        String preferredLink = resourceLinks.get(REST_CLIENT_KEY);
        if (preferredLink != null && !preferredLink.isEmpty()) {
            RestClient restClient = new RestClient();
            String url = address + preferredLink;
            return new RestPulledObject(restClient.getObject(category, url,
                    Context.getAdministrationService().getGlobalProperty("sync2.user.login"),
                    Context.getAdministrationService().getGlobalProperty("sync2.user.password")));
        }
        preferredLink = resourceLinks.get(FHIR_CLIENT_KEY);
        if (preferredLink != null && !preferredLink.isEmpty()) {
            // call FHIRClient
            return null;
        }
        return null;
    }
}
