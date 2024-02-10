package org.example.controllers;

import org.example.config.CustomUserDetailsManager;
import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected CustomUserDetailsManager customUserDetailsManager;

    @GetMapping("/mainpage")
    private String showMainPage() {
        return "/mainpage";
    }

    @GetMapping("/adminpage")
    public String showAdminPage() {
        return "/adminpage";
    }

    @GetMapping("/registration")
    public String createUser(ModelMap modelMap) {
        modelMap.addAttribute("user", new UserDTO());
        return "/registration";
    }

    @PostMapping("/registration")
    public String saveUser(UserDTO userDTO) {
        userService.saveUser(userDTO);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(ModelMap modelMap) {
        modelMap.addAttribute("login", new User());
        return "/login";
    }

    @PostMapping("/login")
    public String getLoginAndPassword(User user) {
        userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        return "/userpage";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "/logout";
    }

    @GetMapping("/userpage")
    public String showClientPage(ModelMap modelMap) {
        modelMap.put("user", userService.findCurrentUser());
        return "/userpage";
    }
}
