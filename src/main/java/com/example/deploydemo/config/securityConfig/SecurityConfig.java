package com.example.deploydemo.config.securityConfig;

import com.example.deploydemo.service.security.JwtRequestFilter;
import com.example.deploydemo.service.security.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static com.example.deploydemo.service.security.Permission.*;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    @Value("${environment.allowedOrigins}")
    private String allowedOrigins;

    private final JwtRequestFilter jwtRequestFilter;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
            UserDetailServiceImpl userService,
            PasswordEncoder passwordEncoder
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins.split(",")));
        //configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:8077", "http://localhost:80", "http://3.70.177.167"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/swagger-ui.html",
                            "/v3/api-docs",
                            "/v3/api-docs/**",
                            "/swagger-ui/**"
                    ).permitAll();
                    auth.requestMatchers("/easyrent-api/v1/login").permitAll();
                    auth.requestMatchers("/easyrent-api/v1/register_owner").permitAll();
                    auth.requestMatchers("/easyrent-api/v1/register_tenant").hasAuthority(TENANT_USER_CREATE.toString());

                    auth.requestMatchers(HttpMethod.GET,"/easyrent-api/v1/security_checks/unauthorized_get").permitAll();
                    auth.requestMatchers(HttpMethod.POST,"/easyrent-api/v1/security_checks/unauthorized_post").permitAll();

                    auth.requestMatchers("/easyrent-api/v1/security_checks/authorized_as_owner_get").hasAuthority(OWNER_SPECIFIC_PERM.toString());
                    auth.requestMatchers("/easyrent-api/v1/security_checks/authorized_as_owner_post").hasAuthority(OWNER_SPECIFIC_PERM.toString());

                    auth.requestMatchers("/easyrent-api/v1/security_checks/authorized_as_tenant_get").hasAuthority(TENANT_SPECIFIC_PERM.toString());
                    auth.requestMatchers("/easyrent-api/v1/security_checks/authorized_as_tenant_post").hasAuthority(TENANT_SPECIFIC_PERM.toString());

                    auth.requestMatchers("/easyrent-api/v1/security_checks/role_check").permitAll();
                    auth.anyRequest().permitAll();

                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(
//                        ex->ex.defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/login"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        ))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

}
