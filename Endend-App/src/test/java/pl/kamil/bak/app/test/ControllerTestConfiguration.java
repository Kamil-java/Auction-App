package pl.kamil.bak.app.test;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import pl.kamil.bak.project.auctionsite.domain.provider.session.UserSessionProvider;
import pl.kamil.bak.project.auctionsite.domain.user.service.UserService;
import pl.kamil.bak.project.auctionsite.security.WebSecurityConfig;

@TestConfiguration
public class ControllerTestConfiguration {
    @Bean
    public UserSessionProvider sessionProvider(){
        return Mockito.mock(UserSessionProvider.class);
    }

    @Bean
    @Primary
    @Order(7)
    public WebSecurityConfig testWebSecurityConfig(){
        return Mockito.mock(WebSecurityConfig.class);
    }

    @Bean
    public UserService userService(){
        return Mockito.mock(UserService.class);
    }
}
