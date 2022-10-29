package dev.koicreek.bokasafn.mimirs.users.cfg;

import dev.koicreek.bokasafn.mimirs.users.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Environment env;
    private final AuthenticationManagerBuilder authManagerBuilder;

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2-console/**");
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http,
                                            AuthenticationProvider authProvider) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeRequests()
                .antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
            .and()
                .authenticationManager(new ProviderManager(authProvider))
                .addFilter(new CustomAuthFilter(env, authManagerBuilder.getOrBuild()));

        /* To Investigate:
         *  - Postman /users POST through the API Gateway succeeds
         *  - Postman /users POST directly to this service fails (403)
         * Thoughts:
         *  - Aren't both requests made via the same IP, i.e. localhost?
         *  - Or maybe Postman has its own IP address which isn't localhost?
         */

        return http.build();
    }

    @Bean
    AuthenticationProvider authProvider(UsersService usersService,
                                        PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(usersService);
        return provider;
    }

}
