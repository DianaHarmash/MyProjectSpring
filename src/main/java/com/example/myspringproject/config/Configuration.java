package com.example.myspringproject.config;


import com.example.myspringproject.locales.LocaleEng;
import com.example.myspringproject.locales.LocaleRus;
import com.example.myspringproject.locales.MyLocale;
import com.example.myspringproject.model.Role;
import com.example.myspringproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.Cookie;
import javax.sql.DataSource;

@org.springframework.context.annotation.Configuration
public class Configuration extends  WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private DataSource dataSource;

    private MyLocale myLocale = new LocaleEng();

    public MyLocale getLocale() {
        return myLocale;
    }

    public void setLocale(String locale) {
        if ("rus".equals(locale)) {
            myLocale = new LocaleRus();
        } else {
            myLocale = new LocaleEng();
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/logIn", "signIn").permitAll()
                .antMatchers("/user/**").hasRole(Role.ROLE_USER.getName())
                .antMatchers("/admin/**").hasRole(Role.ROLE_ADMIN.getName())
                .and().formLogin().loginPage("/signIn")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    if (authentication.isAuthenticated()){
                        long userId = userService.findUser(authentication.getName()).get().getId();
                        Cookie cookie = new Cookie("user-id", String.valueOf(userId));
                        cookie.setPath("/");
                        cookie.setMaxAge(86400);
                        httpServletResponse.addCookie(cookie);
                        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                            httpServletResponse.sendRedirect("/admin/main/"+userId);
                        } else {
                            httpServletResponse.sendRedirect("/user/main/"+userId);
                        }
                    }
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setStatus(403);
                    httpServletResponse.sendRedirect("/signIn");
                })
                .and().formLogin().loginPage("/logIn")
                //.defaultSuccessUrl("/admin/main/1")
                .successHandler((httpServletRequest, httpServletResponse, authentication) -> {
                    if (authentication.isAuthenticated()){
                        long userId = userService.findUser(authentication.getName()).get().getId();
                        Cookie cookie = new Cookie("user-id", String.valueOf(userId));
                        cookie.setPath("/");
                        cookie.setMaxAge(86400);
                        httpServletResponse.addCookie(cookie);
                        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
                            httpServletResponse.sendRedirect("/admin/main/"+userId);
                        } else {
                            httpServletResponse.sendRedirect("/user/main/"+userId);
                        }
                    }
                })
                .failureHandler((httpServletRequest, httpServletResponse, e) -> {
                    httpServletResponse.setStatus(403);
                    if ("/logIn".equals(httpServletRequest.getRequestURI())){
                        httpServletResponse.sendRedirect("/logIn");
                    } else {
                        httpServletResponse.sendRedirect("/signIn");
                    }

                })
                .and()
                .exceptionHandling().accessDeniedPage("/forbidden");
                //.failureForwardUrl("/forbidden");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new PasswordEncoder() {
                    @Override
                    public String encode(CharSequence charSequence) {
                        return new Encoder().encode(charSequence.toString());
                    }

                    @Override
                    public boolean matches(CharSequence charSequence, String s) {
                        return this.encode(charSequence).equals(s);
                    }
                })
                .usersByUsernameQuery("SELECT login, password, true as enabled FROM user_entity WHERE login=?")
                .authoritiesByUsernameQuery("SELECT login, role ,true as enabled FROM user_entity where login=?");
    }

    public boolean config(String userId) {
        return userService.findUserById(Integer.valueOf(userId).longValue()).getRole().name().equals(Role.ROLE_ADMIN.name());
    }

    public boolean configUser(String userId) {
        return userService.findUserById(Integer.valueOf(userId).longValue()).getRole().name().equals(Role.ROLE_USER.name());
    }
}
