package jun.invitation.global.config;

import jun.invitation.auth.jwt.util.JwtAuthorizationFilter;
import jun.invitation.auth.oauth.handler.OAuthSuccessHandler;
import jun.invitation.auth.oauth.PrincipalOauth2UserService;
import jun.invitation.domain.user.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final AuthenticationEntryPoint entryPoint;
    @Autowired
    private final CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        /*
        세션을 사용하지 않고, JWT를 이용하여 인증하기 때문에 formLogin, session -> STATELESS
         */
        log.info("SecurityFilterChain EntryPoint :{}", entryPoint.getClass());

        return http.csrf(CsrfConfigurer::disable)
                .addFilter(corsFilter)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint))
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .successHandler(oAuthSuccessHandler)
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(principalOauth2UserService)
                        )
                )
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers("/user/**").hasAnyRole("USER")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .build();
    }

}
