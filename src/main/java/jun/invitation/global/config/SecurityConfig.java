package jun.invitation.global.config;

import jun.invitation.domain.jwt.util.JwtAuthorizationFilter;
import jun.invitation.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;

    @Autowired
    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
        세션을 사용하지 않고, JWT를 이용하여 인증하기 때문에 formLogin, session -> STATELESS
         */

        return http.csrf(CsrfConfigurer::disable)
                .addFilter(corsFilter)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthorizationFilter(userRepository), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/user/**").hasAnyRole("USER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .build();
    }

}
