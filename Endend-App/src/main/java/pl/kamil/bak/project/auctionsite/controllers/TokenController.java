package pl.kamil.bak.project.auctionsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kamil.bak.project.auctionsite.user.domian.service.TokenService;
import pl.kamil.bak.project.auctionsite.user.domian.service.UserService;
import pl.kamil.bak.project.auctionsite.user.model.enums.Status;
import pl.kamil.bak.project.auctionsite.user.model.Token;
import pl.kamil.bak.project.auctionsite.user.model.User;

import java.sql.Timestamp;

@Controller
public class TokenController {
    private final UserService userService;
    private final TokenService tokenService;

    public TokenController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }


    @GetMapping("/activation")
    public String activation(@RequestParam("token") String token, Model model) {
        Token vToken = tokenService.findByToken(token);
        if (vToken == null) {
            model.addAttribute("message", "You verification token is invalid.");
        } else {
            User user = vToken.getUser();
            if (!user.isEnabled()) {
                Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                if (vToken.getExpiryDate().before(currentTimestamp)) {
                    model.addAttribute("message", "Your verification token has expired");
                } else {
                    user.setStatus(Status.ACTIVE.name());
                    userService.save(user);
                    model.addAttribute("message", "Your account is successfully activated");
                }
            } else {
                model.addAttribute("message", "Your account is already activated");
            }
        }

        return "activation";
    }
}
