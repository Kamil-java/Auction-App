package pl.kamil.bak.project.auctionsite.user.domian.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamil.bak.project.auctionsite.user.dao.UserRepository;
import pl.kamil.bak.project.auctionsite.user.dto.AddressDto;
import pl.kamil.bak.project.auctionsite.user.dto.LocationDto;
import pl.kamil.bak.project.auctionsite.user.dto.UserDto;
import pl.kamil.bak.project.auctionsite.user.model.Address;
import pl.kamil.bak.project.auctionsite.user.model.Location;
import pl.kamil.bak.project.auctionsite.user.model.User;
import pl.kamil.bak.project.auctionsite.user.model.enums.Type;

import java.util.Optional;
import java.util.UUID;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final LocationService locationService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final EmailService emailService;

    public UserService(UserRepository userRepository, LocationService locationService, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, TokenService tokenService, EmailService emailService) {
        this.userRepository = userRepository;
        this.locationService = locationService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    @Transactional
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public Optional<User> findByUsername(String name){
        return userRepository.findByUserName(name);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }



    public void addUser(UserDto userDto, LocationDto locationDto, AddressDto addressDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = new User();
        user.setLocation(locationService.addLocation(locationDto, addressDto));
        modelMapper.map(userDto, user);
        Optional<User> saved = Optional.of(save(user));
        saved.ifPresent(u -> {
            try {
                String token = UUID.randomUUID().toString();
                tokenService.save(saved.get(), token);

                emailService.sendHtmlMail(u);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        saved.get();
    }

    public void update (UserDto userDto,LocationDto locationDto, AddressDto addressDto, User user){
        if (userDto.getEmail()==null || userDto.getUserName()==null){
            if (userDto.getUserName()==null){
               userDto.setUserName(user.getUserName());
            }
            userDto.setEmail(user.getEmail());
        }
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUserName());
        user.setLocation(locationService.update(locationDto,addressDto, user));
        userRepository.save(user);
    }

    public void updatePassword(UserDto userDto, User user){
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
    }

    public void buyPremium(User user){
        if (user.getType().equals(Type.NORMAL.name())){
            user.setType(Type.PREMIUM.name());
        }else {
            user.setType(Type.NORMAL.name());
        }

    }

    public boolean userEmailExists(String email){
        return findByEmail(email).isPresent();
    }

    public boolean userNameExists(String userName){
        return findByUsername(userName).isPresent();
    }


}
