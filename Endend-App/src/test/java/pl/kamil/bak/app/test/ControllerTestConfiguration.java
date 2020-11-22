package pl.kamil.bak.app.test;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import pl.kamil.bak.project.auctionsite.domain.provider.session.UserSessionProvider;
import pl.kamil.bak.project.auctionsite.domain.user.service.UserService;

@TestConfiguration
public class ControllerTestConfiguration {
    @Bean
    public UserSessionProvider sessionProvider(){
        return Mockito.mock(UserSessionProvider.class);
    }

    @Bean
    public UserService userService(){
        return Mockito.mock(UserService.class);
    }
}
