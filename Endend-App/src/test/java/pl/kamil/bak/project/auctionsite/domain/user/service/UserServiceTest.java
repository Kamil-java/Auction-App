package pl.kamil.bak.project.auctionsite.domain.user.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.TemplateEngine;
import pl.kamil.bak.project.auctionsite.domain.token.dao.TokenRepository;
import pl.kamil.bak.project.auctionsite.domain.token.service.TokenService;
import pl.kamil.bak.project.auctionsite.domain.user.dao.AddressRepository;
import pl.kamil.bak.project.auctionsite.domain.user.dao.LocationRepository;
import pl.kamil.bak.project.auctionsite.domain.user.dao.UserRepository;
import pl.kamil.bak.project.auctionsite.model.userEntity.User;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JavaMailSender javaMailSender;


    private UserService userService;

    @BeforeEach
    public void init() {
        ModelMapper modelMapper = new ModelMapper();
        LocationService locationService = new LocationService(locationRepository, addressRepository, modelMapper);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        TokenService tokenService = new TokenService(tokenRepository);
        TemplateEngine templateEngine = new TemplateEngine();
        EmailService emailService = new EmailService(tokenService, templateEngine, javaMailSender);
        userService = new UserService(userRepository, locationService, modelMapper, passwordEncoder, tokenService, emailService);
    }

    @Test
    void findByEmail() {
        //given
        given(userRepository.findByEmail("abc@abc.pl")).willReturn(prepareUser());

        //when
        Optional<User> byEmail = userService.findByEmail("abc@abc.pl");

        //then
        assertNotNull(byEmail);
        assertThat(byEmail).hasSameClassAs(prepareUser());
    }

    @Test
    void findByUsername() {
        //given
        given(userRepository.findByUserName("abc")).willReturn(prepareUser());

        //when
        Optional<User> abc = userService.findByUsername("abc");

        //then
        assertThat(abc).isNotEmpty().isNotNull().hasSameClassAs(prepareUser());
        assertEquals("abc", abc.get().getUserName());
    }

    @Test
    void save() {
        //given
        given(userRepository.save(any())).willReturn(prepareUser().get());

        //when
        User user = prepareUser().get();
        User save = userService.save(user);

        //then
        assertThat(save).hasSameClassAs(prepareUser().get()).isNotNull();
        assertEquals(prepareUser().get().getId(), save.getId());
    }

    @Test
    void changeStatus() {
    }

    @Test
    void addUser() {
    }

    @Test
    void update() {
    }

    @Test
    void updatePassword() {
    }

    @Test
    void buyPremium() {
    }

    @Test
    void userEmailExists() {
    }

    @Test
    void userNameExists() {
    }

    private Optional<User> prepareUser() {
        Optional<User> user = Optional.of(new User());
        user.get().setId(1L);
        user.get().setUserName("abc");
        user.get().setEmail("abc@abc.pl");
        return user;
    }
}