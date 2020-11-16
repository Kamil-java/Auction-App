package pl.kamil.bak.project.auctionsite.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.kamil.bak.project.auctionsite.user.domian.service.UserService;
import pl.kamil.bak.project.auctionsite.user.model.User;
import pl.kamil.bak.project.auctionsite.user.model.enums.Type;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            Optional<User> user = userService.findByEmail(email);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("No user Found with username: " + email);
            }
            return user.get();
        };
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/bidding", "/product/own/product").authenticated()
                .antMatchers(HttpMethod.GET, "/product/all").permitAll()
                .antMatchers(HttpMethod.POST, "/product/add/product").hasAuthority(Type.PREMIUM.name())
                .antMatchers(HttpMethod.PUT, "/product/edit/product/{id}").authenticated()
                .antMatchers(HttpMethod.DELETE, "/product/delete/{id}").authenticated()
                .antMatchers("/", "/sing-up", "activation", "/css/**", "/mainPage").permitAll()
                .anyRequest().authenticated()
                .and()
                //TODO restore the commented parts
                .formLogin()
//                .permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable();
                    .loginPage("/login").permitAll()
                    .defaultSuccessUrl("/mainPage", true)
                    .passwordParameter("password")
                    .usernameParameter("username")
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("userSecret!")
                    .rememberMeParameter("remember-me")
                .and()
                .logout()
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me", "Idea-52a54c20lll").permitAll();
    }
}
