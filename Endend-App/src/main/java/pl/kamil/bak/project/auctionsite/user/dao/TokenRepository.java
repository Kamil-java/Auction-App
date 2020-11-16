package pl.kamil.bak.project.auctionsite.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamil.bak.project.auctionsite.user.model.Token;
import pl.kamil.bak.project.auctionsite.user.model.User;

public interface TokenRepository extends JpaRepository<Token,Long> {

    Token findByToken(String token);

    Token findByUser(User user);
}
