package com.thehecklers.secresserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SecResServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecResServerApplication.class, args);
	}

}

@EnableWebSecurity
class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.mvcMatchers("/claims/**").hasAuthority("SCOPE_openid")
				.mvcMatchers("/email/**").hasAuthority("SCOPE_email")
				.anyRequest().authenticated()
				.and()
				.oauth2ResourceServer().jwt();
	}
}

@RestController
@RequestMapping("/resources")
class ResController {
	@GetMapping("/something")
	String getSomething() {
		return "This is really something!";
	}

	@GetMapping("/claims")
	String getClaims(@AuthenticationPrincipal Jwt jwt) {
		return jwt.getClaims().toString();
	}

	@GetMapping("/email")
	String getEmail(@AuthenticationPrincipal Jwt jwt) {
		return jwt.getSubject();
	}
}