package com.ironhack.theBestMidtermProject.security;
import com.ironhack.theBestMidtermProject.service.impl.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);

//                .inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder.encode("123456")).roles("ADMIN", "USER")
//                .and()
//                .withUser("user").password(passwordEncoder.encode("123456")).roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable().authorizeRequests()
//                .mvcMatchers("/hello-world").authenticated()
//                .mvcMatchers("/goodbye-world").hasRole("ADMIN")
//                .mvcMatchers("/say-hello").authenticated()
//                .mvcMatchers("/getPost/**").authenticated()
//                .mvcMatchers("/addPost").hasAnyRole("ADMIN","CONTRIBUTOR")
//                .mvcMatchers("/addAuthor").hasRole("ADMIN")
//                .mvcMatchers("/updatePost/**").hasAnyRole("ADMIN", "CONTRIBUTOR")
//                .mvcMatchers("/updateAuthor/**").hasAnyRole("ADMIN", "CONTRIBUTOR")
//                .mvcMatchers("/deletePost/**").hasRole("ADMIN")
//                .mvcMatchers("/deleteAuthor/**").hasRole("ADMIN")
                .anyRequest().permitAll();

    }
}
