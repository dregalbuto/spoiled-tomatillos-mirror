package edu.northeastern.cs4500.spoiledTomatillos.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import edu.northeastern.cs4500.spoiledTomatillos.user.service.UserService;

/**
 * This class is for Spring Security configuration
 * It also gives us a /login endpoint
 *
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
    private UserService userService;
	
	/**
	 * keep track of token data in database to provide RememberMe 
	 * functionality
	 * **/
	 
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
        auth.authenticationProvider(authenticationProvider());
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/registration",
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**",
						"/api/movies/**",
						"/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                    .loginPage("/login")
                        .permitAll()
            .and()
            		.csrf().disable()
                .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/login?logout")
            .permitAll();
    }
	
	
	@Bean
	public org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder passwordEncoder(){
		 return new BCryptPasswordEncoder();
	 }

	 @Bean
	 public DaoAuthenticationProvider authenticationProvider(){
		 DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		 auth.setUserDetailsService(userService);
		 auth.setPasswordEncoder(passwordEncoder());
		 return auth;
	 }

	 @Override
	 protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authenticationProvider());
	    }
}