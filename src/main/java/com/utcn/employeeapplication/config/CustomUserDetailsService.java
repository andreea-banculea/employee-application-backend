//package com.utcn.employeeapplication.config;
//
//
//import com.utcn.employeeapplication.employee.Employee;
//import com.utcn.employeeapplication.employee.EmployeeRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collections;
//
//@Service
//@Transactional
//public class CustomUserDetailsService implements UserDetailsService {
//    private final EmployeeRepository employeeRepository;
//
//    @Autowired
//    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
//        this.employeeRepository = employeeRepository;
//    }
//
//    @Autowired
//    private PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder(11);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String emailOrUsername) throws UsernameNotFoundException {
//        Employee employee = employeeRepository.findByEmail(emailOrUsername).orElseThrow(() -> new UsernameNotFoundException("No user with this email/username."));
//        return new org.springframework.security.core.userdetails.User(
//                employee.getEmail(),
//                employee.getPassword(),
//                Collections.emptyList()
//        );
//    }
//
//}
