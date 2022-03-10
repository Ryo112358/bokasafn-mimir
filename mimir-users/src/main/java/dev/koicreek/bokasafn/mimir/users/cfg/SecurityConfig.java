package dev.koicreek.bokasafn.mimir.users.cfg;

import dev.koicreek.bokasafn.mimir.users.security.AuthenticationFilter;
import dev.koicreek.bokasafn.mimir.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    protected void configure(AuthenticationManagerBuilder amb) throws Exception {
        amb.userDetailsService(usersService).passwordEncoder(passwordEncoder);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // Below line only allows requests coming from the API Gateway, but how?
                .authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
                .and()
                .addFilter(this.getAuthenticationFilter());

        /* To Investigate:
         *  - Postman /users POST through the API Gateway succeeds
         *  - Postman /users POST directly to this service fails (403)?!
         * Thoughts:
         *  - Both requests are made via localhost, aren't they?
         *  - Or maybe Postman has its own IP address which isn't 127.0.0.1?
         */
    }

    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        AuthenticationFilter authFilter =
                new AuthenticationFilter(usersService, env, authenticationManager());
        // authFilter.setAuthenticationManager(authenticationManager());

        // authFilter.setFilterProcessesUrl("/users/login");    // Default url is "/login"

        return authFilter;
    }

}
