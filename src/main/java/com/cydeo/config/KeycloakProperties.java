package com.cydeo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class KeycloakProperties {
     // the purpose if this class is by using this class I can access these properties anywhere in the application

    // 1. add dependency on maven
   /*  <dependency>
            <groupId>org.keycloak</groupId>
            <artifactId>keycloak-admin-client</artifactId>
            <version>18.0.0</version>
        </dependency>
        <dependency>
            <groupId>org.jboss.spec.javax.ws.rs</groupId>
            <artifactId>jboss-jaxrs-api_2.1_spec</artifactId>
            <version>2.0.1.Final</version>
        </dependency>
        */

    // 2. add master keycloak info in application properties to create User
    /*
    master.user=admin
    master.user.password=admin
    master.realm=master
    master.client=master-client
     */
// @Value we are use that something we are reading for application properties
    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${master.user}")
    private String masterUser;

    @Value("${master.user.password}")
    private String masterUserPswd;

    @Value("${master.realm}")
    private String masterRealm;

    @Value("${master.client}")
    private String masterClient;

}