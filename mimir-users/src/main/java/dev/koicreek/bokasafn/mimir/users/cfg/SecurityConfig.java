package dev.koicreek.bokasafn.mimir.users.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // Below line only allows requests coming from the API Gateway, but how?
                .authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"));

        /* To Investigate:
         *  - Postman /users POST through the API Gateway succeeds
         *  - Postman /users POST directly to this service fails (403)?!
         * Thoughts:
         *  - Both requests are made via localhost, aren't they? So why the difference?
         *  - Or maybe Postman has its own IP address which isn't 127.0.0.1?
         */
    }

    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    public BCryptPasswordEncoder generateBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
