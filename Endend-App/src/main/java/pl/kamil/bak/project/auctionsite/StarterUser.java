package pl.kamil.bak.project.auctionsite;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kamil.bak.project.auctionsite.model.productEntity.Product;
import pl.kamil.bak.project.auctionsite.domian.user.dao.UserRepository;
import pl.kamil.bak.project.auctionsite.domian.user.service.LocationService;
import pl.kamil.bak.project.auctionsite.domian.user.dto.AddressDto;
import pl.kamil.bak.project.auctionsite.domian.user.dto.LocationDto;
import pl.kamil.bak.project.auctionsite.model.enums.Role;
import pl.kamil.bak.project.auctionsite.model.enums.Status;
import pl.kamil.bak.project.auctionsite.model.enums.Type;
import pl.kamil.bak.project.auctionsite.model.userEntity.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;


//TODO Garbage class to be removed

@Component
public class StarterUser {

    public StarterUser(UserRepository userRepository, PasswordEncoder encoder, LocationService locationService) {
        User admin = new User();
        admin.setEmail("kam@op.pl");
        admin.setUserName("Kamil");
        admin.setPassword(encoder.encode("pass"));
        admin.setRole(Role.ADMIN.name());
        admin.setStatus(Status.ACTIVE.name());
        admin.setType(Type.PREMIUM.name());
        admin.setLocation(locationService.addLocation(new LocationDto("slask", "kato", null), new AddressDto("123", "23", "23321")));
        admin.setProduct(Arrays.asList(new Product("Buty", "Nike nowe", admin, BigDecimal.valueOf(200.00)), new Product("Auto", "Opel Astra", admin, BigDecimal.valueOf(15000.00))));

        User user = new User();
        user.setEmail("lau@op.pl");
        user.setUserName("Laura");
        user.setPassword(encoder.encode("pass"));
        user.setRole(Role.USER.name());
        user.setStatus(Status.ACTIVE.name());
        user.setLocation(locationService.addLocation(new LocationDto("slask", "katowice", null), new AddressDto("Koszutki", "23", "42303")));
        user.setProduct(Collections.singletonList(new Product("Telefon", "Samsung S8", user, BigDecimal.valueOf(2000.00))));

        userRepository.save(admin);
        userRepository.save(user);
    }

}
