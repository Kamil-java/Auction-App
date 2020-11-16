package pl.kamil.bak.project.auctionsite.user.domian.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kamil.bak.project.auctionsite.user.dao.TokenRepository;
import pl.kamil.bak.project.auctionsite.user.model.Token;
import pl.kamil.bak.project.auctionsite.user.model.User;

import java.sql.Timestamp;
import java.util.Calendar;

@Service
public class TokenService {
    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public Token findByToken(String token){
        return tokenRepository.findByToken(token);
    }

    @Transactional
    public Token findByUser(User user){
        return tokenRepository.findByUser(user);
    }

    @Transactional
    public void save(User user, String token){
        Token newToken = new Token(token,user);
        newToken.setExpiryDate(calculateExpiryDate(24*60));
        tokenRepository.save(newToken);
    }

    private Timestamp calculateExpiryDate(int expiryTimeMinutes){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, expiryTimeMinutes);
        return new Timestamp(cal.getTime().getTime());
    }
}
