package de.ahus1.keycloak.dropwizardjaxrs;

import com.google.common.base.Optional;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import org.keycloak.KeycloakSecurityContext;

import javax.servlet.http.HttpServletRequest;

/**
 * Authentication to make it work with Keycloak.
 * @param <P> authentication class you will use throughout your application.
 *           You can use AbstractAuthentication as a base class here.
 */
public abstract class AbstractKeycloakAuthenticator<P> implements Authenticator<HttpServletRequest, P> {

    @Override
    public Optional<P> authenticate(HttpServletRequest request) throws AuthenticationException {
        KeycloakSecurityContext securityContext = (KeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());
        if (securityContext != null) {
            return Optional.fromNullable(prepareAuthentication(securityContext, request));
        } else {
            return Optional.absent();
        }
    }

    protected abstract P prepareAuthentication(KeycloakSecurityContext securityContext, HttpServletRequest request);
}
