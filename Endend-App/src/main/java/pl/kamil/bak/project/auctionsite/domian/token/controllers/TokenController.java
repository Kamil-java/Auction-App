package pl.kamil.bak.project.auctionsite.domian.token.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kamil.bak.project.auctionsite.domian.token.service.TokenService;
import pl.kamil.bak.project.auctionsite.domian.user.service.UserService;
import pl.kamil.bak.project.auctionsite.model.tokenEntity.Token;
import pl.kamil.bak.project.auctionsite.model.userEntity.User;

import java.time.LocalDateTime;

@Controller
public class TokenController {
    private final UserService userService;
    private final TokenService tokenService;

    public TokenController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

//TODO ssdasd
    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token, Model model) {
        Token vToken = tokenService.findByToken(token);
        User user = vToken.getUser();
        if (!user.isEnabled()) {
            if (vToken.getExpirationDate().isBefore(LocalDateTime.now())) {
                model.addAttribute("message", "Your verification token has expired");
            } else {
                userService.changeStatus(user);
                model.addAttribute("message", "Your account is successfully activated");
            }
        }
        model.addAttribute("message", "Your account is already activated");

        return "activation";
    }
}
