package com.example.demo.config;

import com.example.demo.domain.user.entity.UserRoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Security role 수직적 계층 Security에 적용
    @Bean
    public RoleHierarchy roleHierarchy() {
        return RoleHierarchyImpl.withRolePrefix("Role_")
                .role(UserRoleType.ADMIN.toString()).implies(UserRoleType.USER.toString())
                .build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable());
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/join").permitAll()
                        .requestMatchers("/user/update/**").hasRole("USER")
                        .anyRequest().permitAll());  // 나머지는 일단 다 허용
        http
                .formLogin(login -> login
                .loginPage("/login")  // 커스텀 로그인 페이지 지정 (없다면 default 사용됨)
                .permitAll());

        return http.build();
    }

}
