package pl.kamil.bak.project.auctionsite.user.domian;


import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.kamil.bak.project.auctionsite.user.domian.provider.UserSessionProvider;
import pl.kamil.bak.project.auctionsite.user.domian.service.UserService;
import pl.kamil.bak.project.auctionsite.user.dto.AddressDto;
import pl.kamil.bak.project.auctionsite.user.dto.LocationDto;
import pl.kamil.bak.project.auctionsite.user.dto.UserDto;
import pl.kamil.bak.project.auctionsite.user.model.*;


import javax.validation.Valid;

@Controller
public class UserController {
    private final UserService userService;
    private final UserSessionProvider getPrincipal;


    public UserController(UserService userService, UserSessionProvider getPrincipal) {
        this.userService = userService;
        this.getPrincipal = getPrincipal;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


    @GetMapping("/sing-up")
    public String register(@ModelAttribute UserDto userDto, @ModelAttribute LocationDto locationDto, @ModelAttribute AddressDto addressDto, Model model) {
        model.addAttribute("user", userDto);
        return "sing-up";
    }

    @GetMapping("/login")
    public String login() {
        User user = getPrincipal.getPrincipal();
        if (user != null) {
            return "mainPage";
        }
        return "login";
    }


    @PostMapping("/sing-up")
    public String save(@Valid UserDto userDto, BindingResult result, RedirectAttributes redirectAttributes, LocationDto location, AddressDto address) {
        if (userService.userEmailExists(userDto.getEmail())) {
            result.addError(new FieldError("userDto", "email", "Email address already exist"));
        }
        if (userService.userNameExists(userDto.getUserName())){
            result.addError(new FieldError("userDto", "userName", "A user with that name already exists"));
        }
        if (userDto.getPassword() != null && userDto.getConfirmPassword() != null) {
            if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
                result.addError(new FieldError("userDto", "confirmPassword", "Password must match"));
            }
        }
        if (result.hasErrors()) {
            return "sing-up";
        }
        redirectAttributes.addFlashAttribute("message", "Success! Your registration is now complete");
        userService.addUser(userDto, location, address);

        return "redirect:/login";
    }


}