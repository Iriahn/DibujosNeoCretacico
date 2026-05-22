package com.example.proyecto.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.headers(headersConfigurer -> headersConfigurer
            .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.authorizeHttpRequests(
            auth -> auth
            .requestMatchers("/**").permitAll()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // para rutas: /css, /js /images
            .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")
            .anyRequest().authenticated())
            .formLogin(formLogin -> formLogin
                .defaultSuccessUrl("/", true)
                .permitAll())
            .logout(logout -> logout.permitAll())
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .httpBasic(Customizer.withDefaults());
        http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/accessError"));
        return http.build();
    } 
    // @Bean
    // SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
    //     http.headers(headersConfigurer -> headersConfigurer
    //         .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
    //     http.authorizeHttpRequests(
    //         auth -> auth
    //         .requestMatchers("/", "/public/**", "/dibujo/drawlist", "/files/**", "/usuario/autoUser/**", "/valoracion/vallist").permitAll()
    //         .requestMatchers("/valoracion/newVal/**", "/valoracion/borrarVal/**").authenticated()
    //         .requestMatchers("/api/**", "/dibujo/**", "/usuario/**", "/valoracion/borrarVal/**").hasRole("ADMIN")
    //         .requestMatchers("/dibujo/**", "/valoracion/**").hasRole("MANAGER")
    //         .requestMatchers("/accessError").permitAll()
    //         .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll() // para rutas: /css, /js /images
    //         .requestMatchers(PathRequest.toH2Console()).hasRole("ADMIN")
    //         .anyRequest().authenticated())
    //         .formLogin(formLogin -> formLogin
    //             .defaultSuccessUrl("/", true)
    //             .permitAll())
    //         .logout(logout -> logout.permitAll())
    //         .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
    //         .httpBasic(Customizer.withDefaults());
    //     http.exceptionHandling(exceptions -> exceptions.accessDeniedPage("/accessError"));
    //     return http.build();
    // } 

}
