//
//
//package com.utcn.employeeapplication.config;
//
//
//import com.utcn.employeeapplication.util.handlers.LoginFailureHandler;
//import com.utcn.employeeapplication.util.handlers.LoginSuccessHandler;
//import com.utcn.employeeapplication.util.handlers.LogoutSuccessHandler;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//
//@Configuration
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    private static final String[] AUTH_WHITELIST = {
//            // -- Swagger UI v2
//            "/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/webjars/**",
//            // -- Swagger UI v3 (OpenAPI)
//            "/v3/api-docs/**",
//            "/swagger-ui/**"
//            // other public endpoints of your API may be appended to this array
//    };
//    private final CustomAuthenticationProvider customAuthenticationProvider;
//    private final LoginSuccessHandler loginSuccessHandler;
//    private final LoginFailureHandler loginFailureHandler;
//    private final LogoutSuccessHandler logoutSuccessHandler;
//
//    public WebSecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, LoginSuccessHandler loginSuccessHandler, LoginFailureHandler loginFailureHandler, LogoutSuccessHandler logoutSuccessHandler) {
//        this.customAuthenticationProvider = customAuthenticationProvider;
//        this.loginSuccessHandler = loginSuccessHandler;
//        this.loginFailureHandler = loginFailureHandler;
//        this.logoutSuccessHandler = logoutSuccessHandler;
//    }
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(requests -> requests
//                        .requestMatchers(AUTH_WHITELIST).permitAll()
//                        .requestMatchers("/error").permitAll()
//                        .anyRequest().authenticated())
//                .cors()
//                .configurationSource(corsConfigurationSource())
//                .and()
//                .csrf()
//                .disable()
//                .formLogin()
////                .loginPage("http://localhost:3000/login")
//                .loginProcessingUrl("/login")
//                .successHandler(loginSuccessHandler)
//                .failureHandler(loginFailureHandler)
//                .and()
//                .logout()
//                .logoutUrl("/logout") // Specify the logout URL
//                .logoutSuccessHandler(logoutSuccessHandler)
//                .invalidateHttpSession(true)
//                .clearAuthentication(true)
//                .deleteCookies("JSESSIONID");
//        return http.build();
//    }
//
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:3000");
//        configuration.addAllowedOrigin("http://localhost:5173");
//        configuration.addAllowedOrigin("http://localhost:8080");
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
//        configuration.addAllowedHeader("*");
//        configuration.addExposedHeader("*");
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}