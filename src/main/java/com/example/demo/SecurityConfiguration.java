package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private SSUserDetailsService userDetailsService;      //SSUserDetailsService gave an error

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUserDetailsService(userRepository)
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/h2-console/**")
                .permitAll()
//                .access("hasAnyAuthority('USER','ADMIN')")
//                .anyRequest().authenticated()

                .antMatchers("/course")
                .access("hasAnyAuthority('USER','ADMIN')")
//                .anyRequest().authenticated()

                .antMatchers("/student")
                .access("hasAuthority('USER')")
//                .anyRequest().authenticated()

                .antMatchers("/admin")
                .access("hasAuthority('ADMIN')")
//                .anyRequest().authenticated()

                .antMatchers("/teacher")
                .access("hasAuthority('ADMIN')")
                .anyRequest().authenticated()

                .and()
             .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").permitAll()
                .and()
                .httpBasic();
            http.csrf().disable();
            http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
        throws Exception{
        auth.userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder());
    }
//        auth.inMemoryAuthentication()
////                .withUser("dave").password(passwordEncoder().encode("begreat")).authorities("ADMIN")
////                .and()
////                .withUser("user").password(passwordEncoder().encode("password")).authorities("USER")
////                .and()
////                .withUser("superuser").password(passwordEncoder().encode("password")).authorities("USER","ADMIN");
//    }
}
