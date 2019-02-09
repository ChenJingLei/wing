package com.chinatel.caur2cdoauth2.config;

import com.chinatel.caur2cdoauth2.service.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.approval.DefaultUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class OAuth2ServerConfig {

    private static final String WING_OAUTH2_RESOURCE_ID = "wing-oauth2";

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        /*
         * 1、ClientDetails oauth_client_details (client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove, client_id)
         * 2、AccessToken oauth_access_token (token_id, token, authentication_id, user_name, client_id, authentication, refresh_token)
         * 3、Code oauth_code (code, authentication)
         */

        @Override
        //ClientDetailsServiceConfigurer: a configurer that defines the client details service. Client details can be initialized, or you can just refer to an existing store.
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            super.configure(clients);
            // @formatter:off
            clients.inMemory()
                    .withClient("user")
                    .scopes("read", "write")
                    .authorities("ROLE_USER")
                    .authorizedGrantTypes("password", "refresh_token")
                    .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("1"))
                    .accessTokenValiditySeconds(60)
//                    .withClient("tonr")
//                    .resourceIds(SPARKLR_RESOURCE_ID)
//                    .authorizedGrantTypes("authorization_code", "implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write")
//                    .secret("secret")
//                    .redirectUris("http://localhost:8080/tonr2/sparklr/photos")
//                    .and()
//                    .withClient("tonr-with-redirect")
//                    .resourceIds(SPARKLR_RESOURCE_ID)
//                    .authorizedGrantTypes("authorization_code", "implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write")
//                    .secret("secret")
//                    .redirectUris(tonrRedirectUri)
//                    .and()
//                    .withClient("my-client-with-registered-redirect")
//                    .resourceIds(SPARKLR_RESOURCE_ID)
//                    .authorizedGrantTypes("authorization_code", "client_credentials")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "trust")
//                    .redirectUris("http://anywhere?key=value")
//                    .and()
//                    .withClient("my-trusted-client")
//                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                    .scopes("read", "write", "trust")
//                    .accessTokenValiditySeconds(60)
//                    .and()
//                    .withClient("my-trusted-client-with-secret")
//                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
//                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
//                    .scopes("read", "write", "trust")
//                    .secret("somesecret")
//                    .and()
//                    .withClient("my-less-trusted-client")
//                    .authorizedGrantTypes("authorization_code", "implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write", "trust")
//                    .and()
//                    .withClient("my-less-trusted-autoapprove-client")
//                    .authorizedGrantTypes("implicit")
//                    .authorities("ROLE_CLIENT")
//                    .scopes("read", "write", "trust")
//                    .autoApprove(true)
            ;
            // @formatter:on

        }

        @Autowired
        private DataSource dataSource;

        @Autowired
        private JdbcTokenStore jdbcTokenStore;

        @Bean
        public JdbcTokenStore jdbcTokenStore() {
            return new JdbcTokenStore(dataSource);
        }

        @Autowired
        private JPAUserDetailsService userDetailsService;

//        @Autowired
//        private AuthenticationManager authenticationManager;
//
//        @Bean
//        public AuthenticationManager authenticationManager() {
//            return new OAuth2AuthenticationManager();
//        }

        @Autowired
        private AuthorizationCodeServices authorizationCodeServices;

        @Bean
        public AuthorizationCodeServices authorizationCodeServices() {
            return new JdbcAuthorizationCodeServices(dataSource);
        }

        @Autowired
        private UserApprovalHandler userApprovalHandler;

        @Bean
        public UserApprovalHandler userApprovalHandler() {
            return new DefaultUserApprovalHandler();
        }

        @Override
        // AuthorizationServerSecurityConfigurer: defines the security constraints on the token endpoint.
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            super.configure(security);
            security.passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder())
            // Enforcing SSL
            // https://projects.spring.io/spring-security-oauth/docs/oauth2.html#Enforcing SSL
