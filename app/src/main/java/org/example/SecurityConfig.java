package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig{
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http
			.csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
			.authorizeHttpRequests(auth -> auth
					.requestMatchers("/api/**").authenticated()
					.anyRequest().permitAll())
			.exceptionHandling(e -> e
					.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));

		return http.build();
	}
}
