package pl.kamil.bak.project.auctionsite.user.domian.provider;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.kamil.bak.project.auctionsite.user.model.User;

@Component
public class UserSessionProvider {
    public User getPrincipal() {
        User user = null;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User) {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        return user;
    }
}
