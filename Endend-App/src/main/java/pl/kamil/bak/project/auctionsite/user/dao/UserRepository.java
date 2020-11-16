package pl.kamil.bak.project.auctionsite.user.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamil.bak.project.auctionsite.user.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUserName(String name);
}
