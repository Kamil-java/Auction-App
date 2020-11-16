package pl.kamil.bak.project.auctionsite.controllers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kamil.bak.project.auctionsite.user.domian.provider.UserSessionProvider;

import java.security.Principal;
import java.util.Collection;

@Controller
public class MainController {
    private final UserSessionProvider getPrincipal;

    public MainController(UserSessionProvider getPrincipal) {
        this.getPrincipal = getPrincipal;
    }

    @GetMapping("/mainPage")
    public String forUser(Principal principal, Model model) {
        if (getPrincipal.getPrincipal() != null) {
            model.addAttribute("name", principal.getName());
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            model.addAttribute("auth", authorities);
            return "mainPage";
        }
        return "index";
    }


    @GetMapping("/bidding")
    public String bidding() {
        return "bidding";
    }


}
