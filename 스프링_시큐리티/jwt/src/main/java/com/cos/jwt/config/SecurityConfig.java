package com.cos.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {
    private final CorsFilter corsFilter;

    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))) // 세션을 사용하지 않겠다.
                .addFilter(corsFilter) // @CrossOrigin(인증 X), 시큐리티 필터에 등록 인증(O)
                .formLogin(AbstractHttpConfigurer::disable)
                /**
                 * 기존의 **httpBasic** 방식은 클라이언트의 요청 시 http `Authorization` 헤더에 ID, PW 가 같이 날라가는 방식이다.
                 * 이때 ID, PW 는 암호화가 되어 있지 않기 때문에 보안상 좋지 못하다.
                 * 따라서 **Bearer** 방식을 사용하면 http `Authorization` 헤더에 token(유효시간이 있음) 이 담기기 때문에
                 * 토큰을 탈취당해도 ID, PW 가 노출 될 일은 없다.
                 * */
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequest) ->
                        authorizeRequest
                                .requestMatchers("/api/v1/user/**").hasAnyRole("ADMIN", "MANAGER", "USER")
                                .requestMatchers("/api/v1/manager/**").hasAnyRole("ADMIN", "MANAGER")
                                .requestMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
                                .anyRequest().permitAll() // 그 이외 접근은 모두 허용한다.
                )
                .build();
    }

}
