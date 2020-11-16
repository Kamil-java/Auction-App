package pl.kamil.bak.project.auctionsite;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kamil.bak.project.auctionsite.product.model.Product;
import pl.kamil.bak.project.auctionsite.user.dao.UserRepository;
import pl.kamil.bak.project.auctionsite.user.domian.service.LocationService;
import pl.kamil.bak.project.auctionsite.user.dto.AddressDto;
import pl.kamil.bak.project.auctionsite.user.dto.LocationDto;
import pl.kamil.bak.project.auctionsite.user.model.*;
import pl.kamil.bak.project.auctionsite.user.model.enums.Role;
import pl.kamil.bak.project.auctionsite.user.model.enums.Status;
import pl.kamil.bak.project.auctionsite.user.model.enums.Type;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

@Configuration
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
