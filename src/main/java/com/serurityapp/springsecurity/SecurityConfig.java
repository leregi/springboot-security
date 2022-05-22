package com.serurityapp.springsecurity;

import com.serurityapp.springsecurity.entities.AppUser;
import com.serurityapp.springsecurity.filters.JwtAuthenticationFilter;
import com.serurityapp.springsecurity.service.AccountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AccountService accountService;

    public SecurityConfig(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(new UserDetailsManager() {
                @Override
                public void createUser(UserDetails user) {

                }

                @Override
                public void updateUser(UserDetails user) {

                }

                @Override
                public void deleteUser(String username) {

                }

                @Override
                public void changePassword(String oldPassword, String newPassword) {

                }

                @Override
                public boolean userExists(String username) {
                    return false;
                }

                @Override
                public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                    AppUser appUser = accountService.loadUserByUserName(username);
                    Collection<GrantedAuthority> authorities = new ArrayList<>();

                    appUser.getAppRoles().forEach(r -> {
                        authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
                    });
                    return new User(appUser.getUsername(), appUser.getPassword(), authorities);
                }
            });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().disable();
//        http.formLogin();
        http.authorizeHttpRequests().antMatchers("/h2-console").permitAll();
        http.authorizeHttpRequests().anyRequest().authenticated();
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean()));


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
