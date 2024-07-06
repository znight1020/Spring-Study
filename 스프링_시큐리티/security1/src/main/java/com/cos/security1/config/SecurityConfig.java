package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
public class SecurityConfig {

    // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests((authorizeRequest) ->
                        authorizeRequest
                                // 구체적인 순서대로 작성해야 한다.
                                .requestMatchers("/user/**").authenticated() // 인증된 사용자만 user 경로에 접근할 수 있다.
                                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER") // manager 경로 접근 시 인증 뿐 아니라 ADMIN 혹은 MANAGER 역할을 가지고 있어야 한다.
                                .requestMatchers("/admin/**").hasAnyRole("ADMIN") // admin 경로 접근 시 인증 뿐 아니라 ADMIN 역할을 가지고 있어야 한다.
                                .anyRequest().permitAll() // 그 이외 접근은 모두 허용한다.
                )
                .formLogin(configurer ->
                        configurer.loginPage("/loginForm")
                                .loginProcessingUrl("/login") // login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
                                .defaultSuccessUrl("/")
                )
                //.formLogin(Customizer.withDefaults()) // 기존 Security 로그인 페이지 띄우기
                .build();
    }
}