//                    .sslOnly()
            ;
        }

        @Autowired
        private OAuth2RequestFactory oAuth2RequestFactory;

        @Autowired
        private ClientDetailsService clientDetailsService;

        @Bean
        public OAuth2RequestFactory oAuth2RequestFactory() {

            OAuth2RequestFactory factory = new DefaultOAuth2RequestFactory(clientDetailsService);
            ((DefaultOAuth2RequestFactory) factory).setCheckUserScopes(true);
            return factory;
        }

        @Autowired
        private TokenStore tokenStore;

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Override
        // AuthorizationServerEndpointsConfigurer: defines the authorization and token endpoints and the token services.
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            super.configure(endpoints);
            //token services
            endpoints.tokenStore(tokenStore)
                    // authenticationManager: password grants are switched on by injecting an AuthenticationManager.
                    .authenticationManager(authenticationManager)
                    /* userDetailsService: if you inject a UserDetailsService or if one is configured globally anyway
                     * (e.g. in a GlobalAuthenticationManagerConfigurer) then a refresh token grant will contain a check
                     * on the user details, to ensure that the account is still active
                     */
                    .userDetailsService(userDetailsService)
            // authorizationCodeServices: defines the authorization code services (instance of AuthorizationCodeServices) for the auth code grant.
//                    .authorizationCodeServices(authorizationCodeServices)

            // implicitGrantService: manages state during the imlpicit grant.
            // /** @deprecated */ implicitGrantService

            // tokenGranter: the TokenGranter (taking full control of the granting and ignoring the other properties above)
//                    .tokenGranter()
            /*
             * Configuring the Endpoint URLs
             * The AuthorizationServerEndpointsConfigurer has a pathMapping() method. It takes two arguments:
             *      The default (framework implementation) URL path for the endpoint
             *      The custom path required (starting with a "/")
             */
//                    .pathMapping("/oauth/authorize","/api/oauth/authorize")
            /*
             * The URL paths provided by the framework are
             * /oauth/authorize (the authorization endpoint),
             * /oauth/token (the token endpoint),
             * /oauth/confirm_access (user posts approval for grants here),
             * /oauth/error (used to render errors in the authorization server),
             * /oauth/check_token (used by Resource Servers to decode access tokens), and
             * /oauth/token_key (exposes public key for token verification if using JWT tokens).
             */
            // https://projects.spring.io/spring-security-oauth/docs/oauth2.html#Customizing the UI
//                    .userApprovalHandler(userApprovalHandler)
            // Mapping User Roles to Scopes
//                    .requestFactory(oAuth2RequestFactory)
            ;

        }
    }

    @Configuration
//    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            super.configure(resources);
//            resources
            // tokenServices: the bean that defines the token services (instance of ResourceServerTokenServices).
//                    .tokenServices()
            // resourceId: the id for the resource (optional, but recommended and will be validated by the auth server if present).
//                    .resourceId()
            /*
             * other extension points for the resourecs server (e.g. tokenExtractor for extracting the tokens from incoming requests)
             * request matchers for protected resources (defaults to all)
             * access rules for protected resources (defaults to plain "authenticated")
             * other customizations for the protected resources permitted by the HttpSecurity configurer in Spring Security
             */
//                    .
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            super.configure(http);
//            // @formatter:off
//            http
//                    // Since we want the protected resources to be accessible in the UI as well we need
//                    // session creation to be allowed (it's disabled by default in 2.0.6)
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                    .and()
//                    .requestMatchers().antMatchers("/photos/**", "/oauth/users/**", "/oauth/clients/**","/me")
//                    .and()
//                    .authorizeRequests()
//                    .antMatchers("/me").access("#oauth2.hasScope('read')")
//                    .antMatchers("/photos").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
//                    .antMatchers("/photos/trusted/**").access("#oauth2.hasScope('trust')")
//                    .antMatchers("/photos/user/**").access("#oauth2.hasScope('trust')")
//                    .antMatchers("/photos/**").access("#oauth2.hasScope('read') or (!#oauth2.isOAuth() and hasRole('ROLE_USER'))")
//                    .regexMatchers(HttpMethod.DELETE, "/oauth/users/([^/].*?)/tokens/.*")
//                    .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('write')")
//                    .regexMatchers(HttpMethod.GET, "/oauth/clients/([^/].*?)/users/.*")
//                    .access("#oauth2.clientHasRole('ROLE_CLIENT') and (hasRole('ROLE_USER') or #oauth2.isClient()) and #oauth2.hasScope('read')")
//                    .regexMatchers(HttpMethod.GET, "/oauth/clients/.*")
//                    .access("#oauth2.clientHasRole('ROLE_CLIENT') and #oauth2.isClient() and #oauth2.hasScope('read')");
            // @formatter:on
        }
    }

}
