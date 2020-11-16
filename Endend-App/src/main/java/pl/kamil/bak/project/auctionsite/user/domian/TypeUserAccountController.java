package pl.kamil.bak.project.auctionsite.user.domian;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.kamil.bak.project.auctionsite.user.domian.provider.UserSessionProvider;
import pl.kamil.bak.project.auctionsite.user.domian.service.UserService;
import pl.kamil.bak.project.auctionsite.user.model.enums.Type;

@Controller
public class TypeUserAccountController {
    private final UserService userService;
    private final UserSessionProvider getPrincipal;

    public TypeUserAccountController(UserService userService, UserSessionProvider getPrincipal) {
        this.userService = userService;
        this.getPrincipal = getPrincipal;
    }

    @GetMapping("/buy")
    public String buyAccountType(Model model){
        model.addAttribute("name", getPrincipal.getPrincipal().getUserName());
        model.addAttribute("typeAccount", getPrincipal.getPrincipal().getType());
        boolean premium = false;
        boolean normal = false;
        if (getPrincipal.getPrincipal().getType().equals(Type.PREMIUM.name())){
            premium = true;
        }else {
            normal = true;
        }
        model.addAttribute("premium", premium);
        model.addAttribute("normal", normal);
        return "buy-version-type";
    }
    @PostMapping("/buy/change")
    public String changeType(RedirectAttributes redirectAttributes){
        userService.buyPremium(getPrincipal.getPrincipal());
        redirectAttributes.addFlashAttribute("message", "Success! Your account version has been changed successfully");
        if (getPrincipal.getPrincipal().getType().equals(Type.NORMAL.name())){
            return "redirect:/buy";
        }
        return "payment-site";
    }

    @PostMapping("/pay")
    public String pay(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "Success! Your account version has been changed successfully");
        return "redirect:/buy";
    }
}
