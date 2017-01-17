package com.codeup.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

import static com.codeup.auth.BaseController.isLoggedIn;
import static com.codeup.auth.BaseController.loggedInUser;

@Controller
public class AuthenticationController {

    @Autowired
    Users userDao;

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String createUser(@Valid User user, Errors validation, Model model) {
        if (validation.hasErrors()) {
            model.addAttribute(validation.getAllErrors());
            model.addAttribute("user", user);
            return "users/register";
        }
        userDao.save(user);
        return "redirect:/cryptos";
    }

    @GetMapping("/users/{id}")
    public String showUser(@PathVariable long id, Model model){
        User user = userDao.findOne(id);
        model.addAttribute("user", user);
//        model.addAttribute("showEditControls", isLoggedIn() && loggedInUser().getId() == user.getId());
        return "/users/profile";
    }
}