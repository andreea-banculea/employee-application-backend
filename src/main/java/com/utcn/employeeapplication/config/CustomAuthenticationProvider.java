//package com.utcn.employeeapplication.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//    private final CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    public CustomAuthenticationProvider(CustomUserDetailsService customUserDetailsService) {
//        this.customUserDetailsService = customUserDetailsService;
//    }
//
//    @Autowired
//    private PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(11);
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        UserDetails user = customUserDetailsService.loadUserByUsername(username);
//        return checkPassword(user, password);
//    }
//
//    private Authentication checkPassword(UserDetails user, String viewablePassword) {
//        if (passwordEncoder().matches(viewablePassword, user.getPassword())) {
//            return new UsernamePasswordAuthenticationToken(
//                    user.getUsername(),
//                    user.getPassword(),
//                    user.getAuthorities());
//        } else {
//            throw new BadCredentialsException("Wrong Credentials");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
