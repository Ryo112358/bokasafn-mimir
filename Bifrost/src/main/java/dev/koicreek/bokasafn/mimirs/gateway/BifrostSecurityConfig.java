package dev.koicreek.bokasafn.mimirs.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BifrostSecurityConfig {

//    @Bean
//    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
////        http.cors().configurationSource(new CorsConfigurationSource() {
////            @Override
////            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
////                return null;
////            }
////        });
//
//        http.csrf().ignoringAntMatchers("/**");
//
//        return http.build();
//    }

//    @Bean
//    public CorsWebFilter corsWebFilter() {
//
//        final CorsConfiguration corsConfig = new CorsConfiguration();
//        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
//        corsConfig.setMaxAge(3600L);
////        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST"));
//        corsConfig.setAllowedMethods(Collections.singletonList("*"));
//        corsConfig.addAllowedHeader("*");
//
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//
//        return new CorsWebFilter(source);
//    }
}
