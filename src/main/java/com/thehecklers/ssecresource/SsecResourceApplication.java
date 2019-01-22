package com.thehecklers.ssecresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SsecResourceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsecResourceApplication.class, args);
	}

}

//@EnableWebSecurity
//class WebConfig extends WebSecurityConfigurerAdapter {
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//				.anyRequest().authenticated()
//				.and()
//				.oauth2ResourceServer().jwt();
//	}
//}

@RestController
class SimpleController {
	@GetMapping("/something")
	String something() {
		return "Hey, that's something!";
	}

	@GetMapping("/claims")
	String listClaims(@AuthenticationPrincipal Jwt jwt) {
		return "Claims: " + jwt.getClaims().toString();
	}

	@GetMapping("/email")
	String getSubscriberEmail(@AuthenticationPrincipal Jwt jwt) {
		return "Subscriber: " + jwt.getClaimAsString("sub");
	}
}