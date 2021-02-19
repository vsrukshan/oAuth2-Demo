package com.ruk.authserverdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig
        extends AuthorizationServerConfigurerAdapter {

    private AuthenticationManager authenticationManager;
    private UserDetailsService inMemoryUserDetailsManager;
//    private DataSource dataSource;
//    private JdbcClientDetailsService jdbcClientDetailsService;

    @Autowired
    public AuthServerConfig(AuthenticationManager authenticationManager,
                            UserDetailsService inMemoryUserDetailsManager,
                            DataSource dataSource,
                            JdbcClientDetailsService jdbcClientDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
//        this.dataSource = dataSource;
//        this.jdbcClientDetailsService = jdbcClientDetailsService;
    }

//    BaseClientDetails clientDetails = new BaseClientDetails(clientId, resourceId)

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("client")
                .secret("secret")
                .authorizedGrantTypes("authorization_code", "refresh_token")
                .accessTokenValiditySeconds(5)
                .refreshTokenValiditySeconds(10000)
                .scopes("read", "write", "delete")
                .redirectUris("http://localhost:4200/home")
                .and()
                .withClient("res_server")
                .secret("res_server_secret");
//        clients.jdbc(dataSource);

    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager);
        endpoints.userDetailsService(inMemoryUserDetailsManager);
    }

    public void configure(AuthorizationServerSecurityConfigurer serverSecurityConfigurer) {
        serverSecurityConfigurer.checkTokenAccess("isAuthenticated()");
    }

}
